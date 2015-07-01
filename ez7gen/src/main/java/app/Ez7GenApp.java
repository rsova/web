package app;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@ComponentScan("hapi.adt.a01")
//@ComponentScan({"com.teradata.notification","com.teradata.dal"})
public class Ez7GenApp {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Ez7GenApp.class, args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}
}
