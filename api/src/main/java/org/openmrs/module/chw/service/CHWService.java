package org.openmrs.module.chw.service;

import java.util.Date;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonName;
import org.openmrs.Provider;
import org.openmrs.RelationshipType;
import org.openmrs.module.chw.CHWModel;
import org.openmrs.module.chw.CHWType;

// TODO: add transactional stuff based on best practice

public interface CHWService {
	
	// TODO: maybe all these methods should return Sets instead of Lists to avoid duplicates?  
	
	// TODO: make sure to distinguish between active and retired providers
	
	// TODO: carefully think of each validation rule at each time
	
	/** Basic methods to assign CHW types to providers **/
	
	public List<CHWType> getAllCHWTypes();
	public List<CHWType> getCHWTypes(RelationshipType type); 	// get all CHW types that support the specified relationship	
	public List<CHWType> getAllSupervisorTypes();  // types where chwSuperviseeTypes != null
	public List<CHWType> getAllDirectCareTypes();  // types where relationshipTypes != null
	public List<RelationshipType> getAllRelationshipTypes();  // get all relationships with entries in the table tha maps CHWTypes to relationships
	
	
	// set the specified CHW Type to the specified person
	// note that a provider can only have one CHW Type, so setting this will overwrite any existing one
	// this is done by setting the type as a ProviderAttribute of the person
	// (on startup, module will explicitly add a "CHW Provider" ProviderAttributeType to DB that it will 
	// referenced by a constant set to UUID of the ProviderAttributeType entry for "CHW Provider")
	public void setCHWType(Provider provider, CHWType type);
	
	// get the CHW type associated with the specified provider
	public CHWType getCHWType(Provider provider);
	
	// removes the type associated with the this provider
	// (i.e., remove the "CHW Provider" attribute entirely)
	public void deleteCHWType(Provider provider);
	
	// this method will use the provider service to create a Provider for the specified Person, and then setCHWType to set the CHWtype
	public Provider createCHW(Person person, CHWType type);
	
	// simply delegates to getProvider(id) but also calls isCHW as a sanity check and throws an exception if false
	public Provider getCHW(Integer providerId);
	public CHWModel getCHWModel(Integer providerId);
	
	
	public List<Provider> getAllCHWs();   // returns all providers with a "CHW Provider" ProviderAttribute value that is in getAllDirectCareTypes(); 
	public List<Provider> getAllCHWs(Boolean includeRetired);  // note that default method does NOT include retired, and that providers associated with voided persons are ALWAYS excluded (is this correct behavior?)
	public List<Provider> getAllCHWs(Boolean includeSupervisors, Boolean includeRetired); // getAllCHWs() does not include supervisors by default
	
	// returns all providers with a "CHW Provider" ProviderAttribute value that is in getAllSupervisorTypes(); 
	public List<Provider> getAllSupervisors();
	public List<Provider> getAllSupervisors(Boolean includeRetired);
	
	// TODO: note that although a provider can only have one type, it IS possible for a type to both a CHW and CHW supervisor--
	// TODO: so the lists returned by the above two records may not be mutually exclusive
	
	// get all CHWs with a certain type
	// returns all provider that have a "CHW Provider" attribute with value = type
	public List<Provider> getCHWs(CHWType type);
	public List<Provider> getCHWs(CHWType type, Boolean includeRetired);
	
	
	// gets all CHWs that support a certain relationship
	// (basically build list by called getCHW(type) for each type in getCHWTypes(type) where type=specified type
	public List<Provider> getCHWs(RelationshipType type);
	public List<Provider> getCHWs(RelationshipType type, Boolean includeRetired);
	
	// some more complex searching for the search functionality
	public List<Provider> getCHWs(String providerIdentifier, List<CHWType> type,  PersonName name, PersonAddress address, List<PersonAttribute> attributes);
	
	public Boolean isCHW(Provider provider);  // does this include 
	
	public Boolean isSupervisor(Provider provider);
	
	public Boolean supportsRelationshiopType(Provider provider, RelationshipType relationshipType);
	
	
	
	/** CHW-to-Patient service methods **/
	
	// assigns the specified Provider to the specified Patient with the specified RelationshipType
	// link the person underlying the patient to the person underlying the provider in a B-to-A relationship of
	// the RelationshipType underlying the passed RelationshipType
	// the startDate of the relationship is set to the current date
	// should also enforce that the provider has a CHW Type that supports that relationship type
	// need to think about what to do if the patient has any existing relationships of the same type--probably shouldn't end it automatically?
	public void assignCHW(Patient patient, Provider provider, RelationshipType relationshipType);
	
	// same as above but allows specification of the date
	public void assignCHW(Patient patient, Provider provider, RelationshipType relationshipType, Date date);

	// unassigns a CHW from a patient
	// finds the matching relationship and ends it on the current date
	public void unassignCHW(Patient patient, Provider provider, RelationshipType relationshipType);
	
	// unassigns a CHW from a patient
	// finds the matching relationship and ends it on the specified date
	public void unassignCHW(Patient patient, Provider provider, RelationshipType relationshipType, Date date);
	
	// unassigns all patient from this CHW
	// gets all patients that have a relationship of any RelationshipType to this CHW 
	// and end the relationship on the current date
	public void unassignAllPatients(Provider provider);
	
	public void unassignAllPatients(Provider provider, RelationshipType type);
	
	public void unassignAllPatients(Provider provider, Date date);
	
	public void unassignAllPatients(Provider provider, RelationshipType type, Date date);
	
	// should confirm that source and destination are of the same type? or at leaset confirm that destination supports all the necessary relationships
	public void transferAllPatients(Provider source, Provider destination);
	
	public void transferAllPatients(Provider source, Provider destination, Date date);
	
	public void transferAllPatients(Provider source, Provider destination, RelationshipType type);
	
	public void transferAllPatients(Provider source, Provider destination, RelationshipType type, Date date);
	
	// get all CHWs associated with the specified patient
	// finds all relationships where the patient is person B and the RelationType is one of the defined RelationshipTypes,
	// and the current date falls within the start and end date of the relationship
	// note that this method would return retired providers (though they may be flagged differently in the ui)
	public List<Provider> getCHWs(Patient patient);
	
	// does the same as the above but uses the specified date as opposed to the current date
	public List<Provider> getCHWs(Patient patient, Date date);
	
	// does the same as the above but uses the specified date as opposed to the current date
	// if includeHistorical=true, returns all CHWs regardless of start/end date of the relationship
	// includeHistorical=false would do the same as a simple get
	public List<Provider> getCHWs(Patient patient, Boolean includeHistorical);
	
	// get all CHWs of with a certain relationship type to the given patient
	// same as above but now RelationshipType must equal specified type
	public List<Provider> getCHWs(Patient patient, RelationshipType type);
	
	public List<Provider> getCHWs(Patient patient, RelationshipType type, Date date);
	
	public List<Provider> getCHWs(Patient patient, RelationshipType type, Boolean includeHistorical);
	
	// get all patients of the passed chw on the current date
	public List<Patient> getPatients(Provider chw);
	
	// get all patients of the passed chw on the specified date
	public List<Patient> getPatients(Provider chw, Date date);
	
	public List<Patient> getPatients(Provider chw, Boolean includeHistorical);
	
	// get all patients of the specified chw with the specified relationship type on the current date
	public List<Patient> getPatients(Provider chw, RelationshipType type);
	
	public List<Patient> getPatients(Provider chw, RelationshipType type, Date date);
	
	public List<Patient> getPatients(Provider chw, RelationshipType type, Boolean includeHistorical);
	
	// suggest a CHW to for the specified patient
	// first this method builds a list of getCHW(type);
	// if no CHWSuggestions have been specified, method simply returns this list
	// otherwise, the method iterates through all CHWSuggestions where relationshipType = specified type
	// it then iterates through all the providers returns by getCHW(type), and if the 
	// all the address fields specified by the suggestion match between the CHW and the patient,
	// the CHW is added to the results list
	// (need to define what address to use... would only be non-voided addresses active on the current date.. but
	// if there are multiple addresses, do we allow a match on any?)
	public List<Provider> suggestCHW(Patient patient, RelationshipType type);
		
	
	/** CHW Supervisor-to-CHW method **/
	
	// assign a supervisor to a CHW
	// this creates a B-to-A "supervisor" relationship between the supervisee and the supervisor,
	// starting on the current date
	// note that unlike CHW relationships, the supervisor relationship will be an explicit relationship added by the module on
	// startup and referenced by uuid (alternatively, we could create CHWSupervisorType but only use one in Rwanda--would
	// this be better for consistency, or would it just add unnecessary complexity?)
	// also should always confirm that the supervisee is a valid CHW (or supervisor), and the supervisor is a valid supervisor
	// also need to make sure the that supervisor has a type that can supervise a type associated with the CHW?
	// TODO: do we need to do anything in particular to allow for hierarchical references--one supervisor can supervise another supervisor?
	// should do validation here to make sure that no CHWs have a supervisor of the same type(?)
	public void assignSupervisor(Provider supervisee, Provider supervisor);
	
	public void assignSupervisor(Provider supervisee, Provider supervisor, Date date);
	
	public void unassignSupervisor(Provider supervisee, Provider supervisor);
	
	public void unassignSupervisor(Provider supervisee, Provider supervisor, Date date);

	public void unassignAllSupervisors(Provider supervisee);
	
	public void unassignAllSupervisors(Provider supervisee, Date date);
	
	public void unassignAllSupervisees(Provider supervisor);
	
	public void unassignAllSupervisees(Provider supervisor, Date date);
	
	public List<Provider> getSupervisors(Provider supervisee);
	
	public List<Provider> getSupervisors(Provider supervisee, Date date);
	
	public List<Provider> getSupervisors(Provider supervisee, Boolean includeHistorical);
	
	public List<Provider> getSupervisees(Provider supervisor);
	
	public List<Provider> getSupervisees(Provider supervisor, Date date);
	
	public List<Provider> getSupervisees(Provider supervisor, Boolean includeHistorical);

	// first gets all types that the CHWType associated with this supervisor supports
	// then gets all the all the CHWs with these types
	// returns this list if no SuperviseeSugestion provided
	// otherwise does address comparison
	// TODO: maybe JUST have autoassign here?  or do we still need suggestion for accompanteur?
	//public List<Provider> suggestSupervisees(Provider supervisor);
	
	// given a provider, gets the CHW Type for that provider, and then sees if that CHW Type has any auto-assigned rules specified
	// if not, do *NOTHING*... 
	// if there is an autoassign, use those rules to see the list of CHWs that this provider should supervise
	// then compare that list to the list of CHWs the supervisor currently has a supervisor relationship with
	// stop and start relationships as necessary until the two lists are consistent
	public void autoAssignSupervisees(Provider supervisor);
	public void autoAssignSupervisees(Provider supervisor, Date date);
	
	// so, thinking this through... every time you changed the address of a Supervisor, public void autoAssignSupervisees(Provider supervisor), which
	// would only do something if the AutoAssignSupervisees class was defined
	
	// basically does the equivalent of called unassignAllPatients, unassignAllSupervisees, unassignAllSupervisors, and then retireProvider
	public void retireCHW(Provider provider, String retireReason);
	public void retireCHW(Provider provider, Date date, String retireReason);
}
