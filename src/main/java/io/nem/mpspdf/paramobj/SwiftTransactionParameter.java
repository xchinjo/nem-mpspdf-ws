package io.nem.mpspdf.paramobj;

public class SwiftTransactionParameter {
	
	public String getSenderPrivateKey() {
		return senderPrivateKey;
	}
	public void setSenderPrivateKey(String senderPrivateKey) {
		this.senderPrivateKey = senderPrivateKey;
	}
	public String getRecepientPublicKey() {
		return recepientPublicKey;
	}
	public void setRecepientPublicKey(String recepientPublicKey) {
		this.recepientPublicKey = recepientPublicKey;
	}
	private String senderPrivateKey;
	private String recepientPublicKey;
	
	private String accountNumber;
	private String swiftMessage;
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getSwiftMessage() {
		return swiftMessage;
	}
	public void setSwiftMessage(String swiftMessage) {
		this.swiftMessage = swiftMessage;
	}
	
}
