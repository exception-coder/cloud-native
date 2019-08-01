package cn.exceptioncode.webapp.controller;

import org.springframework.stereotype.Controller;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }
}


