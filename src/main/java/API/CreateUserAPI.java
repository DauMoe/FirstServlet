package API;

import Services.UserServices;
import org.json.JSONException;
import org.json.JSONObject;
import utils.StreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/api/modify_user"})
public class CreateUserAPI extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //New user
        req.setCharacterEncoding("UTF-8");
        String sReq = StreamReader.parse2String(req.getReader());
        JSONObject x = new JSONObject(sReq);
        UserServices g = new UserServices();

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            String email    = x.get("email").toString();
            String pass     = x.get("pass").toString();
            String username = x.get("username").toString();
            resp.setStatus(HttpServletResponse.SC_OK);
            boolean res     = g.createUser(email, pass, username);
            JSONObject jResp = new JSONObject();
            if (res) {
                jResp.put("msg", "create user successful");
            } else {
                jResp.put("msg", "create user failed");
            }
            out.println(jResp.toString());
            out.flush();
            out.close();
        } catch (JSONException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{msg: " + e.getMessage() + "}");
            out.flush();
            out.close();
            return;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Edit user
        req.setCharacterEncoding("UTF-8");
        String sReq = StreamReader.parse2String(req.getReader());
        JSONObject x = new JSONObject(sReq);
        PrintWriter out = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.setStatus(HttpServletResponse.SC_OK);
            //Handle here

            out.flush();
            out.close();
        } catch (JSONException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{msg: " + e.getMessage() + "}");
            out.flush();
            out.close();
            return;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Lock user
    }
}
