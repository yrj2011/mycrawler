package com.dean.cloud.consumer.ribbon;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@Configuration
public class Config {
	@Bean
	/**
	 * 不使用@LoadBalanced的话调用restful接口的话无法使用service那么，要使用host：port 无法做到lb
	 * 加了注解可以同过serviceid去注册中心获取服务，就不需要host：port
	 * @return
	 */
	@LoadBalanced
	//初始化生成bean实例，controller中使用，restTemplate和controller变量名一致
	//类同：private RestTemplate restTemplate =new RestTemplate();
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
/**
 * 自己定义均衡算法，在配置文件中还要定义某个service的算法
 * biz:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
    
    默认不自定义本bean和配置文件的ruleclassname 。默认是轮询算法
 * @return
 */
	@Bean
	public IRule ribbonRule() {
		return new RandomRule();//这里配置策略，和配置文件对应
	}
}
