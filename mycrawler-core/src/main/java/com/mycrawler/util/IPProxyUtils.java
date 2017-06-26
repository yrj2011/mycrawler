package com.mycrawler.util;

import java.io.BufferedReader;
/**
 * 
* @ClassName: IPProxyUtils
* @Description:代理IP工具类
* @author yangrenjiang
* @date 2017年6月24日 下午7:18:51
*
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

public class IPProxyUtils {
	public static void createIPAddress(String ip, int port) {
		URL url = null;
		try {
			url = new URL("http://www.baidu.com");
		} catch (MalformedURLException e) {
			System.out.println("url invalidate");
		}
		InetSocketAddress addr = null;
		addr = new InetSocketAddress(ip, port);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http proxy
		InputStream in = null;
		try {
			URLConnection conn = url.openConnection(proxy);
			conn.setConnectTimeout(1000);
			in = conn.getInputStream();
		} catch (Exception e) {
			System.out.println("ip " + ip + " is not aviable");// 异常IP
		}
		String s = convertStreamToString(in);
		System.out.println(s);
		// System.out.println(s);
		if (s.indexOf("baidu") > 0) {// 有效IP
			System.out.println(ip + ":" + port + " is ok");
		}
	}

	public static String convertStreamToString(InputStream is) {
		if (is == null)
			return "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
     * 批量代理IP有效检测
     *
     * @param proxyIpMap
     * @param reqUrl
     */
    public static void checkProxyIp(Map<String, Integer> proxyIpMap, String reqUrl) {
 
          for (String proxyHost : proxyIpMap.keySet()) {
                Integer proxyPort = proxyIpMap.get(proxyHost);
 
                int statusCode = 0;
                try {
                	
                	// 依次是代理地址，代理端口号，协议类型  
            		  CloseableHttpClient httpClient =  HCB.custom().timeout(1000).proxy(proxyHost, proxyPort).build();
            			//执行请求
            		  HttpGet getMethod = new HttpGet(reqUrl);
            		  CloseableHttpResponse resposne =  httpClient.execute(getMethod);
            		  
                     /* // 连接超时时间（默认10秒 10000ms） 单位毫秒（ms）
                      int connectionTimeout = 10000;
                      // 读取数据超时时间（默认30秒 30000ms） 单位毫秒（ms）
                      int soTimeout = 30000;
                      httpClient.getConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
                      httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);*/
            		  resposne.close();
            		  System.out.println("curl -x " + proxyHost +":"+proxyPort + " http://www.baidu.com");
                } catch (Exception e) {
                    System.out.println("ip " + proxyHost + " is not aviable");
                }
                if(statusCode>0){
                     System.out.format("%s:%s-->%sn", proxyHost, proxyPort,statusCode);
                }
                
          }
    }
    
    public static void main(String[] args) {
    	String reqUrl = "http://www.baidu.com";
    	Map<String, Integer> proxyIpMap = new HashMap<>();
    	proxyIpMap.put("127.0.0.1", 80);
    	proxyIpMap.put("60.211.182.76",	8080);
    	proxyIpMap.put("59.44.29.249",	8080);
    	proxyIpMap.put("118.180.49.24",	8080);
    	proxyIpMap.put("125.67.239.175",	8080);
    	proxyIpMap.put("61.158.114.188",	8080);
    	proxyIpMap.put("210.38.1.134",	8080);
    	proxyIpMap.put("210.38.1.138",	8081);
    	proxyIpMap.put("210.38.1.138",	8080);
    	proxyIpMap.put("119.29.113.233",	8088);
    	proxyIpMap.put("61.130.97.212",	8099);
    	
    	checkProxyIp(proxyIpMap, reqUrl);
	}
}
