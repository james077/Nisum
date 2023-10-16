package com.nisum.nisumapi.service.impl;

import com.nisum.nisumapi.domain.User;
import com.nisum.nisumapi.dto.UserDto;
import com.nisum.nisumapi.service.IUserService;
import com.nisum.nisumapi.mapper.IUserMapper;
import com.nisum.nisumapi.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    @Override
    public UserDto save(UserDto userDto) {
        log.info("Saving user...");
        User user = userMapper.dtoToEntity(userDto);
        user.setAllJoins();
        return userMapper.entityToDto(userRepository.save(user));
    }

    @Override
    public boolean emailExist(String email){
        return userRepository.findOneByEmail(email).isPresent();
    }

}
