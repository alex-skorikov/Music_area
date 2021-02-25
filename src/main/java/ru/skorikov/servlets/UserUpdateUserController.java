package ru.skorikov.servlets;

import ru.skorikov.Address;
import ru.skorikov.User;
import ru.skorikov.UserService;
import ru.skorikov.persistent.dao.AddressStore;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 20.06.18.
 */
public class UserUpdateUserController extends HttpServlet {
    /**
     * Instance Address Store.
     */
    private AddressStore addressStore = AddressStore.getInstanse();
    /**
     * Instance ValidateService.
     */
    private UserService service = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("user", service.findById(Integer.valueOf(req.getParameter("id"))));
        req.setAttribute("country", addressStore.getCountries());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/UpdateUserForUser.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = service.findById(Integer.valueOf(req.getParameter("id")));

        user.setName(req.getParameter("name"));
        user.setLogin(req.getParameter("login"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        Address address = user.getAddress();
        address.setCountry(req.getParameter("country"));
        address.setSity(req.getParameter("sity"));
        address.setStreet(req.getParameter("street"));
        addressStore.update(user.getAddress().getId(), address);
        user.setAddress(address);

        if (service.update(user)) {
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/UserMenu.jsp").forward(req, resp);

        }
    }
}
