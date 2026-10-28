package com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Amount of money in a currency. It has two attributes, so it is stored as an embeddable
 * (one column per attribute in the owner table) instead of an attribute converter.
 *
 * @param amount   non-negative amount
 * @param currency ISO 4217 currency code, for example {@code PEN}
 */
@Embeddable
public record Money(BigDecimal amount, String currency) {
    private static final Pattern CURRENCY_PATTERN = Pattern.compile("^[A-Z]{3}$");
    private static final String AMOUNT_INVALID_MESSAGE_KEY = "money.error.amount.invalid";
    private static final String CURRENCY_PATTERN_MESSAGE_KEY = "money.error.currency.pattern";

    public Money {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalArgumentException(AMOUNT_INVALID_MESSAGE_KEY);
        }
        if (currency == null || !CURRENCY_PATTERN.matcher(currency).matches()) {
            throw new IllegalArgumentException(CURRENCY_PATTERN_MESSAGE_KEY);
        }
    }
}
