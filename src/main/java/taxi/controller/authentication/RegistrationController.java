package taxi.controller.authentication;

import taxi.exception.RegistrationException;
import taxi.lib.Injector;
import taxi.model.Driver;
import taxi.service.RegistrationService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/registration")
public class RegistrationController extends HttpServlet {
    private static final String SESSION_ATTRIBUTE_DRIVER_ID = "driver_id";
    private static final Injector injector = Injector.getInstance("taxi");
    private final RegistrationService registrationService
            = (RegistrationService) injector.getInstance(RegistrationService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/authentication/registration.jsp")
                .forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String licenseNumber = req.getParameter("license_number");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat_password");
        Driver driver = new Driver(name, licenseNumber, login, password);
        try {
            Driver registeredDriver = registrationService.register(driver, repeatPassword);
            HttpSession session = req.getSession();
            session.setAttribute(SESSION_ATTRIBUTE_DRIVER_ID, registeredDriver.getId());
            resp.sendRedirect("/");
        } catch (RegistrationException e) {
            req.setAttribute("errorMsg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/authentication/registration.jsp")
                    .forward(req, resp);
        }
    }
}
