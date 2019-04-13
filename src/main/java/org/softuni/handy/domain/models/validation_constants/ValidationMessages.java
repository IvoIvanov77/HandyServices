package org.softuni.handy.domain.models.validation_constants;

public final class ValidationMessages {

    //user
    public static final String USERNAME_LENGTH_ERROR_MESSAGE = "Username must be between 1 and 15 characters long";
    public static final String INVALID_EMAIL_ERROR_MESSAGE = "Invalid email pattern";
    public static final String INVALID_PASSWORD_ERROR_MESSAGE = "Password must contains at least one digit, " +
            "one character and one special symbol";

    //service type
    public static final String EMPTY_SERVICE_TYPE_NAME_ERROR_MESSAGE = "Empty type name field";

    //location
    public static final String EMPTY_LOCATION_NAME_ERROR_MESSAGE = "Empty location name field";

    //shared between service type and location
    public static final String EMPTY_PICTURE_URL_ERROR_MESSAGE = "Empty image url field";

    //professional service
    public static final String SLOGAN_LENGTH_ERROR_MESSAGE = "Slogan must be between 5 and 50 characters long";
    public static final String DESCRIPTION_LENGTH_ERROR_MESSAGE = "Description must be between 15 and 120 characters long";

    //service order
    public static final String ORDER_DESCRIPTION_LENGTH_ERROR_MESSAGE = "Description must be between 20 and 300 characters long";
    public static final String ADDRESS_LENGTH_ERROR_MESSAGE = "Address must be between 5 and 50 characters long";
    public static final String INVALID_DATE_ERROR_MESSAGE = "Invalid date";
    public static final String NOT_NULL_SCHEDULED_DATE_ERROR_MESSAGE = "Scheduled Date can not be null";

    //shared between professional service and service order
    public static final String INVALID_FIRST_NAME_ERROR_MESSAGE = "Invalid first name";
    public static final String INVALID_LAST_NAME_ERROR_MESSAGE = "Invalid last name";
    public static final String INVALID_PHONE_NUMBER_ERROR_MESSAGE = "Invalid phone number pattern";
    public static final String INVALID_SERVICE_TYPE_ERROR_MESSAGE = "Service type must be not null";
    public static final String INVALID_SERVICE_LOCATION_ERROR_MESSAGE = "Service location must be not null";

    //service offer
    public static final String INVALID_HOURS_ERROR_MESSAGE = "Hours must are equal or greater than 1h";
    public static final String INVALID_PRICE_ERROR_MESSAGE = "Price must to be greater or equal than 0.01 $";
    public static final String HOURS_NOT_NULL_ERROR_MESSAGE = "Hours can not are a null";
    public static final String PRICE_NOT_NULL_ERROR_MESSAGE = "Hours can not be a null";


    //claim
    public static final String REASON_NOT_NULL_ERROR_MESSAGE = "Reason can not be a null";
    public static final String INVALID_REASON_ERROR_MESSAGE = "Reason must be between 15 and 250 characters long";

    public static final String PROFESSIONAL_SERVICE_NOT_NULL_ERROR_MESSAGE = "Professional service can not be a null";
    //shared between service offer and claim
    public static final String ORDER_NOT_NULL_ERROR_MESSAGE = "Service order can not be a null";

    protected ValidationMessages() {
    }
}
