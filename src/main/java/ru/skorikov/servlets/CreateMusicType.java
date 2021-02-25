package ru.skorikov.servlets;

import ru.skorikov.MusicType;
import ru.skorikov.UserService;
import ru.skorikov.persistent.dao.MusicTypeStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create music type controller.
 */
public class CreateMusicType extends HttpServlet {
    /**
     * Instance user servise.
     */
    private UserService service = UserService.getInstance();
    /**
     * Music store instance.
     */
    private MusicTypeStore store = MusicTypeStore.getInstanse();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/CreateMusicType.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MusicType musicType = new MusicType();
        musicType.setType(req.getParameter("type"));
        musicType.setDescription(req.getParameter("description"));
        store.add(musicType);
        req.setAttribute("user", req.getParameter("user"));
        req.setAttribute("music", store.findAll());
        req.getRequestDispatcher("/WEB-INF/views/EditMusicType.jsp").forward(req, resp);
    }
}
