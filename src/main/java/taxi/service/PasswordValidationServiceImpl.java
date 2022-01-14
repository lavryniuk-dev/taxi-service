package taxi.service;

import taxi.exception.RegistrationException;
import taxi.lib.Service;

@Service
public class PasswordValidationServiceImpl implements PasswordValidationService {
    @Override
    public boolean validate(String password, String repeatPassword) throws RegistrationException {
        if (!password.equals(repeatPassword)) {
            throw new RegistrationException("Password mismatch");
        }
        return true;
    }
}
