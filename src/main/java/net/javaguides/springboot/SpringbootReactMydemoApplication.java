package net.javaguides.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringbootReactMydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootReactMydemoApplication.class, args);
	}

}
