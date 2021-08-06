package API;

import DAO.UserDAO;
import Models.User;
import Services.UserServices;
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
        boolean f = g.createUser(x.get("email").toString(), x.get("pass").toString(), x.get("username").toString());
        PrintWriter out = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JSONObject jResp = new JSONObject();
        if (f) {
            jResp.put("msg", "create user successful");
        } else {
            jResp.put("msg", "create user failed");
        }
        out.println(jResp.toString());
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Edit user
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Lock user
    }
}
