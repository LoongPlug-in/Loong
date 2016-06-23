package loongplugin.uml.model;

import loongplugin.uml.LoongUMLPlugin;


import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DependencyModel extends AbstractUMLConnectionModel implements StereoTypeModel {

	private String stereoType = "";
	
	public static final String P_STEREO_TYPE = "_stereo_type";
	
	public void setStereoType(String stereoType) {
	this.stereoType = stereoType;
	firePropertyChange(P_STEREO_TYPE, null, stereoType);
	}
	
	public String getStereoType() {
	return this.stereoType;
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
	return new IPropertyDescriptor[] {
			new TextPropertyDescriptor(P_STEREO_TYPE, LoongUMLPlugin
					.getDefault().getResourceString("property.stereoType")),
			new ColorPropertyDescriptor(P_FOREGROUND_COLOR, LoongUMLPlugin
					.getDefault().getResourceString("property.foreground")),
			};
	}
	
	public Object getPropertyValue(Object id) {
	if (id.equals(P_STEREO_TYPE)) {
		return getStereoType();
	}
	return super.getPropertyValue(id);
	}
	
	public void setPropertyValue(Object id, Object value) {
	if (id.equals(P_STEREO_TYPE)) {
		setStereoType((String) value);
	}
	super.setPropertyValue(id, value);
	}
}

