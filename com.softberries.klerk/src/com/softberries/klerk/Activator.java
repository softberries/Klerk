package com.softberries.klerk;

import java.math.RoundingMode;
import java.util.Currency;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import com.softberries.klerk.money.*;

import com.softberries.klerk.gui.helpers.IImageKeys;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.softberries.klerk"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		//initialize Money defaults on startup
		Money.init(Currency.getInstance("PLN"), RoundingMode.HALF_EVEN);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	@Override
    protected void initializeImageRegistry(ImageRegistry registry) {
        super.initializeImageRegistry(registry);
        Bundle bundle = Platform.getBundle(PLUGIN_ID);

        ImageDescriptor mainCategory = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(IImageKeys.MAIN_CATEGORY), null));
        registry.put(IImageKeys.MAIN_CATEGORY, mainCategory);
        
        ImageDescriptor allCategories = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(IImageKeys.ALL_CATEGORIES), null));
        registry.put(IImageKeys.ALL_CATEGORIES, allCategories);
        
        ImageDescriptor documents = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(IImageKeys.CATEGORY_DOCUMENTS), null));
        registry.put(IImageKeys.CATEGORY_DOCUMENTS, documents);
        
        ImageDescriptor invoices = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(IImageKeys.CATEGORY_INVOICES), null));
        registry.put(IImageKeys.CATEGORY_INVOICES, invoices);
        
        ImageDescriptor inventory = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(IImageKeys.CATEGORY_INVENTORY), null));
        registry.put(IImageKeys.CATEGORY_INVENTORY, inventory);
        
        ImageDescriptor products = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(IImageKeys.CATEGORY_PRODUCTS), null));
        registry.put(IImageKeys.CATEGORY_PRODUCTS, products);
        
        ImageDescriptor companies = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(IImageKeys.CATEGORY_COMPANIES), null));
        registry.put(IImageKeys.CATEGORY_COMPANIES, companies);
        
        ImageDescriptor administration = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(IImageKeys.CATEGORY_ADMINISTRATION), null));
        registry.put(IImageKeys.CATEGORY_ADMINISTRATION, administration);
        
        ImageDescriptor people = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(IImageKeys.CATEGORY_PEOPLE), null));
        registry.put(IImageKeys.CATEGORY_PEOPLE, people);
    }
}
