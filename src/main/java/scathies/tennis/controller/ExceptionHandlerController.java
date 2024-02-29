package scathies.tennis.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public String handleValidationException(Model model, HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        model.addAttribute("reason", "имя игрока/ов не должно быть пустым");
        return "new-match-form";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArg(Exception e, Model model, HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        model.addAttribute("reason", e.getMessage());
        return "new-match-form";
    }
}
