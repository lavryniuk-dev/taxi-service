package taxi.controller.car;

import taxi.lib.Injector;
import taxi.model.Car;
import taxi.service.CarService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/cars/my")
public class GetMyCurrentCarsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("taxi");
    private final CarService carService
            = (CarService) injector.getInstance(CarService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long driverId = (Long) session.getAttribute("driver_id");
        List<Car> myCars = carService.getAllByDriver(driverId);
        List<Car> allCars = carService.getAll();
        req.setAttribute("my_cars", myCars);
        req.setAttribute("all_cars", allCars);
        req.getRequestDispatcher("/WEB-INF/views/cars/my.jsp").forward(req, resp);
    }
}
