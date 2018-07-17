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
import java.util.Map;


/**
 * Create User Servlet.
 */
public class UserCreateController extends HttpServlet {
    /**
     * Instance ValidateService.
     */
    private UserService service = UserService.getInstance();
    /**
     * Instance RoleStore.
     */
    private RolesStore rolesStore = RolesStore.getInstanse();
    /**
     * Instance Address Store.
     */
    private AddressStore addressStore = AddressStore.getInstanse();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("role", rolesStore.findAll());
        req.setAttribute("country", addressStore.getCountries());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/CreateUser.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> map = req.getParameterMap();

        User user = new User();
        user.setName(map.get("name")[0]);
        user.setLogin(map.get("login")[0]);
        user.setEmail(map.get("email")[0]);
        user.setPassword(map.get("password")[0]);
        Role role = new Role();
        role.setRole(map.get("role")[0]);
        role.setId(rolesStore.findRoleIDByRoleName(role.getRole()));
        user.setRole(role);
        Address address = new Address();
        address.setCountry(map.get("country")[0]);
        address.setSity(map.get("sity")[0]);
        address.setStreet(map.get("street")[0]);
        address.setId(addressStore.addAddressAndGetID(address));
        user.setAddress(address);

        service.add(user);
        resp.sendRedirect(String.format("%s/list", req.getContextPath()));
    }
}
