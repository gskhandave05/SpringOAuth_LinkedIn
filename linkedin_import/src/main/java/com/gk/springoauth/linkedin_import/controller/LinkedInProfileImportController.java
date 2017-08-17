/**
 * 
 */
package com.gk.springoauth.linkedin_import.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gauravkhandave
 *
 */

@RestController
public class LinkedInProfileImportController {
	
	@RequestMapping("/user")
	  public Principal lookupUser(Principal user) {
	    return user;
	  }
}
