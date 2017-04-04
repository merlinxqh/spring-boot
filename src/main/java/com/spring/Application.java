package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 基于类的注解:
--初始装载
@SpringBootApplication              spring-boot程序入口标志类
@Configuration                             自动配置，类似于加载spring加载xml 装配所有的bean事务等 所标识的类里面可以使用@Bean 并且启动的时候会初始化bean
@EnableAutoConfiguration       Spring-Boot 根据应用所声明的依赖来对Spring框架进行自动配置
@ComponentScan                     规定扫描包的范围
@PropertySources                  property扫描加载
--业务区分
@Component 定义该bean为一个普通组件
@Repository 定义该bean是一个仓储，用于数据库、mq、redis以及其它一些远程访问的资源
@Service  定义该bean是一个业务逻辑
@Controller  定义该bean是一个控制页面访问层
--加载条件
@Order  配置加载顺序
@ConditionalOnClass  该注解的参数对应的类必须存在，否则不解析该注解修饰的配置类；
@ConditionalOnMissingBean  该注解表示，如果存在它修饰的类的bean，则不需要再创建这个bean；可以给该注解传入参数例如@ConditionOnMissingBean(name = "example")，这个表示如果name为“example”的bean存在，这该注解修饰的代码块不执行。
@AutoConfigureAfter 在摸个自动装载类之后装载


基于属性的注解:
@Value 加载配置属性的值
@Autowired 自动注入bean
@Qualifier 当存在多个bean注入时，需要通过name进行过滤
@Resource 获取当前jvm的resource 也类似依赖注入
@Inject 字段注入bean


基于方法的注解:
@Bean  发布一个返回object类型的bean，类似配置xml发布一个bean
@PostConstruct 指定当类加载完成的时候就会执行该方法
 *
 */

/**
 * java -jar target/spring-boot.jar --spring.profiles.active=prod
 */
/**
 * 执行main方法 启动服务 或者 mvn spring-boot:run 启动服务
 * @author leo
 *
 */
@SpringBootApplication
//@RestController是一个特殊的@Controller,它的返回值可以直接作为http response的body部分直接返回给浏览器
@RestController
//默认配置文件application.properties Or application.yml
//@PropertySource(value={"application.properties","file:/d:/application.properties"},ignoreResourceNotFound=false) 
public class Application {
	
    @RequestMapping("/")
    public String greeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
