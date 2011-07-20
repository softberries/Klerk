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
			
			di.setPriceNetAll(priceNet.times(quantity.getAmount().doubleValue()).getAmount().setScale(2).toPlainString());
			di.setPriceGrossSingle(priceNet.times(taxPercent.getAmount().doubleValue()).getAmount().setScale(2).toPlainString());
//			di.setPriceNet(priceNet.times(quantity.getAmount().doubleValue()).getAmount().setScale(2).toPlainString());
			return di;
		}catch(NumberFormatException nfe){
			//ignore it
		}
		return di;
	}
}
