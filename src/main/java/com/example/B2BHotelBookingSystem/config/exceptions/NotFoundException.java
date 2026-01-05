package com.example.B2BHotelBookingSystem.config.exceptions;

public class NotFoundException extends BusinessException {
    public NotFoundException(Long id, String object) {
        super(object + " with id " + id + " Not found!");
    }
    public NotFoundException(String object) {
        super(object + " Not found!");
    }
}
