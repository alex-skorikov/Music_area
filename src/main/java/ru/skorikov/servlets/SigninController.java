package ru.skorikov.servlets;

import ru.skorikov.UserService;
import ru.skorikov.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created on 18.06.18.
 */
public class SigninController extends HttpServlet {
    /**
     * Instance user servise.
     */
    private UserService service = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/LoginView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = service.isCreditional(login, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            session.setAttribute("role", user.getRole().getRole().toLowerCase());
            moveToMenu(req, resp, user);
        } else {
            req.setAttribute("error", "Creditional invalid.");
            doGet(req, resp);
        }
    }

    /**
     * Select menu for User by Role.
     *
     * @param req  requst.
     * @param resp response.
     * @param user User.
     * @throws IOException      exception
     * @throws ServletException exception
     */
    private void moveToMenu(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException, ServletException {
        if (user.getRole() == null) {
            resp.sendRedirect(String.format("%s/signin", req.getContextPath()));
        } else if (user.getRole().getRole().toLowerCase().equals("admin")) {
            req.setAttribute("users", service.findAll());
            req.getRequestDispatcher("/WEB-INF/views/AdminMenu.jsp").forward(req, resp);
        } else if (user.getRole().getRole().toLowerCase().equals("moderator")) {
            req.setAttribute("users", service.findAll());
            req.getRequestDispatcher("/WEB-INF/views/ModeratorMenu.jsp").forward(req, resp);
        } else if (user.getRole().getRole().toLowerCase().equals("user")) {
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/UserMenu.jsp").forward(req, resp);
        }
    }
}
