package com.library.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@EnableAspectJAutoProxy
public class LoggingAspect {
    @Before("execution(public void displayBookRepository())")
    public void beforeCalling(){
        System.out.println("Logging Before Method Execution .");
    }
    @After("execution(public void displayBookRepository())")
    public void afterCalling(){
        System.out.println("Logging After Method Execution .");
    }
}
