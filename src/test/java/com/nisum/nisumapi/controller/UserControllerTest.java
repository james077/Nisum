package com.nisum.nisumapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.nisum.nisumapi.constants.ResourceMapping;
import com.nisum.nisumapi.delegate.impl.UserDelegate;
import com.nisum.nisumapi.dto.PhoneDto;
import com.nisum.nisumapi.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Collections;
import com.fasterxml.jackson.databind.ObjectWriter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters=false)
@ContextConfiguration(classes ={UserController.class})
@WebMvcTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @MockBean
    private UserDelegate userDelegate;

    @Autowired
    private MockMvc mvc;

    private UserDto userDefaulInputDto;
    private UserDto userDefaultOutputDto;
    private ObjectWriter ow;

    @BeforeEach
    private void init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        ow = mapper.writer().withDefaultPrettyPrinter();
        userDefaulInputDto = UserDto.builder()
                .id("12345")
                .name("James Martinez")
                .email("jamesmartinez077@gmail.com")
                .phones(Collections.singletonList(PhoneDto.builder()
                        .number("3214567890")
                        .citycode("1")
                        .countrycode("57").build()))
                .password("Abcdef7*")
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
    public void save_whenInputIsOK_shouldReturn200() throws Exception {
        //Arrange
        when(userDelegate.save(userDefaulInputDto)).thenReturn(ResponseEntity.ok(userDefaultOutputDto));
        String requestJson = ow.writeValueAsString(userDefaulInputDto);

        //Act and Assert
        mvc.perform(post(ResourceMapping.USER)
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void save_whenInputPasswordIsInvalid_shouldReturn400() throws Exception {
        //Arrange
        userDefaulInputDto.setPassword("Password1");
        when(userDelegate.save(userDefaulInputDto)).thenReturn(ResponseEntity.ok(userDefaultOutputDto));
        String requestJson = ow.writeValueAsString(userDefaulInputDto);

        //Act
        Exception thrown = assertThrows(
                Exception.class,
                () -> mvc.perform(post(ResourceMapping.USER)
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
        );

        //Assert
        Assertions.assertTrue(thrown.getMessage().contains("IllegalArgumentException: The password does not meet the security criteria"));
    }

    @Test
    public void save_whenInputEmailIsInvalid_shouldReturn400() throws Exception {
        //Arrange
        userDefaulInputDto.setEmail("email.com");
        when(userDelegate.save(userDefaulInputDto)).thenReturn(ResponseEntity.ok(userDefaultOutputDto));
        String requestJson = ow.writeValueAsString(userDefaulInputDto);

        //Act
        mvc.perform(post(ResourceMapping.USER)
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void update_whenInputIsOK_shouldReturn200() throws Exception {
        //Arrange
        when(userDelegate.update(userDefaulInputDto)).thenReturn(ResponseEntity.ok(userDefaultOutputDto));
        String requestJson = ow.writeValueAsString(userDefaulInputDto);

        //Act and Assert
        mvc.perform(put(ResourceMapping.USER)
                        .content(requestJson)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}

