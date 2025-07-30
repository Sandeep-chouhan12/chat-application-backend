package com.dollop.app.constant;

public class MessageConstants {
	
	public static final String SUCCESS_MESSAGE = "Success";
	public static final String ERROR_MESSAGE = "Error";
	public static final String NOT_FOUND_MESSAGE = " Not Found";
	public static final String ALREADY_EXISTS_MESSAGE = "Already Exists";
	public static final String INVALID_OTP_MESSAGE = "Invalid OTP or Expired OTP";
	public static final String OTP_SENT_MESSAGE = "OTP Sent Successfully";
	public static final String OTP_VERIFIED = "OTP verified Successfully";
	public static final String OTP_TYPE_NOT_MATCHED = "Not match OTP_TYPE";
	public static final String PASSWORD_MUST_DIFF = "New password must be different from the old password";
	
	public static final String INVALID_EMAIL_PASSWORD ="Invalide Email and Password";
	public static final String INVALID_EMAIL = "Invalide Email";
	public static final String INVALID_PASSWORD = "Invalide Password ";
	public static final String PASSWORD_NOT_MATCH = "Old Password Does Not Match ";
	public static final String PASSWORD_NOT_CHANGED = "Some Problem Ocurs While Changing The Password !";
	public static final String PASSWORD_CHANGED_SUCCESSFULLY = "Password changed successfully";

	public static final String CONFERM_PASSWORD_NOT_MATCH = "Conferm Password Does Not Match ";
	public static final String LOGIN_SUCCESSFULLY = "User Login Successfully";
	
	public static final String PRODUCT_OUT_OF_STOCK = "Product Out of Stock !";
	public static final String NO_MORE_UNITS = "sorry ! We don't have any more units for this item";
	public static final String PRODUCT_ADDED_TO_CART = "Product added to cart successfully";
	public static final String NO_CART = "User have no cart";
	public static final String NO_PRODUCT = "User have no product of this id";
    public static final String PRODUCT_REMOVED = "Product Removed From Cart";
	public static final String EMPTY_CART = "Cart Is Empty !";
	public static final String ALL_CART_ITEMS = "All Cart items";
	public static final String QUANTITY_NOT_LESS_1 = "Item quantity cannot be less than 1.";
	public static final String PRODUCT_QUAN_UDATED = "Product quantity udated";
	
	
	
	
    public static final String INVALID_PHONE_FORMATE ="Invalide phone number formate";
    public static final String INVALID_PASSWORD_FORMATE ="Invalide password formate";
    public static final String INVALID_ADDRESS_FORMATE ="Invalide address formate";
    public static final String INVALID_LICENSE_FORMATE ="Invalide License number formate";
    public static final String INVALID_EMAIL_FORMATE ="Invalide email formate";
    public static final String INVALID_NAME_FORMATE ="Invalide name formate";
    public static final String INVALID_PINCODE_FORMATE ="Invalide pincode formate";
    public static final String INVALID_ROLE ="Invalide Role";
	public static final String INVALID_GENDER = "Invalide Gender";
	public static final String INVALID_VEHICLETYPE = "Invalide Vehicle Type";
    
	public static final String EXPIRED_TOKEN_MESSAGE = "Expired Token";
	public static final String UNAUTHORIZED_MESSAGE = "Unauthorized";
	public static final String FORBIDDEN_MESSAGE = "Forbidden";
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error";
	public static final String USER_NOT_FOUND_MESSAGE = "User Not Found";
	public static final String INCORRECT_CREDENTIALS_MESSAGE = "Incorrect Credentials";
	public static final String PENDING_APPROVAL_MESSAGE = "Your request is pending approval";
	public static final String PROVIDER_NOT_FOUND_MESSAGE = "Provider Not Found";

	public static final String NO_SHOP_AVAILABLE = "No Shop Available";
    public static final String  PAST_DATE ="Date of Birth must be in the past";
	
    public static final String  INVALID_DRIVING_LIC_NUM ="Invalid driving license number format";
    public static final String  DRIVING_LIC_EXP =" Driving license expiry date must be in the future";
    public static final String  INVALID_ADHAR_FORMATE ="Invalid Aadhar number. Must be 12 digits";
    public static final String  INVALID_PAN_FORMATE ="Invalid PAN number format";
    public static final String  INVALID_BANK_ACC_NUM ="  Invalid bank account number";
    public static final String  INVALID_IFSC_CODE ="  Invalid IFSC code format";
    public static final String  INVALID_UPI_ID ="Invalid UPI ID format (e.g., example@upi)";
    public static final String  INVALID_VEHICLE_NUM_FORM ="Invalid vehicle number format";
	public static final String PRODUCT_ALREADY_EXISTS = "Product Already Exist";
	public static final String NO_DELIVERY_PERSON_AVAILABLE = "No Delivery Person Available";
	public static final String Id_NOT_NULL = "ID Not Be Null Or Empty";
	public static final String INVALID_STATUS = "Invalide Status Type";
	
	// General Messages
    public static final String FIELD_REQUIRED = "This field is required";
    public static final String INVALID_FORMAT = "Invalid format provided";
    
    // Delivery Address Messages
    public static final String SHOP_ID_REQUIRED = "Shop ID cannot be null";
    public static final String ADDRESS_NAME_REQUIRED = "Address name is required";
    public static final String ADDRESS_NAME_LENGTH = "Address name cannot exceed 100 characters";
    public static final String CONTACT_PERSON_REQUIRED = "Contact person is required";
    public static final String CONTACT_PERSON_LENGTH = "Contact person cannot exceed 100 characters";
    public static final String CONTACT_NUMBER_REQUIRED = "Contact number is required";
    public static final String CONTACT_NUMBER_FORMAT = "Contact number must be 10 digits";
    public static final String ADDRESS_LINE1_REQUIRED = "Address Line 1 is required";
    public static final String ADDRESS_LINE1_LENGTH = "Address Line 1 cannot exceed 255 characters";
    public static final String ADDRESS_LINE2_LENGTH = "Address Line 2 cannot exceed 255 characters";
    public static final String CITY_REQUIRED = "City is required";
    public static final String CITY_LENGTH = "City cannot exceed 100 characters";
    public static final String STATE_REQUIRED = "State is required";
    public static final String STATE_LENGTH = "State cannot exceed 100 characters";
    public static final String POSTAL_CODE_REQUIRED = "Postal code is required";
    public static final String POSTAL_CODE_FORMAT = "Postal code must be 5-6 digits";
    public static final String COUNTRY_REQUIRED = "Country is required";
    public static final String COUNTRY_LENGTH = "Country cannot exceed 100 characters";
    public static final String LATITUDE_RANGE = "Latitude must be between -90 and 90";
    public static final String LONGITUDE_RANGE = "Longitude must be between -180 and 180";
    public static final String ADDRESS_TYPE_REQUIRED = "Address type cannot be null";
    public static final String IS_DEFAULT_REQUIRED = "isDefault cannot be null";
	public static final String LANDMARK_LENGTH = "Landmark lnegth less than 255";
	public static final String INVALID_ADDRESS_TYPE = "This Address Type is Invalide";
    
    
    
	   public static final String ADDRESS_ID_REQUIRED = "Delivery address is required.";
	    public static final String TOTAL_AMOUNT_REQUIRED = "Total amount is required.";
	    public static final String TOTAL_AMOUNT_POSITIVE = "Total amount must be positive.";
	    public static final String PAYMENT_METHOD_REQUIRED = "Payment method is required.";
	    public static final String ORDER_ITEMS_REQUIRED = "Order items cannot be null.";
	    public static final String ORDER_ITEMS_EMPTY = "Order must contain at least one item.";

	    // Order item messages
	    public static final String PRODUCT_ID_REQUIRED = "Product ID is required.";
	    public static final String QUANTITY_REQUIRED = "Quantity is required.";
	    public static final String MIN_QUANTITY = "Quantity must be at least 1.";
	    public static final String PRICE_REQUIRED = "Price is required.";
	    public static final String PRICE_POSITIVE = "Price must be positive.";
		public static final String INVALIDE_METHOD_TYPE = "Invalid Method Type";
		public static final String CART_NOT_EXIST = "This cart not exist in this shop";
		public static final String INSUFFICIENT_STOCK =   "Insufficient stock for product: ";
		public static final String INVALID_ORDER_REQUEST = "Invalid order request!";
		public static final String TRANSACTION_ID_REQUIRED = "Transaction Id Requesred for Online Payment";
		public static final String ORDERS_NOT_AVAILABLE = "Orders not available at this shop";
		public static final String ORDER_ALREADY_CANCELED = "Order is already cancelled.";
		public static final String ONLY_ADMIN_ACCESS = "Only admins can update to VERIFIED or ASSIGNED.";
		public static final String ONLY_DELIVERY_PARTNER_ACCESS = "Only delivery person can update to PICKED_UP or DELIVERED.";
		public static final String ONLY_SHOP_ACCESS = "Only user or admin can cancel the order.";
		public static final String INVALID_ORDER_STATUS = "Invalid order status update.";
		public static final String REASON_REQUIRED = "Reason is required when cancelling the order.";
		public static final String DELIVERY_PERSON_NOT_AVAILABLE = "Delivery person is not available or online at this time";
		public static final String ORDER_MUST_ASSIGN = "Order Must Assign  To a Delivery Person Before PickUp";
		public static final String FIRST_PLACE_ORDER = "First Place Order Than Cancel Work !";
		
		
		public static final String ORDER_PLACED_SUBJECT = "✅ Order Confirmation - Thank You for Your Purchase!";
		public static final String ORDER_PLACED_REASON = "Thank you for your order with FlowMart! We are excited to let you know that your order has been successfully placed.";

		public static final String SHOP_NAME_REQUIRED = "Name is mandatory.";
    	public static final String SHOP_NAME_LENGTH = "Shop name must be between 3 and 50 characters.";
	    public static final String SHOP_NAME_INVALID_FORMAT = "Shop name contains invalid characters.";

	    // Owner Name Validations
	    public static final String NAME_REQUIRED = "Name is mandatory.";
	    public static final String OWNER_NAME_LENGTH = " name must be between 2 and 30 characters.";
   
	    public static final String USER_NAME_REQUIRED = "User Name is mandatory.";
		 

	    // Mobile Validations
	    public static final String MOBILE_REQUIRED = "Mobile is mandatory.";
	   
	    // Email Validations
	    public static final String EMAIL_REQUIRED = "Email is mandatory.";
	  
	    // Business License Number Validations
	    public static final String LICENSE_NUMBER_REQUIRED = "LicenseNumber is mandatory.";
	  
	    // Delivery Address Validations
	    public static final String ADDRESS_REQUIRED = "Address is null.";

	    // Password Validations
	    public static final String PASSWORD_REQUIRED = "Password is mandatory.";
	   
	    // Confirm Password Validations
	    public static final String CONFIRM_PASSWORD_REQUIRED = "Confirm Password is mandatory.";

	    // Role Validations
	    public static final String ROLE_REQUIRED = "Role is mandatory.";
		public static final String EMAIL_ALREADY_EXISTS = "Email Already Exist";
		public static final String MOBILE_ALREADY_EXISTS = "Mobile Number Already Exist";
		public static final String LICENSE_ALREADY_EXISTS = "License Number Already Exist";
    
		 // Shop Messages
	    public static final String SHOP_NOT_FOUND = "Shop not found with the provided ID.";
	    public static final String SHOP_VERIFIED_SUCCESSFULLY = "Shop verified successfully.";
		public static final String ALL_SHOPS_FETCHED = "All Shops fetched successfully.";
		public static final String SHOP_ALREADY_VERIFIED = "Shop Already Verifeid ";
		public static final String SHOP_IS_BLOCKED = "This shop is currently blocked";
		public static final String SHOP_ALREADY_REJECTED = "Shop Already Rejected ";
		public static final String SHOP_ALREADY_BLOCKED = "Shop Already Blocked";
		public static final String SHOP_BLOCKED_SUCCESSFULLY = "Shop has been blocked successfully.";
		public static final String SHOP_ALREADY_UNBLOCKED = "Shop Already UnBlocked";
		public static final String SHOP_UPDATED_SUCCESSFULLY = "Shop updated Successfully";
		public static final String INVALID_TOKEN = "Invalide Token";
		public static final String AADHAR_NUMBER_ALREADY_EXISTS = "Aadhar Already Exist";
		public static final String PAN_NUMBER_ALREADY_EXISTS = "Pan Number Already Exist";
		public static final String DELIVERY_PERSON_ALREADY_BLOCKED = "Delivery Person is already blocked.";
		public static final String DELIVERY_PERSON_ALREADY_UNBLOCKED = "Delivery Person is already unblocked.";
		public static final String DELIVERYPERSON_ALREADY_VERIFIED = "Delivery Person is already verified.";
		public static final String DELIVERYPERSON_ALREADY_REJECTED = "Delivery Person is already rejected.";

		  public static final String BLOCK_SUCCESS = "Delivery Person blocked successfully.";
		    public static final String UNBLOCK_SUCCESS = "Delivery Person unblocked successfully.";
		    public static final String VERIFY_SUCCESS = "Delivery Person verified successfully.";
		    public static final String REJECT_SUCCESS = "Delivery Person rejected successfully.";
		    public static final String UPDATE_SUCCESS = "Delivery Person updated successfully.";
		    public static final String AVAILABILITY_UPDATE_SUCCESS = "Delivery Person availability updated successfully.";
		    public static final String OFFLINE_SUCCESS = "Delivery Person is now offline.";
		    public static final String ONLINE_SUCCESS = "Delivery Person is now online.";
		    public static final String BLOCKED_CANNOT_BE_UPDATED = "Blocked delivery person cannot be updated.";
			public static final String DELIVERYPERSON_ALREADY_ONLINE = "Delivery Person is Already online.";
			public static final String DELIVERYPERSON_ALREADY_OFFLINE = "Delivery Person is Already offline.";
			public static final String DRIVINGLICENSE_NUMBER_ALREADY_EXISTS = "Driving License Number Already Exist";
			public static final String VEHICLE_ALREADY_EXISTS = "Vehicle Already Exist";
			public static final String DELIVERY_PERSON_NOT_FOUND_MESSAGE = "Dlivery Person Not Found";
			public static final String BANK_ACCOUNT_ALREADY_EXISTS = "Bank Account Already Exists";
			public static final String UPIID_ALREADY_EXISTS = "Upi Already Exists";
			public static final String VEHICLE_NOT_FOUND = "Vehicle Not Found";
			public static final String VEHICLE_NOT_AVAILABLE = "Vehicle not Availble or Null";
			public static final Object VEHICLE_UPDATED_SUCCESSFULLLY = "Vehicle Updated Successfully";
			public static final String USER_ALREADY_HAVE_VEHICLE = "Delivery person already added vehicle";
			public static final String BANK_NOT_AVAILABLE = "Bank Details not available";
			public static final Object BANK_UPDATED_SUCCESSFULLLY = "Bank Details Updated Successfully";
			public static final String CATAGORY_NOT_FOUND_MESSAGE = "Catagory Not Found";
			public static final String PRODUCT_NOT_FOUND_MESSAGE = "Product Not Found ";
			public static final Object PRODUCT_ALREADY_DELETED = "Product is already deleted (inactive)";
			public static final Object PRODUCT_DELETED_SUCCESSFULLY = "Product Deleted successfully";
			public static final Object PRODUCT_QUANTITY_UPDATED = "Product quantity updated successfully";
			public static final Object NO_SEARCHED_PRODUCT = "No products found for the given searched criteria";
			public static final Object NO_FILTERED_PRODUCT = "No products found for the given filter criteria";
			public static final Object PRODUCT_UPDATED = "Product Updated Successfully";
			public static final Object NO_PRODUCT_FOUND = "No Product found";
			public static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "Category Already Exist";
			public static final Object CATEGORY_UPDATED_SUCCESSFULLY = "Category Updated Successfully";
			public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category Not Found";
			public static final Object CATEGORY_ALREADY_DELETED = "Category is already deleted";
			public static final Object CATEGORY_REMOVED_SUCCESSFULLY = "category removed successfully";
			public static final String PRODUCT_ALREADY_EXISTS_MESSAGE = "Product Already Exist In The Cart";
			public static final String ADDRESS_NOT_FOUND_MESSAGE = "This Delivery Address not Available at this shop";
			public static final String ORDERS_NOT_ASSIGNED = "Order not assigned to this delivery person";
			public static final String INVALID_DOCUMENT_TYPE = "Invalid Document Typ";
			public static final String COMMENT_NOT_BLANK = "Comment not be blank or null";
			public static final String RATING_NOT_NULL = "Rationg not be null";
			public static final String PRODUCTID_NOT_BLANK = "ProductId Not be null";
			public static final String RATING_MUST_1 = "Rating must be at least 1";
			public static final String RATING_LESS_THEN_5 = "Rating cannot be more than 5";
			public static final Object REVIEW_CREATED = "Review Created Successfully";
			public static final String REVIES_NOT_AVAILABLE = "Revies Not Found";
			public static final String DOCUMENT_REQUIRED = "Document Required";
			public static final Object SHOP_STATUS_UPDATED_SUCCESSFULLY = "Shop Status Updated Successfully";
			public static final Object SHOP_BLOCK_STATUS_UPDATED_SUCCESSFULLY = "Shop Block Status Updated Successfully";
			public static final Object SHOP_ALREADY_DELETED = "Shop Already Deleted";
			public static final Object SHOP_DELETED_SUCCESSFULLY = "Shop Deleted Successfully";
			public static final String DELIVERY_PERSON_ALREADY_VERIFIED = "Delivery Person Already Verified";
			public static final String DELIVERY_PERSON_ALREADY_REJECTED = "Delivery Person Already Rejected";
			public static final Object DELIVERY_PERSON_ALREADY_DELETED = "Delivery Person Already Deleted";
			public static final Object DELETED_SUCCESS = "Delivery Person Deleted Successfully";
			public static final Object PRODUCT_IMAGE_UPDATED = "product image updated successfully";
			public static final String INVALID_USER_NAME_FORMATE = "Username must start with a letter and can contain letters, digits, and underscores. Length 3-30 characters";
			public static final String ROOM_NOT_FOUND = "Room Not Found!";
			public static final String ALREADY_EXISTS_IN_GROUP = "User Already Exist In Room";
			public static final String MESSAGE_NOT_FOUND = "Message Not Found !";
	     	public static final String INVALID_UUID = "Invalid UUID format";
		
	        public static final String DROUP_NAME_REQUIRED = "Group name is mandatory.";
			public static final String NO_MESSAGE_AVAILABLE = "No Message Found";
}
