package com.gestion.almacenes.commons.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitorAspect {

  @Around("execution(* com.gestion.almacenes.servicesImpls.*.*(..))")
  public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.nanoTime();
    Object result = joinPoint.proceed(); // Ejecuta el método original
    long endTime = System.nanoTime();

    long duration = (endTime - startTime) / 1_000_000; // Convertir a ms
    System.out.println(joinPoint.getSignature() + " ejecutado en " + duration + " ms");

    return result;
  }
}