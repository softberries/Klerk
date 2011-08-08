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

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private MenuManager showViewMenuMgr;
	private IWorkbenchAction showHelpAction; // NEW
	private IWorkbenchAction searchHelpAction; // NEW
	private IWorkbenchAction dynamicHelpAction; // NEW
	private IContributionItem showViewItem;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	@Override
	protected void makeActions(IWorkbenchWindow window) {
		showViewMenuMgr = new MenuManager("Show View", "showview");
		showViewItem = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
		showHelpAction = ActionFactory.HELP_CONTENTS.create(window); // NEW
		register(showHelpAction); // NEW

		searchHelpAction = ActionFactory.HELP_SEARCH.create(window); // NEW
		register(searchHelpAction); // NEW

		dynamicHelpAction = ActionFactory.DYNAMIC_HELP.create(window); // NEW
		register(dynamicHelpAction); // NEW
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

		helpMenu.add(showHelpAction); // NEW
		helpMenu.add(searchHelpAction); // NEW
		helpMenu.add(dynamicHelpAction); // NEW
	}

}
