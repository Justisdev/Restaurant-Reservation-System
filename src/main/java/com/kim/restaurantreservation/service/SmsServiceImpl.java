package com.kim.restaurantreservation.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Primary
@Slf4j
public class SmsServiceImpl implements MessageService{

	@Override
	public void sendMessage(String recipient, String message) {
		log.info("Sending SMS to: {} - {}", recipient, message);
	}

}
