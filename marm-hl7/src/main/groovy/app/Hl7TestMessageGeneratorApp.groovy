package app


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
//@ComponentScan("hapi.adt.a01")
//@ComponentScan({"com.teradata.notification","com.teradata.dal"})
class Hl7TestMessageGeneratorApp {

//TODO 
// - Spring Boot
// - Controller
// Properties reader
// XML parser
// Message generator
				
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Hl7TestMessageGeneratorApp.class, args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

}