package Controller;

import Models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//List urls use this controller
@WebServlet(urlPatterns = {"/home"})

public class HomeCtrl extends HttpServlet {
    private static final long serialVersionUID = 2686801510274002166L;

    public HomeCtrl() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        User x = new User();
        x.setUsername("Dau Moe");
        req.setAttribute("model", x);
        RequestDispatcher rd = req.getRequestDispatcher("Views/homepage.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
