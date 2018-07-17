package ru.skorikov.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Test user controller class.
 */
@RunWith(MockitoJUnitRunner.class)
public class UsersControllerTest {
    /**
     * Instance ValidateService.
     */
    @Mock
    private UserService service = mock(UserService.class);
    /**
     * Test controller.
     */
    @InjectMocks
    private UsersController controller = new UsersController();

    /**
     * Test method doGet.
     *
     * @throws IOException      exception
     * @throws ServletException exception.
     */
    @Test
    public void doGet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(service.findAll()).thenReturn(new CopyOnWriteArrayList<>());
        when(request.getRequestDispatcher("/WEB-INF/views/AdminMenu.jsp")).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(dispatcher, times(1)).forward(request, response);
    }

    /**
     * Test method doPost.
     *
     * @throws IOException exception
     */
    @Test
    public void doPost() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(service.delete(any(Integer.class))).thenReturn(true);

        controller.doPost(request, response);

        verify(response, times(1)).sendRedirect(any(String.class));
    }
}