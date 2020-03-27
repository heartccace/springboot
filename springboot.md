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

1、java -jar + jar或者war，根据系统类加载器去解析并加载meta-info下面的配置MANIFEST.MF中的Main-Class，结果根据jar或者war加载JarLauncher或者WarLauncher，即其中任意一个会去解析当前路径，加载BOOT-INFO下面的lib和class，根据MANIFEST.MF中的Start-Class启动应用程序

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

