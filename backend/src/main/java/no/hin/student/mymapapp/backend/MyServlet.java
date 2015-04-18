package no.hin.student.mymapapp.backend;

import java.io.IOException;
import javax.servlet.http.*;


public class MyServlet extends HttpServlet {
    Database database = new Database();


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println(getData());
        doPost(req,resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String device = req.getParameter("device");
        String latitude = req.getParameter("latitude");
        String longitude = req.getParameter("longitude");

        resp.setContentType("text/plain");
        //resp.getWriter().println(device + " " + latitude + " " + longitude);
        if (device == null || latitude == null || longitude == null) {
            resp.getWriter().println("Please use all required parameters: device, latitude and longitude");
        }
        resp.getWriter().println("Writing data");
        database.writeData(device, latitude, longitude);
    }

    private String getData() {
        return database.getData();
    }
}
