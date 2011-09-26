package com.softberries.klerk.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.part.NullEditorInput;

import com.softberries.klerk.gui.editors.SalesDocumentsEditor;

public class OpenSalesCommand implements IHandler {


	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public boolean isEnabled() {
		//for now make it always enabled (and maybe forever)
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		
	}
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("open document");
		 IWorkbenchPage page;
		 IEditorInput input = new NullEditorInput();
		 page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage ();
		 // else if in ViewPart // page = getSite ().getPage ();
		 try {
			page.openEditor(input, SalesDocumentsEditor.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}
}
