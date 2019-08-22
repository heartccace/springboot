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
Spring条件配置
-------------------------------------------------
    定义: 基于约定大于配置的原则，实现spring组件自动装配的目的
    装配: 模式注解、@Enable模块、条件装配、工厂加载机制
    实现: 激活自动装配、实现自动装配、配置自动装配实现
        工厂加载机制实现类: SpringFactoriesLoader
        配置资源:META-INFO/spring.factories
SpringApplication 
-----------------------------------------------
    定义: Spring应用引导类,提供便利的自定义引导方法
    场景: 嵌入式WeB应用和非Web应用
    运行: SpringApplication#run(String...)
            
    1. 准备阶段
        配置: Spring Bean 来源
            java配置class(使用注解)或者xml上下文配置文件集合用于spring boot BeanDefinitionLoader读取，并将配置源解析为spring bean定义
        推断: web应用类型和主引导类
            WebApplicationType#deduceFromClasspath在SpringApplication初始化时推导应用类型
            SpringApplication#deduceMainApplicationClass推导主引导类
        加载: 应用上下文初始器和应用事件监听器
            ApplicationContextInitializer应用上下文初始化器,可以调整容器结构，但是不能获取bean，此时bean未被初始化，
            配置方式在MATA-INF/spring.factories文件中配置实现，可以根据@Order(org.springframework.core.annotation.Order)或者Ordered(org.springframework.core.Ordered)接口实现加载顺序
            监听根据ApplicationListener的实现来完成监听，主要包括事件源和事件发生时间，跟注册顺序无关，监听需要配置到MATA-INF/spring.factories文件中
            SpringApplicationRunListener监听多个方法：
                1. starting()    首次启动run方法时立即调用。可以用于非常早期初始化,Spring应用刚启动
                2. environmentPrepared(ConfigurableEnvironment)  ConfigurableEnvironment准备妥当，允许将其调整
                3. contextPrepared(ConfigurableApplicationContext) ConfigurableApplicationContextt准备妥当，允许将其调整
                4. contextLoaded(ConfigurableApplicationContext)   ConfigurableApplicationContext已装载,但任未启动 
                5. started(ConfigurableApplicationContext)         ConfigurableApplicationContext已启动,此时spring bean初始化完成
                6. running(ConfigurableApplicationContext)         spring应用正在运行
                7. failed(ConfigurableApplicationContext)          spring运用运行失败
             SpringApplicationRunListener的唯一实现EventPublishingRunListener,与事件相关联
     2. 运行阶段
        创建: 应用上下文、Enviroment(抽象环境对象)、其他不重要
        失败: 故障分析报告
        回调: CommandLineRunner、ApplicationRunner
        
Spring FrameWork事件/监听编程模型
------------------------------------------
    1. Spring 应用事件
        普通应用事件: ApplicationEvent
        应用上下文事件: ApplicationContextEvent
    2. Spring 应用监听器
        接口编程模型: ApplicationListener
        注解编程模型: @EventListener
    3. Spring应用事件广播器
        接口: ApplicationEventMulticaster
        实现类: SimpleApplicationEventMulticaster
            执行方式: 同步or异步
EventPublishingRunListener监听方法与SpringBoot事件对应关系
----------------------------------------------------------
    监听方法                                 SpringBoot事件                                   SpringBoot起始版本
    starting                                ApplicationStartingEvent                         1.5
    environmentPrepared                     ApplicationEnvironmentPreparedEvent              1.0             
    contextPrepared                         
    contextLoaded                           ApplicationPreparedEvent                         1.0
    started                                 ApplicationStartedEvent                          2.0
    running                                 ApplicationReadyEvent                            2.0
    failed                                  ApplicationFailedEvent                           1.0