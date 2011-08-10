package com.softberries.klerk.dao.to;

import java.io.Serializable;

public class DocumentWrapper implements Serializable{

	private Document document;
	private String copy;
	private String createdDate;
	private String transactionDate;
	private String toPayInWords;
	private String dueDate;
	private String receiver;
	private String toPayAmount;
	private String toPayCurrency;
	
	
	//labels
	private String createdDateLbl;
	private String transactionDateLbl;
	private String placeCreatedLbl;
	private String sellerLbl;
	private String buyerLbl;
	private String vatIdLbl;
	private String toPayLbl;
	private String toPayInWordsLbl;
	private String paymentMethodLbl;
	private String dueDateLbl;
	private String notesLbl;
	private String receiverLbl;
	private String creatorLbl;
	private String footerLbl;
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public String getCopy() {
		return copy;
	}
	public void setCopy(String copy) {
		this.copy = copy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getToPayInWords() {
		return toPayInWords;
	}
	public void setToPayInWords(String toPayInWords) {
		this.toPayInWords = toPayInWords;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getCreatedDateLbl() {
		return createdDateLbl;
	}
	public void setCreatedDateLbl(String createdDateLbl) {
		this.createdDateLbl = createdDateLbl;
	}
	public String getTransactionDateLbl() {
		return transactionDateLbl;
	}
	public void setTransactionDateLbl(String transactionDateLbl) {
		this.transactionDateLbl = transactionDateLbl;
	}
	public String getPlaceCreatedLbl() {
		return placeCreatedLbl;
	}
	public void setPlaceCreatedLbl(String placeCreatedLbl) {
		this.placeCreatedLbl = placeCreatedLbl;
	}
	public String getSellerLbl() {
		return sellerLbl;
	}
	public void setSellerLbl(String sellerLbl) {
		this.sellerLbl = sellerLbl;
	}
	public String getBuyerLbl() {
		return buyerLbl;
	}
	public void setBuyerLbl(String buyerLbl) {
		this.buyerLbl = buyerLbl;
	}
	public String getVatIdLbl() {
		return vatIdLbl;
	}
	public void setVatIdLbl(String vatIdLbl) {
		this.vatIdLbl = vatIdLbl;
	}
	public String getToPayLbl() {
		return toPayLbl;
	}
	public void setToPayLbl(String toPayLbl) {
		this.toPayLbl = toPayLbl;
	}
	public String getToPayInWordsLbl() {
		return toPayInWordsLbl;
	}
	public void setToPayInWordsLbl(String toPayInWordsLbl) {
		this.toPayInWordsLbl = toPayInWordsLbl;
	}
	public String getPaymentMethodLbl() {
		return paymentMethodLbl;
	}
	public void setPaymentMethodLbl(String paymentMethodLbl) {
		this.paymentMethodLbl = paymentMethodLbl;
	}
	public String getDueDateLbl() {
		return dueDateLbl;
	}
	public void setDueDateLbl(String dueDateLbl) {
		this.dueDateLbl = dueDateLbl;
	}
	public String getNotesLbl() {
		return notesLbl;
	}
	public void setNotesLbl(String notesLbl) {
		this.notesLbl = notesLbl;
	}
	public String getReceiverLbl() {
		return receiverLbl;
	}
	public void setReceiverLbl(String receiverLbl) {
		this.receiverLbl = receiverLbl;
	}
	public String getCreatorLbl() {
		return creatorLbl;
	}
	public void setCreatorLbl(String creatorLbl) {
		this.creatorLbl = creatorLbl;
	}
	public String getFooterLbl() {
		return footerLbl;
	}
	public void setFooterLbl(String footerLbl) {
		this.footerLbl = footerLbl;
	}
	public String getToPayAmount() {
		return toPayAmount;
	}
	public void setToPayAmount(String toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	public String getToPayCurrency() {
		return toPayCurrency;
	}
	public void setToPayCurrency(String toPayCurrency) {
		this.toPayCurrency = toPayCurrency;
	}
	
	
	
}
