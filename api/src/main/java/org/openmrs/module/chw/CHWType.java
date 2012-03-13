package org.openmrs.module.chw;

import java.util.List;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.RelationshipType;


public class CHWType extends BaseOpenmrsMetadata {

	// TODO: transactional annotations and privileges
	
	// note that "CHWType" will be defined as a new ProviderAttributeType
	// all providers that are CHWs will have a value defined for CHWType
	
	// for simplicities sake, a provider will only be allowed to have one CHWType,
	// and we will assume that a person is only associated with one unretired provider
	// (though we may change this in the future)
	// a key point here is that although relationship types will be tied to chw types, this is 
	// a relatively loose relationship... we should be able to determine all we need to do about
	// the relationship between two people based on their CHWRelationshipType; for example, if at 
	// some time in the future a single provider is able to have more than one CHWType, remember that
	// a *Person* is linked in a Relationship, not a *Provider*... if we have someone who is both a "binome"
	// and an "accompateur", and she has a "accompateur" relationship to a patient, we have no way of knowing
	// which "hat" (binome of accompatuer) the provider is wearing when taking part in this relationship
	// generally, this shouldn't matter--**BUT** we need to make sure we model our metadata so that this doesn't matter
	
	
	// the list of relationships type this role supports
	// if this is null, it means that this role cannot provide any direct care to patients (is only a supervisor)
	// represented by a table mapping chw_role_id to chw_relationship_id in a many-to-many relationship
	List<RelationshipType> relationshipTypes;
	
	// the list of chw roles this role can supervise
	// if this is null, it means that this role cannot provide any "supervisor" operations
	// represented by a table mapping chw_role_id to chw_role_id in a many-to-many relationship
	List<CHWType> chwTypesToSupervise;    // TODO: come up with a better name
	
	public Boolean isSupervisorRole()  {
		return (chwTypesToSupervise == null || chwTypesToSupervise.size() == 0 ? false : true);
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
