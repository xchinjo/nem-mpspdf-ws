package io.nem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.nem.apps.builders.ConfigurationBuilder;

@SpringBootApplication
public class NemApplication {

	public static void main(String[] args) {


		ConfigurationBuilder.nodeNetworkName("mijinnet").nodeNetworkProtocol("http")
		.nodeNetworkUri("a1.dfintech.com").nodeNetworkPort("7895")
		.setup();
		


		SpringApplication.run(NemApplication.class, args);
	}


	

}
