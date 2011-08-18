package com.softberries.klerk.gui.editors.input;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.softberries.klerk.dao.to.Document;

public class DocumentEditorInput implements IEditorInput {

	private Document document;
	
	public DocumentEditorInput(Document d){
		this.document = d;
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		return this.document;
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
		return this.document.getTitle();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return this.document.getTitle();
	}

}
