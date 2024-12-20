package com.kim.restaurantreservation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kim.restaurantreservation.CustomerBookingSystemApplication;
import com.kim.restaurantreservation.dto.DateAndGuestUpdateRequest;
import com.kim.restaurantreservation.dto.ReservationRequest;
import com.kim.restaurantreservation.dto.ReservationResponse;
import com.kim.restaurantreservation.entity.Reservation;
import com.kim.restaurantreservation.service.EmailServiceImpl;
import com.kim.restaurantreservation.service.NotificationService;
import com.kim.restaurantreservation.service.ReservationService;
import com.kim.restaurantreservation.service.SmsServiceImpl;


@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {CustomerBookingSystemApplication.class, NotificationService.class, SmsServiceImpl.class, EmailServiceImpl.class, ReservationService.class})
class ReservationControllerTest {

	@Autowired
	ReservationService reservationService;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	String customerName = "Kim";
	
	ReservationRequest reservationRequest = new ReservationRequest();
	
	ReservationResponse response = new ReservationResponse();
	
	
	@BeforeEach
    void setup() {
		System.out.print("Setting up");
			reservationRequest.setContactChannel("sms");
			reservationRequest.setCustomerName("Kim");
			reservationRequest.setEmail("kim@email.com");
			reservationRequest.setGuestNumber(4);
			reservationRequest.setPhoneNumber("982722221");
			reservationRequest.setReservationDatetime(LocalDateTime.parse("2024-12-19T09:17:10"));

		response = reservationService.saveReservation(reservationRequest);
    }
	
	@Test
	@Order(1)
	@DisplayName("Create reservation is successfully inserted and generated a reservation id.")
	void testSaveReservationIfSuccess() {

		assertEquals("Successfully Inserted.", response.getMessage());
		assertNotNull(response.getReservationId());

	}

	@Test
	@Order(2)
	@DisplayName("Retrieve reservation by customer returns a non empty list.")
	void testGetReservationByCustomerName() {
		List<Reservation> reservation = reservationService.getReservationByCustomerName(customerName);
		assertFalse(reservation.isEmpty());
	}

	@Test
	@Order(3)
	@DisplayName("Retrieve reservation by id returns not null.")
	void testGetReservationById() {
		List<Reservation> reservationList = reservationService.getReservationByCustomerName(customerName);
		
		Reservation reservation = reservationService.getReservationById(reservationList.get(0).getReservationId());
		
		assertNotNull(reservation);
	}

	@Test
	@Order(4)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@DisplayName("Reservation is successfully canceled with status False.")
	void testCancelReservation() {
		List<Reservation> reservationList = reservationService.getReservationByCustomerName(customerName);
		
		assertTrue(reservationList.get(0).isStatus());
		
		response = reservationService.setStatusToCancelById(reservationList.get(0).getReservationId());
		
		testEntityManager.flush();
		testEntityManager.clear();
		
		Reservation reservation = reservationService.getReservationById(response.getReservationId());
		
		assertEquals("Successefully Cancelled.", response.getMessage());
		assertFalse(reservation.isStatus());
	}

	@Test
	@Order(5)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@DisplayName("Reservation is successfully updated and changes are reflected.")
	void testUpdateReservationDateAndGuestReturnSuccess() {
		
		List<Reservation> reservationList = reservationService.getReservationByCustomerName(customerName);
		
		DateAndGuestUpdateRequest dateAndGuestUpdateRequest = new DateAndGuestUpdateRequest();
			dateAndGuestUpdateRequest.setReservationId(reservationList.get(0).getReservationId());
			dateAndGuestUpdateRequest.setNewReservationDate(LocalDateTime.parse("2024-12-19T09:17:10"));
			dateAndGuestUpdateRequest.setNewGuestNumber(10);
			
		response = reservationService.updateReservationDateAndGuestById(dateAndGuestUpdateRequest);
		
		testEntityManager.flush();
		testEntityManager.clear();
		
		Reservation reservation = reservationService.getReservationById(reservationList.get(0).getReservationId());
		
		System.out.println("Guest Number: " + reservation.getGuestNumber());
		assertEquals("Successefully Updated.", response.getMessage());
		assertEquals(10,reservation.getGuestNumber());
	}

}
