package ru.skorikov.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.Role;
import ru.skorikov.User;
import ru.skorikov.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Test Signin controller.
 */
@RunWith(MockitoJUnitRunner.class)
public class SigninControllerTest {
    /**
     * Instance ValidateService.
     */
    @Mock
    private UserService service = mock(UserService.class);
    /**
     * Test controller.
     */
    @InjectMocks
    private SigninController controller = new SigninController();

    /**
     * Test doGet method.
     *
     * @throws ServletException exception
     * @throws IOException      exception
     */
    @Test
    public void doGet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(any(String.class));
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }

    /**
     * Test doPost method where userRole == user.
     *
     * @throws ServletException exception
     * @throws IOException      exception
     */
    @Test
    public void doPost() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);
        User user = new User();
        user.setName("name");
        user.setLogin("login");
        user.setPassword("password");
        Role role = new Role();
        role.setRole("user");
        user.setRole(role);
        when(request.getParameter("login")).thenReturn(user.getLogin());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        when(service.isCreditional(any(String.class), any(String.class))).thenReturn(user);

        controller.doPost(request, response);
        verify(request.getRequestDispatcher("/WEB-INF/views/UserMenu.jsp"),
                times(1)).forward(request, response);
        verify(dispatcher, times(1)).forward(request, response);
        verify(request, times(1)).getSession();
    }

    /**
     * Test doPost method where User == admin.
     *
     * @throws ServletException exception
     * @throws IOException      exception
     */
    @Test
    public void doPost2() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);
        User user = new User();
        user.setName("name");
        user.setLogin("login");
        user.setPassword("password");
        Role role = new Role();
        role.setRole("admin");
        user.setRole(role);
        when(request.getParameter("login")).thenReturn(user.getLogin());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        when(service.isCreditional(any(String.class), any(String.class))).thenReturn(user);
        when(service.findAll()).thenReturn(new CopyOnWriteArrayList<>());

        controller.doPost(request, response);
        verify(request.getRequestDispatcher("/WEB-INF/views/AdminMenu.jsp"),
                times(1)).forward(request, response);
        verify(dispatcher, times(1)).forward(request, response);
        verify(request, times(1)).getSession();
    }

    /**
     * Test doPost method where userRole == moderator.
     *
     * @throws ServletException exception
     * @throws IOException      exception
     */
    @Test
    public void doPost3() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);
        User user = new User();
        user.setName("name");
        user.setLogin("login");
        user.setPassword("password");
        Role role = new Role();
        role.setRole("moderator");
        user.setRole(role);
        when(request.getParameter("login")).thenReturn(user.getLogin());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        when(service.isCreditional(any(String.class), any(String.class))).thenReturn(user);
        when(service.findAll()).thenReturn(new CopyOnWriteArrayList<>());

        controller.doPost(request, response);
        verify(request.getRequestDispatcher("/WEB-INF/views/ModeratorMenu.jsp"),
                times(1)).forward(request, response);
        verify(dispatcher, times(1)).forward(request, response);
        verify(request, times(1)).getSession();

    }

    /**
     * Test doPost method where user == null.
     *
     * @throws ServletException exception
     * @throws IOException      exception
     */
    @Test
    public void doPost4() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);
        User user = new User();
        user.setName("name");
        user.setLogin("login");
        user.setPassword("password");
        when(request.getParameter("login")).thenReturn(user.getLogin());
        when(request.getParameter("password")).thenReturn(user.getPassword());
        when(service.isCreditional(any(String.class), any(String.class))).thenReturn(null);

        controller.doPost(request, response);

        verify(request, times(1)).setAttribute("error", "Creditional invalid.");

    }
}