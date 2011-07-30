package com.softberries.klerk.gui.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.gui.editors.DocumentsEditor;

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
		container.setLayout(new GridLayout(2, false));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			lblTest = new Label(container, SWT.NONE);
			GridData gd_lblTest = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_lblTest.widthHint = 563;
			lblTest.setLayoutData(gd_lblTest);
			lblTest.setText("test");
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
		System.out.println("SELECTION: " + sel + "PART: " + part);
		Object selection = ((IStructuredSelection) sel).getFirstElement();
		if(selection != null && selection instanceof Document){
			Document p = (Document)selection;
			this.lblTest.setText(p.getTitle());
		}
	}

}
