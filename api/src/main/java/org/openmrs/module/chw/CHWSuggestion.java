package org.openmrs.module.chw;

import java.util.List;


// TODO: this may be non-persisted, but instead possibly set via xml global property?
public class CHWSuggestion {
	
	// the relationship type we asking for a suggestion for
	private CHWRelationshipType relationshipType;
	
	// only suggest CHWs that have one or more of the following roles
	// (would be specified by name, uuid, or id in the XML (?)
	private List<CHWRole> roles;
	
	// only suggest CHWs where ALL the specified address fields match between the CHW and the patient
	// address fields can be specified by the actual OpenMRS field, or the address template mapped name
	private List<String> addressFields;
	
	
	// TODO: do we need to create a "work" address for a CHW?
	
	
	
}
