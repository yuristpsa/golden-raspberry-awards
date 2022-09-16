package com.yurisa.gra;

import com.yurisa.gra.domain.csv.CSVToDatabaseComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class GoldenRaspberryAwardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldenRaspberryAwardsApplication.class, args);
	}

	@Autowired
	private CSVToDatabaseComponent csvToDatabaseComponent;

}
