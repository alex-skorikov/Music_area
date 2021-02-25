package ru.skorikov.servlets;

import ru.skorikov.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Update music controller.
 */
public class MusicUpdateController extends HttpServlet {
    /**
     * Instance ValidateService.
     */
    private UserService service = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", service.findById(Integer.valueOf(req.getParameter("id"))));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/UpdateMusic.jsp");
        requestDispatcher.forward(req, resp);
    }
}
