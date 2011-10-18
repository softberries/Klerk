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
package com.softberries.klerk.reports;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.DocumentWrapper;

public class Main {

	public static void main(String[] art){
		new Main().generate();
	}
	public void generate(){
		DocumentWrapper docWrp = new DocumentWrapper();
    	docWrp.setBuyerLbl("Kupujący");
    	docWrp.setCopy("Kopia");
    	docWrp.setCreatedDate("10/09/2011");
    	docWrp.setCreatedDateLbl("Data Wystawienia:");
    	docWrp.setCreatorLbl("Osoba upoważniona do wystawienia");
    	Company seller = new Company();
    	Company buyer = new Company();
    	Document document = new Document();
    	document.setSeller(seller);
    	document.setBuyer(buyer);
    	docWrp.setDocument(document);
    	docWrp.setDueDate("10/10/2011");
    	docWrp.setDueDateLbl("Termin Płatności:");
    	docWrp.setFooterLbl("Faktura wygenerowana w systemie Klerk");
    	docWrp.setNotesLbl("Notes:");
    	docWrp.setPaymentMethodLbl("Metoda Płatności:");
    	docWrp.setPlaceCreatedLbl("Miejsce wystawienia:");
    	docWrp.setReceiver("");
    	docWrp.setReceiverLbl("Osoba upoważniona do odbioru");
    	docWrp.setSellerLbl("Sprzedawca");
    	docWrp.setToPayAmount("1000.00");
    	docWrp.setToPayCurrency("PLN");
    	docWrp.setToPayInWords("x.x.x");
    	docWrp.setToPayLbl("Do Zapłaty:");
    	docWrp.setTransactionDate("10/09/2011");
    	docWrp.setTransactionDateLbl("Data Sprzedaży:");
    	docWrp.setVatIdLbl("NIP:");
    	List<DocumentWrapper> docWrpList = new ArrayList<DocumentWrapper>();
    	docWrpList.add(docWrp);
    	new ReportManager().generateDocumentReport(docWrpList, new File("/home/kris/.klerk"), "faktura", null, true);
        
	}
}
