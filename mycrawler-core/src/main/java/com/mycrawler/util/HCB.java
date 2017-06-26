package com.mycrawler.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;

/**
 * httpclient创建者
 * 
 * @author arron
 * @date 2015年11月9日 下午5:45:47 
 * @version 1.0
 */
public class  HCB extends HttpClientBuilder{
	
	public boolean isSetPool=false;//记录是否设置了连接池
	
	private HCB(){}
	public static HCB custom(){
		return new HCB();
	}

	/**
	 * 设置超时时间
	 * 
	 * @param timeout		超市时间，单位-毫秒
	 * @return
	 */
	public HCB timeout(int timeout){
		return timeout(timeout, true);
	}
	
	/**
	 * 设置超时时间以及是否允许网页重定向（自动跳转 302）
	 * 
	 * @param timeout		超时时间，单位-毫秒
	 * @param redirectEnable		自动跳转
	 * @return
	 */
	public HCB timeout(int timeout,  boolean redirectEnable){
		// 配置请求的超时设置
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(timeout)
				.setConnectTimeout(timeout)
				.setSocketTimeout(timeout)
				.setRedirectsEnabled(redirectEnable)
				.build();
		return (HCB) this.setDefaultRequestConfig(config);
	}
	

	/**
	 * 设置代理
	 * 
	 * @param hostOrIP		代理host或者ip
	 * @param port			代理端口
	 * @return
	 */
	public HCB proxy(String hostOrIP, int port){
		// 依次是代理地址，代理端口号，协议类型  
		HttpHost proxy = new HttpHost(hostOrIP, port, "http");  
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		return (HCB) this.setRoutePlanner(routePlanner);
	}
	
	/**
	 * 重试（如果请求是幂等的，就再次尝试）
	 * 
	 * @param tryTimes		重试次数
	 * @return
	 */
	public HCB retry(final int tryTimes){
		return retry(tryTimes, false);
	}
	
	/**
	 * 重试（如果请求是幂等的，就再次尝试）
	 * 
	 * @param tryTimes						重试次数
	 * @param retryWhenInterruptedIO		连接拒绝时，是否重试
	 * @return
	 */
	public HCB retry(final int tryTimes, final boolean retryWhenInterruptedIO){
		// 请求重试处理
	    HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
	        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
	            if (executionCount >= tryTimes) {// 如果已经重试了n次，就放弃
	                return false;
	            }
	            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
	                return true;
	            }
	            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
	                return false;
	            }
	            if (exception instanceof InterruptedIOException) {// 超时
	                //return false;
	                return retryWhenInterruptedIO;
	            }
	            if (exception instanceof UnknownHostException) {// 目标服务器不可达
	                return true;
	            }
	            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
	            	return false;
	            }
	            if (exception instanceof SSLException) {// SSL握手异常
	                return false;
	            }

	            HttpClientContext clientContext = HttpClientContext .adapt(context);
	            HttpRequest request = clientContext.getRequest();
	            // 如果请求是幂等的，就再次尝试
	            if (!(request instanceof HttpEntityEnclosingRequest)) {
	                return true;
	            }
	            return false;
	        }
	    };
	    this.setRetryHandler(httpRequestRetryHandler);
	    return this;
	}
	
}