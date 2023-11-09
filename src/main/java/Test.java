import java.time.LocalDate;

public class Test {

    public static void main(String[] args) {

        Customer customer = new Customer(
                "Saman",
                "saman@gmail.com",
                "02228383744",
                LocalDate.of(2000, 1, 1)
        );

        /*CustomerValidationService validationService =
                new CustomerValidationService();
        System.out.println(validationService.isValid(customer));*/

        CustomerRegistrationValidationService.ValidationResult result =
                CustomerRegistrationValidationService
                .isEmailValid()
                        .and(CustomerRegistrationValidationService.isPhoneNumberValid())
                        .and(CustomerRegistrationValidationService.isAdult())
                        .apply(customer);

        System.out.println(result);

        if(!result.equals(CustomerRegistrationValidationService.ValidationResult.SUCCESS)) {
            throw new IllegalStateException(result.name());
        }
    }
}
