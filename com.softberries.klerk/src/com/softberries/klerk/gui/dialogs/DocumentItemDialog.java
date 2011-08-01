package com.softberries.klerk.gui.dialogs;

import java.util.List;

import net.sf.swtaddons.autocomplete.text.AutocompleteTextInput;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.gui.helpers.table.ProductsModelProvider;

public class DocumentItemDialog extends Dialog {
	private Text productTxt;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DocumentItemDialog(Shell parentShell) {
		super(parentShell);
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
		
		productTxt = new Text(container, SWT.BORDER);
		productTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		productTxt.setFocus();
//		createDeco(productTxt, ProductsModelProvider.INSTANCE.getProducts());
		new AutocompleteTextInput(productTxt, createProductDescriptions(ProductsModelProvider.INSTANCE.getProducts()));
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

	private void createDeco(Text text, List<Product> products) {
		String options[] = createProductDescriptions(products);
		ControlDecoration deco = new ControlDecoration(text, SWT.LEFT);
		deco.setDescriptionText("Use CTRL + SPACE to see possible values");
		deco.setImage(FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION)
				.getImage());
		deco.setShowOnlyOnFocus(false);
		// Help the user with the possible inputs
		// "." and "#" will also activate the content proposals
		char[] autoActivationCharacters = new char[] { '.', '#' };
		KeyStroke keyStroke;
		try {
			//
			keyStroke = KeyStroke.getInstance("Ctrl+Space");
			SimpleContentProposalProvider proposalProvider = new SimpleContentProposalProvider(options);
			proposalProvider.setFiltering(true);
			
			ContentProposalAdapter adapter = new ContentAssistCommandAdapter(text,
					new TextContentAdapter(),
					proposalProvider, null,
					autoActivationCharacters);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
