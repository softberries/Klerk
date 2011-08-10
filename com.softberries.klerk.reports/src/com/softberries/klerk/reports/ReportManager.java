package com.softberries.klerk.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
