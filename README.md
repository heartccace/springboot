# springboot  
一. 核心特性
--------------
     组件自动装配:
        激活: @EnableAutoConfiguration
        配置: /META-INFO/spring.factories
        实现: XXXAutpConfiguration
        
        
     生产准备特性:
        指标: /actuator/metrics
        健康检查: /actuator/health
        外部化配置: /actuator/configprops
              
二. WEB应用
----------
     传统servlet应用
        Servlet组件: Servlet、Filter、Listener                    
        Servlet注册: Servlet注解、spring Bean、RegistrationBean
        异步非阻塞: 异步Servlet、非阻塞Servlet
     实现:
        1. 使用@WebServlet注解
        2. 继承HttpServlet
        3. 使用ServletComponentScan注册
   