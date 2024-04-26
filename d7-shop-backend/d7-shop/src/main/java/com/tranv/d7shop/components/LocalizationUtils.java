package com.tranv.d7shop.components;

import com.tranv.d7shop.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class LocalizationUtils {
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public String getLocalizedMessage(String message, Object... param) {
        HttpServletRequest request = WebUtils.getRequest();
        Locale locale = localeResolver.resolveLocale(request);
        return messageSource.getMessage(message, param, locale);
    }
}
