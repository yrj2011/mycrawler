package com.mycrawler.common.utils;

/**
 * 
* @ClassName: NumberUtils
* @Description: 
* @author yangrenjiang
* @date 2017年6月26日 下午11:02:48
*
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {

   public static int intValue(Integer value) {
        return value == null ? 0 : value.intValue();
    }


    public static int intValue(Integer value , int defaultValue) {
    	return value == null ? defaultValue : value.intValue();
    }
     
    public static boolean equals(Integer value1 , Integer value2) {
        return intValue(value1 , 0) == intValue(value2 , 0);
    }
     
    public static float intValue(Float value , int defaultValue){
    	return value == null ? defaultValue : value ;
    }
     
    public static boolean equals(Float value1 , Float value2) {
        return intValue(value1 , 0) == intValue(value2 , 0);
    }
}
