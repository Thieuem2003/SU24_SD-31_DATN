package com.backend.dto.request.account;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
    @NotEmpty
    @Length(min = 6)
    private String newPassword;
    @NotEmpty
    @Length(min = 6)
    private String confirmPassword;
}
