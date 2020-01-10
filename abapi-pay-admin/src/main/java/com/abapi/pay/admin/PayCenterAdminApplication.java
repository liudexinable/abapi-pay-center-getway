package com.abapi.pay.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author  gavine
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PayCenterAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayCenterAdminApplication.class, args);
	}

}
