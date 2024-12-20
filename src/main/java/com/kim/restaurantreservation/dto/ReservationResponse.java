package com.kim.restaurantreservation.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationResponse {

	private String message;
	
	private String reservationId;
	
	public ReservationResponse(String message, String reservationId) {
		this.message = message;
		this.reservationId = reservationId;
	}
	
	public ReservationResponse() {
	}
	
}
