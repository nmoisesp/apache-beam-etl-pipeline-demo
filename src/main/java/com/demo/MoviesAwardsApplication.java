package com.demo;

import com.demo.exceptions.GlobalApplicationException;
import org.apache.beam.repackaged.core.org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoviesAwardsApplication {

	public static void main(String[] args) throws GlobalApplicationException {
		try {
			SpringApplication.run(MoviesAwardsApplication.class, args);
		} catch (Exception e) {
			if(!StringUtils.isEmpty(e.getMessage()))
				throw new GlobalApplicationException(e.getMessage(), e);
		}
	}
}
