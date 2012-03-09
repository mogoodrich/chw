package org.openmrs.module.chw.service;

import java.util.Date;
import java.util.List;

import org.openmrs.Person;
import org.openmrs.Provider;
import org.openmrs.module.chw.CHWRole;
import org.openmrs.module.chw.CHWSupervisorRole;


public interface CHWSupervisorService {
	
	// the challenge is that we have types of CHWs, and then types of relationships?
	
/** Basic CHW Supervisor Service methods **/
	
	// (these basically act the same way as the basic CHW methods, except they use a "CHW Supervisor Role" ProviderAttributeType)
	// TODO: could there be some way to share some common elements here?  is it worth keeping a CHW separate from a CHW supervisor because
	// it realy is a distinct relationship?
	
	// TODO: make sure to account for/ignore voided patients/providers?  
	// TODO: (but what does voided really mean?  distinguish between an inactive supervisor/CHW and a voided supervisor/CHW?  add an "active" field to provider?
	
	// TODO: ** is this a big issue--different roles only supervise different types of CHWs?
	
	// TODO: a *provider* (but a person might be able be associated with multiple providers?)
	// TODO: can only have ONE CHWRole OR CHWSupervisorRole--would making this assumption allow us to avoid confusion?
	
	
	public List<CHWRole> getAllCHWSupervisersRoles();
	
	// TODO: does supervisor need to a provider?
	public void addCHWSupervisorRole(Provider provider, CHWSupervisorRole role);
	
	public void removeCHWSupervisorRole(Provider provider, CHWSupervisorRole role);
	
	public List<Provider> getAllCHWSupervisors();
	
	public List<Provider> getCHWSupervisors(CHWSupervisorRole role);

	public List<CHWRole> getCHWSupervisorRoles(Provider provider);
	
	public Boolean isCHWSupervisor(Provider provider);
	
	/** CHW Supervisor-to-CHW method **/
	
	// assign a supervisor to a CHW
	// this creates a B-to-A "supervisor" relationship between the supervisee and the supervisor,
	// starting on the current date
	// note that unlike CHW relationships, the supervisor relationship will be an explicit relationship added by the module on
	// startup and referenced by uuid (alternatively, we could create CHWSupervisorRole but only use one in Rwanda--would
	// this be better for consistency, or would it just add unnecessary complexity?)
	// also should always confirm that the supervisee is a valid CHW (or supervisor), and the supervisor is a valid supervisor
	// also need to make sure the that supervisor has a supervisor role that can supervise a role associated with the CHW?
	// are we allowing for hierarchical references--one supervisor can supervise another supervisor?
	public void assignCHWSupervisor(Provider supervisee, Provider supervisor);
	
	public void assignCHWSupervisor(Provider supervisee, Provider supervisor, Date date);
	
	public void unassignCHWSupervisor(Provider supervisee, Provider supervisor);
	
	public void unassignCHWSupervisor(Provider supervisee, Provider supervisor, Date date);
	
	public List<Provider> getSupervisors(Provider supervisee);
	
	public List<Provider> getSupervisors(Provider supervisee, Date date);
	
	public List<Provider> getSupervisors(Provider supervisee, Boolean includeHistorical);
	
	public List<Provider> getSupervisorees(Provider supervisor);
	
	public List<Provider> getSupervisorees(Provider supervisor, Date date);
	
	public List<Provider> getSupervisorees(Provider supervisor, Boolean includeHistorical);
	
	// DO WE NEED TO FETCH supervisors by role?
	
	// get all CHWs that have the specified person as a supervisor
	public List<Provider> getCHWs(Person supervisor);
	
	// get all CHWS that have the specified person as a supervisor of the specified role
	public List<Provider> getCHWs(Person supervisor, CHWSupervisorRole role);
	
	
}
