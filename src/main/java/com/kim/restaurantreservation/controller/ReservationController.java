package com.kim.restaurantreservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kim.restaurantreservation.dto.DateAndGuestUpdateRequest;
import com.kim.restaurantreservation.dto.ReservationRequest;
import com.kim.restaurantreservation.dto.ReservationResponse;
import com.kim.restaurantreservation.entity.Reservation;
import com.kim.restaurantreservation.service.ReservationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/reservation")
@Slf4j
public class ReservationController {

	@Autowired
	private final ReservationService reservationService;
	
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	@PostMapping("")
	public ResponseEntity<ReservationResponse> saveReservation(@RequestBody ReservationRequest reservationRequest) {
		ReservationResponse response = reservationService.saveReservation(reservationRequest);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/customerName/{customerName}")
	public List<Reservation> getReservationByCustomerName(@PathVariable String customerName){
		return reservationService.getReservationByCustomerName(customerName);
	}
	
	@GetMapping("reservationId/{reservationId}")
	public Reservation getReservationById(@PathVariable String reservationId){
		return reservationService.getReservationById(reservationId);
	}
	
	@PutMapping("/cancel")
	public ReservationResponse cancelReservation(@RequestParam String reservationId) {
		return reservationService.setStatusToCancelById(reservationId);
	}
	
	@PutMapping("/reservationDateAndGuest")
	public ReservationResponse updateReservationDateAndGuest(@RequestBody DateAndGuestUpdateRequest dateAndGuestUpdateRequest) {
		return reservationService.updateReservationDateAndGuestById(dateAndGuestUpdateRequest);
	}
}
