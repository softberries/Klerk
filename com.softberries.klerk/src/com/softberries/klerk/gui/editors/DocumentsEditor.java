package com.softberries.klerk.gui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.gui.editors.input.DocumentEditorInput;
import com.softberries.klerk.gui.helpers.table.DocumentComparator;
import com.softberries.klerk.gui.helpers.table.DocumentFilter;
import com.softberries.klerk.gui.helpers.table.DocumentsModelProvider;

public class DocumentsEditor extends EditorPart implements IDoubleClickListener {

	public static final String ID = "com.softberries.klerk.gui.editors.DocumentsEditor"; //$NON-NLS-1$
	private TableViewer viewer;
	private DocumentComparator comparator;
	private DocumentFilter filter;

	public DocumentsEditor() {
	}

	/**
	 * Create contents of the editor part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText("Search: ");
		final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));
		createViewer(parent);
		// Set the sorter for the table
		comparator = new DocumentComparator();
		viewer.setComparator(comparator);
		// New to support the search
		searchText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				filter.setSearchText(searchText.getText());
				viewer.refresh();
			}

		});
		filter = new DocumentFilter();
		viewer.addFilter(filter);
	}

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(DocumentsModelProvider.INSTANCE.getDocuments());
		// Make the selection available to other views
		getSite().setSelectionProvider(viewer);
		// Set the sorter for the viewer

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
		viewer.addDoubleClickListener(this);
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Title", "Notes", "Date Created", "Transaction Date", "Due Date", "Place Created",
				"Creator" };
		int[] bounds = { 100, 100, 100, 100, 100, 100, 100 };

		// First column is for the title
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getTitle());
			}
		});

		// Second column is for the notes
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getNotes());
			}
		});
		// Now the date (created)
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getCreatedDate()
						.toLocaleString());
			}
		});
		// Now the date (transaction)
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement())
						.getTransactionDate().toLocaleString());
			}
		});
		// Now the date (due)
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getDueDate()
						.toLocaleString());
			}
		});
		// place
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Document) cell.getElement()).getPlaceCreated());
			}
		});
		// creator
		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Document doc = ((Document) cell.getElement());
				cell.setText(doc.getCreator().getFirstName() + " "
						+ doc.getCreator().getLastName());
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
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

	private SelectionAdapter getSelectionAdapter(final TableColumn column,
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
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Do the Save operation
	}

	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		IWorkbenchPage page;
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		if (selection != null) {
			Document d = (Document) selection.getFirstElement();
			IEditorInput input = new DocumentEditorInput(d);
			page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage();
			try {
				page.openEditor(input, SingleDocumentEditor.ID);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}

}
