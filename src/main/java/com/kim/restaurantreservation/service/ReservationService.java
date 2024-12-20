package com.kim.restaurantreservation.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kim.restaurantreservation.config.Constant;
import com.kim.restaurantreservation.dto.DateAndGuestUpdateRequest;
import com.kim.restaurantreservation.dto.ReservationRequest;
import com.kim.restaurantreservation.dto.ReservationResponse;
import com.kim.restaurantreservation.entity.Reservation;
import com.kim.restaurantreservation.repository.ReservationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReservationService {
	
	private final ReservationRepository reservationRepository;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}
	
	public ReservationResponse saveReservation(ReservationRequest reservationRequest) {
		Reservation reservation	= new Reservation();

		String reservationId = generateID();
		
		if(isInvalidReservedDateValid(reservationRequest.getReservationDatetime()))
			return new ReservationResponse("Invalid Reservation Date.",reservationId);
		
		LocalDateTime now = LocalDateTime.now();
		
			reservation.setReservationId(reservationId);
			reservation.setCustomerName(reservationRequest.getCustomerName());
			reservation.setEmail(reservationRequest.getEmail());
			reservation.setGuestNumber(reservationRequest.getGuestNumber());
			reservation.setContactChannel(reservationRequest.getContactChannel());
			reservation.setPhoneNumber(reservationRequest.getPhoneNumber());
			reservation.setReservationDatetime(reservationRequest.getReservationDatetime());
			reservation.setCreatedDate(now);
			reservation.setUpdatedDate(now);
			reservation.setStatus(true);
		//log.info("Inserting: {}",reservation.toString());
		reservationRepository.save(reservation);
		
		notificationService.sendNotification(
				reservation,
				Constant.TransactionType.CREATE);
		
		return new ReservationResponse("Successfully Inserted.",reservationId);
	}
	
	public List<Reservation> getReservationByCustomerName(String customerName){
		//log.info("Customer Name: {}", customerName);
		return reservationRepository.getReservationByCustomerName(customerName);
	}
	
	public ReservationResponse setStatusToCancelById(String reservationId) {
		//log.info("ReservationId: {}", reservationId);
		int response = reservationRepository.setStatusToCancelById(reservationId, LocalDateTime.now());
		
		if(response!=1) return new ReservationResponse("Failed Cancel.",reservationId);
		
		Reservation reservation = getReservationById(reservationId);

		notificationService.sendNotification(
				reservation,
				Constant.TransactionType.CANCEL);
		
		return new ReservationResponse("Successefully Cancelled.",reservationId);
	}
	
	public Reservation getReservationById(String reservationId) {
		return reservationRepository.getReservationById(reservationId);
	}
	
	public ReservationResponse updateReservationDateAndGuestById(DateAndGuestUpdateRequest dateAndGuestUpdateRequest) {
		log.info("{}", dateAndGuestUpdateRequest.toString());
		
		int response = reservationRepository.updateDateAndGuestById(
				dateAndGuestUpdateRequest.getNewReservationDate(), 
				dateAndGuestUpdateRequest.getNewGuestNumber(),
				dateAndGuestUpdateRequest.getReservationId(),
				LocalDateTime.now());
		
		Reservation reservation = reservationRepository
				.getReservationById(
						dateAndGuestUpdateRequest.getReservationId());
		
		if(response!=1) 
			return new ReservationResponse("Failed Update.",dateAndGuestUpdateRequest.getReservationId());
		

		notificationService.sendNotification(
				reservation,
				Constant.TransactionType.UPDATE);
		
		return new ReservationResponse("Successefully Updated.",dateAndGuestUpdateRequest.getReservationId());
	}
	
	private String generateID() {
		StringBuilder idBuilder = new StringBuilder();
		LocalDateTime timeNow = LocalDateTime.now();
			idBuilder.append("RESV");
			idBuilder.append(timeNow.format(DateTimeFormatter.ofPattern("yyMMddhhmmssMs")));
		return idBuilder.toString();
	}
	
	private boolean isInvalidReservedDateValid(LocalDateTime reservedDatetime) {
		return reservedDatetime.isBefore(LocalDateTime.now());
	}
}
