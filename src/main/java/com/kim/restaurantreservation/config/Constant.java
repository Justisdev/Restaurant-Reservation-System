package com.kim.restaurantreservation.config;

public class Constant {

	public enum TransactionType{
			CREATE, UPDATE, CANCEL, REMINDER;
	}
	
	public final static String CHANNEL_SMS = "SMS";
	public final static String CHANNEL_EMAIL = "EMAIL";
	
	
	public final static String NOTIFICATION_MSG_CREATE = "Your reservation is confirmed with ID: %s";
	public final static String NOTIFICATION_MSG_UPDATE = "Your reservation with ID %s was succesfully updated";
	public final static String NOTIFICATION_MSG_CANCEL = "Your reservation with ID %s was succesfully cancelled";
	public final static String REMINDER_MSG = "You have a scheduled reservation on : %s";
}
