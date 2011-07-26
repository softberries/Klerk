package com.softberries.klerk.calc;

import java.math.BigDecimal;

import com.softberries.klerk.dao.to.DocumentItem;
import com.softberries.klerk.money.Money;

public class DocumentCalculator {

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
}
