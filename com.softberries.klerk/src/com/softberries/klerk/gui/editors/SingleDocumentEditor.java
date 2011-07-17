package com.softberries.klerk.gui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.softberries.klerk.dao.to.Document;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.ColumnLayoutData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.custom.ScrolledComposite;

public class SingleDocumentEditor extends EditorPart {

	public static final String ID = "com.softberries.klerk.gui.editors.SingleDocument";
	private Document document;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtNewText;
	
	public SingleDocumentEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		document = (Document) input.getAdapter(Document.class);
		setPartName(document.getTitle());
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ScrolledForm scrldfrmNewInvoice = formToolkit.createScrolledForm(parent);
		formToolkit.paintBordersFor(scrldfrmNewInvoice);
		scrldfrmNewInvoice.setText("New Invoice");
		scrldfrmNewInvoice.getBody().setLayout(new ColumnLayout());
		
		Label lblTitle = formToolkit.createLabel(scrldfrmNewInvoice.getBody(), "Title:", SWT.NONE);
		ColumnLayoutData cld_lblTitle = new ColumnLayoutData();
		cld_lblTitle.heightHint = 18;
		cld_lblTitle.widthHint = 175;
		lblTitle.setLayoutData(cld_lblTitle);
		
		txtNewText = formToolkit.createText(scrldfrmNewInvoice.getBody(), "New Text", SWT.NONE);
		ColumnLayoutData cld_txtNewText = new ColumnLayoutData();
		cld_txtNewText.widthHint = 334;
		txtNewText.setLayoutData(cld_txtNewText);
		
		Label lblNewLabel = formToolkit.createLabel(scrldfrmNewInvoice.getBody(), "Creator:", SWT.NONE);
		
		ComboViewer comboViewer = new ComboViewer(scrldfrmNewInvoice.getBody(), SWT.NONE);
		Combo combo = comboViewer.getCombo();
		formToolkit.paintBordersFor(combo);
		
		Label lblNewLabel_1 = formToolkit.createLabel(scrldfrmNewInvoice.getBody(), "Date created:", SWT.NONE);
		
		DateTime dateTime = new DateTime(scrldfrmNewInvoice.getBody(), SWT.BORDER);
		formToolkit.adapt(dateTime);
		formToolkit.paintBordersFor(dateTime);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(scrldfrmNewInvoice.getBody(), SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		ColumnLayoutData cld_scrolledComposite = new ColumnLayoutData();
		cld_scrolledComposite.heightHint = 200;
		scrolledComposite.setLayoutData(cld_scrolledComposite);
		formToolkit.adapt(scrolledComposite);
		formToolkit.paintBordersFor(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		// TODO Auto-generated method stub

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}
