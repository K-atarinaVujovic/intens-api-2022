package intens.api.praksa2022;

import intens.api.praksa2022.model.Candidate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CandidateUnitTests {

    private static final String VALID_FIRST = "Marko";
    private static final String VALID_LAST = "Markovic";
    private static final LocalDate VALID_BIRTH = LocalDate.of(2000, 1, 5);
    private static final String VALID_PHONE = "+381661234567";
    private static final String VALID_EMAIL = "mar.ko@hotmail.com";

    private static Candidate validCandidate() {
        return new Candidate(VALID_FIRST, VALID_LAST, VALID_BIRTH, VALID_PHONE, VALID_EMAIL);
    }

    @Test
    void createsCandidateSuccessfully() {
        Candidate candidate = validCandidate();

        assertThat(candidate.getFirstName()).isEqualTo(VALID_FIRST);
        assertThat(candidate.getLastName()).isEqualTo(VALID_LAST);
        assertThat(candidate.getDateOfBirth()).isEqualTo(VALID_BIRTH);
        assertThat(candidate.getPhoneNumber()).isEqualTo(VALID_PHONE);
        assertThat(candidate.getEmailAddress()).isEqualTo(VALID_EMAIL);
    }

    @Test
    void dateOfBirthToday_throwsInvalidArgumentException() {
        LocalDate today = LocalDate.now();

        assertThatThrownBy(() ->
                new Candidate(VALID_FIRST, VALID_LAST, today, VALID_PHONE, VALID_EMAIL))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void dateOfBirthInFuture_throwsInvalidArgumentException() {
        LocalDate future = LocalDate.now().plusDays(1);

        assertThatThrownBy(() ->
                new Candidate(VALID_FIRST, VALID_LAST, future, VALID_PHONE, VALID_EMAIL))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
