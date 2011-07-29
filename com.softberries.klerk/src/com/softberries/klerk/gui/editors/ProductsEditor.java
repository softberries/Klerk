package com.softberries.klerk.gui.editors;


import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;
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
		System.out.println("delete product");
		viewer.setInput(ProductsModelProvider.INSTANCE.getProducts());
		viewer.refresh();
	}

	@Override
	protected void editButtonClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void refreshButtonClicked() {
		System.out.println("refresh model");
		viewer.setInput(ProductsModelProvider.INSTANCE.getProducts());
		viewer.refresh();
	}

	@Override
	protected void onDoubleClick(IStructuredSelection selection) {
		Product d = (Product) selection.getFirstElement();
		openSingleObjectEditor(new ProductEditorInput(d), SingleProductEditor.ID);
	}
}
