package ru.skorikov.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.Address;
import ru.skorikov.Role;
import ru.skorikov.User;
import ru.skorikov.UserService;
import ru.skorikov.persistent.dao.AddressStore;
import ru.skorikov.persistent.dao.RolesStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

/**
 * User update controkker test.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserUpdateControllerTest {

    /**
     * Instance ValidateService.
     */
    @Mock
    private UserService service = mock(UserService.class);
    /**
     * Instance RoleStore.
     */
    @Mock
    private RolesStore rolesStore = mock(RolesStore.class);
    /**
     * Instance Address Store.
     */
    @Mock
    private AddressStore addressStore = mock(AddressStore.class);
    /**
     * Test controller.
     */
    @InjectMocks
    private UserUpdateController controller = new UserUpdateController();

    /**
     * Test method doGet.
     *
     * @throws ServletException exception
     * @throws IOException      exception
     */
    @Test
    public void doGet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(rolesStore.findAll()).thenReturn(new ArrayList<>());
        when(addressStore.getCountries()).thenReturn(new ArrayList<>());
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(service.findById(any(Integer.class))).thenReturn(new User());
        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(request, never()).getSession();
        verify(dispatcher, times(1)).forward(request, response);
    }

    /**
     * Test method doPost.
     *
     * @throws IOException      exception
     * @throws ServletException exception
     */
    @Test
    public void doPost() throws IOException, ServletException {
        User user = new User();
        Role role = new Role();
        role.setId(1);
        role.setRole("");
        Address address = new Address();
        address.setId(1);
        address.setCountry("");
        address.setSity("");
        address.setStreet("");
        user.setRole(role);
        user.setAddress(address);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter(any(String.class))).thenReturn(String.valueOf(1));
        when(service.findById(any(Integer.class))).thenReturn(user);
        when(addressStore.update(any(Integer.class), any(Address.class))).thenReturn(true);
        when(rolesStore.findRoleIDByRoleName(any(String.class))).thenReturn(1);
        when(rolesStore.findById(any(Integer.class))).thenReturn(new Role());
        when(service.update(any(User.class))).thenReturn(true);

        controller.doPost(request, response);

        verify(request, never()).getSession();
        verify(dispatcher, never()).forward(request, response);
        verify(response, times(1)).sendRedirect(any(String.class));
    }
}