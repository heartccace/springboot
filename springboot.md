# <center>Springboot应用分析</center>

### 1、打包结果内容与结构深入分析

##### *备注 fatjar表示jar中包含jar

springboot应用通过maven打包成的jar

![](https://github.com/heartccace/springboot/tree/master/src/main/resources/static/pics/maven打包的jar文件.png)

jar解压后的目录

![](https://github.com/heartccace/springboot/tree/master/src/main/resources/static/pics/jar解压后目录.png)

目录详情

![](https://github.com/heartccace/springboot/tree/master/src/main/resources/static/pics/目录详情.png)

```
// springboot启动类
public class JarLauncher extends ExecutableArchiveLauncher {
	// 定义启动类路径
	static final String BOOT_INF_CLASSES = "BOOT-INF/classes/";
	// 定义第三方jar路径
	static final String BOOT_INF_LIB = "BOOT-INF/lib/";

	public JarLauncher() {
	}

	protected JarLauncher(Archive archive) {
		super(archive);
	}

	@Override
	protected boolean isNestedArchive(Archive.Entry entry) {
		if (entry.isDirectory()) {
			return entry.getName().equals(BOOT_INF_CLASSES);
		}
		return entry.getName().startsWith(BOOT_INF_LIB);
	}

	public static void main(String[] args) throws Exception {
		new JarLauncher().launch(args);
	}

}

```

```
// Launch.class 执行main方法
protected void launch(String[] args, String mainClass, ClassLoader classLoader) throws Exception {
		Thread.currentThread().setContextClassLoader(classLoader);
		createMainMethodRunner(mainClass, args, classLoader).run();
	}
```

// org.springframework.boot.loader.MainMethodRunner

// 调用main方法的入口，即springboot的真正执行开始

public void run() throws Exception {
   Class<?> mainClass = Thread.currentThread().getContextClassLoader().loadClass(this.mainClassName);
   Method mainMethod = mainClass.getDeclaredMethod("main", String[].class);
   mainMethod.invoke(null, new Object[] { this.args });
}

运行过程：

1、java -jar + jar，根据系统类加载器去解析并加载meta-info下面的配置MANIFEST.MF中的Main-Class，结果根据jar加载JarLauncher，即其中任意一个会去解析当前路径，加载BOOT-INFO下面的lib和class，根据MANIFEST.MF中的Start-Class启动应用程序。

- JarLauncher运行main方法

  ```
  //JarLauncher.class
  public static void main(String[] args) throws Exception {
  // 当前函数即父类构造函数初始化
     new JarLauncher().launch(args);
  }
  ```

- JarLauncher构造函数以及父类构造函数执行

  ```
  // ExecutableArchiveLauncher.calss
  public ExecutableArchiveLauncher() {
  		try {
  		// 创建archive对象，主要用于获取第三方依赖和源码class，用于一面构造类加载器
  			this.archive = createArchive();
  		}
  		catch (Exception ex) {
  			throw new IllegalStateException(ex);
  		}
  	}
  ```

- 创建自定义类加载器

  ```
  // Launcher.class，用于加载BOOT-INF下的第三方类库和源码
  protected ClassLoader createClassLoader(URL[] urls) throws Exception {
  // 创建LaunchedURLClassLoader并设置父加载器为当前的类加载器即Lanucher@Appclassloader
  		return new LaunchedURLClassLoader(urls, getClass().getClassLoader());
  	}
  	
  	
  		protected void launch(String[] args, String mainClass, ClassLoader classLoader) throws Exception {
  		// 将创建好的LaunchedURLClassLoader设置为线程上线文
  		Thread.currentThread().setContextClassLoader(classLoader);
  		// 创建MainMethodRunner对象并运行main方法
  		createMainMethodRunner(mainClass, args, classLoader).run();
  	
  ```

  

运行方式：

springboot运行方式分为两种

- 通过main方法运行，此时的类加载器是Launcher的内部内AppclassLoader进行加载的
- 采用jar或者war包进行加载的是spring自定义的LauncherURLClassloader进行加载的

### 2、java调试JDWP(JAVA DEBUG WIRE PROTOCOL, java调试协议)

通过JDWP可以通过远程应用，远程字节码关联本地代码，在本地进行调试

```
// 用法
C:\Users\admin>java -agentlib:jdwp=help
               Java Debugger JDWP Agent Library
               --------------------------------

  (see http://java.sun.com/products/jpda for more information)

jdwp usage: java -agentlib:jdwp=[help]|[<option>=<value>, ...]

Option Name and Value            Description                       Default
---------------------            -----------                       -------
//程序启动时是否等待，远程socket连接
suspend=y|n                      wait on startup?                  y 
//传输规范
transport=<name>                 transport spec                    none
//传输地址（本机指定端口号）
address=<listen/attach address>  transport spec                    ""
// 是否监听调试器（需要开启）
server=y|n                       listen for debugger?              n
//当事件发生时运行调试器
launch=<command line>            run debugger on event             none
//当异常发生时
onthrow=<exception name>         debug on throw                    none
onuncaught=y|n                   debug on any uncaught?            n
timeout=<timeout value>          for listen/attach in milliseconds n
mutf8=y|n                        output modified utf-8             n
```



```
#用法
服务器端
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y
客户端：
java -agentlib:jdwp=transport=dt_socket,address=localhost:8080
```

```
C:\Users\admin\Desktop\demo\target>java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=9990 -jar demo-0.0.1-SNAPSHOT.jar


Listening for transport dt_socket at address: 9990
```

在idea里面配置客户端详情

![](https://github.com/heartccace/springboot/tree/master/src/main/resources/static/pics/JDWP.png)

### 3、@SpringbootApplication注解详解

@SpringbootApplication(属于springboot注解)是多个注解的组合体

```
// 标识该类是个配置类(属于springboot注解)
@SpringBootConfiguration   //由@Configuration修饰
// 启用自动配置
@EnableAutoConfiguration(属于springboot注解)
// 启用组件扫描
@ComponentScan
```

#### 3.1、@Configuration详解

// 表明声名一个或者多个被@Bean注解修饰方法的类可以在运行时被spring容器利用去生成bean definitions（bean定义）和服务请求，

Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime, for example:
   @Configuration
   public class AppConfig {

       @Bean
       public MyBean myBean() {
           // instantiate, configure and return bean ...
       }
//启用@Configuration类可以通过AnnotationConfigApplicationContext。

Bootstrapping @Configuration classes
Via AnnotationConfigApplicationContext
@Configuration classes are typically bootstrapped using either AnnotationConfigApplicationContext or its web-capable variant, AnnotationConfigWebApplicationContext. A simple example with the former follows:
   AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
   ctx.register(AppConfig.class);
   ctx.refresh();
   MyBean myBean = ctx.getBean(MyBean.class);
   // use myBean ...

See the AnnotationConfigApplicationContext javadocs for further details, and see AnnotationConfigWebApplicationContext for web configuration instructions in a Servlet container.
Via Spring <beans> XML
As an alternative to registering @Configuration classes directly against an AnnotationConfigApplicationContext, @Configuration classes may be declared as normal <bean> definitions within Spring XML files:
   <beans>
      <context:annotation-config/>
      <bean class="com.acme.AppConfig"/>
   </beans>

In the example above, <context:annotation-config/> is required in order to enable ConfigurationClassPostProcessor and other annotation-related post processors that facilitate handling @Configuration classes.
Via component scanning
@Configuration is meta-annotated with @Component, therefore @Configuration classes are candidates for component scanning (typically using Spring XML's <context:component-scan/> element) and therefore may also take advantage of @Autowired/@Inject like any regular @Component. In particular, if a single constructor is present autowiring semantics will be applied transparently for that constructor:
   @Configuration
   public class AppConfig {

       private final SomeBean someBean;
      
       public AppConfig(SomeBean someBean) {
           this.someBean = someBean;
       }
      
       // @Bean definition using "SomeBean"

   }

// @Configuration类不仅可以使用组件扫描来引导，还可以自己使用@ComponentScan注释配置组件扫描：

@Configuration classes may not only be bootstrapped using component scanning, but may also themselves configure component scanning using the @ComponentScan annotation:
   @Configuration
   @ComponentScan("com.acme.app.services")
   public class AppConfig {
       // various @Bean definitions ...
   }
See the @ComponentScan javadocs for details.
Working with externalized values
Using the Environment API
Externalized values may be looked up by injecting the Spring org.springframework.core.env.Environment into a @Configuration class — for example, using the @Autowired annotation:
   @Configuration
   public class AppConfig {

       @Autowired Environment env;
      
       @Bean
       public MyBean myBean() {
           MyBean myBean = new MyBean();
           myBean.setName(env.getProperty("bean.name"));
           return myBean;
       }
   }
Properties resolved through the Environment reside in one or more "property source" objects, and @Configuration classes may contribute property sources to the Environment object using the @PropertySource annotation:
   @Configuration
   @PropertySource("classpath:/com/acme/app.properties")
   public class AppConfig {

       @Inject Environment env;
      
       @Bean
       public MyBean myBean() {
           return new MyBean(env.getProperty("bean.name"));
       }
   }
See the Environment and @PropertySource javadocs for further details.
Using the @Value annotation
Externalized values may be injected into @Configuration classes using the @Value annotation:
   @Configuration
   @PropertySource("classpath:/com/acme/app.properties")
   public class AppConfig {

       @Value("${bean.name}") String beanName;
      
       @Bean
       public MyBean myBean() {
           return new MyBean(beanName);
       }
   }
This approach is often used in conjunction with Spring's PropertySourcesPlaceholderConfigurer that can be enabled automatically in XML configuration via <context:property-placeholder/> or explicitly in a @Configuration class via a dedicated static @Bean method (see "a note on BeanFactoryPostProcessor-returning @Bean methods" of @Bean's javadocs for details). Note, however, that explicit registration of a PropertySourcesPlaceholderConfigurer via a static @Bean method is typically only required if you need to customize configuration such as the placeholder syntax, etc. Specifically, if no bean post-processor (such as a PropertySourcesPlaceholderConfigurer) has registered an embedded value resolver for the ApplicationContext, Spring will register a default embedded value resolver which resolves placeholders against property sources registered in the Environment. See the section below on composing @Configuration classes with Spring XML using @ImportResource; see the @Value javadocs; and see the @Bean javadocs for details on working with BeanFactoryPostProcessor types such as PropertySourcesPlaceholderConfigurer.
Composing @Configuration classes
With the @Import annotation
@Configuration classes may be composed using the @Import annotation, similar to the way that <import> works in Spring XML. Because @Configuration objects are managed as Spring beans within the container, imported configurations may be injected — for example, via constructor injection:
   @Configuration
   public class DatabaseConfig {

       @Bean
       public DataSource dataSource() {
           // instantiate, configure and return DataSource
       }
   }

   @Configuration
   @Import(DatabaseConfig.class)
   public class AppConfig {

       private final DatabaseConfig dataConfig;
      
       public AppConfig(DatabaseConfig dataConfig) {
           this.dataConfig = dataConfig;
       }
      
       @Bean
       public MyBean myBean() {
           // reference the dataSource() bean method
           return new MyBean(dataConfig.dataSource());
       }
   }
Now both AppConfig and the imported DatabaseConfig can be bootstrapped by registering only AppConfig against the Spring context:
   new AnnotationConfigApplicationContext(AppConfig.class);
With the @Profile annotation
@Configuration classes may be marked with the @Profile annotation to indicate they should be processed only if a given profile or profiles are active:
   @Profile("development")
   @Configuration
   public class EmbeddedDatabaseConfig {

       @Bean
       public DataSource dataSource() {
           // instantiate, configure and return embedded DataSource
       }
   }

   @Profile("production")
   @Configuration
   public class ProductionDatabaseConfig {

       @Bean
       public DataSource dataSource() {
           // instantiate, configure and return production DataSource
       }
   }
Alternatively, you may also declare profile conditions at the @Bean method level — for example, for alternative bean variants within the same configuration class:
   @Configuration
   public class ProfileDatabaseConfig {

       @Bean("dataSource")
       @Profile("development")
       public DataSource embeddedDatabase() { ... }
      
       @Bean("dataSource")
       @Profile("production")
       public DataSource productionDatabase() { ... }
   }
See the @Profile and org.springframework.core.env.Environment javadocs for further details.
With Spring XML using the @ImportResource annotation
As mentioned above, @Configuration classes may be declared as regular Spring <bean> definitions within Spring XML files. It is also possible to import Spring XML configuration files into @Configuration classes using the @ImportResource annotation. Bean definitions imported from XML can be injected — for example, using the @Inject annotation:
   @Configuration
   @ImportResource("classpath:/com/acme/database-config.xml")
   public class AppConfig {

       @Inject DataSource dataSource; // from XML
      
       @Bean
       public MyBean myBean() {
           // inject the XML-defined dataSource bean
           return new MyBean(this.dataSource);
       }
   }
With nested @Configuration classes
@Configuration classes may be nested within one another as follows:
   @Configuration
   public class AppConfig {

       @Inject DataSource dataSource;
      
       @Bean
       public MyBean myBean() {
           return new MyBean(dataSource);
       }
      
       @Configuration
       static class DatabaseConfig {
           @Bean
           DataSource dataSource() {
               return new EmbeddedDatabaseBuilder().build();
           }
       }
   }
When bootstrapping such an arrangement, only AppConfig need be registered against the application context. By virtue of being a nested @Configuration class, DatabaseConfig will be registered automatically. This avoids the need to use an @Import annotation when the relationship between AppConfig and DatabaseConfig is already implicitly clear.
Note also that nested @Configuration classes can be used to good effect with the @Profile annotation to provide two options of the same bean to the enclosing @Configuration class.
Configuring lazy initialization
By default, @Bean methods will be eagerly instantiated at container bootstrap time. To avoid this, @Configuration may be used in conjunction with the @Lazy annotation to indicate that all @Bean methods declared within the class are by default lazily initialized. Note that @Lazy may be used on individual @Bean methods as well.
Testing support for @Configuration classes
The Spring TestContext framework available in the spring-test module provides the @ContextConfiguration annotation which can accept an array of component class references — typically @Configuration or @Component classes.
   @RunWith(SpringRunner.class)
   @ContextConfiguration(classes = {AppConfig.class, DatabaseConfig.class})
   public class MyTests {

       @Autowired MyBean myBean;
      
       @Autowired DataSource dataSource;
      
       @Test
       public void test() {
           // assertions against myBean ...
       }
   }
See the TestContext framework  reference documentation for details.
Enabling built-in Spring features using @Enable annotations
Spring features such as asynchronous method execution, scheduled task execution, annotation driven transaction management, and even Spring MVC can be enabled and configured from @Configuration classes using their respective "@Enable" annotations. See @EnableAsync, @EnableScheduling, @EnableTransactionManagement, @EnableAspectJAutoProxy, and @EnableWebMvc for details.
Constraints when authoring @Configuration classes
Configuration classes must be provided as classes (i.e. not as instances returned from factory methods), allowing for runtime enhancements through a generated subclass.
Configuration classes must be non-final (allowing for subclasses at runtime), unless the proxyBeanMethods flag is set to false in which case no runtime-generated subclass is necessary.
Configuration classes must be non-local (i.e. may not be declared within a method).
Any nested configuration classes must be declared as static.
@Bean methods may not in turn create further configuration classes (any such instances will be treated as regular beans, with their configuration annotations remaining undetected).
Since:
3.0

AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
   ctx.register(AppConfig.class);
   ctx.refresh();
   MyBean myBean = ctx.getBean(MyBean.class);
   // use myBean ...

#### 3.2、@EnableAutoConfiguration



```
@AutoConfigurationPackage(springboot注解)
@Import(AutoConfigurationImportSelector.class)
```

启用spring应用上下文自动配置功能，尝试猜测和配置你可能需要的beans。

Enable auto-configuration of the Spring Application Context, attempting to guess and configure beans that 

自动配置的应用通常基于类路径和你已经敌营的bean。

you are likely to need. Auto-configuration classes are usually applied based on your classpath and what 

比如，你有一个 tomcat-embedded.jar的包在你的类路径下，那么你很有可能需要一个TomcatServletWebServerFactory 的bean

beans you have defined. For example, if you have tomcat-embedded.jar on your classpath you are likely to want a TomcatServletWebServerFactory (unless you have defined your own ServletWebServerFactory bean).

当使用SpringBootApplication，容器的自动配置自动生效

When using SpringBootApplication, the auto-configuration of the context is automatically enabled and 

再添加额外的注解也没有额外的效果

adding this annotation has therefore no additional effect.
Auto-configuration tries to be as intelligent as possible and will back-away as you define more of your own configuration. You can always manually exclude() any configuration that you never want to apply (use excludeName() if you don't have access to them). You can also exclude them via the 

自动配置总是在用户自定义bean被注册之后被应用

spring.autoconfigure.exclude property. Auto-configuration is always applied after user-defined beans have been registered.
The package of the class that is annotated with @EnableAutoConfiguration, usually via @SpringBootApplication, has specific significance and is often used as a 'default'. For example, it will be used when scanning for @Entity classes. It is generally recommended that you place @EnableAutoConfiguration (if you're not using @SpringBootApplication) in a root package so that all sub-packages and classes can be searched.
Auto-configuration classes are regular Spring Configuration beans. They are located using the SpringFactoriesLoader mechanism (keyed against this class). Generally auto-configuration beans are @Conditional beans (most often using @ConditionalOnClass and @ConditionalOnMissingBean annotations).

### 4、SpringApplication

// 该类用于从一个main方法启动和配置一个Spring应用

Class that can be used to bootstrap and launch a Spring application from a Java main method. By default 

默认情况下，class将执行以下步骤来引导你的应用程序：

class will perform the following steps to bootstrap your application:

// 创建一个合适的应用上下文实例（取决于你的类路径）

Create an appropriate ApplicationContext instance (depending on your classpath)

// 注册一个命令行属性源去暴露命令行参数作为spring属性

Register a CommandLinePropertySource to expose command line arguments as Spring properties

// 刷新应用上下文，加载所有单例

Refresh the application context, loading all singleton beans

// 触发每一个CommandLineRunner  bean

Trigger any CommandLineRunner beans

// 在大多数情况下静态的run方法可以被你主方法直接调用去启动你的应用

In most circumstances the static run(Class, String[]) method can be called directly from your main method to bootstrap your application:
   @Configuration
   @EnableAutoConfiguration
   public class MyApplication  {

     // ... Bean definitions
      
     public static void main(String[] args) {
       SpringApplication.run(MyApplication.class, args);
     }
   }

//对于更高级的配置，可以在运行之前创建和定制SpringApplication实例。

For more advanced configuration a SpringApplication instance can be created and customized before being run:
   public static void main(String[] args) {
     SpringApplication application = new SpringApplication(MyApplication.class);
     // ... customize application settings here
     application.run(args)
   }

//SpringApplications 可以读取来自各种不同的来源。

SpringApplications can read beans from a variety of different sources. It is generally recommended that a single @Configuration class is used to bootstrap your application, however, you may also set sources from:
The fully qualified class name to be loaded by AnnotatedBeanDefinitionReader
The location of an XML resource to be loaded by XmlBeanDefinitionReader, or a groovy script to be loaded by GroovyBeanDefinitionReader
The name of a package to be scanned by ClassPathBeanDefinitionScanner
Configuration properties are also bound to the SpringApplication. This makes it possible to set SpringApplication properties dynamically, like additional sources ("spring.main.sources" - a CSV list) the flag to indicate a web environment ("spring.main.web-application-type=none") or the flag to switch off the banner ("spring.main.banner-mode=off").
See Also:
run(Class, String[]), run(Class[], String[]), SpringApplication(Class...)



运行分析：

```

// Application.class
public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
   return new SpringApplication(primarySources).run(args);
}
```

new SpringApplication():Create a new SpringApplication instance. The application context will load beans from the specified primary sources（创建一个新的SpringApplication 实例，该实例会从指定原文件加载bean）



```
// Application.class
public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
   this.resourceLoader = resourceLoader;
   Assert.notNull(primarySources, "PrimarySources must not be null");
   this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
   // 推断应用类型(web)
   this.webApplicationType = WebApplicationType.deduceFromClasspath();
   // 查找classpath下面的meta-info目录，并加载spring.factories文件，将实现ApplicationContextInitializer接口的类实例化，并将实例对象保存到initializers的属性中
   setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
   // 查找classpath下面的meta-info目录，并加载spring.factories文件，将实现ApplicationListener接口的类实例化，并将实例对象保存到listeners的属性中
   setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
   this.mainApplicationClass = deduceMainApplicationClass();
}


private <T> Collection<T> getSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, Object... args) {
// 获取类加载器，首先获取资源类加载器，获取不到就从线程上下文中获取，仍然获取就获取ClassUtils的类加载器
		ClassLoader classLoader = getClassLoader();
		// Use names and ensure unique to protect against duplicates
		Set<String> names = new LinkedHashSet<>(SpringFactoriesLoader.loadFactoryNames(type, classLoader));
		List<T> instances = createSpringFactoriesInstances(type, parameterTypes, classLoader, args, names);
		AnnotationAwareOrderComparator.sort(instances);
		return instances;
	}
```



### 5、SpringFactoryLoader

框架内部内部使用的通用工厂加载机制。

General purpose factory loading mechanism for internal use within the framework.

SpringFactoriesLoader从类路径下的META-INF/spring.factories或类路径下多个jar文件中的META-INF/spring.factories加载和实例化所给类型

SpringFactoriesLoader loads and instantiates factories of a given type from "META-INF/spring.factories" files which may be present in multiple JAR files in the classpath. The spring.factories file must be in Properties format, where the key is the fully qualified name of the interface or abstract class, and the value is a comma-separated list of implementation class names. For example:
example.MyService=example.MyServiceImpl1,example.MyServiceImpl2
where example.MyService is the name of the interface, and MyServiceImpl1 and MyServiceImpl2 are two implementations.

### 6.ApplicationContext

application context是一个为应用提供配置的核心接口，当应用运行时，是可读的。

- 提供访问bean factory方法获取应用的组件
- 加载资源文件的能力
- 发布消息
- 继承自夫context，优先级更高

### 7.SpringApplicationRunListener和ApplicationListener

SpringApplicationRunListener对springboot启动进行监听，在不同阶段调用EventPublishingRunListener，进行事件广播。调用springboot继承的spring的Application的类。

```
# pringboot事件 
#在首次启动run方法时立即调用。 可以用于非常早期初始化
ApplicationRunListener.starting() 对应的事件是 ApplicationStartingEvent	

#一旦environment被准备，立即调用，但是在applicationcontext被创建前，
ApplicationRunListener.environmentPrepared(ConfigurableEnvironment environment)对应事件ApplicationEnvironmentPreparedEvent

#当application被创建和准备好之后立即调用，但是此时资源文件未被加载
ApplicationRunListener.contextPrepared(ConfigurableApplicationContext context)对应事件ApplicationContextInitializedEvent


#applicaiton context在加载完成之后，刷新之前被调用
ApplicationRunListener.contextLoaded(ConfigurableApplicationContext context)对应事件ApplicationPreparedEvent
 

#容器被刷新，启动但是此时未调用CommandLineRunner和ApplicationRunner
ApplicationRunListener.started(ConfigurableApplicationContext context)对应事件ApplicationStartedEvent


#在run方法完成前立即被调用，此时容器已经被刷新，CommandLineRunner和ApplicationRunner已经被调用
ApplicationRunListener.running(ConfigurableApplicationContext context)对应事件ApplicationReadyEvent
```

#当应用运行发生异常时调用

ApplicationRunListener.failed(ConfigurableApplicationContext context, Throwable exception)对应事件ApplicationFailedEvent

### 8.日志



