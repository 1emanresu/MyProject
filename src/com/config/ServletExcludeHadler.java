package com.config;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class ServletExcludeHadler extends Handler{
	 
@SuppressWarnings("serial")
 public static final HashSet<String> servletSet = new HashSet<String>(){{
        // add("/codeimg");
     }};
 
@Override
 public void handle(String target, HttpServletRequest request,
 HttpServletResponse response, boolean[] isHandled) {
 if (servletSet.contains(target)) 
           return;
       nextHandler.handle(target,request,response,isHandled);
 
}

}
