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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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
import org.eclipse.wb.swt.ResourceManager;

import com.softberries.klerk.Activator;
import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.gui.editors.input.ProductEditorInput;
import com.softberries.klerk.gui.helpers.IImageKeys;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.table.ProductComparator;
import com.softberries.klerk.gui.helpers.table.ProductFilter;
import com.softberries.klerk.gui.helpers.table.ProductsModelProvider;

public class ProductsEditor extends EditorPart implements
		ISelectionChangedListener, ISelectionListener, IDoubleClickListener {

	public static final String ID = "com.softberries.klerk.gui.editors.ProductsEditor"; //$NON-NLS-1$
	private TableViewer viewer;
	private ProductComparator comparator;
	private ProductFilter filter;

	public ProductsEditor() {
	}

	/**
	 * Create contents of the editor part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(6, false);
		parent.setLayout(layout);
		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText(Messages.ProductsEditor_Search);
		final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));
		
		Button addBtn = new Button(parent,  SWT.BORDER);
		addBtn.setToolTipText("Add new product");
		addBtn.setImage(ResourceManager.getPluginImage("com.softberries.klerk", "icons/png/add.png"));
		addBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Product newP = new Product();
				newP.setCode("Enter the code...");
				newP.setName("New Product");
				newP.setDescription("");
				openSingleProductEditor(newP);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		Button editBtn = new Button(parent,  SWT.BORDER);
		editBtn.setImage(ResourceManager.getPluginImage("com.softberries.klerk", "icons/png/edit.png"));
		Button deleteBtn = new Button(parent,  SWT.BORDER);
		deleteBtn.setToolTipText("Delete Product");
		deleteBtn.setImage(ResourceManager.getPluginImage("com.softberries.klerk", "icons/png/remove.png"));
		deleteBtn.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("delete product");
				viewer.setInput(ProductsModelProvider.INSTANCE.getProducts());
				viewer.refresh();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		//refresh button
		Button refreshBtn = new Button(parent,  SWT.BORDER);
		refreshBtn.setToolTipText("Refresh");
		refreshBtn.setImage(ResourceManager.getPluginImage("com.softberries.klerk", "icons/png/refresh.png"));
		refreshBtn.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("refresh model");
				viewer.setInput(ProductsModelProvider.INSTANCE.getProducts());
				viewer.refresh();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		createViewer(parent);
		// Set the sorter for the table
		comparator = new ProductComparator();
		viewer.setComparator(comparator);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		// New to support the search
		searchText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				filter.setSearchText(searchText.getText());
				viewer.refresh();
			}

		});
		filter = new ProductFilter();
		viewer.addFilter(filter);
	}

	private void createViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(ProductsModelProvider.INSTANCE.getProducts());
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
		viewer.addSelectionChangedListener(this);
		getSite().getPage().addSelectionListener((ISelectionListener) this);
		viewer.addDoubleClickListener(this);
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { Messages.ProductsEditor_Code, Messages.ProductsEditor_Name, Messages.ProductsEditor_Description};
		int[] bounds = { 100, 200, 100 };

		// First column is for the code
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Product) cell.getElement()).getCode());
			}
		});

		// Second column is for the name
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Product) cell.getElement()).getName());
			}
		});
		// Now the description
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Product) cell.getElement()).getDescription());
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
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		// System.out.println(selection.getFirstElement());
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// System.out.println(selection);
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		IWorkbenchPage page;
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		if (selection != null) {
			Product d = (Product) selection.getFirstElement();
			openSingleProductEditor(d);
		}
	}

	/**
	 * Opens {@code SingleProductEditor} to edit the product details
	 * 
	 * @param p Product to be edited
	 */
	private void openSingleProductEditor(Product p) {
		IWorkbenchPage page;
		IEditorInput input = new ProductEditorInput(p);
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage();
		try {
			page.openEditor(input, SingleProductEditor.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

}
