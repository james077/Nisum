package com.nisum.nisumapi.service;

import com.nisum.nisumapi.domain.Phone;
import com.nisum.nisumapi.domain.User;
import com.nisum.nisumapi.dto.PhoneDto;
import com.nisum.nisumapi.dto.UserDto;
import com.nisum.nisumapi.mapper.IUserMapper;
import com.nisum.nisumapi.repository.IUserRepository;
import com.nisum.nisumapi.service.impl.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private IUserRepository userRepository;
    @Mock
    private IUserMapper userMapper;

    private UserDto userDefaultInputDto;
    private UserDto userDefaultOutputDto;
    private User userDefaultInput;
    private User userDefaultOutput;

    @BeforeEach
    public void init(){
        userDefaultInputDto = UserDto.builder()
                .name("James Martinez")
                .email("jamesmartinez077@gmil.com")
                .password("P455w0rd*")
                .phones(Collections.singletonList(PhoneDto.builder()
                        .number("3214567890")
                        .citycode("1")
                        .countrycode("57").build()))
                .build();

        userDefaultOutputDto = UserDto.builder()
                .name("James Martinez")
                .email("jamesmartinez077@gmil.com")
                .password("P455w0rd*")
                .phones(Collections.singletonList(PhoneDto.builder()
                        .number("3214567890")
                        .citycode("1")
                        .countrycode("57").build()))
                .created(LocalDateTime.of(2023,10,15,10,30))
                .modified(LocalDateTime.of(2023,10,15,10,30))
                .lastLogin(LocalDateTime.of(2023,10,15,10,30))
                .isActive(true)
                .build();

        Phone phone = new Phone();
        phone.setNumber("3214567890");
        phone.setCitycode("1");
        phone.setCountrycode("57");
        userDefaultInput = new User();
        userDefaultInput.setId("12345");
        userDefaultInput.setName("James Martinez");
        userDefaultInput.setEmail("jamesmartinez077@gmail.com");
        userDefaultInput.setPassword("Abcdef7*");
        userDefaultInput.setPhones(Collections.singletonList(phone));

        Phone phone2 = new Phone();
        phone.setNumber("3214567890");
        phone.setCitycode("1");
        phone.setCountrycode("57");
        userDefaultOutput = new User();
        userDefaultOutput.setId("12345");
        userDefaultOutput.setName("James Martinez");
        userDefaultOutput.setEmail("jamesmartinez077@gmail.com");
        userDefaultOutput.setPassword("Abcdef7*");
        userDefaultOutput.setPhones(Collections.singletonList(phone2));
        userDefaultOutput.setCreated(LocalDateTime.of(2023,10,15,10,30));
        userDefaultOutput.setModified(LocalDateTime.of(2023,10,15,10,30));
        userDefaultOutput.setLastLogin(LocalDateTime.of(2023,10,15,10,30));
        userDefaultOutput.setIsActive(true);
    }

    @Test
    public void save_whenDataIsCorrect_shouldReturnUserSaved() {
        //Arrange
        when(userMapper.dtoToEntity(userDefaultInputDto)).thenReturn(userDefaultInput);
        when(userRepository.save(userDefaultInput)).thenReturn(userDefaultOutput);
        when(userMapper.entityToDto(userDefaultOutput)).thenReturn(userDefaultOutputDto);
        //Act
        UserDto response = userService.save(userDefaultInputDto);
        //Assert
        Assertions.assertEquals(response, userDefaultOutputDto);
    }

    @Test
    public void emailExist_whenEmailExist_shouldReturnTrue() {
        //Arrange
        when(userRepository.findOneByEmail(userDefaultInputDto.getEmail())).thenReturn(Optional.of(userDefaultOutput));
        //Act
        boolean response = userService.emailExist(userDefaultInputDto.getEmail());
        //Assert
        Assertions.assertTrue(response);
    }

    @Test
    public void emailExist_whenEmailDoesntExist_shouldReturnFalse() {
        //Arrange
        when(userRepository.findOneByEmail(userDefaultInputDto.getEmail())).thenReturn(Optional.empty());
        //Act
        boolean response = userService.emailExist(userDefaultInputDto.getEmail());
        //Assert
        Assertions.assertFalse(response);
    }

}
