package ru.skorikov.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.MusicType;
import ru.skorikov.UserService;
import ru.skorikov.persistent.dao.MusicTypeStore;

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
 * Test controller Create music type.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateMusicTypeTest {
    /**
     * Service for test.
     */
    @Mock
    private UserService service = mock(UserService.class);
    /**
     * Store for test.
     */
    @Mock
    private MusicTypeStore store = mock(MusicTypeStore.class);
    /**
     * Test controller.
     */
    @InjectMocks
    private CreateMusicType createMusicType = new CreateMusicType();

    /**
     * Nest method doGet.
     *
     * @throws ServletException exception.
     * @throws IOException      exception
     */
    @Test
    public void doGet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);
        createMusicType.doGet(request, response);
        verify(dispatcher, times(1)).forward(request, response);
    }

    /**
     * Test method doPost.
     *
     * @throws ServletException exception.
     * @throws IOException      exception
     */
    @Test
    public void doPost() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("type")).thenReturn("type");
        when(request.getParameter("description")).thenReturn("description");
        when(store.add(any(MusicType.class))).thenReturn(true);
        when(request.getParameter("user")).thenReturn("user");
        when(store.findAll()).thenReturn(new ArrayList<>());
        when(request.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);

        createMusicType.doPost(request, response);

        verify(dispatcher, times(1)).forward(request, response);
        verify(request, never()).getSession();
    }
}