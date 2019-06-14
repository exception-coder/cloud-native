package cn.exceptioncode.webapp.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author zhangkai
 */
@Service
public class WebService {
    public void service()throws Exception{
//        try{
            System.out.println(1/0);
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }



}
