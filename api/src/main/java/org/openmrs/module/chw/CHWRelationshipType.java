package org.openmrs.module.chw;

import java.util.Set;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.RelationshipType;

//TODO: could this just be a list stored in a global property?
public class CHWRelationshipType extends BaseOpenmrsMetadata {
	
	RelationshipType relationshipType;  // this would be a foreign key reference to the relationship_type table


	Set<CHWRole> roles;  // represented by a many-to-many CHWRole to CHWRelationType table
	 					// (is there a downside to having both roles on this object and relationships on the role object?)

	@Override
    public Integer getId() {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public void setId(Integer arg0) {
	    // TODO Auto-generated method stub
	    
    }
	
	public RelationshipType getRelationshipType() {
    	return relationshipType;
    }

	
    public void setRelationshipType(RelationshipType relationshipType) {
    	this.relationshipType = relationshipType;
    }
	
}
