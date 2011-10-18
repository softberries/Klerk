package com.softberries.klerk;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * 
 * @author krzysztof.grajek@softberries.com
 *
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private MenuManager showViewMenuMgr;
	private IContributionItem showViewItem;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	@Override
	protected void makeActions(IWorkbenchWindow window) {
		showViewMenuMgr = new MenuManager("Show View", "showview");
		showViewItem = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
	}

	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager windowMenu = new MenuManager("&Start",
				IWorkbenchActionConstants.M_WINDOW);
		MenuManager helpMenu = new MenuManager("&Help",
				IWorkbenchActionConstants.M_HELP);
		showViewMenuMgr.add(showViewItem);
		windowMenu.add(showViewMenuMgr);
		menuBar.add(windowMenu);
		menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menuBar.add(helpMenu);
	}

}
