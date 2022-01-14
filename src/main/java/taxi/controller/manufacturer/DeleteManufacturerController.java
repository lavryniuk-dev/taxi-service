package taxi.controller.manufacturer;

import taxi.lib.Injector;
import taxi.service.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/manufacturers/delete")
public class DeleteManufacturerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("taxi");
    private final ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        manufacturerService.delete(id);
        resp.sendRedirect("/manufacturers");
    }
}
