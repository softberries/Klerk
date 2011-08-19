package com.softberries.klerk.gui.editors;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.statushandlers.StatusManager;

import com.softberries.klerk.Activator;
import com.softberries.klerk.LogUtil;
import com.softberries.klerk.dao.ProductDao;
import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.table.ProductsModelProvider;
import com.softberries.klerk.gui.validators.FieldNotEmptyValidator;

public class SingleProductEditor extends SingleObjectEditor {
	
	public static final String ID = "com.softberries.klerk.gui.editors.SingleProduct"; //$NON-NLS-1$
	/**
	 * Default constructor
	 */
	public SingleProductEditor() {
	}
	/**
	 * Keeps a reference to the edited {@code Product} (if in editing mode)
	 */
	private Product product;
	/**
	 * Keeps a reference to the original {@code Product} code set while opening this editor,
	 * this way we can skip saving the {@code Product} if the modified {@code Product} code is the same as the original
	 * one.
	 */
	private String oldProductCode;
	/**
	 * Keeps a reference to the current {@code Product} code value, we are checking this value before saving to 
	 * make sure its filled with correct value
	 */
	private Text codeTxt;
	/**
	 * Keeps a ref to the {@code Product} name to ensure upon saving that we are going to store correct values
	 * in the database
	 */
	private String currentProductName = ""; //$NON-NLS-1$
	/**
	 * If the validator for the {@code Product} code property returns false this member variable prevents saving the
	 * modified product
	 */
	private boolean codeInvalid;
	
	/**
	 * Enable save conditionally, if the {@code dirty} - drt argument is set to true, this
	 * method tries to enable save on the editor, before save can be enabled we are checking if
	 * all the required fields for {@code Product} are filled with correct values
	 * 
	 * @param drt - stands for 'dirty' like in standard Eclipse RCP editor
	 */
	private void enableSave(boolean drt) {
		//check if code and product name is filled with value and check if the code is valid
		if(drt && !codeTxt.getText().isEmpty() && !currentProductName.isEmpty() && !codeInvalid){
			dirty = drt;
			//notify editor that its dirty/not dirty
			firePropertyChange(ISaveablePart.PROP_DIRTY);
		}else{
			dirty = false;
			firePropertyChange(ISaveablePart.PROP_DIRTY);
		}
	}
	/**
	 * Saves current {@code Product} either new or edited one
	 * by using {@link ProductDao}
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		ProductDao dao = new ProductDao();
		product.setCode(prepareProductCode(product.getCode().toUpperCase()));
		try {
			//if {@value product} doesn't have id set it means that its a new product
			//otherwise we are editing exising {@code Product}
			if(product.getId() == null){
				dao.create(product);
				ProductsModelProvider.INSTANCE.getProducts().add(product);
			}else{
				LogUtil.logInfo("Updating product: " + product.getId()); //$NON-NLS-1$
				dao.update(product);
			}
			
		} catch (SQLException e) {
			IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, Messages.SingleProductEditor_cannotUpdateError, e);
			StatusManager.getManager().handle(status, StatusManager.SHOW);
		}
		enableSave(false);
	}

	/**
	 * Remove special characters for the {@link Product} code
	 * @param code
	 * @return formatted code
	 */
	private String prepareProductCode(String code) {
		code = code.toUpperCase();
		code = code.replaceAll("[^a-zA-Z0-9]", ""); //$NON-NLS-1$ //$NON-NLS-2$
		return code;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		product = (Product) input.getAdapter(Product.class);
		this.oldProductCode = product.getCode();
		this.currentProductName = product.getName();
		setPartName(product.getName());
	}

	/**
	 * Creates all user interface controls
	 */
	@Override
	public void createPartControl(Composite parent) {
		form = toolkit.createScrolledForm(parent);
		form.setText(Messages.SingleProductEditor_PRODUCT + this.product.getName());
		TableWrapLayout twlayout = new TableWrapLayout();
		twlayout.numColumns = 2;
		form.getBody().setLayout(twlayout);

		// general section
		Section sectionGeneral = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		sectionGeneral.setText(Messages.SingleProductEditor_Main);
		sectionGeneral.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});

		toolkit.createCompositeSeparator(sectionGeneral);
		createSectionToolbar(sectionGeneral, toolkit);

		sectionGeneral.setDescription(Messages.SingleProductEditor_Product_main_properties);
		Composite sectionGeneralClient = toolkit
				.createComposite(sectionGeneral);
		TableWrapLayout twLayoutSectionGeneral = new TableWrapLayout();
		twLayoutSectionGeneral.numColumns = 4;
		sectionGeneralClient.setLayout(twLayoutSectionGeneral);
		// product code
		final Label codeLbl = toolkit.createLabel(sectionGeneralClient, Messages.SingleProductEditor_code);
		codeTxt = toolkit.createText(sectionGeneralClient, this.product.getCode(), SWT.BORDER);
		bindValidator(codeTxt, product, "code", new IValidator(){ //$NON-NLS-1$
			List<String> productCodes = getProductCodes();
			@Override
			public IStatus validate(Object value) {
				String s = String.valueOf(value);
				for(String p : productCodes){
					if(p.equalsIgnoreCase(s) && !p.equalsIgnoreCase(oldProductCode)){
						codeInvalid = true;
						return ValidationStatus.error(Messages.SingleProductEditor_productCode + s + Messages.SingleProductEditor_alreadyExists);
					}
				}
				codeInvalid = false;
				return ValidationStatus.ok();
			}
			
		});
		bindValidator(codeTxt, product, "code", new FieldNotEmptyValidator(Messages.SingleProductEditor_fieldCannotBeEmpty)); //$NON-NLS-1$
		codeTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				enableSave(true);
			}
		});
		
		TableWrapData twd_codeTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_codeTxt.colspan = 3;
		twd_codeTxt.indent = 5;
		codeTxt.setLayoutData(twd_codeTxt);
		// product title
		final Label nameLbl = toolkit.createLabel(sectionGeneralClient, Messages.SingleProductEditor_Name);
		final Text nameTxt = toolkit.createText(sectionGeneralClient,
				this.product.getName(), SWT.BORDER);
		bindValidator(nameTxt, product, "name",new FieldNotEmptyValidator(Messages.SingleProductEditor_fieldCannotBeEmpty)); //$NON-NLS-1$
		nameTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				product.setName(nameTxt.getText());
				currentProductName = product.getName();
				form.setText(Messages.SingleProductEditor_PRODUCT + product.getName());
				setPartName(product.getName());
				enableSave(true);
			}
		});
		
		
		TableWrapData twd_titleTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_titleTxt.colspan = 3;
		twd_titleTxt.indent = 5;
		nameTxt.setLayoutData(twd_titleTxt);
		
		sectionGeneral.setClient(sectionGeneralClient);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionGeneral.setLayoutData(data);
		// product description section
		Section sectionDescription = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		sectionDescription.setText(Messages.SingleProductEditor_Description);
		sectionDescription.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionDescription);
		sectionDescription.setDescription(Messages.SingleProductEditor_Product_Description);
		Composite sectionDescClient = toolkit.createComposite(sectionDescription);
		TableWrapLayout twLayoutSectionDesc = new TableWrapLayout();
		twLayoutSectionDesc.numColumns = 1;
		sectionDescClient.setLayout(twLayoutSectionDesc);
		final Text descTxt = toolkit.createText(sectionDescClient,
				this.product.getDescription(), SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		descTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				product.setDescription(descTxt.getText());
				enableSave(true);
			}
		});
		TableWrapData twd_descTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_descTxt.heightHint = 180;
		twd_descTxt.grabVertical = true;
		twd_descTxt.colspan = 1;
		descTxt.setLayoutData(twd_descTxt);
		sectionDescription.setClient(sectionDescClient);
		data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionDescription.setLayoutData(data);
	}

	/**
	 * Converts list of {@code Product} objects into corresponding
	 * {@code java.util.List} of their codes
	 * 
	 * @return {@link java.util.List} of {@link Product} codes
	 */
	private List<String> getProductCodes() {
		List<String> strs = new ArrayList<String>();
		for(Product p : ProductsModelProvider.INSTANCE.getProducts()){
			strs.add(p.getCode());
		}
		return strs;
	}
}
