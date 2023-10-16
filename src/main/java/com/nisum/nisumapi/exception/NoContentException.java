package com.nisum.nisumapi.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class NoContentException extends RuntimeException {

    private int id;

}
