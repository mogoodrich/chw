package org.openmrs.module.chw.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.ProviderAttribute;
import org.openmrs.ProviderAttributeType;
import org.openmrs.Relationship;
import org.openmrs.RelationshipType;
import org.openmrs.api.context.Context;
import org.openmrs.module.chw.CHWConstants;
import org.openmrs.module.chw.CHWRelationshipType;
import org.openmrs.module.chw.CHWType;


public class CHWServiceImpl implements CHWService {

	@Override
    public List<Patient> getPatients(Provider provider, CHWRelationshipType type) {
	    // TODO Auto-generated method stub
	    return null;
    }
	
    public List<Patient> getPatients(Provider provider) {
    	
    	List<RelationshipType> relationshipTypes = getRelationshipTypes(getCHWRelationshipTypes());
    	
    	List<Patient> patients = new ArrayList<Patient>();
    	
    	// TODO: might make sense just to get all relationships for this person and do checks to see if there is a match myself?
    	for (RelationshipType relationshipType : relationshipTypes) {
    		for (Relationship relationship : Context.getPersonService().getRelationships(provider.getPerson(), null, relationshipType)) {
    			
    			// TODO: handle the (error) case that a the patient is not a patient!
    			// TODO: can we just cast here? probably not?
    			patients.add(Context.getPatientService().getPatient(relationship.getPersonB().getId()));
 
    		}
    	}
    	
    	return patients;
    }
	
    public List<CHWType> getCHWRoles(Provider provider) {
    	ProviderAttributeType chwRoleAttributeType = Context.getProviderService().getProviderAttributeTypeByUuid(CHWConstants.CHW_TYPE_PROVIDER_ATTRIBUTE_TYPE_UUID);
    	
    	// TODO: might want to move this out into a utility method if there isn't one provided elsewhere that I'm missing
    	List<CHWType> roles = new ArrayList<CHWType>();
    	
    	for (ProviderAttribute attribute : provider.getActiveAttributes()) {
    		if (attribute.getAttributeType().equals(chwRoleAttributeType)) {
    			roles.add((CHWType) attribute.getValue());
    		}
     	}
    	
    	return roles;
    }
    
    
    public List<Provider> getCHWs(CHWType role) {
	    
    	ProviderAttributeType chwRoleAttributeType = Context.getProviderService().getProviderAttributeTypeByUuid(CHWConstants.CHW_TYPE_PROVIDER_ATTRIBUTE_TYPE_UUID);
    	Map<ProviderAttributeType, Object> attrs = new HashMap<ProviderAttributeType, Object>();
    	attrs.put(chwRoleAttributeType, role);
    	
    	return Context.getProviderService().getProviders(null, null, null, attrs);
    }
    
    public List<CHWRelationshipType> getCHWRelationshipTypes() {
	    // TODO Auto-generated method stub
	    return null;
    }

	
    // TODO: some auditing tools since we do allow some invalid instances to occur
    
    // TODO: move this into a public utility method
    private List<RelationshipType> getRelationshipTypes(List<CHWRelationshipType> chwRelationshipTypes) {
    	List<RelationshipType> relationshipTypes = new ArrayList<RelationshipType>();
    	
    	for (CHWRelationshipType chwRelationshipType : chwRelationshipTypes) {
    		relationshipTypes.add(chwRelationshipType.getRelationshipType());
    	}
    	
    	return relationshipTypes;
    }

	@Override
    public List<Provider> getCHWs(Patient patient, CHWRelationshipType type) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public List<Provider> getCHWs(Patient patient, CHWType role) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public List<Provider> getCHWs(Patient patient) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public List<Provider> getAllCHWs() {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public List<CHWType> getAllCHWRoles() {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public List<CHWRelationshipType> getAllCHWRelationshipTypes() {
	    // TODO Auto-generated method stub
	    return null;
    }

}
