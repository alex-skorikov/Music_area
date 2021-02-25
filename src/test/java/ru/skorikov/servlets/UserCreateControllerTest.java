package ru.skorikov.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.Address;
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
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

/**
 * User create controller test.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserCreateControllerTest {

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
    private UserCreateController controller = new UserCreateController();

    /**
     * Testmethod doGet.
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
        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(dispatcher, times(1)).forward(request, response);
        verify(request, never()).getSession();
    }

    /**
     * Test method doPost.
     *
     * @throws IOException exception.
     */
    @Test
    public void doPost() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Map<String, String[]> map = new HashMap<>();
        map.put("name", new String[]{""});
        map.put("login", new String[]{""});
        map.put("email", new String[]{""});
        map.put("password", new String[]{""});
        map.put("role", new String[]{""});
        map.put("country", new String[]{""});
        map.put("sity", new String[]{""});
        map.put("street", new String[]{""});
        when(request.getParameterMap()).thenReturn(map);
        when(rolesStore.findRoleIDByRoleName(any(String.class))).thenReturn(1);
        when(addressStore.addAddressAndGetID(any(Address.class))).thenReturn(1);
        when(service.add(any(User.class))).thenReturn(true);

        controller.doPost(request, response);

        verify(request, times(1)).getParameterMap();
        verify(response, times(1)).sendRedirect(any(String.class));
    }
}