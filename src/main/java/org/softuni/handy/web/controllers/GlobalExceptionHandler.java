package org.softuni.handy.web.controllers;

import org.softuni.handy.web.anotations.PageTitle;
import org.softuni.handy.web.web_constants.PageTitles;
import org.softuni.handy.web.web_constants.Templates;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {



    @ExceptionHandler({Throwable.class})
    @PageTitle(PageTitles.ERROR)
    public ModelAndView handleSqlException(Throwable e) {

        Throwable throwable = e;

        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        return this.view(Templates.ERROR)
                .addObject("message", throwable.getMessage());
    }
}
