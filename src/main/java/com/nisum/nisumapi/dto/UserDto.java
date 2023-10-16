package com.nisum.nisumapi.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nisum.nisumapi.constants.ErrorMessages;
import com.nisum.nisumapi.constants.GeneralConstants;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLogin;
    private String name;
    @Pattern(regexp = GeneralConstants.REGEX_EMAIL, message = ErrorMessages.INVALID_EMAIL)
    private String email;
    @NotNull(message = "stringValue has to be present")
    private String password;
    private List<PhoneDto> phones;
    private boolean isActive;

}
