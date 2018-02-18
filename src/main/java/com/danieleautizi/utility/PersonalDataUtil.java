package com.danieleautizi.utility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Locale;

/**
 * Helper class to work generic needs.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PersonalDataUtil {

    private static final int DEFAULT_ELLIPSIS_LENGTH = 25;

    private final MessageSource messageSource;

    public String getMessage(final String messageKey, final Object... args) {

        return messageSource.getMessage(messageKey, args, messageKey, Locale.ROOT);
    }

    public URI getURI(final String uri, final String viewParams) {

        return UriComponentsBuilder.fromUriString(uri)
                                   .buildAndExpand(viewParams)
                                   .encode()
                                   .toUri();
    }

    public String ellipsize(final String text) {

        return ellipsize(text, DEFAULT_ELLIPSIS_LENGTH);
    }

    public String ellipsize(final String text, final int max) {

        if (textWidth(text) <= max) {
            return text;
        }

        // Start by chopping off at the word before max
        // This is an over-approximation due to thin-characters...
        int end = text.lastIndexOf(' ', max - 3);

        // Just one long word. Chop it off.
        if (end == -1) {
            return text.substring(0, max - 3) + "...";
        }

        // Step forward as long as textWidth allows.
        int newEnd = end;
        do {
            end = newEnd;
            newEnd = text.indexOf(' ', end + 1);

            // No more spaces.
            if (newEnd == -1) {
                newEnd = text.length();
            }

        } while (textWidth(text.substring(0, newEnd) + "...") < max);

        return text.substring(0, end) + "...";
    }

    private int textWidth(String str) {

        String NON_THIN = "[^iIl1\\.,']";
        return (int) (str.length() - str.replaceAll(NON_THIN, "").length() / 2);
    }

}
