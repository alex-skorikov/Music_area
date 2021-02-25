package ru.skorikov.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Test LogOut controller class.
 */
@RunWith(MockitoJUnitRunner.class)
public class LogOutTest {
    /**
     * Test logOut.
     */
    private LogOut logOut = new LogOut();

    /**
     * Test method doGet.
     * @throws IOException exception
     */
    @Test
    public void doGet() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        logOut.doGet(request, response);

        verify(response, times(1)).sendRedirect("/");

    }
}