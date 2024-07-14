package com.invermo.persistence.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateParser {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm");

    public static LocalDateTime parseStringToLocalDateTime(String date) {
        return LocalDateTime.parse(date, DATE_TIME_FORMATTER);
    }
}
