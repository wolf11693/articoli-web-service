package com.xantrix.webapp.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ValidationMessageConfig {

	private static final String BASENAME = "valid_messages";
	
	@Bean(name = "messageSource")
	public MessageSource getMessageSource() {
		ResourceBundleMessageSource theResourceBundleMessageSource = new ResourceBundleMessageSource();
		theResourceBundleMessageSource.setBasename(BASENAME);
		theResourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
		
		return theResourceBundleMessageSource;
	}
	
	@Bean(name = "validator")
	public LocalValidatorFactoryBean getValidator() {
		MessageSource theMessageSource = this.getMessageSource();
		
		LocalValidatorFactoryBean theLocalValidatorFactoryBean = new LocalValidatorFactoryBean();
		theLocalValidatorFactoryBean.setValidationMessageSource( theMessageSource );
		
		return theLocalValidatorFactoryBean;
	}
	
	@Bean(name = "localeResolver")
	public LocaleResolver getLocaleResolver() {
		Locale theLocale = LocaleContextHolder.getLocale();
		
		SessionLocaleResolver theSessionLocaleResolver = new SessionLocaleResolver();
		theSessionLocaleResolver.setDefaultLocale( theLocale );
		
		return theSessionLocaleResolver;
	}
}