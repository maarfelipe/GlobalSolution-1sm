package com.cropsage.exceptions;

public record RestError(
    int cod,
    String message
) {}
