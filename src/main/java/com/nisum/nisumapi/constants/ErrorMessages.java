package com.nisum.nisumapi.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

    public static final String ALREADY_REGISTERED_USER = "El correo ya está registrado";
    public static final String INVALID_EMAIL = "Formato de correo inválido";
    public static final String SQL_TRANSACTION = "Error ejecutando la transacción SQL";
    public static final String NOT_CONTROLLED_ERROR = "Error no controlado";
    public static final String NOT_FOUND_USER = "Usuario no encontrado";


}
