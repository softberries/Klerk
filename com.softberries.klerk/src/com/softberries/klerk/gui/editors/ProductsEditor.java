package com.softberries.klerk.gui.editors;


import java.sql.SQLException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.softberries.klerk.dao.ProductDao;
import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.gui.editors.input.ProductEditorInput;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.table.ProductComparator;
import com.softberries.klerk.gui.helpers.table.ProductFilter;
import com.softberries.klerk.gui.helpers.table.ProductsModelProvider;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkComparator;
import com.softberries.klerk.gui.helpers.table.SimpleKlerkFilter;

public class ProductsEditor extends GenericKlerkEditor{

	public static final String ID = "com.softberries.klerk.gui.editors.ProductsEditor"; //$NON-NLS-1$
	
	private Product selectedProduct;
	
	public ProductsEditor(SimpleKlerkComparator comp, SimpleKlerkFilter filter, Object input) {
		super(comp, filter, input);
	}


	public ProductsEditor(){
		super(new ProductComparator(), new ProductFilter(), ProductsModelProvider.INSTANCE.getProducts());
	}

	@Override
	protected void createColumns(final Composite parent, final TableViewer viewer) {
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
	

	@Override
	protected void addButtonClicked() {
		Product newP = new Product();
		newP.setCode("Enter the code...");
		newP.setName("New Product");
		newP.setDescription("");
		openSingleObjectEditor(new ProductEditorInput(newP), SingleProductEditor.ID);
	}

	@Override
	protected void deleteButtonClicked() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		if(this.getSelectedProduct() == null || this.getSelectedProduct().getId() == null){
			MessageDialog.openInformation(shell, "Information", "Nothing to delete");
			return;
		}
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm", "Are you sure you want to delete this product?");
		if(confirmed){
			ProductDao dao = new ProductDao();
			try {
				dao.delete(this.getSelectedProduct().getId());
				closeOpenedEditorForThisItem(new ProductEditorInput(this.getSelectedProduct()));
				ProductsModelProvider.INSTANCE.getProducts().remove(this.getSelectedProduct());
				this.setSelectedProduct(null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			viewer.setInput(ProductsModelProvider.INSTANCE.getProducts());
			viewer.refresh();
		}
	}

	@Override
	protected void editButtonClicked() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		if(this.getSelectedProduct() == null || this.getSelectedProduct().getId() == null){
			MessageDialog.openInformation(shell, "Information", "Nothing to edit");
			return;
		}
		openSingleObjectEditor(new ProductEditorInput(this.getSelectedProduct()), SingleProductEditor.ID);
	}

	@Override
	protected void refreshButtonClicked() {
		viewer.setInput(ProductsModelProvider.INSTANCE.getProducts());
		viewer.refresh();
	}

	@Override
	protected void onDoubleClick(IStructuredSelection selection) {
		Product d = (Product) selection.getFirstElement();
		openSingleObjectEditor(new ProductEditorInput(d), SingleProductEditor.ID);
	}


	@Override
	protected void setSelectedObject(Object selection) {
		if(selection != null && selection instanceof Product){
			this.setSelectedProduct((Product) selection);
		}
	}

	/**
	 * @return the selectedProduct
	 */
	public Product getSelectedProduct() {
		return selectedProduct;
	}


	/**
	 * @param selectedProduct the selectedProduct to set
	 */
	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}


}
