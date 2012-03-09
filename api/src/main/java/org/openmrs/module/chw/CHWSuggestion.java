package org.openmrs.module.chw;

import java.util.List;


// this may be non-persisted, but rather set via xml global property?
public class CHWSuggestion {
	
	// the relationship type we asking for a suggestion for
	private CHWRelationshipType relationshipType;  
	
	// only suggest CHWs where ALL the specified address fields match between the CHW and the patient
	// address fields can be specified by the actual OpenMRS field, or the address template mapped name
	private List<String> addressFields;
	
	
	// TODO: do we need to create a "work" address for a CHW?
	
	
	
}
