package exercise.dto;

// BEGIN

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GuestCreateDTO {
    @NotBlank
    private String name;

    @Column(unique = true)
    @Email
    private String email;

    @Pattern(regexp = "^(\\+\\d{11,13})$")
    private String phoneNumber;

    @Size(min = 4, max = 4)
    private String clubCard;

    @Future
    private LocalDate cardValidUntil;
}
// END
