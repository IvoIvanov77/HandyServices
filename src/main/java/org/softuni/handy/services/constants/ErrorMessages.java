package org.softuni.handy.services.constants;

public final class ErrorMessages {

    private static final String INVALID_SERVICE_MODEL_TEMPLATE = "Invalid %s.";
    private static final String RESOURCE_NOT_FOUND_TEMPLATE = "%s not found.";


    private static String formatNotFoundMessage(String resourceName){
        return String.format(RESOURCE_NOT_FOUND_TEMPLATE, resourceName);
    }

    private static String formatInvalidModelMessage(String modelName){
        return String.format(INVALID_SERVICE_MODEL_TEMPLATE, modelName + " model");
    }

    //user service
    public static final String USERNAME_NOT_FOUND_ERROR_MESSAGE = formatNotFoundMessage("User");
    public static final String INVALID_USER_MODEL_ERROR_MESSAGE = formatInvalidModelMessage("User");

    //service type service
    public static final String SERVICE_TYPE_NOT_FOUND = formatNotFoundMessage("Service type");
    public static final String INVALID_SERVICE_TYPE_MODEL = formatInvalidModelMessage("Service type");

    //professional service service
    public static final String PROFESSIONAL_SERVICE_NOT_FOUND_ERROR_MESSAGE =
            formatNotFoundMessage("Professional service");
    public static final String INVALID_PROFESSIONAL_SERVICE_MODEL_ERROR_MESSAGE =
            formatInvalidModelMessage("Professional service");

    //order service
    public static final String ORDER_NOT_FOUND_ERROR_MESSAGE = formatNotFoundMessage("Order");
    public static final String INVALID_ORDER_MODEL_ERROR_MESSAGE = formatInvalidModelMessage("Order");

    //offer service
    public static final String OFFER_NOT_FOUND = formatNotFoundMessage("Offer");
    public static final String INVALID_OFFER_MODEL = formatInvalidModelMessage("Offer");

    //location service
    public static final String LOCATION_NOT_FOUND = formatNotFoundMessage("Location");
    public static final String INVALID_LOCATION_MODEL = formatInvalidModelMessage("Location");

    //location service
    public static final String CLAIM_NOT_FOUND = formatNotFoundMessage("Claim");
    public static final String INVALID_CLAIM_MODEL = formatInvalidModelMessage("Claim");

    private ErrorMessages() {
    }
}
