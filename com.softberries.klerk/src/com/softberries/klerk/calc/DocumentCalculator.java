package com.softberries.klerk.calc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
			Money priceGrossAll = summaryItems.get(PRICE_ALL_GROSS);
			String taxLevelName = TAX_LEVEL + "["+taxLevel.getAmount().setScale(2).toPlainString() + "%]";
			Money taxLevelAll = summaryItems.get(taxLevelName);
			
			System.out.println("TaxLevel: " + taxLevel + ", taxPrice: " + taxPrice);
			//price net
			if(priceNetAll == null){
				summaryItems.put(PRICE_ALL_NET, priceNet);
			}else{
				Money res = priceNet.plus(priceNetAll);
				summaryItems.remove(PRICE_ALL_NET);
				summaryItems.put(PRICE_ALL_NET, res);
			}
			//price gross
			if(priceGrossAll == null){
				summaryItems.put(PRICE_ALL_GROSS, priceGross);
			}else{
				Money res = priceGross.plus(priceGrossAll);
				summaryItems.remove(PRICE_ALL_GROSS);
				summaryItems.put(PRICE_ALL_GROSS, res);
			}
			//taxes (divided by tax level)
			if(taxLevelAll == null){
				summaryItems.put(taxLevelName, taxPrice);
			}else{
				Money res = taxPrice.plus(taxLevelAll);
				summaryItems.remove(taxLevelName);
				summaryItems.put(taxLevelName, res);
			}
		}
		//prepare results
		//add price net first
		SummaryTableItem stiNet = new SummaryTableItem();
        stiNet.setName(PRICE_ALL_NET);
        stiNet.setValue(summaryItems.get(PRICE_ALL_NET).toString());
        result.add(stiNet);
        summaryItems.remove(PRICE_ALL_NET);
        //store temporarily price gross to print after taxes
        Money priceGrossAll = summaryItems.get(PRICE_ALL_GROSS);
        summaryItems.remove(PRICE_ALL_GROSS);
        //print taxes
		Iterator it = summaryItems.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        SummaryTableItem sti = new SummaryTableItem();
	        sti.setName((String) pairs.getKey());
	        sti.setValue(pairs.getValue().toString());
	        result.add(sti);
	    }
	    //print prices gross
	    SummaryTableItem stiGross = new SummaryTableItem();
        stiGross.setName(PRICE_ALL_GROSS);
        stiGross.setValue(priceGrossAll.toString());
        result.add(stiGross);
		return result;
	}

}
