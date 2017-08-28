# springcloud-
自建学习
#eurekaserver如zk 启动三个


#consumer
包含ribbon原始链接 需要加上lb
使用feign配置类似dubbo reference 其实是打包了ribbon+eureka
后面使用了hy熔断器，并且配置feign的超时、链接重试次数
