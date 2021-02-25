package ru.skorikov.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.skorikov.User;
import ru.skorikov.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


/**
 * Test Music update controller class.
 */
@RunWith(MockitoJUnitRunner.class)
public class MusicUpdateControllerTest {
    /**
     * Instance ValidateService.
     */
    @Mock
    private UserService service = mock(UserService.class);
    /**
     * Controller test.
     */
    @InjectMocks
    private MusicUpdateController controller = new MusicUpdateController();

    /**
     * Test method doGet.
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Test
    public void doGet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(service.findById(1)).thenReturn(new User());

        controller.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/UpdateMusic.jsp");
        verify(dispatcher, times(1)).forward(request, response);

    }
}