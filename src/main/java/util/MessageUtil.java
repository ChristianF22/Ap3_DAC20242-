package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageUtil {
	public static void addInfoMsg(String sumary, String detail) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, sumary, detail);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public static void addErrorMsg(String sumary, String detail) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, sumary, detail);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}