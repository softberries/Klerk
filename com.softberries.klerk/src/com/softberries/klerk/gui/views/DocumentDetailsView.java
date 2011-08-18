package com.softberries.klerk.gui.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.gui.helpers.table.Property;
import com.softberries.klerk.gui.helpers.table.SingleDocumentModelProvider;

public class DocumentDetailsView extends ViewPart implements ISelectionListener {

	public static final String ID = "com.softberries.klerk.gui.views.DocumentDetailsView"; //$NON-NLS-1$
	private Document document;
	private Table table;
	private TableViewer tableViewer;

	public DocumentDetailsView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		GridLayout gl_parent = new GridLayout(1, false);
		gl_parent.marginWidth = 0;
		gl_parent.marginHeight = 0;
		parent.setLayout(gl_parent);
		
		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		createColumns(parent, tableViewer);
		table = tableViewer.getTable();
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table.heightHint = 466;
		gd_table.widthHint = 283;
		table.setLayoutData(gd_table);
		table.setLinesVisible(true);
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		
		createActions();
		initializeToolBar();
		initializeMenu();
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getActiveWorkbenchWindow().getActivePage().addSelectionListener(this);
	}

	private void createColumns(Composite parent, TableViewer tableViewer2) {
		String[] titles = { "Name", "Value"};
		int[] bounds = { 100, 100};

		// First column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Property p = (Property) element;
				return p.getName();
			}
		});

		// Second column is for the last name
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Property p = (Property) element;
				return p.getValue();
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;

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
			Document d = (Document)selection;
			document = d;
			tableViewer.setInput(new SingleDocumentModelProvider(this.document).getProperties());
			tableViewer.refresh();
		}
	}

}
