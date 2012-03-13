package org.openmrs.module.chw;

import java.util.List;
import java.util.Map;

import org.openmrs.Patient;
import org.openmrs.Provider;

public class CHWModel {

	//  a detached CHWModel object to pass details to the view
	
	public Provider provider;
	
	public CHWType type;
	
	Map<CHWRelationshipType, List<Patient>> patients;
	
	Map<CHWType,List<Provider>> supervisees;   
	
	Map<CHWType,Provider> supervisors;    // make this a provider list if we want to support multiple supervisors of the same type?	 

	
}
