package com.taikang.ribbon.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.taikang.ribbon.service.FeignService;


@RestController
public class MyWebController {
	
	private static final Logger log = LoggerFactory.getLogger(MyWebController.class);

	@Autowired
	private RestTemplate client;
	
	@Autowired
	private AsyncRestTemplate asyncClient;
	
	@Autowired
	private FeignService service;
	
	@Autowired
	private RetryTemplate retryTemplate;
	
	
	/**
	*This method use RestTemplate ,the retry works well.
	*
	*/
	@RequestMapping("/helloRibbon")
	public String hello(){
		log.info("you called me");	
		ResponseEntity<String> forEntity = client.getForEntity("http://hystrix/helloSpringBoot", String.class);
		String body = forEntity.getBody();
		return body;
	}
	/**
	*This method use Feign Client ,the retry doesn't work.
	*/
	@RequestMapping("/helloRibbon2")
	public String hello2() throws Exception{
		// 同步请求
		String result = retryTemplate.execute(new RetryCallback<String,Exception>(){
			@Override
			public String doWithRetry(RetryContext context) throws Exception {
				return service.hello();
			}
		});
		
		return result;
		/*return service.hello();*/
	}
	
	@RequestMapping("/helloRibbon3")
	public String hello3() throws Exception{
		log.info("you called me");
		String result = retryTemplate.execute(new RetryCallback<String,Exception>(){
			@Override
			public String doWithRetry(RetryContext context) throws Exception {
				try {
					ResponseEntity<String> forEntity = client.getForEntity("http://hystrix/helloSpringBoot", String.class);
					String body = forEntity.getBody();
					return body;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
		});
		return result;
		/*return service.hello();*/
	}
	
	@RequestMapping("/helloRibbon4")
	public String hello4() throws Exception{
		
		log.info("you called me");
	      ListenableFutureCallback<ResponseEntity<String>> callback =  new ListenableFutureCallback<ResponseEntity<String>>() {  
	        @Override  
	        public void onSuccess(ResponseEntity<String> result) {  
	            System.out.println("======client get result : " + result.getBody());  
	        }  
	  
	        @Override  
	        public void onFailure(Throwable t) {  
	        	t.printStackTrace();
	              System.out.println("======client failure : " + t); 
	        }  
	   };
		String result = retryTemplate.execute(new RetryCallback<String,Exception>(){
			@Override
			public String doWithRetry(RetryContext context) throws Exception {
				
				   ListenableFuture<ResponseEntity<String>> future = asyncClient.getForEntity("http://localhost:8000/1", String.class);  
				   future.addCallback(callback); 
				  return "OK";
			}
		});
		return result;
		/*return service.hello();*/
	}
}
