package com.poly.petfoster.constant;

public enum RespMessage {

    SUCCESS("Successfully!"), 
    
    FAILURE("Failures!"), 
    
    INTERNAL_SERVER_ERROR("Internal Server Error!"),

    TOKEN_EXPIRED("Can't authenticate because Token Expired!"),

    TOKEN_INVALID("Can't authenticate because Token Invalid!"),
    
    USERNAME_ALREADY("Username already exists!"),

    EMAIl_ALREADY("Email already exists!"),

    INVALID_REQUEST("The request invalid!"),

    PASSWORD_INCORRECT("Your confirm password is incorrect!"),

    NOT_FOUND("Can't found data!"),

    INVALID("is invalid!");


    private final String message;


    RespMessage(String message) { this.message = message; }
    
    public String getValue() { return message; }

    public static final String NOT_EMPTY = "can't be empty!";

    public static final String EXISTS = "already exists!";

}
