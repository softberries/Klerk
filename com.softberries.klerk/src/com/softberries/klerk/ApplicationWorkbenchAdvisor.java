package com.softberries.klerk;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.softberries.klerk.gui.perspectives.MainPerspective;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {


    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

	public String getInitialWindowPerspectiveId() {
		return MainPerspective.ID;
	}
}
