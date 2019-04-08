package org.softuni.handy.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler({Throwable.class})
    public ModelAndView handleSqlException(Throwable e) {

        Throwable throwable = e;

        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        return this.view("error")
                .addObject("message", throwable.getMessage());
    }
}
