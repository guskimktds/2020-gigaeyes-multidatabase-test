package io.gusraccoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MultiDataSourceTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiDataSourceTestApplication.class, args);
	}

}
