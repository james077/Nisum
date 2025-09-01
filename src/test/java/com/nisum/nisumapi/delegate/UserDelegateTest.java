package com.nisum.nisumapi.delegate;

import com.nisum.nisumapi.constants.ErrorMessages;
import com.nisum.nisumapi.constants.ResourceMapping;
import com.nisum.nisumapi.delegate.impl.UserDelegate;
import com.nisum.nisumapi.domain.Phone;
import com.nisum.nisumapi.domain.User;
import com.nisum.nisumapi.dto.PhoneDto;
import com.nisum.nisumapi.dto.UpdateUserDto;
import com.nisum.nisumapi.dto.UserDto;
import com.nisum.nisumapi.exception.BusinessException;
import com.nisum.nisumapi.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.wildfly.common.Assert;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class UserDelegateTest {
    @InjectMocks
    private UserDelegate userDelegate;

    @Mock
    private IUserService userService;

    private UserDto userDefaultInputDto;
    private UserDto userDefaultOutputDto;

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
                .id("12345")
                .name("James Martinez")
                .email("jamesmartinez077@gmail.com")
                .phones(Collections.singletonList(PhoneDto.builder()
                        .number("3214567890")
                        .citycode("1")
                        .countrycode("57").build()))
                .password("Abcdef7*")
                .created(LocalDateTime.of(2023,10,15,10,30))
                .modified(LocalDateTime.of(2023,10,15,10,30))
                .lastLogin(LocalDateTime.of(2023,10,15,10,30))
                .isActive(true)
                .build();
    }

    @Test
    public void save_whenDataIsCorrect_shouldReturnUserSaved() {
        //Arrange
        when(userService.save(userDefaultInputDto)).thenReturn(userDefaultOutputDto);
        when(userService.emailExist(userDefaultInputDto.getEmail())).thenReturn(false);
        //Act
        ResponseEntity<UserDto> response = userDelegate.save(userDefaultInputDto);
        //Assert
        Assert.assertNotNull(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void save_whenEmailAlreadyExists_shouldReturnUserSaved() {
        //Arrange
        when(userService.emailExist(userDefaultInputDto.getEmail())).thenReturn(true);
        //Act
        Exception thrown = assertThrows(
                Exception.class,
                () -> userDelegate.save(userDefaultInputDto)
        );
        //Assert
        Assertions.assertEquals(thrown.getMessage(),ErrorMessages.ALREADY_REGISTERED_USER);
    }

    @Test
    public void update_whenDataIsCorrect_shouldReturnUserSaved() {
        //Arrange
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .name("James Martinez C.")
                .email("jamesmartinez077@gmil.com")
                .password("P455w0rd*")
                .phones(Collections.singletonList(PhoneDto.builder()
                        .number("3214567890")
                        .citycode("1")
                        .countrycode("57").build()))
                .build();
        when(userService.findByEmail(updateUserDto.getEmail())).thenReturn(userDefaultInputDto);
        when(userService.save(userDefaultInputDto)).thenReturn(userDefaultOutputDto);

        //Act
        ResponseEntity<UserDto> response = userDelegate.update(updateUserDto);
        //Assert
        Assert.assertNotNull(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
