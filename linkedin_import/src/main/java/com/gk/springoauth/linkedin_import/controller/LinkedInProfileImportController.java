/**
 * 
 */
package com.gk.springoauth.linkedin_import.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gauravkhandave
 *
 */

@RestController
@RequestMapping(value="/linkedin")
public class LinkedInProfileImportController {
	
	@Autowired
	private LinkedIn linkedIn;
	
	//private UserProfile userProfile;
	
	@RequestMapping(method=RequestMethod.GET)
	public String importProfile(Model model){
		try {
			if (!linkedIn.isAuthorized()) {
			return "redirect:/importer/linkedin";

			}
			}catch (NullPointerException e){
			return "redirect:/importer/linkedin";
			}
		
		LinkedInProfile profile = linkedIn.profileOperations().getUserProfile();
		return profile.getFirstName() +"\t"+profile.getLastName()+"\n"+profile.getSummary();
	}
}
