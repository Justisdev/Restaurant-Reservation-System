package com.kim.restaurantreservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.kim.restaurantreservation.config.Constant;
import com.kim.restaurantreservation.entity.Reservation;
import com.kim.restaurantreservation.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class CustomerBookingSystemApplication {


	@Value("${reminder.period}")
	private int reminderPeriod;
	
	@Autowired
	NotificationService notificationService;
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerBookingSystemApplication.class, args);
	}

	public static void sendReminders() {
		
	}
	
	@Scheduled(cron = "${app.reminder.cron}")
	public void runCronPerExpression() {
	   sendReminder();
	}
	
	public void sendReminder() {
		LocalDateTime time = LocalDateTime.now().plusHours(reminderPeriod);
		log.info("Checking datetime {}", time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
		List<Reservation> allReservations = 
				notificationService.getAllReservationByDatetime(
						time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
		
		for(Reservation reservation : allReservations) {
			log.info("Sending reminder to {}", reservation.getReservationId());
			notificationService.sendNotification(
					reservation,
					Constant.TransactionType.REMINDER);
		}
	}
}
