package com.softberries.klerk.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import com.softberries.klerk.dao.to.DocumentWrapper;

public class ReportManager {

	Desktop desktop = null;

	public void generateDocumentReport(List<DocumentWrapper> docList,
			File tempDir, String fileName, String language, boolean execute) {
		// test jasper reports
		JasperPrint jPrint = null;
		String fullFileName = null;
		DocumentWrapper dw = docList.get(0);
		System.out.println("DOC title: " + dw.getDocument().getTitle());
		try {
			Map parameters = new HashMap();
			parameters
					.put("SUBREPORT_DIR",
							tempDir.getAbsolutePath()
									+ System.getProperty("file.separator")
									+ "reports/");
			String filePath = tempDir.getAbsolutePath()
					+ System.getProperty("file.separator")
					+ "reports"
					+ System.getProperty("file.separator")
					+ "invoice_items_locale_" + Locale.getDefault() + ".properties";
			System.out.println("PATH: " + filePath);
            FileInputStream fis = new FileInputStream( filePath );
            
            ResourceBundle bundle = new PropertyResourceBundle(fis);
            
            Locale locale = new Locale( "pl", "PL" );
            parameters.put("REPORT_LOCALE", locale);
			parameters.put("REPORT_RESOURCE_BUNDLE", bundle);
			fullFileName = tempDir.getAbsolutePath()
					+ System.getProperty("file.separator") + fileName;

			JasperPrint print = JasperFillManager.fillReport(
					tempDir.getAbsolutePath()
							+ System.getProperty("file.separator")
							+ "reports/invoice.jasper", parameters,
					new JRBeanCollectionDataSource(docList));
			JasperExportManager.exportReportToPdfFile(print, fullFileName
					+ ".pdf");
			System.out.println("success");
			if (Desktop.isDesktopSupported() && execute) {
				desktop = Desktop.getDesktop();
				desktop.open(new File(fullFileName + ".pdf"));
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
