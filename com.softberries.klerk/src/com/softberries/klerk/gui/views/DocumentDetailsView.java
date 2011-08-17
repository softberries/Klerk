package com.softberries.klerk.gui.views;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;

import com.softberries.klerk.Activator;
import com.softberries.klerk.dao.to.Document;

public class DocumentDetailsView extends ViewPart implements ISelectionListener {

	public static final String ID = "com.softberries.klerk.gui.views.DocumentDetailsView"; //$NON-NLS-1$
	private Label lblTest;

	public DocumentDetailsView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		Composite composite;
		container.setLayout(new GridLayout(1, false));
		Browser browser = new Browser(container, SWT.SCROLL_LOCK);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		Activator instance = Activator.getDefault();
		Bundle bundle = instance.getBundle();
		URL url = FileLocator.find(bundle, new Path("xhtml/product_chart.html"),null);
		System.out.println(url.getPath());
		try {
			URL fileUrl = FileLocator.toFileURL(url);
			System.out.println(fileUrl.getPath());
			browser.setUrl(fileUrl.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		createActions();
		initializeToolBar();
		initializeMenu();
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getActiveWorkbenchWindow().getActivePage().addSelectionListener(this);
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		Object selection = ((IStructuredSelection) sel).getFirstElement();
		if(selection != null && selection instanceof Document){
			Document p = (Document)selection;
			this.lblTest.setText(p.getTitle());
		}
	}

}
