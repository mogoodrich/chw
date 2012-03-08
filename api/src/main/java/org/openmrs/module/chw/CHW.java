package org.openmrs.module.chw;

import org.openmrs.Provider;
import org.openmrs.ProviderAttribute;
import org.openmrs.ProviderAttributeType;
import org.openmrs.api.context.Context;


// TODO: is this even necessary?
// TODO: do we enforce that a provider has only one CHW Role

public class CHW extends Provider {
	
	
	
	public CHWRole getRole() {
		// TODO: in the fleshed out version, we'd handle the case of multiple CHWTypes per Provider
		// TODO: my gut is that multiple types should be allowed... but maybe not?
		
		for (ProviderAttribute attribute : this.getActiveAttributes()) {
			
			if (attribute.getValue() instanceof CHWRole) {
				return (CHWRole) attribute.getValue();
			}	
		}
		return null;
	}
	
	public void setRole(CHWRole role) {
		// TODO: in the fleshed out version, we'd handle the case of multiple CHWRole per Provider
		// TODO: right now we are really only 
		
		ProviderAttributeType chwRoleType = Context.getProviderService().getProviderAttributeTypeByUuid(CHWConstants.CHW_ROLE_ATTRIBUTE_TYPE_UUID);
		ProviderAttribute attr = new ProviderAttribute();
		attr.setAttributeType(chwRoleType);
		attr.setValue(role);
		
		
	}
	
	
	// TODO: get patients?
}
