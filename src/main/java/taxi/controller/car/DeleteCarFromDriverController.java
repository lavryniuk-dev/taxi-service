package taxi.controller.car;

import taxi.lib.Injector;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.service.CarService;
import taxi.service.DriverService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/cars/my/delete")
public class DeleteCarFromDriverController extends HttpServlet {
    private static final String SESSION_ATTRIBUTE_DRIVER_ID = "driver_id";
    private static final Injector injector = Injector.getInstance("taxi");
    private final CarService carService = (CarService) injector.getInstance(CarService.class);
    private final DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long driverId = (Long) req.getSession().getAttribute(SESSION_ATTRIBUTE_DRIVER_ID);
        Long carId = Long.valueOf(req.getParameter("id"));
        Car car = carService.get(carId);
        Driver driver = driverService.get(driverId);
        carService.removeDriverFromCar(driver, car);
        resp.sendRedirect("/cars/my");
    }
}
