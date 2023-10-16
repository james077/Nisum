package com.nisum.nisumapi.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BusinessException extends RuntimeException {

    private String message;

}


