package taxi.controller.manufacturer;

import taxi.lib.Injector;
import taxi.model.Manufacturer;
import taxi.service.ManufacturerService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/manufacturers/add")
public class AddManufacturerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("taxi");
    private final ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        String country = req.getParameter("country");
        manufacturerService.create(new Manufacturer(name, country));
        resp.sendRedirect("/manufacturers");
    }
}
