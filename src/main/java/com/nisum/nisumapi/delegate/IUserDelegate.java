package com.nisum.nisumapi.delegate;


import com.nisum.nisumapi.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface IUserDelegate {

    ResponseEntity<UserDto> save(UserDto userDto);
    ResponseEntity<UserDto> update(UserDto userDto);
}
