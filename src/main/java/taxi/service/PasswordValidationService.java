package taxi.service;

import taxi.exception.RegistrationException;

public interface PasswordValidationService {
    boolean validate(String password, String repeatPassword) throws RegistrationException;
}
