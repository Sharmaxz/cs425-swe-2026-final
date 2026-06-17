package edu.miu.cs425.studentrecords.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateStringConverter implements AttributeConverter<LocalDate, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String convertToDatabaseColumn(LocalDate locDate) {
        return locDate == null ? null : locDate.format(FORMATTER);
    }

    @Override
    public LocalDate convertToEntityAttribute(String sqlDate) {
        if (sqlDate == null || sqlDate.isEmpty()) {
            return null;
        }
        // If the database happens to have a timestamp string (e.g. "2026-01-15 00:00:00.000"), 
        // we can take just the date part to be safe.
        if (sqlDate.length() > 10) {
            sqlDate = sqlDate.substring(0, 10);
        }
        return LocalDate.parse(sqlDate, FORMATTER);
    }
}
