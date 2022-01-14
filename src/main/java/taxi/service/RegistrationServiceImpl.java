package taxi.service;

import taxi.exception.RegistrationException;
import taxi.lib.Inject;
import taxi.lib.Service;
import taxi.model.Driver;
import taxi.service.DriverService;
import taxi.service.PasswordValidationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Inject
    private DriverService driverService;
    @Inject
    private PasswordValidationService passwordValidationService;

    @Override
    public Driver register(Driver driver, String repeatPassword) throws RegistrationException {
        if (driverService.findByLogin(driver.getLogin()).isPresent()) {
            throw new RegistrationException("Driver with this login is already exist");
        }
        if (passwordValidationService.validate(driver.getPassword(), repeatPassword)) {
            return driverService.create(driver);
        }
        return null;
    }
}
