/*******************************************************************************
 * Copyright (c) 2011 Softberries Krzysztof Grajek.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Softberries Krzysztof Grajek - initial API and implementation
 ******************************************************************************/
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
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return this.person.getFirstName();
	}

	@Override
	public IPersistableElement getPersistable() {
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
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof PersonEditorInput){
			PersonEditorInput p = (PersonEditorInput)arg0;
			if(p.getPerson().getId() != null && this.getPerson().getId() != null){
				long id1 = this.getPerson().getId().longValue();
				long id2 = p.getPerson().getId().longValue();
				return id1 == id2;
			}else{
				return super.equals(arg0);
			}
		}else{
			return super.equals(arg0);
		}
	}
}
