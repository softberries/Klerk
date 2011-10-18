package com.softberries.klerk.gui.editors;

import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.wb.swt.ResourceManager;

import com.softberries.klerk.LogUtil;
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.gui.editors.input.DocumentEditorInput;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkComparator;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkFilter;


public abstract class GenericKlerkEditor extends EditorPart implements IDoubleClickListener, ISelectionListener{

	protected TableViewer viewer;
	protected SimpleKlerkComparator comparator;
	protected SimpleKlerkFilter filter;
	protected Object input;
	protected Document selectedDocument;
	public static final String DB_FOLDER_PATH = Platform.getInstallLocation().getURL().getPath() + "database" + System.getProperty("file.separator") + "klerk";
	
	private String addBtnTooltipText = Messages.GenericKlerkEditor_add;
	private String editBtnTooltipText = Messages.GenericKlerkEditor_edit;
	private String deleteBtnTooltipText = Messages.GenericKlerkEditor_delete;
	private String refreshBtnTooltipText = Messages.GenericKlerkEditor_refresh;

	
	public GenericKlerkEditor(SimpleKlerkComparator comp, SimpleKlerkFilter filter, Object input){
		this.comparator = comp;
		this.filter = filter;
		this.input = input;
		if(input != null && input instanceof DocumentEditorInput){
			DocumentEditorInput dei = (DocumentEditorInput)input;
			Document d = (Document)dei.getAdapter(Document.class);
		}
	}

	protected void openEditor(Document newD, int type) {
		LogUtil.logInfo("Opening document: " + type);
		switch(type){
			case 0: openSingleObjectEditor(new DocumentEditorInput(newD), SinglePurchaseEditor.ID);
			break;
			case 1: openSingleObjectEditor(new DocumentEditorInput(newD), SingleSaleEditor.ID);
			break;
		}
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
	}
	@Override
	public void doubleClick(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		if (selection != null) {
			onDoubleClick(selection);
		}
	}

	/**
	 * When deleting an item this method make sure that
	 * any open editor which was editing same item will be closed
	 * <p>
	 * Make sure you {@code IEditorInput} properly overrides {@code equals() } method
	 * 
	 * @param editorInput
	 */
	protected void closeOpenedEditorForThisItem(IEditorInput editorInput) {
		IEditorPart toClose = getSite().getWorkbenchWindow().getActivePage().findEditor(editorInput);
		if(toClose != null){
			getSite().getWorkbenchWindow().getActivePage().closeEditor(toClose, false);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(6, false);
		parent.setLayout(layout);
		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText(Messages.GenericKlerkEditor_search);
		final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));
		
		Button addBtn = new Button(parent,  SWT.BORDER);
		addBtn.setToolTipText(this.addBtnTooltipText);
		addBtn.setImage(ResourceManager.getPluginImage("com.softberries.klerk", "icons/png/add.png")); //$NON-NLS-1$ //$NON-NLS-2$
		addBtn.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addButtonClicked();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		Button editBtn = new Button(parent,  SWT.BORDER);
		editBtn.setImage(ResourceManager.getPluginImage("com.softberries.klerk", "icons/png/edit.png")); //$NON-NLS-1$ //$NON-NLS-2$
		editBtn.setToolTipText(this.editBtnTooltipText);
		editBtn.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editButtonClicked();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		Button deleteBtn = new Button(parent,  SWT.BORDER);
		deleteBtn.setToolTipText(this.deleteBtnTooltipText);
		deleteBtn.setImage(ResourceManager.getPluginImage("com.softberries.klerk", "icons/png/remove.png")); //$NON-NLS-1$ //$NON-NLS-2$
		deleteBtn.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteButtonClicked();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		//refresh button
		Button refreshBtn = new Button(parent,  SWT.BORDER);
		refreshBtn.setToolTipText(this.refreshBtnTooltipText);
		refreshBtn.setImage(ResourceManager.getPluginImage("com.softberries.klerk", "icons/png/refresh.png")); //$NON-NLS-1$ //$NON-NLS-2$
		refreshBtn.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refreshButtonClicked();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		createViewer(parent, input);
		// Set the sorter for the table
		viewer.setComparator(comparator);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		// New to support the search
		searchText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				filter.setSearchText(searchText.getText());
				viewer.refresh();
			}

		});
		viewer.addFilter(filter);
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getActiveWorkbenchWindow().getActivePage().addSelectionListener(this);
	}

	private void createViewer(Composite parent, Object input) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(input);
		// Make the selection available to other views
		getSite().setSelectionProvider(viewer);
		// Set the sorter for the viewer

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 6;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
		viewer.addDoubleClickListener(this);
	}

	protected TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		column.addSelectionListener(getSelectionAdapter(column, colNumber));
		return viewerColumn;

	}
	protected SelectionAdapter getSelectionAdapter(final TableColumn column,
			final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comparator.setColumn(index);
				int dir = comparator.getDirection();
				viewer.getTable().setSortDirection(dir);
				viewer.refresh();
			}
		};
		return selectionAdapter;
	}
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	protected void openSingleObjectEditor(final IEditorInput input,final String editorID){
		IWorkbenchPage page;
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage();
		try {
			page.openEditor(input, editorID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	
	@Override
	public void doSave(IProgressMonitor monitor) {
		
	}

	@Override
	public void doSaveAs() {
		
	}

	@Override
	public boolean isDirty() {
		return false;
	}
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		Object selection = ((IStructuredSelection) sel).getFirstElement();
		setSelectedObject(selection);
	}
	
	public String getAddBtnTooltipText() {
		return addBtnTooltipText;
	}
	public void setAddBtnTooltipText(String addBtnTooltipText) {
		this.addBtnTooltipText = addBtnTooltipText;
	}
	public String getEditBtnTooltipText() {
		return editBtnTooltipText;
	}
	public void setEditBtnTooltipText(String editBtnTooltipText) {
		this.editBtnTooltipText = editBtnTooltipText;
	}
	public String getDeleteBtnTooltipText() {
		return deleteBtnTooltipText;
	}
	public void setDeleteBtnTooltipText(String deleteBtnTooltipText) {
		this.deleteBtnTooltipText = deleteBtnTooltipText;
	}
	public String getRefreshBtnTooltipText() {
		return refreshBtnTooltipText;
	}
	public void setRefreshBtnTooltipText(String refreshBtnTooltipText) {
		this.refreshBtnTooltipText = refreshBtnTooltipText;
	}
	
	protected abstract void createColumns(final Composite parent, final TableViewer viewer);
	protected abstract void addButtonClicked();
	protected abstract void deleteButtonClicked();
	protected abstract void editButtonClicked();
	protected abstract void refreshButtonClicked();
	protected abstract void onDoubleClick(IStructuredSelection selection);
	protected abstract void setSelectedObject(Object selection);
}
