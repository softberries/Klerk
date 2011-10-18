package com.softberries.klerk.calc;

import java.math.BigDecimal;
import java.net.NetPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.softberries.klerk.dao.to.DocumentItem;
import com.softberries.klerk.dao.to.VatLevelItem;
import com.softberries.klerk.money.Money;

public class DocumentCalculator {

	private static final String TAX_LEVEL = "Tax Level: ";
	private static final String PRICE_ALL_NET = "NET";
	private static final String PRICE_ALL_GROSS = "GROSS";

	public DocumentItem calculateByQuantity(DocumentItem di, Object value){
		try{
			//get new quantity value
			Money quantity = new Money(new BigDecimal(value.toString()).setScale(2));
			di.setQuantity(quantity.getAmount().setScale(2).toPlainString());
			//no exception here? get the rest of the values
			Money priceNet = new Money(new BigDecimal(di.getPriceNetSingle()).setScale(2));
			Money taxPercent = new Money(new BigDecimal(di.getTaxValue()).setScale(2)).div(100.00);
			
			//calculated
			Money grossSingleCalc = priceNet.times(taxPercent.getAmount().doubleValue()).plus(priceNet);
			Money priceNetAllCalc = priceNet.times(quantity.getAmount().doubleValue());
			Money priceGrossAllCalc = priceNetAllCalc.times(taxPercent.getAmount().doubleValue()).plus(priceNetAllCalc);
			Money taxValueAll = priceGrossAllCalc.minus(priceNetAllCalc);
			
			di.setPriceNetAll(priceNetAllCalc.getAmount().setScale(2).toPlainString());
			di.setPriceGrossSingle(grossSingleCalc.getAmount().setScale(2).toPlainString());
			di.setPriceGrossAll(priceGrossAllCalc.getAmount().setScale(2).toPlainString());
			di.setPriceTaxAll(taxValueAll.getAmount().setScale(2).toPlainString());
			return di;
		}catch(NumberFormatException nfe){
			//ignore it
		}
		return di;
	}

	public DocumentItem calculateByBasePrice(DocumentItem di, Object value) {
		Money priceNet = new Money(new BigDecimal(value.toString()).setScale(2));
		di.setPriceNetSingle(priceNet.getAmount().setScale(2).toPlainString());
		//no exception here? get the rest of the values
		Money quantity = new Money(new BigDecimal(di.getQuantity()).setScale(2));
		Money taxPercent = new Money(new BigDecimal(di.getTaxValue()).setScale(2)).div(100.00);
		
		//calculated
		Money grossSingleCalc = priceNet.times(taxPercent.getAmount().doubleValue()).plus(priceNet);
		Money priceNetAllCalc = priceNet.times(quantity.getAmount().doubleValue());
		Money priceGrossAllCalc = priceNetAllCalc.times(taxPercent.getAmount().doubleValue()).plus(priceNetAllCalc);
		Money taxValueAll = priceGrossAllCalc.minus(priceNetAllCalc);
		
		di.setPriceNetAll(priceNetAllCalc.getAmount().setScale(2).toPlainString());
		di.setPriceGrossSingle(grossSingleCalc.getAmount().setScale(2).toPlainString());
		di.setPriceGrossAll(priceGrossAllCalc.getAmount().setScale(2).toPlainString());
		di.setPriceTaxAll(taxValueAll.getAmount().setScale(2).toPlainString());
		
		return di;
	}

	public DocumentItem calculateByPriceNetAll(DocumentItem di, Object value) {
		Money priceNetAll = new Money(new BigDecimal(value.toString()).setScale(2));
		di.setPriceNetAll(priceNetAll.getAmount().setScale(2).toPlainString());
		//no exception here? get the rest of the values
		Money quantity = new Money(new BigDecimal(di.getQuantity()).setScale(2));
		Money taxPercent = new Money(new BigDecimal(di.getTaxValue()).setScale(2)).div(100.00);
		Money priceNet = new Money(new BigDecimal("0"));
		if(quantity.getAmount().doubleValue() > 0){
			priceNet = new Money(new BigDecimal(priceNetAll.getAmount().setScale(2).toPlainString()).setScale(2)).div(quantity.getAmount().doubleValue());
		}
		Money grossSingleCalc = priceNet.times(taxPercent.getAmount().doubleValue()).plus(priceNet);
		Money priceGrossAllCalc = priceNetAll.times(taxPercent.getAmount().doubleValue()).plus(priceNetAll);
		Money taxValueAll = priceGrossAllCalc.minus(priceNetAll);
		
		di.setPriceGrossSingle(grossSingleCalc.getAmount().setScale(2).toPlainString());
		di.setPriceGrossAll(priceGrossAllCalc.getAmount().setScale(2).toPlainString());
		di.setPriceTaxAll(taxValueAll.getAmount().setScale(2).toPlainString());
		di.setPriceNetSingle(priceNet.getAmount().setScale(2).toPlainString());
		return di;
	}
	public DocumentItem calculateByPriceGrossAll(DocumentItem di, Object value) {
		Money priceGrossAll = new Money(new BigDecimal(value.toString()).setScale(2));
		di.setPriceGrossAll(priceGrossAll.getAmount().setScale(2).toPlainString());
		
		//no exception here? get the rest of the values
		Money quantity = new Money(new BigDecimal(di.getQuantity()).setScale(2));
		Money taxPercent = new Money(new BigDecimal(di.getTaxValue()).setScale(2)).div(100.00);
		Money grossSingleCalc = new Money(new BigDecimal("0"));
		if(quantity.getAmount().setScale(2).doubleValue() > 0){
			grossSingleCalc = new Money(new BigDecimal(priceGrossAll.getAmount().setScale(2).toPlainString()).setScale(2)).div(quantity.getAmount().setScale(2).doubleValue());
		}
		System.out.println("Gross single: " + grossSingleCalc.getAmount().toPlainString());
		Money priceNetSingle = grossSingleCalc.minus(grossSingleCalc.times(taxPercent.getAmount().setScale(2).doubleValue()));
		System.out.println("Net single: " + priceNetSingle.getAmount().toPlainString());
		
		Money priceNetAllCalc = priceNetSingle.times(quantity.getAmount().doubleValue());
		Money taxValueAll = priceGrossAll.minus(priceNetAllCalc);
		
		di.setPriceGrossSingle(grossSingleCalc.getAmount().setScale(2).toPlainString());
		di.setPriceGrossAll(priceGrossAll.getAmount().setScale(2).toPlainString());
		di.setPriceTaxAll(taxValueAll.getAmount().setScale(2).toPlainString());
		di.setPriceNetSingle(priceNetSingle.getAmount().setScale(2).toPlainString());
		di.setPriceNetAll(priceNetAllCalc.getAmount().setScale(2).toPlainString());
		return di;
	}
	public DocumentItem calculateByTaxPercent(DocumentItem di, Object value) {
		Money taxPercentFull = new Money(new BigDecimal(value.toString()).setScale(2));
		di.setTaxValue(taxPercentFull.getAmount().setScale(2).toPlainString());
		Money taxPercent = new Money(new BigDecimal(di.getTaxValue()).setScale(2)).div(100.00);
		
		Money quantity = new Money(new BigDecimal(di.getQuantity()).setScale(2));
		Money priceNetSingle = new Money(new BigDecimal(di.getPriceNetSingle()).setScale(2));
		
		Money grossSingleCalc = priceNetSingle.times(taxPercent.getAmount().doubleValue()).plus(priceNetSingle);
		Money priceNetAllCalc = priceNetSingle.times(quantity.getAmount().doubleValue());
		Money priceGrossAllCalc = priceNetAllCalc.times(taxPercent.getAmount().doubleValue()).plus(priceNetAllCalc);
		Money taxValueAll = priceGrossAllCalc.minus(priceNetAllCalc);
		
		di.setPriceNetAll(priceNetAllCalc.getAmount().setScale(2).toPlainString());
		di.setPriceGrossSingle(grossSingleCalc.getAmount().setScale(2).toPlainString());
		di.setPriceGrossAll(priceGrossAllCalc.getAmount().setScale(2).toPlainString());
		di.setPriceTaxAll(taxValueAll.getAmount().setScale(2).toPlainString());
		return di;
	}
	
	public List<VatLevelItem> getSummaryTaxLevelItems(List<DocumentItem> items) {
		Map<String, VatLevelItem> summaryItems = new HashMap<String, VatLevelItem>();
		if(items == null || items.size() == 0){
			return new ArrayList<VatLevelItem>();
		}
		for(DocumentItem di : items){
			Money priceNet = new Money(new BigDecimal(di.getPriceNetAll()).setScale(2));
			Money priceGross = new Money(new BigDecimal(di.getPriceGrossAll()).setScale(2));
			Money taxLevel = new Money(new BigDecimal(di.getTaxValue()).setScale(2));
			Money taxPrice = new Money(new BigDecimal(di.getPriceTaxAll()).setScale(2));

			String taxLevelName = TAX_LEVEL + "["+taxLevel.getAmount().setScale(2).toPlainString() + "%]";
			VatLevelItem vatItem = summaryItems.get(taxLevelName);
			
			System.out.println("TaxLevel: " + taxLevel + ", taxPrice: " + taxPrice);
			
			//taxes (divided by tax level)
			if(vatItem == null){
				VatLevelItem item = new VatLevelItem();
				item.setVatLevel(taxLevel.getAmount().setScale(2).toPlainString());
				item.setNetValue(priceNet.getAmount().setScale(2).toPlainString());
				item.setVatValue(taxPrice.getAmount().setScale(2).toPlainString());
				item.setGrossValue(priceGross.getAmount().setScale(2).toPlainString());
				summaryItems.put(taxLevelName, item);
			}else{
				Money priceNetTemp = new Money(new BigDecimal(vatItem.getNetValue()));
				Money priceTaxTemp = new Money(new BigDecimal(vatItem.getVatValue()));
				Money priceGrossTemp = new Money(new BigDecimal(vatItem.getGrossValue()));
				
				Money resTax = taxPrice.plus(priceTaxTemp);
				Money resNet = priceNet.plus(priceNetTemp);
				Money resGross = priceGross.plus(priceGrossTemp);
				
				vatItem.setNetValue(resNet.getAmount().setScale(2).toPlainString());
				vatItem.setVatValue(resTax.getAmount().setScale(2).toPlainString());
				vatItem.setGrossValue(resGross.getAmount().setScale(2).toPlainString());
				
				summaryItems.remove(taxLevelName);
				summaryItems.put(taxLevelName, vatItem);
			}
		}
		//prepare results
		return new ArrayList<VatLevelItem>(summaryItems.values());
	}

	public Money getNetPrice(List<DocumentItem> items) {
		Money result = new Money(new BigDecimal("0.00"));
		for(DocumentItem di : items){
			Money priceNetAll = new Money(new BigDecimal(di.getPriceNetAll()).setScale(2));
			result = result.plus(priceNetAll);
		}
		return result;
	}

	public Money getGrossPrice(List<DocumentItem> items) {
		Money result = new Money(new BigDecimal("0.00"));
		for(DocumentItem di : items){
			Money priceGrossAll = new Money(new BigDecimal(di.getPriceGrossAll()).setScale(2));
			result = result.plus(priceGrossAll);
		}
		return result;
	}
}
