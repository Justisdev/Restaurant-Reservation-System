package com.kim.restaurantreservation.entity;

import java.time.LocalDateTime;
import java.util.function.BooleanSupplier;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="reservation")
@Setter
@Getter
public class Reservation {

	@Id
	@Column(name="reservation_id")
	private String reservationId;
	
	@Column(name="customer_name", nullable=false)
	private String customerName;
	
	@Column(name="phone_number", nullable=false)
	private String phoneNumber;

	@Column(name="email", nullable=false)
	private String email;

	@Column(name="reservation_datetime", nullable=false)
	private LocalDateTime reservationDatetime;

	@Column(name="guest_number", nullable=false)
	private int guestNumber;
	
	@Column(name="status", nullable=false)
	private boolean status;

	@Column(name="contact_channel", nullable=false)
	private String contactChannel;

	@Column(name="created_date", nullable=false)
	private LocalDateTime createdDate;

	@Column(name="updated_date", nullable=false)
	private LocalDateTime updatedDate;

	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", customerName=" + customerName + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", reservationDatetime=" + reservationDatetime + ", guestNumber="
				+ guestNumber + ", status=" + status + ", contactChannel=" + contactChannel + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + "]";
	}

}
