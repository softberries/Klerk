package com.softberries.klerk.calc;

import java.math.BigDecimal;

import com.softberries.klerk.dao.to.DocumentItem;
import com.softberries.klerk.money.Money;

public class DocumentCalculator {

	public DocumentItem calculateByQuantity(DocumentItem di, Object value){
		try{
			Money quantity = new Money(new BigDecimal(value.toString()));
			di.setQuantity(quantity.getAmount().setScale(2).toPlainString());
			//no exception here? get the rest of the values
			Money priceNet = new Money(new BigDecimal(di.getPriceNetSingle()));
			Money priceNetAll = new Money(new BigDecimal(di.getPriceNetAll()));
			Money priceGross = new Money(new BigDecimal(di.getPriceGrossSingle()));
			Money priceGrossAll = new Money(new BigDecimal(di.getPriceGrossAll()));
			Money taxPercent = new Money(new BigDecimal(di.getTaxValue()));
			Money tax = new Money(new BigDecimal(di.getPriceTaxSingle()));
			Money taxAll = new Money(new BigDecimal(di.getPriceTaxAll()));
			
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
		Money priceNet = new Money(new BigDecimal(value.toString()));
		di.setPriceNetSingle(priceNet.getAmount().setScale(2).toPlainString());
		//no exception here? get the rest of the values
		Money quantity = new Money(new BigDecimal(di.getQuantity()));
		Money priceNetAll = new Money(new BigDecimal(di.getPriceNetAll()));
		Money priceGross = new Money(new BigDecimal(di.getPriceGrossSingle()));
		Money priceGrossAll = new Money(new BigDecimal(di.getPriceGrossAll()));
		Money taxPercent = new Money(new BigDecimal(di.getTaxValue()));
		Money tax = new Money(new BigDecimal(di.getPriceTaxSingle()));
		Money taxAll = new Money(new BigDecimal(di.getPriceTaxAll()));
		
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
		Money priceNetAll = new Money(new BigDecimal(value.toString()));
		di.setPriceNetAll(priceNetAll.getAmount().setScale(2).toPlainString());
		//no exception here? get the rest of the values
		Money quantity = new Money(new BigDecimal(di.getQuantity()));
		Money priceGross = new Money(new BigDecimal(di.getPriceGrossSingle()));
		Money priceGrossAll = new Money(new BigDecimal(di.getPriceGrossAll()));
		Money taxPercent = new Money(new BigDecimal(di.getTaxValue()));
		Money tax = new Money(new BigDecimal(di.getPriceTaxSingle()));
		Money taxAll = new Money(new BigDecimal(di.getPriceTaxAll()));
		
		Money priceNet = new Money(new BigDecimal(priceNetAll.getAmount().setScale(2).toPlainString())).div(quantity.getAmount().doubleValue());
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
//		Money priceGrossAll = new Money(new BigDecimal(value.toString()));
//		di.setPriceGrossAll(priceGrossAll.getAmount().setScale(2).toPlainString());
//		//no exception here? get the rest of the values
//		
//		Money quantity = new Money(new BigDecimal(di.getQuantity()));
//		Money priceGross = new Money(new BigDecimal(di.getPriceGrossSingle()));
//		Money taxPercent = new Money(new BigDecimal(di.getTaxValue()));
//		Money tax = new Money(new BigDecimal(di.getPriceTaxSingle()));
//		Money taxAll = new Money(new BigDecimal(di.getPriceTaxAll()));
//		
//		
//		Money grossSingleCalc = new Money(new BigDecimal(priceGrossAll.getAmount().setScale(2).toPlainString())).div(quantity.getAmount().doubleValue());
//		Money priceNet = grossSingleCalc.minus(grossSingleCalc.times(taxPercent.getAmount().doubleValue()));
//		Money priceNetAllCalc = priceNet.times(quantity.getAmount().doubleValue());
//		Money taxValueAll = priceGrossAll.minus(priceNetAllCalc);
//		
//		di.setPriceGrossSingle(grossSingleCalc.getAmount().setScale(2).toPlainString());
//		di.setPriceGrossAll(priceGrossAll.getAmount().setScale(2).toPlainString());
//		di.setPriceTaxAll(taxValueAll.getAmount().setScale(2).toPlainString());
//		di.setPriceNetSingle(priceNet.getAmount().setScale(2).toPlainString());
		return di;
	}
	public DocumentItem calculateByTaxPercent(DocumentItem di, Object value) {
		// TODO Auto-generated method stub
		return di;
	}
	public DocumentItem calculateByPriceTaxAll(DocumentItem di, Object value) {
		// TODO Auto-generated method stub
		return di;
	}
}
