package com.nisum.nisumapi.service;

import com.nisum.nisumapi.dto.UserDto;

public interface IUserService {

    UserDto save(UserDto userDto);
    boolean emailExist(String emai);

}
