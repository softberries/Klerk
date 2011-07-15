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
		TreeObject to1 = new TreeObject(Messages.CategoriesView_documents,
				IImageKeys.WEBPAGE_FEED);
		TreeObject to2 = new TreeObject(Messages.CategoriesView_inventory,
				IImageKeys.WEBPAGE_FEED);
		TreeParent p1 = new TreeParent(Messages.CategoriesView_all_Categories, IImageKeys.WEBPAGE_ROOT);
		p1.addChild(to1);
		p1.addChild(to2);

		TreeParent root = new TreeParent("", IImageKeys.WEBPAGE_ROOT); //$NON-NLS-1$
		root.addChild(p1);
		return root;
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		if (viewer.getSelection().isEmpty()) {
			return;
		} else {
			IStructuredSelection selection = (IStructuredSelection) viewer
					.getSelection();
			TreeObject selectedDomainObject = (TreeObject) selection.getFirstElement();
			System.out.println(selectedDomainObject);
		}
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IHandlerService service = (IHandlerService) this.getSite().getService(
				IHandlerService.class);
		try {
			service.executeCommand(ICommandIds.CMD_OPEN_DOCUMENTS, null);
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
