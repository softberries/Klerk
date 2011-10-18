package com.softberries.klerk;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.softberries.klerk.gui.perspectives.MainPerspective;

/**
 * 
 * @author krzysztof.grajek@softberries.com
 *
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {


    @Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

	@Override
	public String getInitialWindowPerspectiveId() {
		return MainPerspective.ID;
	}
}
