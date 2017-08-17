/**
 * 
 */
package com.gk.springoauth.linkedin_import.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

/**
 * @author gauravkhandave
 *
 */
@Configuration
@EnableOAuth2Client
@ComponentScan
public class OAuthConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	OAuth2ClientContext oAuth2ClientContext;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class).authorizeRequests()
				.antMatchers("/", "/connect**", "/webjars/**").permitAll().anyRequest().authenticated().and().logout()
				.logoutSuccessUrl("/").permitAll().and().csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		;
	}

	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();

		OAuth2ClientAuthenticationProcessingFilter linkedInFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/connect/linkedIn");
		OAuth2RestTemplate linkedInTemplate = new OAuth2RestTemplate(linkedIn(), oAuth2ClientContext);
		linkedInFilter.setRestTemplate(linkedInTemplate);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(linkedInResource().getUserInfoUri(), linkedIn().getClientId());
		tokenServices.setRestTemplate(linkedInTemplate);
		linkedInFilter.setTokenServices(tokenServices);

		filters.add(linkedInFilter);

		filter.setFilters(filters);

		return filter;
	}

	@Bean
	@ConfigurationProperties("linkedin.client")
	public AuthorizationCodeResourceDetails linkedIn() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("linkedin.resource")
	public ResourceServerProperties linkedInResource() {
		return new ResourceServerProperties();
	}

	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(filter);
		bean.setOrder(-100);
		return bean;
	}
}
