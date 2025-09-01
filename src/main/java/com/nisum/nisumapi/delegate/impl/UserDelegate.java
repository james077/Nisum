package com.nisum.nisumapi.delegate.impl;

import com.nisum.nisumapi.constants.ErrorMessages;
import com.nisum.nisumapi.delegate.IUserDelegate;
import com.nisum.nisumapi.dto.UpdateUserDto;
import com.nisum.nisumapi.dto.UserDto;
import com.nisum.nisumapi.exception.BusinessException;
import com.nisum.nisumapi.mapper.IUserMapper;
import com.nisum.nisumapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserDelegate implements IUserDelegate {

    private final IUserService userService;


    @Override
    public ResponseEntity<UserDto> save(UserDto userDto){
        if(userService.emailExist(userDto.getEmail()))
            throw new BusinessException(ErrorMessages.ALREADY_REGISTERED_USER);
        userDto.setIsActive(true);
        userDto.setId(java.util.UUID.randomUUID().toString());
        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        return ResponseEntity.ok(userService.save(userDto));
    }

    @Override
    public ResponseEntity<UserDto> update(UpdateUserDto updateUserDto){
        UserDto user = userService.findByEmail(updateUserDto.getEmail());
        if(user == null){
            throw new BusinessException(ErrorMessages.NOT_FOUND_USER);
        }
        if (updateUserDto.getName() != null)
            user.setName(updateUserDto.getName());

        if (updateUserDto.getPassword() != null)
            user.setPassword(new BCryptPasswordEncoder().encode(updateUserDto.getPassword()));

        if (updateUserDto.getPhones() != null)
            user.setPhones(updateUserDto.getPhones());

        user.setModified(LocalDateTime.now());

        return ResponseEntity.ok(userService.save(user));
    }

}
