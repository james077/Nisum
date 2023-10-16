package com.nisum.nisumapi.controller;

import com.nisum.nisumapi.constants.ResourceMapping;
import com.nisum.nisumapi.delegate.IUserDelegate;
import com.nisum.nisumapi.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(ResourceMapping.USER)
public class UserController {

    @Value("${password.regexp}")
    private String regExPassword;
    private final IUserDelegate userDelegate;
    
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto) {
        if(!hasValidPassword(userDto))
            throw new IllegalArgumentException("The password does not meet the security criteria");
        return userDelegate.save(userDto);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {
        return userDelegate.update(userDto);
    }

    private boolean hasValidPassword(UserDto userDto){
        return Pattern.compile(regExPassword)
                .matcher(userDto.getPassword())
                .matches();
    }

}
