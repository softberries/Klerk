package com.softberries.klerk.calc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softberries.klerk.dao.to.DocumentItem;
import com.softberries.klerk.dao.to.SummaryTableItem;
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
		
		Money priceNet = new Money(new BigDecimal(priceNetAll.getAmount().setScale(2).toPlainString()).setScale(2)).div(quantity.getAmount().doubleValue());
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
		Money grossSingleCalc = new Money(new BigDecimal(priceGrossAll.getAmount().setScale(2).toPlainString()).setScale(2)).div(quantity.getAmount().setScale(2).doubleValue());
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
	
	public List<SummaryTableItem> getSummaryItems(List<DocumentItem> items) {
		List<SummaryTableItem> result = new ArrayList<SummaryTableItem>();
		Map<String, Money> summaryItems = new HashMap<String, Money>();
		if(items == null || items.size() == 0){
			SummaryTableItem priceNetSTI = new SummaryTableItem();
			priceNetSTI.setName(PRICE_ALL_NET);
			priceNetSTI.setValue("0.00");
			result.add(priceNetSTI);
			
			SummaryTableItem priceGrossSTI = new SummaryTableItem();
			priceGrossSTI.setName(PRICE_ALL_GROSS);
			priceGrossSTI.setValue("0.00");
			result.add(priceGrossSTI);
			
			SummaryTableItem priceTaxSTI = new SummaryTableItem();
			priceTaxSTI.setName(TAX_LEVEL + "0%");
			priceTaxSTI.setValue("0.00");
			result.add(priceTaxSTI);
			return result;
		}
		for(DocumentItem di : items){
			Money priceNet = new Money(new BigDecimal(di.getPriceNetAll()).setScale(2));
			Money priceGross = new Money(new BigDecimal(di.getPriceGrossAll()).setScale(2));
			Money taxLevel = new Money(new BigDecimal(di.getTaxValue()).setScale(2));
			Money taxPrice = new Money(new BigDecimal(di.getPriceTaxAll()).setScale(2));
			Money priceNetAll = summaryItems.get(PRICE_ALL_NET);
			if(priceNetAll == null){
				summaryItems.put(PRICE_ALL_NET, priceNet);
				System.out.println("new price" + priceNet);
			}else{
				Money temp = summaryItems.get(PRICE_ALL_NET);
				System.out.println("temp: " + temp);
				Money res = priceNet.plus(priceNetAll);
				System.out.println("res: " + res);
				summaryItems.remove(PRICE_ALL_NET);
				summaryItems.put(PRICE_ALL_NET, res);
			}
		}
		SummaryTableItem priceNetSTI = new SummaryTableItem();
		priceNetSTI.setName(PRICE_ALL_NET);
		priceNetSTI.setValue(summaryItems.get(PRICE_ALL_NET).getAmount().setScale(2).toPlainString());
		result.add(priceNetSTI);
		return result;
	}

}
