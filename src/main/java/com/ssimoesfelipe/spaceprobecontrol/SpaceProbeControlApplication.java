package com.ssimoesfelipe.spaceprobecontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpaceProbeControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceProbeControlApplication.class, args);
	}

}
