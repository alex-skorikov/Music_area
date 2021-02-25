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

/**
 * User update user controller test.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserUpdateUserControllerTest {

    /**
     * Instance ValidateService.
     */
    @Mock
    private UserService service = mock(UserService.class);

    /**
     * Instance Address Store.
     */
    @Mock
    private AddressStore addressStore = mock(AddressStore.class);
    /**
     * Test controller.
     */
    @InjectMocks
    private UserUpdateUserController controller = new UserUpdateUserController();

    /**
     * Test method doGet.
     *
     * @throws ServletException exception.
     * @throws IOException      exception.
     */
    @Test
    public void doGet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(service.findById(1)).thenReturn(new User());
        when(addressStore.getCountries()).thenReturn(new ArrayList<>());

        controller.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/UpdateUserForUser.jsp");
        verify(dispatcher, times(1)).forward(request, response);
    }

    /**
     * Test method doPost.
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Test
    public void doPost() throws ServletException, IOException {
        User user = new User();
        Address address = new Address();
        user.setAddress(address);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);
        when(request.getParameter(any(String.class))).thenReturn(String.valueOf(1));
        when(service.findById(any(Integer.class))).thenReturn(user);
        when(addressStore.update(any(Integer.class), any(Address.class))).thenReturn(true);
        when(service.update(any(User.class))).thenReturn(true);

        controller.doPost(request, response);

        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/UserMenu.jsp");
        verify(dispatcher, times(1)).forward(request, response);

    }
}