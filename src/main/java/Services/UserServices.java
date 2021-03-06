package Services;

import DAO.UserDAO;
import Interfaces.UserInterface;
import Models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.json.JSONObject;
import utils.SHA256Encrypt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserServices implements UserInterface {
    public static final int TOKEN_TIMEOUT = 1000 * 60 * 60; //1 hour
//    private UserDAO usDAO = new UserDAO();

    public UserServices() {

    }

    public User CreateToken(String email, String pass, Map<String, Object> payload) {
        /*
            * Use signature to gen token return to user if login successful
            * JWT Doc           : https://gpcoder.com/5827-gioi-thieu-json-web-token-jwt/
            *                   : https://github.com/auth0/java-jwt
            * Gen RSA Key       : https://www.novixys.com/blog/how-to-generate-rsa-keys-java/
            * Load RSA from file: https://stackoverflow.com/questions/11410770/load-rsa-public-key-from-file
        */

        Key pubKey = null, priKey = null;
        String              sPubPath    = "./rsa.pub";
        String              sPriPath    = "./rsa.pri";
        File                pubFile     = new File(sPubPath);
        File                priFile     = new File(sPriPath);
        if (!pubFile.exists() || !priFile.exists()) {
            System.out.println("=========== CREATE NEW ============");
            try {
                Base64.Encoder      encoder = Base64.getEncoder();
                KeyPairGenerator    kpg     = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(512);
                KeyPair             kp      = kpg.generateKeyPair();
                                    pubKey  = kp.getPublic();
                                    priKey  = kp.getPrivate();
                OutputStream        pubOut  = new FileOutputStream(sPubPath);
                OutputStream        priOut  = new FileOutputStream(sPriPath);
                pubOut.write(pubKey.getEncoded());
                priOut.write(priKey.getEncoded());
//                System.out.println("Public key format: " + pubKey.getFormat());// X.509
//                System.out.println("Private key format: " + priKey.getFormat()); //PKCS#8
                pubOut.close();
                priOut.close();

            } catch (NoSuchAlgorithmException | IOException e) {
                throw new Error(e);
            }
        } else {
            System.out.println("=========== READ FILE ============");
            try {
                byte[]              pubBytes    = Files.readAllBytes(Paths.get(sPubPath));
                byte[]              priBytes    = Files.readAllBytes(Paths.get(sPriPath));
                X509EncodedKeySpec  pubSpec     = new X509EncodedKeySpec(pubBytes);
                PKCS8EncodedKeySpec priSpec     = new PKCS8EncodedKeySpec(priBytes);
                KeyFactory          kf          = KeyFactory.getInstance("RSA");
                                    pubKey      = kf.generatePublic(pubSpec);
                                    priKey      = kf.generatePrivate(priSpec);
            } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new Error(e);
            }
        }

        //Start create token
        Base64.Encoder      encoder     = Base64.getEncoder();
        Algorithm           algorithm   = Algorithm.RSA256((RSAPublicKey) pubKey, (RSAPrivateKey) priKey);
        String              token       = JWT.create()
                                        .withPayload(payload)
                                        .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_TIMEOUT))
                                        .sign(algorithm);
        User                x           = new User();
        x.setEmail(email);
        x.setPublicKey(encoder.encodeToString(pubKey.getEncoded()));
        x.setToken(token);
        return x;
    }

    @Override
    public JSONObject Login(String email, String pass) {
        JSONObject g = new UserDAO().getAUser(email);
        JSONObject resp = new JSONObject();
        if ((Boolean) g.get("isErr")) return g;
        User x = (User) g.get("msg");
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", email);
        if (new SHA256Encrypt().HashString(pass).equals(x.getPassword())) {
            x = CreateToken(email, pass, payload);
            resp.put("isErr", false);
            resp.put("msg", x);
        } else {
            resp.put("isErr", true);
            resp.put("msg", "Password is wrong");
        }
        return resp;
    }

    @Override
    public boolean createUser(String email, String pass, String username) {
        //Hash password
        User x = new User();
        x.setUsername(username);
        x.setEmail(email);
        x.setPassword(new SHA256Encrypt().HashString(pass));//hash password
        return new UserDAO().CreateUser(x);
    }
}
