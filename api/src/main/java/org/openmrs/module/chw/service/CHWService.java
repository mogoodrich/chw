package org.openmrs.module.chw.service;

import java.util.Date;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.Role;
import org.openmrs.module.chw.CHWRelationshipType;
import org.openmrs.module.chw.CHWRole;

// TODO: add transactional stuff based on best practice

public interface CHWService {
	
	// TODO: maybe all these methods should return Sets instead of Lists to avoid duplicates?  
	
	// TODO: make sure to account for/ignore voided patients/providers?
	// TODO: add the retire/inactive feature?
	
	
	/** Basic methods to assign CHW roles to providers **/
	
	// first, some "getAll" methods for the two new domain objects we are adding
	public List<CHWRole> getAllCHWRoles();
	public List<CHWRelationshipType> getAllCHWRelationshipTypes();
	
	// add the specified CHW Role to the specified person
	// this is done by setting the role as a ProviderAttribute of the person
	// (on startup, module will explicitly add a "CHW Provider" ProviderAttributeType to DB that it will 
	// referenced by a constant containing the UUID of the ProviderAttributeType entry)
	public void addCHWRole(Provider provider, CHWRole role);
	
	public void removeCHWRole(Provider provider, CHWRole role);
	
	// get all CHWs
	// returns all providers that have one or more not-null ProviderAttributes of the "CHW Provider" ProviderAttributeType 
	public List<Provider> getAllCHWs();
	
	// get all CHWs with a certain role
	// returns all provider that have a "CHW Provider" attribute with value = role
	public List<Provider> getCHWs(CHWRole role);
	
	// get all CHW roles that support the specified relationship
	public List<CHWRole> getCHWRoles(CHWRelationshipType type);
	
	// gets all CHWs that support a certain relationship
	// (basically build list by called getCHW(role) for each role in getCHWRoles(type) where type=specified type
	public List<Provider> getCHWs(CHWRelationshipType type);
	
	// get the CHW roles associated with the specified provider
	// returns a list of the values of all the "CHW Provider" attributes associated with the provider
	public List<CHWRole> getCHWRoles(Provider provider);
	
	// maybe a utility "create CHW" method that does a savePerson and then assignCHWRole?
	
	public Boolean isCHW(Provider provider);
	
	public Boolean hasCHWRole(Provider provider, CHWRole role);
	
	public Boolean supportsCHWRelationshiopType(Provider provider, CHWRelationshipType relationshipType);
	
	
	
	/** CHW-to-Patient service methods **/
	
	// assigns the specified Provider to the specified Patient with the specified CHWRelationshipType
	// link the person underlying the patient to the person underlying the provider in a B-to-A relationship of
	// the RelationshipType underlying the passed CHWRelationshipType
	// the startDate of the relationship is set to the current date
	// should also enforce that the provider has a CHW Role that supports that relationship type
	// need to think about what to do if the patient has any existing relationships of the same type--probably shouldn't end it automatically?
	public void assignCHW(Patient patient, Provider provider, CHWRelationshipType relationshipType);
	
	// same as above but allows specification of the date
	public void assignCHW(Patient patient, Provider provider, CHWRelationshipType relationshipType, Date date);

	// unassigns a CHW from a patient
	// finds the matching relationship and ends it on the current date
	public void unassignCHW(Patient patient, Provider provider, CHWRelationshipType relationshipType);
	
	// unassigns a CHW from a patient
	// finds the matching relationship and ends it on the specified date
	public void unassignCHW(Patient patient, Provider provider, CHWRelationshipType relationshipType, Date date);
	
	// unassigns all patient from this CHW
	// gets all patients that have a relationship of any CHWRelationshipType to this CHW 
	// and end the relationship on the current date
	public void unassignAllPatients(Provider provider);
	
	public void unassignAllPatients(Provider provider, CHWRelationshipType type);
	
	public void unassignAllPatients(Provider provider, Date date);
	
	public void unassignAllPatients(Provider provider, CHWRelationshipType type, Date date);
	
	// get all CHWs associated with the specified patient
	// finds all relationships where the patient is person B and the RelationType is one of the defined CHWRelationshipTypes,
	// and the current date falls within the start and end date of the relationship
	public List<Provider> getCHWs(Patient patient);
	
	// does the same as the above but uses the specified date as opposed to the current date
	public List<Provider> getCHWs(Patient patient, Date date);
	
	// does the same as the above but uses the specified date as opposed to the current date
	// if includeHistorical=true, returns all CHWs regardless of start/end date of the relationship
	// includeHistorical=false would do the same as a simple get
	public List<Provider> getCHWs(Patient patient, Boolean includeHistorical);
	
	// get all CHWs of with a certain relationship type to the given patient
	// same as above but now RelationshipType must equal specified type
	public List<Provider> getCHWs(Patient patient, CHWRelationshipType type);
	
	public List<Provider> getCHWs(Patient patient, CHWRelationshipType type, Date date);
	
	public List<Provider> getCHWs(Patient patient, CHWRelationshipType type, Boolean includeHistorical);

	// get all patients of the passed chw on the current date
	public List<Patient> getPatients(Provider chw);
	
	// get all patients of the passed chw on the specified date
	public List<Patient> getPatients(Provider chw, Date date);
	
	public List<Patient> getPatients(Provider chw, Boolean includeHistorical);
	
	// get all patients of the specified chw with the specified relationship type on the current date
	public List<Patient> getPatients(Provider chw, CHWRelationshipType type);
	
	public List<Patient> getPatients(Provider chw, CHWRelationshipType type, Date date);
	
	public List<Patient> getPatients(Provider chw, CHWRelationshipType type, Boolean includeHistorical);
	
	// suggest a CHW to for the specified patient
	// first this method builds a list of getCHW(type);
	// if no CHWSuggestions have been specified, method simply returns this list
	// otherwise, the method iterates through all CHWSuggestions where relationshipType = specified type
	// it then iterates through all the providers returns by getCHW(type), and if the 
	// all the address fields specified by the suggestion match between the CHW and the patient,
	// the CHW is added to the results list
	// (need to define what address to use... would only be non-voided addresses active on the current date.. but
	// if there are multiple addresses, do we allow a match on any?)
	public List<Provider> suggestCHW(Patient patient, CHWRelationshipType type);
	
	
}
