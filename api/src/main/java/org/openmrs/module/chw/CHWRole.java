package org.openmrs.module.chw;

import java.util.List;

import org.openmrs.BaseOpenmrsMetadata;


public class CHWRole extends BaseOpenmrsMetadata {
	
	// the list of relationships type this role supports
	// if this is null, it means that this role cannot provide any direct care to patients (is only a supervisor)
	// represented by a table mapping chw_role_id to chw_relationship_id in a many-to-many relationship
	List<CHWRelationshipType> relationshipTypes;
	
	// the list of chw roles this role can supervise
	// if this is null, it means that this role cannot provide any "supervisor" operations
	// represented by a table mapping chw_role_id to chw_role_id in a many-to-many relationship
	List<CHWRole> chwRolesToSupervise;    // TODO: come up with a better name
	
	public Boolean isSupervisorRole()  {
		return (chwRolesToSupervise == null || chwRolesToSupervise.size() == 0 ? false : true);
	}
	
	public Boolean isDirectCareRole() {   // TODO: come up with a better name?
		return (relationshipTypes == null || relationshipTypes.size() == 0 ? false : true);
	}
	
	@Override
    public Integer getId() {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public void setId(Integer arg0) {
	    // TODO Auto-generated method stub
	    
    }
	
}
