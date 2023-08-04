package zhakav.springframework.springrestmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(TransactionSystemException.class)
    ResponseEntity handleJPAViolations(TransactionSystemException exception){

        ResponseEntity.BodyBuilder response=ResponseEntity.badRequest();

        if(exception.getCause().getCause() instanceof ConstraintViolationException){

            ConstraintViolationException ve=(ConstraintViolationException) exception.getCause().getCause();

            List errors=ve.getConstraintViolations().stream()
                    .map(constraintViolation -> {

                        Map<String,String> errMap=new HashMap<>();
                        errMap.put(constraintViolation.getPropertyPath().toString(),
                                constraintViolation.getMessage());

                        return errMap;

                    }).collect(Collectors.toList());

            return response.body(errors);
        }

        return response.build();

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){

        List listErrors=exception.getFieldErrors().stream()
                .map(fieldError->{

                    Map<String,String> errorMap=new HashMap<>();
                    errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listErrors);

    }

}
