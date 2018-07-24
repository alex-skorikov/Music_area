package ru.skorikov.servlets;

import com.google.gson.JsonObject;
import ru.skorikov.persistent.dao.AddressStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * Created on 27.06.18.
 */
public class SityJsonController extends HttpServlet {
    /**
     * Country Sity store instance.
     */
    private AddressStore addressStore = AddressStore.getInstanse();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        ArrayList<String> sities = addressStore.getSities(req.getParameter("country"));
        JsonObject object = new JsonObject();
        for (int i = 0; i < sities.size(); i++) {
            object.addProperty(String.valueOf(i), sities.get(i));
        }
        writer.append(object.toString());
        writer.flush();
    }
}
