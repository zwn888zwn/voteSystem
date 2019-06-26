package test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Around;

@Aspect
public class PointCut {
	@Pointcut("execution(* test.OriginClazz.show(..))")
	private void showMethod(){}
	@Before("showMethod()")
	public void before(){
		System.out.println("before");
	}
	@Before("showMethod()")
	public void before1(){
		System.out.println("before1");
	}
	@AfterReturning("showMethod()")
	public void afterR(){
		System.out.println("arfterR");
	}
	@Around("showMethod()")
	public void around(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("aroudB");
		pjp.proceed();
		System.out.println("aroudE");
	}
	@After("showMethod()")
	public void after(){
		System.out.println("after");
		
	}
	@After("showMethod()")
	public void after1(){
		System.out.println("after1");
		
	}
	@AfterReturning("showMethod()")
	public void afterR1(){
		System.out.println("arfterR1");
	}

}
