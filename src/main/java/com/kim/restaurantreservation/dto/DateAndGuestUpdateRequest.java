package com.kim.restaurantreservation.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DateAndGuestUpdateRequest {

	private String reservationId;
	private LocalDateTime newReservationDate;
	private int newGuestNumber;
	
	@Override
	public String toString() {
		return "DateAndGuestUpdateRequest [reservationId=" + reservationId + ", newReservationDate="
				+ newReservationDate + ", newGuestNumber=" + newGuestNumber + "]";
	}
}
