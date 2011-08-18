package com.softberries.klerk.gui.dialogs;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.swtaddons.autocomplete.combo.AutocompleteComboInput;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.softberries.klerk.dao.to.Address;
import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.gui.helpers.table.ProductsModelProvider;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.runtime.IStatus;

import com.softberries.klerk.gui.validators.FieldNotEmptyValidator;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.TraverseEvent;

public class AddressDialog extends Dialog {
	private DataBindingContext m_bindingContext;
	private Text countryTxt;
	private Text cityTxt;
	private Text streetTxt;
	private Text houseNrTxt;
	private Text flatNrTxt;
	private Text postCodeTxt;
	private Text notesTxt;
	private Address currentAddress;
	private Button okButton;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AddressDialog(Shell parentShell, Address address) {
		super(parentShell);
		this.currentAddress = address;
	}
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));
		
		Label lblProduct = new Label(container, SWT.NONE);
		lblProduct.setText("Country:");
		
		countryTxt = new Text(container, SWT.BORDER);
		countryTxt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				okButton.setEnabled(getFormValid());
			}
		});
		countryTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCity = new Label(container, SWT.NONE);
		lblCity.setText("City:");
		
		cityTxt = new Text(container, SWT.BORDER);
		cityTxt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				okButton.setEnabled(getFormValid());
			}
		});
		
		cityTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblStreet = new Label(container, SWT.NONE);
		lblStreet.setText("Street:");
		
		streetTxt = new Text(container, SWT.BORDER);
		streetTxt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				okButton.setEnabled(getFormValid());
			}
		});
		streetTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblHouseNr = new Label(container, SWT.NONE);
		lblHouseNr.setText("House Nr:");
		
		houseNrTxt = new Text(container, SWT.BORDER);
		houseNrTxt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				okButton.setEnabled(getFormValid());
			}
		});
		houseNrTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblFlatNr = new Label(container, SWT.NONE);
		lblFlatNr.setText("Flat Nr:");
		
		flatNrTxt = new Text(container, SWT.BORDER);
		flatNrTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPostCode = new Label(container, SWT.NONE);
		lblPostCode.setText("Post Code:");
		
		postCodeTxt = new Text(container, SWT.BORDER);
		postCodeTxt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				okButton.setEnabled(getFormValid());
			}
		});
		postCodeTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNotes = new Label(container, SWT.NONE);
		lblNotes.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblNotes.setText("Notes:");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_scrolledComposite.heightHint = 110;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		notesTxt = new Text(scrolledComposite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		scrolledComposite.setContent(notesTxt);
		scrolledComposite.setMinSize(notesTxt.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		m_bindingContext = initDataBindings();
		okButton.setEnabled(getFormValid());
	}
	private boolean getFormValid() {
		if(!countryTxt.getText().isEmpty() &&
				!cityTxt.getText().isEmpty() &&
				!streetTxt.getText().isEmpty() &&
				!houseNrTxt.getText().isEmpty() &&
				!postCodeTxt.getText().isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(438, 414);
	}
	public Address getAddressFromDialog(){
		int result = super.open();
		if(result == Dialog.OK){
			return currentAddress;
		}else{
			return null;
		}
		
	}
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
	      shell.setText("Address Editor");
	}
	protected DataBindingContext initDataBindings() {
		IValidator notEmptyValidator = new FieldNotEmptyValidator("This field cannot be empty!");
		DataBindingContext bindingContext = new DataBindingContext();
		UpdateValueStrategy ttm = new UpdateValueStrategy();
		ttm.setBeforeSetValidator(notEmptyValidator);
		//
		IObservableValue countryTxtObserveTextObserveWidget = SWTObservables.observeText(countryTxt, SWT.Modify);
		IObservableValue currentAddressCountryObserveValue = PojoObservables.observeValue(currentAddress, "country");
		Binding binding = bindingContext.bindValue(countryTxtObserveTextObserveWidget, currentAddressCountryObserveValue, ttm, null);
		ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);
		//
		IObservableValue cityTxtObserveTextObserveWidget = SWTObservables.observeText(cityTxt, SWT.Modify);
		IObservableValue currentAddressCityObserveValue = PojoObservables.observeValue(currentAddress, "city");
		binding = bindingContext.bindValue(cityTxtObserveTextObserveWidget, currentAddressCityObserveValue, ttm, null);
		ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);
		//
		IObservableValue streetTxtObserveTextObserveWidget = SWTObservables.observeText(streetTxt, SWT.Modify);
		IObservableValue currentAddressStreetObserveValue = PojoObservables.observeValue(currentAddress, "street");
		binding = bindingContext.bindValue(streetTxtObserveTextObserveWidget, currentAddressStreetObserveValue, ttm, null);
		ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);
		//
		IObservableValue houseNrTxtObserveTextObserveWidget = SWTObservables.observeText(houseNrTxt, SWT.Modify);
		IObservableValue currentAddressHouseNumberObserveValue = PojoObservables.observeValue(currentAddress, "houseNumber");
		binding = bindingContext.bindValue(houseNrTxtObserveTextObserveWidget, currentAddressHouseNumberObserveValue, ttm, null);
		ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);
		//
		IObservableValue flatNrTxtObserveTextObserveWidget = SWTObservables.observeText(flatNrTxt, SWT.Modify);
		IObservableValue currentAddressFlatNumberObserveValue = PojoObservables.observeValue(currentAddress, "flatNumber");
		bindingContext.bindValue(flatNrTxtObserveTextObserveWidget, currentAddressFlatNumberObserveValue, null, null);
		//
		IObservableValue postCodeTxtObserveTextObserveWidget = SWTObservables.observeText(postCodeTxt, SWT.Modify);
		IObservableValue currentAddressPostCodeObserveValue = PojoObservables.observeValue(currentAddress, "postCode");
		binding = bindingContext.bindValue(postCodeTxtObserveTextObserveWidget, currentAddressPostCodeObserveValue, ttm, null);
		ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT);
		//
		IObservableValue notesTxtObserveTextObserveWidget = SWTObservables.observeText(notesTxt, SWT.Modify);
		IObservableValue currentAddressNotesObserveValue = PojoObservables.observeValue(currentAddress, "notes");
		bindingContext.bindValue(notesTxtObserveTextObserveWidget, currentAddressNotesObserveValue, null, null);
		//
		return bindingContext;
	}
}
