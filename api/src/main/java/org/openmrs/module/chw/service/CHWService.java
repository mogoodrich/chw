package org.openmrs.module.chw.service;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.Provider;
import org.openmrs.module.chw.CHWRelationshipType;
import org.openmrs.module.chw.CHWRole;
import org.openmrs.module.chw.SuperviserRole;
import org.openmrs.module.chw.SupervisorRelationshipType;

// TODO: add transactional stuff based on best practice


public interface CHWService {
	
	// TODO: get date-specific relationships?
	
	
	// get all patients of the specified chw with the specified relationship type
	public List<Patient> getPatients(Provider chw, CHWRelationshipType type);
	
	// get all patients of the passed chw
	public List<Patient> getPatients(Provider chw);
	
	// get all CHWs of with a certain relationship type to the given patient
	public List<Provider> getCHWs(Patient patient, CHWRelationshipType type);
	
	// get all CHWs of a certain role associated with a given patient
	public List<Provider> getCHWs(Patient patient, CHWRole role);
	
	// get all CHWs that have the specified person as a supervisor of the represented by the specified supervisor relationship type
	public List<Provider> getCHWs(Person supervisor, SupervisorRelationshipType type);
	
	// get all CHWS that have the specified person as a supervisor of the specified role
	public List<Provider> getCHWs(Person supervisor, SuperviserRole role);
	
	// get all CHWs that have the specified person as a supervisor
	public List<Provider> getCHWs(Person supervisor);
	
	// get all CHWs associated with the specified patient
	public List<Provider> getCHWs(Patient patient);
	
	// get all CHWs with a certain role
	public List<Provider> getCHWs(CHWRole role);
	
	// get all CHWs
	public List<Provider> getAllCHWs();
	
	// get the CHW roles associated with the specified provider
	public List<CHWRole> getCHWRoles(Provider provider);
	
	// get all the chw roles
	public List<CHWRole> getAllCHWRoles();
	
	// get all the CHW relationship types
	public List<CHWRelationshipType> getAllCHWRelationshipTypes();
	
	// get all the Supervisor relationship types
	public List<SupervisorRelationshipType> getAllSupervisorRelationshipTypes();
	
}
