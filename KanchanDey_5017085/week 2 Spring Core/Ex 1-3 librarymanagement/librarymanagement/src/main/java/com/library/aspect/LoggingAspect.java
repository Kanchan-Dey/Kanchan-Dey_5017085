package com.library.aspect;

import java.util.Date;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@EnableAspectJAutoProxy
public class LoggingAspect {
    @Before("execution(public void printBookRepository())")
    public void logExecutionTime(){
        long start=System.currentTimeMillis();
        Date current=new Date(start);
        System.out.println("Execution Time : "+current);
    }
}
