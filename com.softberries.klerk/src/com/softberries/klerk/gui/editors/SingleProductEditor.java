package com.softberries.klerk.gui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.softberries.klerk.Activator;
import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.gui.helpers.IImageKeys;

public class SingleProductEditor extends EditorPart {

	public static final String ID = "com.softberries.klerk.gui.editors.SingleProduct";
	private Product product;
	private final FormToolkit toolkit = new FormToolkit(Display.getDefault());
	private ScrolledForm form;
	
	public SingleProductEditor() {
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
		product = (Product) input.getAdapter(Product.class);
		setPartName(product.getName());
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
		form = toolkit.createScrolledForm(parent);
		form.setText("PRODUCT: " + this.product.getName());
		TableWrapLayout twlayout = new TableWrapLayout();
		twlayout.numColumns = 2;
		form.getBody().setLayout(twlayout);

		// general section
		Section sectionGeneral = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED);
		sectionGeneral.setText("Main");
		sectionGeneral.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});

		toolkit.createCompositeSeparator(sectionGeneral);
		createSectionToolbar(sectionGeneral, toolkit);

		sectionGeneral.setDescription("Product main properties");
		Composite sectionGeneralClient = toolkit
				.createComposite(sectionGeneral);
		TableWrapLayout twLayoutSectionGeneral = new TableWrapLayout();
		twLayoutSectionGeneral.numColumns = 4;
		sectionGeneralClient.setLayout(twLayoutSectionGeneral);
		// product title
		Label nameLbl = toolkit.createLabel(sectionGeneralClient, "Name:");
		Text nameTxt = toolkit.createText(sectionGeneralClient,
				this.product.getName(), SWT.BORDER);
		TableWrapData twd_titleTxt = new TableWrapData(TableWrapData.FILL_GRAB);
		twd_titleTxt.colspan = 3;
		nameTxt.setLayoutData(twd_titleTxt);
		
		sectionGeneral.setClient(sectionGeneralClient);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		data.colspan = 2;
		sectionGeneral.setLayoutData(data);
		// product description section
		Section sectionDescription = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED);
		sectionDescription.setText("Description");
		sectionDescription.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		toolkit.createCompositeSeparator(sectionDescription);
		sectionDescription.setDescription("Product Description:");
		Composite sectionDescClient = toolkit.createComposite(sectionDescription);
		TableWrapLayout twLayoutSectionDesc = new TableWrapLayout();
		twLayoutSectionDesc.numColumns = 1;
		sectionDescClient.setLayout(twLayoutSectionDesc);
		Text descTxt = toolkit.createText(sectionDescClient,
				this.product.getName(), SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		
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

	private void createSectionToolbar(Section section, FormToolkit toolkit) {
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(),
				SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if ((handCursor != null) && (handCursor.isDisposed() == false)) {
					handCursor.dispose();
				}
			}
		});

		// save
		CommandContributionItemParameter saveContributionParameter = new CommandContributionItemParameter(
				this.getSite(), null, "org.eclipse.ui.window.preferences",
				CommandContributionItem.STYLE_PUSH);
		String imageKey = IImageKeys.ALL_CATEGORIES;
		AbstractUIPlugin plugin = Activator.getDefault();
		ImageRegistry imageRegistry = plugin.getImageRegistry();
		saveContributionParameter.icon = imageRegistry.getDescriptor(imageKey);

		CommandContributionItem saveMenu = new CommandContributionItem(
				saveContributionParameter);

		toolBarManager.add(saveMenu);

		toolBarManager.update(true);

		section.setTextClient(toolbar);
	}
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
