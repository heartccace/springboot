# springboot  
#文档源(https://docs.spring.io/spring-boot/docs/2.1.7.RELEASE/reference/htmlsingle/)
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
三. Spring Web MVC应用
----------------------
    Web MVC 视图: 模板引擎、内容协商、异常处理等
        1. 视图
            . viewResolver
            . View
        2. 模板引擎
            . Thymeleaf
            . Freemarker
            . JSP
        3. 内容协商(当项目存在多个模板引擎,可以根据内容协商来选择最佳模板引擎渲染)
            . ContentNegotiationConfigurer
            . ContentNegotiationStrategy
            . ContentNegotiatingViewResolver
        4. 异常处理
            . @ExceptionHandler
            . HandlerExceptionResolver
                . ExceptionHandlerExceptionResolver
            . BasicErrorController(Spring Boot)
    Web MVC Rest: 资源服务、资源跨域、服务发现等
        1. 资源服务
            . @RequestMappting
                . @GetMapping
            . @ResponseBody
            . @RequestBody
        2. 跨域资源
            . CrossOrigin(注解驱动)
            . WebMvcConfigurer#addCorsMappings(接口编程)
            . 传统解决方案
                . JFrame
                . JSONP
        3. 服务发现
    Web MVC 核心: 核心架构、处理流程、核心组件
        1. 核心架构
        2. 处理流程
        3. 核心组件
            . DispatcherServlet
            . HaddlerMapping
            . HandlerAdaptor
            . ViewResolver
Spring Web Flux 应用
 --------------------
    Reactor 基础: Java Lambda、Mono、Flux
    Web Flux核心: Web MVC注解、函数式声明、异步非阻塞
关系型数据
---------------------------
    JDBC: 数据源、jdbcTemplate、自动装配
    JPA: 实体映射关系、实体操作、自动装配
    事务: Spring事务抽象、JDBC事务处理、自动装配
SpringBoot应用
-------------------
    SpringApplication: 失败分析、应用特性、时间监听
    SpringBoot 配置: 外部化配置、Profile、配置属性
        1. 失败分析(FailureAnalysisReporter)
        2. SpringBoot外部化配置
            . ConfigurationProperty
            . @Profile
            . 配置属性(PropertySources)
Spring Boot Actuator(运维管理)
-----------------------------
    端点: 各类Web和JMX Endpoints
    健康检查: Health、HealthIndicator
    指标: 内建Metrics、自定义Metrics
Spring 模式注解装配
--------------------------------------
    定义: 一种用于声名在应用中扮演"组件"角色的注解
    举例: @Component、@Service、@Configuration等
    装配: \<context:component-scan\>或者@ComponentScan
Spring @Enable 模块装配
-----------------------------------------
    定义: 具备相同领域功能组件集合,组合形成一个独立的单元
    举例: @EnableWebMvc、@EnableAutoConfiguration等
    实现方式: 注解驱动方式、接口编程方式
        1. 注解驱动方式
            . 实现
                使用@Configuration注解
        2. 接口变成方式
            . 实现
                实现接口Selector
Spring 条件装配
----------------------------------------
    定义: Bean 装配的前置判断
    举例: @Profile (3.1)、@Conditional (4.0)
    实现: 注解方式、编程方式
           . 
        
   
    