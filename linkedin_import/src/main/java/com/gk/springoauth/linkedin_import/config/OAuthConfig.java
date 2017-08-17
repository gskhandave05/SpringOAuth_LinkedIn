/**
 * 
 */
package com.gk.springoauth.linkedin_import.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author gauravkhandave
 *
 */
@Configuration
@EnableOAuth2Client
public class OAuthConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	  OAuth2ClientContext oAuth2ClientContext;
	
	@Override
	protected void configure(HttpSecurity http)
		      throws Exception {
		    http.antMatcher("/**")
		        .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
		        .authorizeRequests()
		        .antMatchers("/", "/login**", "/webjars/**")
		        .permitAll()
		        .anyRequest()
		        .authenticated();
		  }
	
	private Filter ssoFilter(){
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/login/linkedin");
		OAuth2RestTemplate template = new OAuth2RestTemplate(linkedin(), oAuth2ClientContext);
	    filter.setRestTemplate(template);
	    filter.setTokenServices(new UserInfoTokenServices(linkedinResource().getUserInfoUri(), linkedin().getClientId()));

	    return filter;
	}
	
	//@Bean
	  @ConfigurationProperties("linkedin.client")
	  OAuth2ProtectedResourceDetails linkedin() {
	    return new AuthorizationCodeResourceDetails();
	  }

	  //@Bean
	  @ConfigurationProperties("linkedin.resource")
	  ResourceServerProperties linkedinResource() {
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
