package com.kim.restaurantreservation.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kim.restaurantreservation.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query(value="SELECT * FROM reservation r WHERE customer_name= :customerName ORDER BY reservation_datetime ASC", nativeQuery=true)
	List<Reservation> getReservationByCustomerName(@Param("customerName") String customerName);
	
	@Query(value="SELECT * FROM reservation r WHERE reservation_datetime = :reservedDatetime AND status=true", nativeQuery=true)
	List<Reservation> getAllReservationByDatetime(@Param("reservedDatetime") String reservedDateTime);
	
	@Query(value="SELECT * FROM reservation r WHERE reservation_id = :reservationId", nativeQuery=true)
	Reservation getReservationById(@Param("reservationId") String reservationId);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE reservation SET status = false, updated_date = :updatedDate WHERE reservation_id = :reservationId", nativeQuery=true)
	int setStatusToCancelById(@Param("reservationId") String reservationId, @Param("updatedDate")LocalDateTime updatedDate);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE reservation SET reservation_datetime = :newDate, guest_number = :newGuest, updated_date = :updatedDate  WHERE reservation_id=:id", nativeQuery=true)
	int updateDateAndGuestById(
			@Param("newDate") LocalDateTime newDate, 
			@Param("newGuest") int newGuest, 
			@Param("id") String id,
			@Param("updatedDate") LocalDateTime updatedDate);
}
