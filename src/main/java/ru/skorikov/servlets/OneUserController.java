package ru.skorikov.servlets;

import ru.skorikov.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 25.06.18.
 */
public class OneUserController extends HttpServlet {
    /**
     * Instance User Service.
     */
    private UserService service = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", service.findById(Integer.valueOf(req.getParameter("id"))));
        req.getRequestDispatcher("/WEB-INF/views/UserMenu.jsp").forward(req, resp);
    }
}
