package Models;

public class User {
    private String username, email, password, token, signature, secreteKey, publicKey;
    private int id;

    public User() {
    }

    public User(String username, String email, String password, String token, String signature, String secreteKey, String publicKey, int id) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
        this.signature = signature;
        this.secreteKey = secreteKey;
        this.publicKey = publicKey;
        this.id = id;
    }

    public String getSecreteKey() {
        return secreteKey;
    }

    public void setSecreteKey(String secreteKey) {
        this.secreteKey = secreteKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
