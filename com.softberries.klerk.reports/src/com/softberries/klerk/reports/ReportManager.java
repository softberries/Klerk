package com.softberries.klerk.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.softberries.klerk.dao.to.Document;

public class ReportManager {

	Desktop desktop = null;
	
	public void generateDocumentReport(Document doc, File tempDir, String fileName, String language, boolean execute){
		//test jasper reports
		JasperPrint jPrint = null;
		String fullFileName = null;
		try {
			JasperReport jasperReport = null;
			jasperReport = (JasperReport) net.sf.jasperreports.engine.util.JRLoader.loadObject(Activator.class.getClassLoader()
			.getResourceAsStream("reports/Invoice.jasper"));
			Map parameters = new HashMap();
			parameters.put("document", doc);
			jPrint = JasperFillManager.fillReport(jasperReport,parameters);
			System.out.println("JPrint: " + jPrint);
			fullFileName = tempDir.getAbsolutePath() + System.getProperty("file.separator") + fileName;
			JasperExportManager.exportReportToPdfFile(jPrint, fullFileName);
			System.out.println("success");
			if (Desktop.isDesktopSupported() && execute) {
		        desktop = Desktop.getDesktop();
		        desktop.open(new File(fullFileName));
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
