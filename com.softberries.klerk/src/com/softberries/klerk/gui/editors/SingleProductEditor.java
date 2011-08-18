package com.softberries.klerk.gui.editors;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
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
import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.gui.helpers.Messages;
import com.softberries.klerk.gui.helpers.table.ProductsModelProvider;

public class SingleProductEditor extends SingleObjectEditor {
	public SingleProductEditor() {
	}

	public static final String ID = "com.softberries.klerk.gui.editors.SingleProduct"; //$NON-NLS-1$
	private Product product;
	private String oldProductCode;
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		System.out.println("do save"); //$NON-NLS-1$
		ProductDao dao = new ProductDao();
		product.setCode(product.getCode().toUpperCase());
		try {
			if(product.getId() == null){
				dao.create(product);
				ProductsModelProvider.INSTANCE.getProducts().add(product);
			}else{
				LogUtil.logInfo("Updating product: " + product.getId());
				dao.update(product);
			}
			
		} catch (SQLException e) {
			IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, "Sorry, couldn't update product database.", e);
			StatusManager.getManager().handle(status, StatusManager.SHOW);
		}
		dirty = false;
		firePropertyChange(ISaveablePart.PROP_DIRTY);
	}

	private String prepareProductCode(String code) {
		code = code.toUpperCase();
		code = code.replaceAll("[^a-zA-Z0-9]", "");
		return code;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		product = (Product) input.getAdapter(Product.class);
		this.oldProductCode = product.getCode();
		setPartName(product.getName());
	}

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
		final Label codeLbl = toolkit.createLabel(sectionGeneralClient, "Code:");
		final Text codeTxt = toolkit.createText(sectionGeneralClient, this.product.getCode(), SWT.BORDER);
		codeTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
//				product.setCode(prepareProductCode(codeTxt.getText()));
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		bindValues(codeTxt);
		TableWrapData twd_codeTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_codeTxt.colspan = 3;
		codeTxt.setLayoutData(twd_codeTxt);
		// product title
		final Label nameLbl = toolkit.createLabel(sectionGeneralClient, Messages.SingleProductEditor_Name);
		final Text nameTxt = toolkit.createText(sectionGeneralClient,
				this.product.getName(), SWT.BORDER);
		nameTxt.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				product.setName(nameTxt.getText());
				form.setText(Messages.SingleProductEditor_PRODUCT + product.getName());
				setPartName(product.getName());
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		});
		TableWrapData twd_titleTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_titleTxt.colspan = 3;
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
				dirty = true;
				firePropertyChange(ISaveablePart.PROP_DIRTY);
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
	public void bindValues(Text codeTxt){
		final List<String> productCodes = getProductCodes();
		DataBindingContext bindingContext = new DataBindingContext();
		IObservableValue widgetValue = WidgetProperties.text(SWT.Modify)
				.observe(codeTxt);
		IObservableValue modelValue = BeanProperties.value(Product.class,
				"code").observe(product);
		bindingContext.bindValue(widgetValue, modelValue);
		// We want that age is a number
		IValidator numberValidator = new IValidator() {

			@Override
			public IStatus validate(Object value) {
				String s = String.valueOf(value);
				for(String p : productCodes){
					if(p.equalsIgnoreCase(s) && !p.equalsIgnoreCase(oldProductCode)){
						dirty = false;
						firePropertyChange(ISaveablePart.PROP_DIRTY);
						return ValidationStatus.error("Product code: " + s + " already exists!");
					}
				}
				return ValidationStatus.ok();
				
			}
		};
		UpdateValueStrategy targetToModel = new UpdateValueStrategy();
		targetToModel.setBeforeSetValidator(numberValidator);

		// Bind values using the validator
		// Remember the bindingContext to allow control decoration
		Binding bindValue = bindingContext.bindValue(widgetValue, modelValue,
				targetToModel, null);
		ControlDecorationSupport.create(bindValue, SWT.TOP | SWT.LEFT);
	}

	private List<String> getProductCodes() {
		List<String> strs = new ArrayList<String>();
		for(Product p : ProductsModelProvider.INSTANCE.getProducts()){
			strs.add(p.getCode());
		}
		return strs;
	}

}
