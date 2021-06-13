package com.rovio.plushmarket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlushMarketApplication {
	static final Logger log = LoggerFactory.getLogger(PlushMarketApplication.class);
	public static void main(String[] args) {
		log.info("Before Starting PlushMarketApplication");
		SpringApplication.run(PlushMarketApplication.class, args);
		log.debug("Starting PlushMarketApplication in debug with {} args", args.length);
		log.info("Starting PlushMarketApplication with {} args.", args.length);
	}

}
