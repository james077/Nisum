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
public class UpdateUserDto {

    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String name;
    @Pattern(regexp = GeneralConstants.REGEX_EMAIL, message = ErrorMessages.INVALID_EMAIL)
    private String email;
    private String password;
    private List<PhoneDto> phones;
    private Boolean isActive;

}
