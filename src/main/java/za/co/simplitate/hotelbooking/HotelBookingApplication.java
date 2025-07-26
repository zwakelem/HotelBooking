package za.co.simplitate.hotelbooking;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import za.co.simplitate.hotelbooking.dtos.NotificationTO;
import za.co.simplitate.hotelbooking.notifications.NotificationService;

@EnableAsync
@EnableJpaRepositories
@SpringBootApplication
public class HotelBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingApplication.class, args);
	}

}
