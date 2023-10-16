package com.nisum.nisumapi.delegate.impl;

import com.nisum.nisumapi.constants.ErrorMessages;
import com.nisum.nisumapi.delegate.IUserDelegate;
import com.nisum.nisumapi.dto.UserDto;
import com.nisum.nisumapi.exception.BusinessException;
import com.nisum.nisumapi.mapper.IUserMapper;
import com.nisum.nisumapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserDelegate implements IUserDelegate {

    private final IUserService userService;
    private final IUserMapper userMapper;


    @Override
    public ResponseEntity<UserDto> save(UserDto userDto){
        if(userService.emailExist(userDto.getEmail()))
            throw new BusinessException(ErrorMessages.ALREADY_REGISTERED_USER);
        userDto.setActive(true);
        userDto.setId(java.util.UUID.randomUUID().toString());
        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        return ResponseEntity.ok(userService.save(userDto));
    }

    @Override
    public ResponseEntity<UserDto> update(UserDto userDto){
        return ResponseEntity.ok(userService.save(userDto));
    }

}
