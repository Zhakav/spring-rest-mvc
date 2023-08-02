package zhakav.springframework.springrestmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import zhakav.springframework.springrestmvc.exception.NotFoundException;

@Slf4j
//@ControllerAdvice
public class ExceptionController {
//    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFound(){

        log.debug("IN Exception CONTROLLER -NOT FOUND EXCEPTION HANDLER");

        return ResponseEntity.notFound().build();

    }
}
