package com.nisum.nisumapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PhoneDto {

    private Integer id;
    private String userId;
    @Size(min = 7 , max = 20)
    private String number;
    @Size(min = 1 , max = 3)
    private String citycode;
    @Size(min = 1 , max = 3)
    private String countrycode;

}
