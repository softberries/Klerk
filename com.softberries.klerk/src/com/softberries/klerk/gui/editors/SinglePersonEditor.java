package com.softberries.klerk.gui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.dao.to.Product;

public class SinglePersonEditor extends SingleObjectEditor{

	public static final String ID = "com.softberries.klerk.gui.editors.SinglePersonEditor";
	private Person person;
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		person = (Person) input.getAdapter(Product.class);
		setPartName(person.getFirstName() + " " + person.getLastName());
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		
	}

}
