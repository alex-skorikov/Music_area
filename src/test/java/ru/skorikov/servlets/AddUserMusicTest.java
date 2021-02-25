package ru.skorikov.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.MusicType;
import ru.skorikov.User;
import ru.skorikov.UserMusic;
import ru.skorikov.UserService;
import ru.skorikov.persistent.dao.MusicTypeStore;
import ru.skorikov.persistent.dao.UserMusicStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

/**
 * Test add user music servlet.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddUserMusicTest {
    /**
     * User-Music store instance.
     */
    @Mock
    private UserMusicStore userMusicStore;
    /**
     * Music Store instance for test.
     */
    @Mock
    private MusicTypeStore musicTypeStore;
    /**
     * Servise for controller.
     */
    @Mock
    private UserService service;
    /**
     * Test controller.
     */
    @InjectMocks
    private AddUserMusic addUserMusic = new AddUserMusic();
    /**
     * Request for test.
     */
    @Mock
    private HttpServletRequest req;
    /**
     * Response for test.
     */
    @Mock
    private HttpServletResponse resp;
    /**
     * Dispatcher for test.
     */
    @Mock
    private RequestDispatcher dispatcher;

    /**
     * Test method doGet.
     * @throws ServletException exception.
     * @throws IOException exception.
     */
    @Test
    public void doGet() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn(String.valueOf(1));
        when(musicTypeStore.findAll()).thenReturn(new ArrayList<>());
        when(req.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);
        when(service.findById(any(Integer.class))).thenReturn(new User());
        addUserMusic.doGet(req, resp);

        verify(dispatcher, times(1)).forward(req, resp);
    }

    /**
     * Test method doPost.
     * @throws ServletException exception.
     * @throws IOException exception.
     */
    @Test
    public void doPost() throws ServletException, IOException {
        MusicType musicType = new MusicType();
        musicType.setId(1);
        musicType.setType("type");
        musicType.setDescription("desc");
        User user = new User();
        user.setName("Name");
        when(req.getRequestDispatcher(any(String.class))).thenReturn(dispatcher);
        when(req.getParameter("id")).thenReturn(String.valueOf(1));
        when(service.findById(1)).thenReturn(user);
        when(musicTypeStore.findByType(any(String.class))).thenReturn(musicType);
        when(userMusicStore.add(any(UserMusic.class))).thenReturn(true);

        addUserMusic.doPost(req, resp);
        verify(req, times(1)).getRequestDispatcher(any(String.class));
        verify(req, never()).getSession();
        verify(dispatcher).forward(req, resp);
    }
}