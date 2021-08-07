package API;

import Models.User;
import Services.UserServices;
import org.json.JSONException;
import org.json.JSONObject;
import utils.StreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/api/login"})
public class LoginAPI extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String      sReq    = StreamReader.parse2String(req.getReader());
        JSONObject  jReq    = new JSONObject(sReq);
        PrintWriter out     = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            String  name    = jReq.getString("email");
            String  pass    = jReq.getString("pass");
            JSONObject x    = new UserServices().Login(name, pass);
            if ((Boolean) x.get("isErr")) {
                out.println(x.toString());
            } else {
                JSONObject  jResp    = new JSONObject();
                User        f        = (User) x.get("msg");
                jResp.put("token", f.getToken());
                jResp.put("pub", f.getPublicKey());
                jResp.put("email", f.getEmail());
                jResp.put("username", f.getUsername());
                jResp.put("isErr", false);
                out.println(jResp.toString());
            }
            out.flush();
            out.close();
        } catch (JSONException e) {
            out.println("{\"msg\": \""+e.getMessage()+"\", \"isErr\": \"true\"}\"}");
            out.flush();
            out.close();
        }
    }
}
