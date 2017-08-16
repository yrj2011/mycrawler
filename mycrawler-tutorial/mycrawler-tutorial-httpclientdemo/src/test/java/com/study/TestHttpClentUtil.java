package com.study;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * PackageName  com.study
 * Description  test HttpClientGet.
 * User:        yh
 * Time:        2016/10/28 10:07
 */
public class TestHttpClentUtil
{
    @Test
    public void testHttpGet() {
        String url = "http://localhost:8000/user/1";
        String jsonData = HttpClientUtil.getInstance().sendHttpGet(url);
        System.out.println(jsonData);
       /** List<Customer> list = JSON.parseArray(jsonData, Customer.class);
        System.out.println(jsonData);*/
    }

    public static String chatRobot(String requestParm){
/*        String url = "http://op.juhe.cn/robot/index?key=d13acdcdff79eada8b79d410e469989f&info=";
        url += requestParm;
        String jsonData = HttpClientUtil.getInstance().sendHttpGet(url);
        JSONObject jsonObject = JSON.parseObject(jsonData);
        String reason = (String) jsonObject.get("reason");
        if(reason.equals("成功的返回")){
            Map<String, String> map = (Map<String, String>) jsonObject.get("result");
            return map.get("text");
        }else {
            return reason;
        }*/
    	return null;
    }

    public static void main(String[] args)
    {
      /*  Scanner scanner = new Scanner(System.in);
        while( true) {
            String request = scanner.nextLine();
            System.out.println("你说：" + request);
            String response = chatRobot(request);
            System.out.println("chatRobat:" + response);
        }*/
    }


}
