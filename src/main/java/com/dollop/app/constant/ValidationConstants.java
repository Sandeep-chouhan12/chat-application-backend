package com.dollop.app.constant;

public class ValidationConstants {

	public static final int NAME_MIN_LENGTH = 2;
	public static final int NAME_MAX_LENGTH = 50;
	public static final int PASSWORD_MIN_LENGTH = 8;
	public static final int PASSWORD_MAX_LENGTH = 30;
	public static final int EMAIL_MIN_LENGTH = 10;
	public static final int EMAIL_MAX_LENGTH = 50;
	public static final int PHONE_NUMBER_LENGTH = 10;
	public static final int ADDRESS_MIN_LENGTH = 5;
	public static final int ADDRESS_MAX_LENGTH = 100;
	public static final int OTP_LENGTH = 6;
	
	//regex validation constants
	public static final String NAME_REGEX = "^[a-zA-Z\\s]*$";
	public static final String EMAIL_REGEX = "^[a-z][a-z0-9._-]*@[a-z0-9.-]+\\.com$";
	public static final String PHONE_REGEX = "^[6-9]\\d{9}$";
	public static final String ADDRESS_REGEX = "^[A-Za-z0-9,\\s]*$";
	public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,}$";
	public static final String OTP_REGEX = "\\d{6}";
	public static final String USER_ROLE_REGEX = "^(SHOP|ADMIN|DELIVERY_PARTNER)$";
	
	
	
	// First two uppercase letters (State Code, e.g., MH, KA, DL)
	//	Next two digits (RTO Code, e.g., 12, 34)
	// Next one or two uppercase letters (Alphabetic series, e.g., AB, X, YZ)
    //	Last four digits (Unique vehicle number, e.g., 1234)
	public static final String VEHICLE_NUMBER_REGEX = "^[A-Z]{2}\\d{2}[A-Z]{1,2}\\d{4}$"; 
	
	
	
	// First two uppercase letters (State Code, e.g., MH, DL, KA)
	//Next two digits (RTO Code, e.g., 12, 34)
	//Last 11 digits (Unique License Number
	public static final String DRIVING_LICENSE_REGEX = "^[A-Z]{2}\\d{2}\\d{11}$"; 
	
	
	public static final String ADHAR_NUMBER_REGEX = "^\\d{12}$";
	
   //	First 5 uppercase letters (e.g., ABCDE)
   //	Next 4 digits (e.g., 1234)
   //	Last 1 uppercase letter (e.g., F)
	public static final String PAN_NUMBER_REGEX = "^[A-Z]{5}\\d{4}[A-Z]$";
	
	//Allows only digits (0-9) with a length between 9 and 18
	public static final String BANK_ACCOUNT_REGEX = "^\\d{9,18}$";
	
	// First 4 uppercase letters (Bank Code)
	//	The 5th character must be '0' (Reserved for future use)
	// Last 6 characters can be uppercase letters or digits (Branch Code)
	public static final String IFSC_CODE_REGEX = "^[A-Z]{4}0[A-Z0-9]{6}$";

	public static final String UPI_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z]+$";

	
	//Format: 2-digit state code + 10-digit PAN + 1 character + Z + 1 digit
    //Example: 27ABCDE1234FZ1
	public static final String BUSINESS_LICENSE_NUM_REGEX = "^\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[Z]{1}[0-9]{1}$";
	
	//contains 6 digit and start with 1 to 9 not 0
	public static final String PINCODE_REGEX = "^[1-9][0-9]{5}$"; 
	
	
	//ex. FlowMart 24x7
	public static final String SHOP_NAME_REGEX = "^[a-zA-Z0-9\\s,.'-]{3,50}$";
	
	
	// new project 
	
	public static final String USER_NAME_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*_)[a-zA-Z0-9_]{4,}$";
	public static final String ID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

}
