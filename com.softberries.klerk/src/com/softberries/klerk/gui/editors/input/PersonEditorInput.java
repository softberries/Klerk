package com.softberries.klerk.gui.editors.input;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.softberries.klerk.dao.to.Person;

public class PersonEditorInput implements IEditorInput {

	private Person person;
	
	public PersonEditorInput(Person p){
		this.person = p;
	}
	@Override
	public Object getAdapter(Class adapter) {
		return this.person;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return this.person.getFirstName();
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		return this.person.getFirstName();
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person p) {
		this.person = p;
	}
	
}
