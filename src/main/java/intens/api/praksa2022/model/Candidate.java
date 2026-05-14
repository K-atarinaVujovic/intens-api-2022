package intens.api.praksa2022.model;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Candidate {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String emailAddress;

    public Candidate(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String phoneNumber,
            String emailAddress
    ) {
        validate(firstName, lastName, dateOfBirth, phoneNumber, emailAddress);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    private static void validate(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String phoneNumber,
            String emailAddress
    ) {
        if (firstName == null || firstName.trim().isEmpty() || firstName.length() > 50)
            throw new IllegalArgumentException("First name is required and must be under 50 characters.");

        if (lastName == null || lastName.trim().isEmpty() || lastName.length() > 50)
            throw new IllegalArgumentException("Last name is required and must be under 50 characters.");

        if (dateOfBirth != null && !dateOfBirth.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Date of birth must be in the past.");

        if (phoneNumber != null && !PHONE_PATTERN.matcher(phoneNumber).matches())
            throw new IllegalArgumentException("Invalid phone number format.");

        if (emailAddress == null || emailAddress.trim().isEmpty() || !EMAIL_PATTERN.matcher(emailAddress).matches())
            throw new IllegalArgumentException("Invalid email address format.");
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmailAddress() { return emailAddress; }
}
