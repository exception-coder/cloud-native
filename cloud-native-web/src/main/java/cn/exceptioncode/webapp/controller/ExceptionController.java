package cn.exceptioncode.webapp.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author zhangkai
 */
@RestControllerAdvice
public class ExceptionController{

    @ExceptionHandler
    public String exception(ServerHttpRequest request, ServerHttpResponse response, Exception e){
        if(e instanceof  NullPointerException){
            return "空指针";
        }
        return e.getMessage();
    }
}
