package com.delitech.revealing.commons;

import lombok.AllArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class MessageComponent {
    /**
     * message
     */
    private final ResourceBundleMessageSource messageSource;

    public String getBundleMessage(String code, String... params) {
        return messageSource.getMessage(code, params, Locale.getDefault());
    }

}
