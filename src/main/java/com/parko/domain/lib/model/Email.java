package com.parko.domain.lib.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^([1-9][0-9]{0,4})@(?:([a-z]+)\\.)?frc\\.utn\\.edu\\.ar$"
    );

    private final String value;
    private final String studentId;
    private final InstitutionalDomain institutionalDomain;

    public Email(String value) {
        if (value == null) {
            throw new IllegalArgumentException("el email no puede ser nulo");
        }

        Matcher matcher = EMAIL_PATTERN.matcher(value);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Email invalido, formato esperado legajo@dominio.frc.utn.edu.ar: " + value
            );
        }

        String rawDomain = matcher.group(2);
        this.institutionalDomain = parseDomain(rawDomain);
        this.studentId = matcher.group(1);
        this.value = value;
    }

    private InstitutionalDomain parseDomain(String rawDomain) {
        if (rawDomain == null) {
            return InstitutionalDomain.FRC;
        }

        try {
            return InstitutionalDomain.valueOf(rawDomain.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Dominio institucional no reconocido: " + rawDomain
            );
        }
    }
}