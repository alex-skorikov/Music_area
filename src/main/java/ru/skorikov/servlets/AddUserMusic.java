package ru.skorikov.servlets;

import ru.skorikov.MusicType;
import ru.skorikov.User;
import ru.skorikov.UserMusic;
import ru.skorikov.UserService;
import ru.skorikov.persistent.dao.MusicTypeStore;
import ru.skorikov.persistent.dao.UserMusicStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Add user music controller.
 */

public class AddUserMusic extends HttpServlet {
    /**
     * User service instance.
     */
    private UserService service = UserService.getInstance();
    /**
     * Music Store instance.
     */
    private MusicTypeStore musicTypeStore = MusicTypeStore.getInstanse();
    /**
     * User-Music store instance.
     */
    private UserMusicStore userMusicStore = UserMusicStore.getInstanse();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", service.findById(Integer.valueOf(req.getParameter("id"))));
        req.setAttribute("music", musicTypeStore.findAll());
        req.getRequestDispatcher("/WEB-INF/views/AddUserMusic.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = service.findById(Integer.valueOf(req.getParameter("id")));
        MusicType musicType = musicTypeStore.findByType(req.getParameter("music"));
        UserMusic userMusic = new UserMusic();
        userMusic.setUserId(user.getId());
        userMusic.setMusicId(musicType.getId());
        userMusicStore.add(userMusic);
        user.getMusicType().add(musicType);
        req.setAttribute("user", user);
        req.setAttribute("users", service.findAll());
        req.getRequestDispatcher("/WEB-INF/views/UserMenu.jsp").forward(req, resp);
    }
}
