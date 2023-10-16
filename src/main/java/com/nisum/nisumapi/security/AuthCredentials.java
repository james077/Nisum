package com.nisum.nisumapi.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCredentials {

    private String email;
    private String password;
}
