package io.nem.mpspdf.model;

import java.io.Serializable;

public class SwiftTransferTransaction implements Serializable {
	
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
