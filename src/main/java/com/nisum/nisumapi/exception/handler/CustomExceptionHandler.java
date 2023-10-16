package com.nisum.nisumapi.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nisum.nisumapi.constants.ErrorMessages;
import com.nisum.nisumapi.exception.BusinessException;
import com.nisum.nisumapi.exception.NoContentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {NoContentException.class, BusinessException.class, DataIntegrityViolationException.class,
            ConstraintViolationException.class,  NullPointerException.class, IllegalArgumentException.class})
    public final ResponseEntity handleConflict(RuntimeException ex) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        if (ex instanceof BusinessException) {
            log.info(((BusinessException) ex).getMessage());
            rootNode.put("Error", ((BusinessException)ex).getMessage());
            return new ResponseEntity(rootNode,HttpStatus.BAD_REQUEST);
        }
        if (ex instanceof DataIntegrityViolationException || ex instanceof ConstraintViolationException) {
           log.error(ErrorMessages.SQL_TRANSACTION, ex);
            rootNode.put("Error", ErrorMessages.SQL_TRANSACTION);
           return new ResponseEntity(rootNode, HttpStatus.CONFLICT);
        }
        if (ex instanceof IllegalArgumentException) {
            log.error(String.format(ex.getMessage()));
            rootNode.put("Error", ex.getMessage());
            return new ResponseEntity(rootNode, HttpStatus.BAD_REQUEST);
        }
        log.error(ErrorMessages.NOT_CONTROLLED_ERROR, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
