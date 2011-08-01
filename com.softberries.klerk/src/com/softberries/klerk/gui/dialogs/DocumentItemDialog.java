package com.softberries.klerk.gui.dialogs;

import java.util.List;

import net.sf.swtaddons.autocomplete.combo.AutocompleteComboInput;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.gui.helpers.table.ProductsModelProvider;

public class DocumentItemDialog extends Dialog {
	private Combo productCombo;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DocumentItemDialog(Shell parentShell) {
		super(parentShell);
	}

	public Combo getProductCombo() {
		return productCombo;
	}

	public void setProductCombo(Combo productCombo) {
		this.productCombo = productCombo;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(3, false));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblProduct = new Label(container, SWT.NONE);
		lblProduct.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProduct.setText("Product:");
		
		productCombo = new Combo(container, SWT.BORDER);
		productCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		productCombo.setItems(createProductDescriptions(ProductsModelProvider.INSTANCE.getProducts()));
		new AutocompleteComboInput(productCombo);
		// Make the selection available to other views
//		getSite().setSelectionProvider(productCombo);
	    
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	private String[] createProductDescriptions(List<Product> products) {
		String[] result = new String[products.size()];
		for(int index = 0; index < products.size(); index++){
			Product p = products.get(index);
			result[index] = "" + p.getName() + " " + "Code: +  " + " (" + p.getCode() + ")";
		}
		return result;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 166);
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
	      shell.setText("Find Product");
	}
	

}
