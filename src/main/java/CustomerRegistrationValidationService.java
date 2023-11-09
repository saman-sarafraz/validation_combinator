import java.time.LocalDate;
import java.time.Period;
import java.util.function.Function;

public interface CustomerRegistrationValidationService
    extends Function<Customer, CustomerRegistrationValidationService.ValidationResult> {

    static CustomerRegistrationValidationService isEmailValid() {
        return customer -> {
            System.out.println("Email validation is running");
            return customer.getEmail().contains("@") ? ValidationResult.SUCCESS
                : ValidationResult.EMAIL_IS_NOT_VALID;
        };
    }

    static CustomerRegistrationValidationService isPhoneNumberValid() {
        return customer -> customer.getPhoneNumber().startsWith("+0") ? ValidationResult.SUCCESS :
                ValidationResult.PHONE_NUMBER_IS_NOT_VALID;
    }

    static CustomerRegistrationValidationService isAdult() {
        return customer -> Period.between(customer.getDob(), LocalDate.now()).getYears() > 16 ?
                ValidationResult.SUCCESS :
                ValidationResult.IS_NOT_AN_ADULT;
    }

    default CustomerRegistrationValidationService and (CustomerRegistrationValidationService other) {
        return customer -> {
            ValidationResult result = this.apply(customer);
            return result.equals(ValidationResult.SUCCESS) ? other.apply(customer) : result;
        };
    }

    enum ValidationResult {
        SUCCESS,
        EMAIL_IS_NOT_VALID,
        PHONE_NUMBER_IS_NOT_VALID,
        IS_NOT_AN_ADULT
    }
}
