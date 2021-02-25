package ru.skorikov.servlets;

import ru.skorikov.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Moderator controller.
 */
public class ModeratorController extends HttpServlet {
    /**
     * Instance User Service.
     */
    private UserService service = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", service.findAll());
        req.getRequestDispatcher("/WEB-INF/views/ModeratorMenu.jsp").forward(req, resp);
    }
}
