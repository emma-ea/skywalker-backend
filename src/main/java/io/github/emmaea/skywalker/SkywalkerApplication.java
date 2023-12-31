package io.github.emmaea.skywalker;

import io.github.emmaea.skywalker.util.ShopSeedRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SkywalkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkywalkerApplication.class, args);
	}

	@Bean
	public ShopSeedRunner shopSeedRunner() {
		return new ShopSeedRunner();
	}

}
