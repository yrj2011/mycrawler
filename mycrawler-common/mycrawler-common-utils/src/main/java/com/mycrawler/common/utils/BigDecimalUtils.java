package com.mycrawler.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 
* @ClassName: BigDecimalUtils
* @Description: 
* @author yangrenjiang
* @date 2017年6月26日 下午10:56:58
*
 */
public class BigDecimalUtils {

    /** DEFDIV_SCALE */
    private static final int DEFDIV_SCALE = 6;

    /** WAN_FORMAT */
    public static final String WAN_FORMAT = "#,##0.####";

    /** STAWAN_FORMAT */
    public static final String STAWAN_FORMAT = "#,##0.##";

    /** YUAN_FORMAT */
    public static final String YUAN_FORMAT = "#,##0.##";

    /** DOUBLE_FORMAT */
    private static final String DOUBLE_FORMAT = "#,##0.##";

    /** DOUBLE_NO_DOT_FORMAT */
    public static final String DOUBLE_NO_DOT_FORMAT = "###0.##";

    /** DOT_FORMAT */
    private static final String DOT_FORMAT = "#,##0.0000";

    /** DOUBLE_FORMAT_SIX */
    private static final String DOUBLE_FORMAT_SIX = "#,##0.000000";

    /** DOT_FORMAT_SIX */
    private static final String DOT_FORMAT_SIX = "#,##0.000000";

    public static final String DOUBLE_FORMAT_TWO = "#,##0.00######";

    public static final String DOUBLE_FORMAT_ONE = "#,##0.00";

    public static final String DOUBLE_FORMAT_THREE = "0.######";

    /** ZERO */
    public static final BigDecimal ZERO = BigDecimal.ZERO;

    protected BigDecimalUtils() {

    }
    
    public static BigDecimal add(BigDecimal number0, BigDecimal number1) {
        if (number0 == null) {
            number0 = BigDecimalUtils.ZERO;
        }
        if (number1 == null) {
            number1 = BigDecimalUtils.ZERO;
        }
        return number0.add(number1);
    }

    public static BigDecimal format(BigDecimal bgValue, int scale) {
        if (bgValue == null) {
            return BigDecimalUtils.ZERO;
        }

        BigDecimal bd = new BigDecimal(Double.toString(bgValue.doubleValue()))
                .setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public static BigDecimal multiAdd(List<BigDecimal> number) {
        BigDecimal startNumber = BigDecimalUtils.ZERO;
        for (int i = 0; i < number.size(); i++) {
            startNumber = startNumber.add(number.get(i));
        }
        return startNumber;
    }

    public static BigDecimal subtract(BigDecimal number0, BigDecimal number1) {
        if (number0 == null) {
            number0 = BigDecimalUtils.ZERO;
        }
        if (number1 == null) {
            number1 = BigDecimalUtils.ZERO;
        }
        return number0.subtract(number1);
    }

    public static BigDecimal div(long arg0, long arg1) {
        if (arg1 == 0) {
            return BigDecimalUtils.ZERO;
        }
        BigDecimal number0 = BigDecimal.valueOf(arg0);
        BigDecimal number1 = BigDecimal.valueOf(arg1);
        return div(number0, number1);
    } 
    
    public static BigDecimal div(long arg0, long arg1, int scale) {
        if (arg1 == 0) {
            return BigDecimalUtils.ZERO;
        }
        BigDecimal number0 = BigDecimal.valueOf(arg0);
        BigDecimal number1 = BigDecimal.valueOf(arg1);
        return div(number0, number1, scale);
    }

    public static BigDecimal div(BigDecimal number0, BigDecimal number1) {
        try {
            return number0.divide(number1, DEFDIV_SCALE, BigDecimal.ROUND_HALF_UP);
        } catch (RuntimeException e) {
            return BigDecimalUtils.ZERO;
        }
    }

    public static BigDecimal div(BigDecimal number0, BigDecimal number1, int scale) {
        try {
            return number0.divide(number1, scale, BigDecimal.ROUND_HALF_UP);
        } catch (RuntimeException e) {
            return BigDecimalUtils.ZERO;
        }
    }
    
    public static BigDecimal multiply(String number0, String number1) {
        BigDecimal dnumber0 = new BigDecimal(number0);
        BigDecimal dnumber1 = new BigDecimal(number1);
        return dnumber0.multiply(dnumber1);
    }
    
    public static BigDecimal multiply(BigDecimal number0, BigDecimal number1) {
        if (number0 == null) {
            number0 = BigDecimalUtils.ZERO;
        }
        if (number1 == null) {
            number1 = BigDecimalUtils.ZERO;
        }
        return number0.multiply(number1);
    }

    public static boolean isLessThan(BigDecimal source, BigDecimal destination) {
        if (source == null) {
            source = BigDecimalUtils.ZERO;
        }
        if (destination == null) {
            destination = BigDecimalUtils.ZERO;
        }
        return source.compareTo(destination) < 0;
    }

    public static boolean isEquals(BigDecimal source, BigDecimal destination) {
        if (source == null) {
            source = BigDecimalUtils.ZERO;
        }
        if (destination == null) {
            destination = BigDecimalUtils.ZERO;
        }
        return source.compareTo(destination) == 0;
    }

    public static int compareTo(BigDecimal source, BigDecimal destination) {
        if (source == null) {
            source = BigDecimalUtils.ZERO;
        }
        if (destination == null) {
            destination = BigDecimalUtils.ZERO;
        }
        return source.compareTo(destination);
    }

    public static boolean isGreaterThan(BigDecimal source, BigDecimal destination) {
        if (source == null) {
            source = BigDecimalUtils.ZERO;
        }
        if (destination == null) {
            destination = BigDecimalUtils.ZERO;
        }
        return source.compareTo(destination) > 0;
    }


    public static String format(BigDecimal doubleValue) {
        if (doubleValue == null) {
            doubleValue = BigDecimalUtils.ZERO;
        }
        return format(doubleValue.doubleValue(), DOUBLE_FORMAT);
    }

    public static boolean isNumber(String arg0) {
        try {
            new BigDecimal(arg0.replaceAll(",", ""));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String format(double doubleValue, String formatStyle) {
        if ("".equals(formatStyle)) {
            formatStyle = DOUBLE_FORMAT;
        }
        DecimalFormat objDecimalFormat = new DecimalFormat(formatStyle);
        return objDecimalFormat.format(doubleValue);
    }

    public static String format(Double doubleValue, String formatStyle) {
        if (doubleValue == null) {
            doubleValue = 0d;
        }
        if ("".equals(formatStyle)) {
            formatStyle = DOUBLE_FORMAT;
        }
        DecimalFormat objDecimalFormat = new DecimalFormat(formatStyle);
        return objDecimalFormat.format(doubleValue);
    }
    
    public static String format(BigDecimal doubleValue, String formatStyle) {
        if (null == doubleValue) {
            return "";
        }
        if (StringUtils.isBlank(formatStyle)) {
            formatStyle = DOT_FORMAT;
        } else if (formatStyle.endsWith("-4")) {// ���format��ʽ��"-4"��β,���doubleValueֵ����10000���ٸ�ʽ��(��������Ԫ��ʾ���)
            int iStart = formatStyle.indexOf('-');
            if (iStart > 0) {
                formatStyle = formatStyle.substring(0, iStart);
            } else {
                formatStyle = "";
            }
            if (StringUtils.isBlank(formatStyle)) {
                formatStyle = DOT_FORMAT;
            }
            doubleValue = BigDecimalUtils.div10000(doubleValue);
        }
        DecimalFormat objDecimalFormat = new DecimalFormat(formatStyle);
        return objDecimalFormat.format(doubleValue);
    }

    public static String format(BigDecimal doubleValue, boolean numberDot) {
        if (null == doubleValue) {
            return "";
        }
        DecimalFormat objDecimalFormat = new DecimalFormat(numberDot ? DOT_FORMAT : DOUBLE_FORMAT);
        return objDecimalFormat.format(doubleValue);
    }

    public static BigDecimal multiply10000(BigDecimal bigValue) {
        try {
            if (bigValue == null) {
                return null;
            }
            return bigValue.multiply(BigDecimal.valueOf(10000));
        } catch (Exception e) {
            return BigDecimalUtils.ZERO;
        }
    }
    
    public static BigDecimal div10000(BigDecimal bigValue) {
        try {
            if (bigValue == null) {
                return null;
            }
            return BigDecimalUtils.div(bigValue, BigDecimal.valueOf(10000));
        } catch (Exception e) {
            return BigDecimalUtils.ZERO;
        }
    }


    public static BigDecimal getValue(BigDecimal bigValue) {
        try {
            if (bigValue == null) {
                bigValue = BigDecimalUtils.ZERO;
            }
            return bigValue;
        } catch (Exception e) {
            return BigDecimalUtils.ZERO;
        }
    }


    public static BigDecimal divRate(BigDecimal number0, BigDecimal number1, int scale) {
        try {
            return div(multiply(number0, new BigDecimal(100)), number1, scale);
        } catch (RuntimeException e) {
            return BigDecimalUtils.ZERO;
        }
    }


    public static String formatFund(BigDecimal value) {
        if (null == value) {
            return "";
        }
        BigDecimal objMoney = value.movePointLeft(4);
        return BigDecimalUtils.format(objMoney, BigDecimalUtils.DOT_FORMAT);
    }

    public static String formatSixFund(BigDecimal value) {
        if (null == value) {
            return "";
        }
        BigDecimal objMoney = value.movePointLeft(4);
        return BigDecimalUtils.format(objMoney, BigDecimalUtils.DOUBLE_FORMAT_SIX);
    }


    public static String formatFundSix(BigDecimal value) {
        if (null == value) {
            return "";
        }
        BigDecimal objMoney = value.movePointLeft(4);
        return BigDecimalUtils.format(objMoney, BigDecimalUtils.DOT_FORMAT_SIX);
    }


    public static BigDecimal restoreFund(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        value = value.trim().replace(",", "");
        BigDecimal objBigDecimal = new BigDecimal(value);
        objBigDecimal = objBigDecimal.movePointRight(4);
        return objBigDecimal;
    }


    public static BigDecimal multiplyAndFormat(BigDecimal number0, BigDecimal number1, int scale) {
        if (number0 == null) {
            number0 = BigDecimalUtils.ZERO;
        }
        if (number1 == null) {
            number1 = BigDecimalUtils.ZERO;
        }
        return number0.multiply(number1).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static boolean compareInt(Integer number0, Integer number1) {
        if (number0 == null) {
            return false;
        }

        if (number1 == null) {
            return false;
        }
        return number0.compareTo(number1) == 0;

    }


    public static BigDecimal valueOf(BigDecimal num) {
        return null == num ? BigDecimal.ZERO : num;
    }


    public static BigDecimal valueOf(Double d){
    	return d == null ? BigDecimal.ZERO : new BigDecimal(d);
    }


    public static BigDecimal valueOf(Integer d){
    	return d == null ? BigDecimal.ZERO : new BigDecimal(d);
    }
}
