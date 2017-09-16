package io.nem.mpspdf.paramobj;

public class SwiftTransactionDecodeParameter {
	
	private String receipientPrivateKey;
	private String senderPublicKey;
	private String hash;
	public String getReceipientPrivateKey() {
		return receipientPrivateKey;
	}
	public void setReceipientPrivateKey(String receipientPrivateKey) {
		this.receipientPrivateKey = receipientPrivateKey;
	}
	public String getSenderPublicKey() {
		return senderPublicKey;
	}
	public void setSenderPublicKey(String senderPublicKey) {
		this.senderPublicKey = senderPublicKey;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	
	
}
