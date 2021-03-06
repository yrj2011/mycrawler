package com.blueware.agent;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TimeUtil {
    private static Map<String, Long> startTimes = new HashMap<String, Long>();
    private static Map<String, Long> endTimes   = new HashMap<String, Long>();

    private TimeUtil() {
    }

    public static void before (String param) {
        System.out.println("before(" + param + ")");
    }

    public static void after (String result, String param) {
        System.out.println("after( result = " + result + " ,param = " + param + ")");
    }

    
    public static long getStartTime(String key) {
        return startTimes.remove(key);
    }

    public static void setStartTime(String key) {
        startTimes.put(key, System.currentTimeMillis());
    }
    public static void getInvokeStack(){  
    	 Throwable t = new Throwable();  
    	 StackTraceElement ste[] = t.getStackTrace();  
    	 StringBuilder sb = new StringBuilder();  
    	 for (int i = 1; i < ste.length; i++)  
    	 {  
    	  sb.append(ste[i].toString()).append(";");   
    	 }
    	 System.out.println(sb.toString());
   }
    public static long getEndTime(String key) {
        return endTimes.remove(key);
    }

    public static void setEndTime(String key) {
        endTimes.put(key, System.currentTimeMillis());
    }
    
    public static void test(String className,String method,String opCode,Object[] localVar,Object[] args,Object[] ret) {
	   System.out.println("asdfadfs");
   }
    
    public static void test2(String className,String method,String opCode,Object[] localVar,Object[] args,Object returns) {
 	   System.out.println("asdfadfs");
   }
    
    
    public static void getExclusiveTime(String className, String methodName, String methodDesc) {
        String key = className + methodName + methodDesc;
        long exclusive = getEndTime(key) - getStartTime(key);
        System.out.println(className.replace("/", ".") + "." + methodName + " exclusive:" + exclusive);
    }

    public static Map<String, Long> getStartTimes() {
        return Collections.unmodifiableMap(startTimes);
    }

    public static Map<String, Long> getEndTimes() {
        return Collections.unmodifiableMap(endTimes);
    }

}
