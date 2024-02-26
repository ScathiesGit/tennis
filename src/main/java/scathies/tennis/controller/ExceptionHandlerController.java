package scathies.tennis.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, Model model) {
        if (exception instanceof HandlerMethodValidationException e) {
            model.addAttribute("reason", "имя игрока/ов не должно быть пустым");
            return "new-match-form";
        } else if (exception instanceof IllegalArgumentException) {
            model.addAttribute("reason", exception.getMessage());
            return "new-match-form";
        }

        return "";
    }
}
