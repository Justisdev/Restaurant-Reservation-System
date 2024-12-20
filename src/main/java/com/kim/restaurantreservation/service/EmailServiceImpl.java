package com.kim.restaurantreservation.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements MessageService {

	@Override
	public void sendMessage(String recipient, String message) {
		log.info("Sending Email To: {} - {}", recipient, message);
	}

}
