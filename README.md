# weirdo
一、所涉及到技术：
                ①：SpringCloud
                ②：SpringBoot
                ③：Mybatis
                ④：MQTT协议
                ⑤：ActiveMQ
                ⑥：Camel

二、聚合工程说明
  ①：microservice-discovery-eureka 
      主要用于服务的注册于发现，找到启动类首先运行此工程
  ②：microservice-provider-mybatis-user
      利用Mybatis做了简单用户信息的查询，用于microservice-consumer-user-feign模块调用
      ActiveMQ消息队列定时的发送(AmqProducer 将//@Scheduled(fixedDelay = 5000)这个注解放开运行可执行发送消息)
      MQTT协议发送定时主题消息（ "Topic/Weirdo/DaChange"）
      另外：mqtt配置信息走的是xml文件，也可使用javabean配置文件
   ③：microservice-consumer-user-feign
       当microservice-provider-mybatis-user模块利用ActiveMQ消息队列定时的发送消息时，可将AmqConsumer的@JmsListener(destination = "${queue}")注解放开即可实现消息点对点消费
       使用Camel自定义方法上接收主题、队列消息，详细配置（spring_camel.xml）
       feign包下面的UserFeignClient类做了Feign调用以及熔断处理也可以使用RestTemplate调用
       Controller层有熔断处理机制
       index.html页面中可订阅mqtt协议发送的主题消息