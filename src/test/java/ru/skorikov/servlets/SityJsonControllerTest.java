package ru.skorikov.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.persistent.dao.AddressStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

/**
 * Test Sity json contrller.
 */
@RunWith(MockitoJUnitRunner.class)
public class SityJsonControllerTest {
    /**
     * Country Sity store instance.
     */
    @Mock
    private AddressStore addressStore = mock(AddressStore.class);
    /**
     * Test controller.
     */
    @InjectMocks
    private SityJsonController controller = new SityJsonController();

    /**
     * Test method doGet.
     *
     * @throws IOException      exception
     * @throws ServletException exception
     */
    @Test
    public void doGet() throws IOException, ServletException {
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(response.getOutputStream()).thenReturn(outputStream);
        when(request.getParameter(any(String.class))).thenReturn("");
        when(addressStore.getSities(any(String.class))).thenReturn(new CopyOnWriteArrayList<>());

        controller.doGet(request, response);

        verify(dispatcher, never()).forward(request, response);
    }
}