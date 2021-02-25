package ru.skorikov.servlets;

import ru.skorikov.Address;
import ru.skorikov.Role;
import ru.skorikov.User;
import ru.skorikov.UserService;
import ru.skorikov.persistent.dao.AddressStore;
import ru.skorikov.persistent.dao.RolesStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Update User Servlet.
 */
public class UserUpdateController extends HttpServlet {
    /**
     * Instance Address Store.
     */
    private AddressStore addressStore = AddressStore.getInstanse();
    /**
     * Instance RoleStore.
     */
    private RolesStore rolesStore = RolesStore.getInstanse();
    /**
     * Instance ValidateService.
     */
    private UserService service = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", rolesStore.findAll());
        req.setAttribute("country", addressStore.getCountries());
        req.setAttribute("user", service.findById(Integer.valueOf(req.getParameter("id"))));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/UpdateUserForRoot.jsp");
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

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
        Role role = user.getRole();
        role.setId(rolesStore.findRoleIDByRoleName(req.getParameter("role")));
        role.setRole(rolesStore.findById(role.getId()).getRole());
        user.setAddress(address);
        user.setRole(role);

        if (service.update(user)) {
            resp.sendRedirect(String.format("%s/list", req.getContextPath()));
        }
    }
}
