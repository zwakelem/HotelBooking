package za.co.simplitate.hotelbooking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.simplitate.hotelbooking.enums.UserRole;

@Builder
public record RegistrationRequest(
        @NotBlank(message = "FirstName is required")
        String firstName,
        @NotBlank(message = "LastName is required")
        String lastName,
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "PhoneNumber is required")
        String phoneNumber,
        @NotBlank(message = "Role is required")
        UserRole role,
        @NotBlank(message = "Password is required")
        String password
) { }
