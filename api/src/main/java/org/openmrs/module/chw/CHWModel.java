package org.openmrs.module.chw;

import java.util.List;
import java.util.Map;

import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.RelationshipType;

public class CHWModel {

	//  a detached CHWModel object to pass details to the view; not used for data transfer or calculations within the API
	
	public Provider provider;
	
	public CHWType type;
	
	Map<RelationshipType, List<Patient>> patients;
	
	Map<CHWType,List<Provider>> supervisees;   
	
	Map<CHWType,Provider> supervisors;    // make this a provider list if we want to support multiple supervisors of the same type?	 

	
}
