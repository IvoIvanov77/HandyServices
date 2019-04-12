package org.softuni.handy.domain.models.binding.validation_constants;

public final class ValidationConstraints {

    //user
    public final static int MIN_USERNAME_LENGTH = 1;
    public final static int MAX_USERNAME_LENGTH = 15;
    public static final String EMAIL_PATTERN = "^(.+)@(.+)$";
    public static final String PASSWORD_PATTERN = ".*";
//            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";

    //professional service

    public static final int MIN_SLOGAN_LENGTH = 5;
    public static final int MAX_SLOGAN_LENGTH = 30;
    public static final int MIN_SERVICE_DESCRIPTION_LENGTH = 15;
    public static final int MAX_SERVICE_DESCRIPTION_LENGTH = 120;

    //service order
    public static final int MIN_ORDER_DESCRIPTION_LENGTH = 20;
    public static final int MAX_ORDER_DESCRIPTION_LENGTH = 300;
    public static final String LOCAL_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final int MIN_ADDRESS_LENGTH = 5;
    public static final int MAX_ADDRESS_LENGTH = 50;


    //shared between professional service and service order
    public static final String PERSON_NAME_PATTERN = "^[a-zA-Z]+$";
    public static final String PHONE_NUMBER_PATTERN = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

    //service offer
    public static final int MIN_OFFER_HOURS = 1;
    public static final String MIN_OFFER_PRICE = "0.01";


    private ValidationConstraints() {
    }
}
