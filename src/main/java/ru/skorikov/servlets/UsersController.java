package ru.skorikov.servlets;

import ru.skorikov.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by AlexSkorikov on 20.05.18.
 */
public class UsersController extends HttpServlet {
    /**
     * Instance Validateservice.
     */
    private UserService service = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("users", service.findAll());
        req.getRequestDispatcher("/WEB-INF/views/AdminMenu.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));

        service.delete(id);

        resp.sendRedirect(String.format("%s/list", req.getContextPath()));
    }
}
