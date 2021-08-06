package API;

import org.json.JSONObject;
import utils.StreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/api/all_user"})
public class GetUserAPI extends HttpServlet {
    /*
    *   ========================= Tutorial =====================
    * WebServlet    : https://mail.codejava.net/java-ee/servlet/webservlet-annotation-examples
    * Excel         : https://www.javatpoint.com/how-to-read-excel-file-in-java
    * JSONObject    : https://github.com/stleary/JSON-java
    * */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);
        out.println("<h1>Hello</h1>");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding("UTF-8");
//        String sReq = StreamReader.parse2String(req.getReader());

        PrintWriter out = resp.getWriter();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        JSONObject jResp = new JSONObject();
        jResp.put("name", "moe");
        jResp.put("age", "10");
        jResp.put("ssid", "QR-123-1441-4521");
        out.println(jResp.toString());
        out.flush();

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
