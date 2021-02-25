package ru.skorikov.servlets;

import ru.skorikov.MusicType;
import ru.skorikov.persistent.dao.MusicTypeStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Edit music controller.
 */
public class EditMusicController extends HttpServlet {
    /**
     * Music store instance.
     */
    private MusicTypeStore musicTypeStore = MusicTypeStore.getInstanse();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("music", musicTypeStore.findById(Integer.valueOf(req.getParameter("id"))));
        req.getRequestDispatcher("/WEB-INF/views/EditOneTypeMusic.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MusicType musicType = musicTypeStore.findById(Integer.valueOf(req.getParameter("id")));
        musicType.setType(req.getParameter("type"));
        musicType.setDescription(req.getParameter("description"));
        musicTypeStore.update(musicType.getId(), musicType);
        req.setAttribute("music", musicTypeStore.findAll());
        req.getRequestDispatcher("/WEB-INF/views/EditMusicType.jsp").forward(req, resp);
    }
}
