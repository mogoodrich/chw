package org.openmrs.module.chw;

import java.util.List;

//this may be non-persisted, but rather set via xml global property?
public class SuperviseeSuggestion {
	
		// TODO: call this "Supervisee Auto Assign"
	
		// the *supervisor* role we asking for a suggestion for
		private CHWRole role;  
		
		// only suggest CHWs where ALL the specified address fields match between the CHW and the patient
		// address fields can be specified by the actual OpenMRS field, or the address template mapped name
		private List<String> addressFields;
		
		// also provide ability to match on a Provider (or Person?) attribute?
}
