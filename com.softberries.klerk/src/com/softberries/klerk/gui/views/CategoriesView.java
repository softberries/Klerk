package com.softberries.klerk.gui.views;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;



import com.softberries.klerk.ICommandIds;
import com.softberries.klerk.commands.OpenInvoicesCommand;
import com.softberries.klerk.gui.helpers.IImageKeys;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.tree.*;

public class CategoriesView extends ViewPart implements ISelectionChangedListener, ITreeViewerListener, IDoubleClickListener {

	public static final String ID = "com.softberries.klerk.gui.views.CategoriesView"; //$NON-NLS-1$
	private TreeViewer viewer;

	public CategoriesView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		setPartName(Messages.CategoriesView_categories);
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		{
			viewer = new TreeViewer(container, SWT.BORDER);
		}
		viewer.addSelectionChangedListener(this);
		viewer.addTreeListener(this);
		viewer.addDoubleClickListener(this);
		viewer.setContentProvider(new TreeViewContentProvider());
		viewer.setLabelProvider(new TreeViewLabelProvider());
		viewer.setInput(createDummyModel());
		viewer.expandAll();
		createActions();
		initializeToolBar();
		initializeMenu();
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
		viewer.getControl().setFocus();
	}

	private TreeObject createDummyModel() {
		TreeObject invoices = new TreeObject(Messages.CategoriesView_invoices,
				IImageKeys.CATEGORY_INVOICES);
		TreeObject products = new TreeObject(Messages.CategoriesView_products,
				IImageKeys.CATEGORY_PRODUCTS);
		TreeObject companies = new TreeObject(Messages.CategoriesView_companies,
				IImageKeys.CATEGORY_COMPANIES);
		TreeObject people = new TreeObject(Messages.CategoriesView_people,
				IImageKeys.CATEGORY_PEOPLE);
		TreeParent docs = new TreeParent(Messages.CategoriesView_documents,
				IImageKeys.CATEGORY_DOCUMENTS);
		TreeParent inventory = new TreeParent(Messages.CategoriesView_inventory,
				IImageKeys.CATEGORY_INVENTORY);
		TreeParent administration = new TreeParent(Messages.CategoriesView_administration,
				IImageKeys.CATEGORY_ADMINISTRATION);
		
		docs.addChild(invoices);
		inventory.addChild(products);
		inventory.addChild(companies);
		administration.addChild(people);
		
		TreeParent p1 = new TreeParent(Messages.CategoriesView_all_Categories, IImageKeys.ALL_CATEGORIES);
		p1.addChild(docs);
		p1.addChild(inventory);
		p1.addChild(administration);
		TreeParent root = new TreeParent("", IImageKeys.ALL_CATEGORIES); //$NON-NLS-1$
		root.addChild(p1);
		return root;
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		TreeObject selectedDomainObject = null;
		if (viewer.getSelection().isEmpty()) {
			return;
		} else {
			IStructuredSelection selection = (IStructuredSelection) viewer
					.getSelection();
			selectedDomainObject = (TreeObject) selection.getFirstElement();
			System.out.println(selectedDomainObject + Messages.CategoriesView_messages + Messages.CategoriesView_invoices);
		}
		try {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			IHandlerService service = (IHandlerService) this.getSite().getService(
					IHandlerService.class);
			if(Messages.CategoriesView_invoices.toString().equals(selectedDomainObject.toString())){
				service.executeCommand(ICommandIds.CMD_OPEN_DOCUMENTS_INVOICES, null);
			}else if(Messages.CategoriesView_products.toString().equals(selectedDomainObject.toString())){
				service.executeCommand(ICommandIds.CMD_OPEN_INVENTORY_PRODUCTS, null);
			}else if(Messages.CategoriesView_companies.toString().equals(selectedDomainObject.toString())){
				service.executeCommand(ICommandIds.CMD_OPEN_INVENTORY_COMPANIES, null);
			}else if(Messages.CategoriesView_people.toString().equals(selectedDomainObject.toString())){
				service.executeCommand(ICommandIds.CMD_OPEN_ADMINISTRATION_PEOPLE, null);
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (NotDefinedException e) {
			e.printStackTrace();
		} catch (NotEnabledException e) {
			e.printStackTrace();
		} catch (NotHandledException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void treeCollapsed(TreeExpansionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void treeExpanded(TreeExpansionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO Auto-generated method stub
		
	}
}
