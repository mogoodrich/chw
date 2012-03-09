package org.openmrs.module.chw.service;

import java.util.Date;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.Provider;
import org.openmrs.Role;
import org.openmrs.module.chw.CHWRelationshipType;
import org.openmrs.module.chw.CHWRole;

// TODO: add transactional stuff based on best practice

public interface CHWService {
	
	// TODO: should CHW be a first-class object?  Then we could add things like PersonAddress at a later date?
	// TODO: maybe do this to start out... add CHW with get address?  how would this change everything?
	// TODO: then we could also get rid of the provider attribute type?  does that matter?
	// TODO: allow for more direct querying?
	// TODO: allows us to provide a CHWAttributeType??? So we don't have to global prop this?
	
	// TODO: maybe all these methods should return Sets instead of Lists to avoid duplicates?  
	
	// TODO: make sure to account for/ignore voided patients/providers?
	// TODO: add the retire/inactive feature?
	// TODO: (but what does voided really mean?  distinguish between an inactive supervisor/CHW and a voided supervisor/CHW?  add an "active" field to provider?
	
	/** Basic methods to assign CHW roles to providers **/
	
	public List<CHWRole> getAllCHWRoles();
	public List<CHWRole> getCHWRoles(CHWRelationshipType type); 	// get all CHW roles that support the specified relationship	
	public List<CHWRole> getAllSupervisorRoles();  // roles where chwSuperviseeRoles != null
	public List<CHWRole> getAllDirectCareRoles();  // roles where relationshipTypes != null
	public List<CHWRelationshipType> getAllCHWRelationshipTypes();
	
	
	// set the specified CHW Role to the specified person
	// note that a provider can only have one CHW Role, so setting this will overwrite any existing one
	// TODO: could a single person be associated with two different providers with different roles, and would this work?
	// this is done by setting the role as a ProviderAttribute of the person
	// (on startup, module will explicitly add a "CHW Provider" ProviderAttributeType to DB that it will 
	// referenced by a constant set to UUID of the ProviderAttributeType entry for "CHW Provider")
	public void setCHWRole(Provider provider, CHWRole role);
	
	// get the CHW role associated with the specified provider
	public CHWRole getCHWRoles(Provider provider);
	
	// removes the role associated with the this provider
	// (i.e., remove the "CHW Provider" attribute entirely)
	public void deleteCHWRole(Provider provider);
	
	// returns all providers with a "CHW Provider" ProviderAttribute value that is in getAllDirectCareRoles(); 
	public List<Provider> getAllCHWs();
	public List<Provider> getAllCHWs(Boolean includeRetired);  // note that default method does NOT include retired, and that providers associated with voided persons are ALWAYS excluded (is this correct behavior?)
	public List<Provider> getAllCHWs(Boolean includeSupervisors, Boolean includeRetired); // getAllCHWs() does not include supervisors by default
	
	// returns all providers with a "CHW Provider" ProviderAttribute value that is in getAllSupervisorRoles(); 
	public List<Provider> getAllSupervisors();
	public List<Provider> getAllSupervisors(Boolean includeRetired);
	
	// TODO: note that although a provider can only have one role, it IS possible for a role to both a CHW and CHW supervisor--
	// TODO: so the lists returned by the above two records may not be mutually exclusive
	
	// get all CHWs with a certain role
	// returns all provider that have a "CHW Provider" attribute with value = role
	public List<Provider> getCHWs(CHWRole role);
	public List<Provider> getCHWs(CHWRole role, Boolean includeRetired);
	
	
	// gets all CHWs that support a certain relationship
	// (basically build list by called getCHW(role) for each role in getCHWRoles(type) where type=specified type
	public List<Provider> getCHWs(CHWRelationshipType type);
	public List<Provider> getCHWs(CHWRelationshipType type, Boolean includeRetired);
	
	// maybe a utility "create CHW" method that does a savePerson and then assignCHWRole?
	
	public Boolean isCHW(Provider provider);
	
	public Boolean isSupervisor(Provider provider);
	
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
		
	/** CHW Supervisor-to-CHW method **/
	
	// assign a supervisor to a CHW
	// this creates a B-to-A "supervisor" relationship between the supervisee and the supervisor,
	// starting on the current date
	// note that unlike CHW relationships, the supervisor relationship will be an explicit relationship added by the module on
	// startup and referenced by uuid (alternatively, we could create CHWSupervisorRole but only use one in Rwanda--would
	// this be better for consistency, or would it just add unnecessary complexity?)
	// also should always confirm that the supervisee is a valid CHW (or supervisor), and the supervisor is a valid supervisor
	// also need to make sure the that supervisor has a role that can supervise a role associated with the CHW?
	// TODO: do we need to do anything in particular to allow for hierarchical references--one supervisor can supervise another supervisor?
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

	// first gets all roles that the CHWRole associated with this supervisor supports
	// then gets all the all the CHWs with these roles
	// returns this list if no SuperviseeSugestion provided
	// otherwise does address comparison
	// TODO: maybe JUST have autoassign here?  or do we still need suggestion for accompanteur?
	public List<Provider> suggestSupervisees(Provider supervisor);
	
	// calls unassignAllSupervisees(), then suggestSupervisees, and then assignSupervisor for all supervisees in the result list
	public void autoAssignSupervisees(Provider supervisor);
	public void autoAssignSupervisees(Provider supervisor, Date date);
	
	// so, thinking this through... every time you changed the address of a Supervisor, public void autoAssignSupervisees(Provider supervisor), which
	// would only do something if the AutoAssignSupervisees class was defined
	
	// basically does the equivalent of called unassignAllPatients, unassignAllSupervisees, unassignAllSupervisors, and then retireProvider
	public void retireCHW(Provider provider);
	public void retireCHW(Provider provider, Date date);
}
