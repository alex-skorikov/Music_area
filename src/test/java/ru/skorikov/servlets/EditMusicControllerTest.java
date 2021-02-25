package ru.skorikov.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.MusicType;
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

/**
 * Test Edit music controller.
 */
@RunWith(MockitoJUnitRunner.class)
public class EditMusicControllerTest {
    /**
     * Music store instance.
     */
    @Mock
    private MusicTypeStore musicTypeStore = mock(MusicTypeStore.class);
    /**
     * Test controller.
     */
    @InjectMocks
    private EditMusicController controller = new EditMusicController();

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
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(musicTypeStore.findById(1)).thenReturn(new MusicType());
        when(request.getRequestDispatcher("/WEB-INF/views/EditOneTypeMusic.jsp")).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(dispatcher, times(1)).forward(request, response);
    }

    /**
     * Test method doPost.
     *
     * @throws ServletException exception
     * @throws IOException      exception
     */
    @Test
    public void doPost() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/views/EditMusicType.jsp")).thenReturn(dispatcher);
        when(request.getParameter("id")).thenReturn(String.valueOf(1));
        when(musicTypeStore.findById(1)).thenReturn(new MusicType());
        when(request.getParameter("type")).thenReturn("");
        when(request.getParameter("description")).thenReturn("");
        when(musicTypeStore.update(any(Integer.class), any(MusicType.class))).thenReturn(true);
        when(musicTypeStore.findAll()).thenReturn(new ArrayList<>());

        controller.doPost(request, response);

        verify(dispatcher, times(1)).forward(request, response);
    }
}