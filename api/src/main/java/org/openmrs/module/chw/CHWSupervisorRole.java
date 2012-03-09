package org.openmrs.module.chw;

import java.util.List;

import org.openmrs.BaseOpenmrsMetadata;


public class CHWSupervisorRole extends BaseOpenmrsMetadata {

	// the list of chw roles this supervisor role can supervise
	List<CHWRole> chwRoles;
	
	// TODO: also needs to be able supervise other supervisors?
	
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
