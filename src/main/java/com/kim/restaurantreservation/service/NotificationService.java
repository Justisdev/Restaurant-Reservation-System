package com.kim.restaurantreservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kim.restaurantreservation.config.Constant;
import com.kim.restaurantreservation.entity.Reservation;
import com.kim.restaurantreservation.repository.ReservationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {
	
	private ReservationRepository reservationRepository;

	@Autowired
	private SmsServiceImpl smsService;
	
	@Autowired
	private EmailServiceImpl emailService;

	@Autowired
	public NotificationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}
	
	public List<Reservation> getAllReservationByDatetime(String date){
		return reservationRepository.getAllReservationByDatetime(date);
	}
	
	public void sendNotification(Reservation reservation, Constant.TransactionType trxType) {
		String message = generateMessage(trxType, reservation);
		
		if(reservation.getContactChannel().equalsIgnoreCase(Constant.CHANNEL_SMS)) {
			smsService.sendMessage(
					reservation.getPhoneNumber(), 
					String.format(message, reservation.getReservationId()));
		}
		
		else if(reservation.getContactChannel().equalsIgnoreCase(Constant.CHANNEL_EMAIL)) {
			emailService.sendMessage(
					reservation.getEmail(), 
					String.format(message, reservation.getReservationId()));
		}
	}
	
	public String generateMessage(Constant.TransactionType trxType, Reservation reservation) {
		switch(trxType) {
			case CREATE:
				return String.format(Constant.NOTIFICATION_MSG_CREATE, reservation.getReservationId());
			case UPDATE:
				return String.format(Constant.NOTIFICATION_MSG_UPDATE, reservation.getReservationId());
			case CANCEL:
				return String.format(Constant.NOTIFICATION_MSG_CANCEL, reservation.getReservationId());
			case REMINDER:
				return String.format(Constant.REMINDER_MSG, reservation.getReservationDatetime());
			default:
				break;
		}
		return "";
	}
}
