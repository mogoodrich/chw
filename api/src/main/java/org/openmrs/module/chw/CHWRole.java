package org.openmrs.module.chw;

import java.util.Set;

import org.openmrs.BaseOpenmrsMetadata;


public class CHWRole extends BaseOpenmrsMetadata {
	
	
	// TODO: do we need this?
	private Set<CHWRelationshipType> relationshipTypes;    // represented by a many-to-many CHWRole to CHWRelationType table
	
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
