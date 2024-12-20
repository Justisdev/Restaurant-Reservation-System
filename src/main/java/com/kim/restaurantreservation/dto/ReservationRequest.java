package com.kim.restaurantreservation.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationRequest {

	private String customerName;
	
	private String phoneNumber;

	private String email;

	private LocalDateTime reservationDatetime;

	private int guestNumber;
	
	private String contactChannel;

	@Override
	public String toString() {
		return "ReservationRequest [customerName=" + customerName + ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", reservationDatetime=" + reservationDatetime + ", guestNumber=" + guestNumber
				+ ", contactChannel=" + contactChannel + "]";
	}

}
