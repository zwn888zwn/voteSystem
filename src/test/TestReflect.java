package test;

import java.lang.reflect.Method;

public class TestReflect {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws Exception, InstantiationException, IllegalAccessException {
		Class clazz=Class.forName("test.OriginClazz");
		Object obj=clazz.newInstance();
		Method meth=clazz.getMethod("show", null);
		meth.invoke(obj, null);
		

	}

}
