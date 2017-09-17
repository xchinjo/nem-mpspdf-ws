package io.nem.mpspdf.paramobj;

import java.util.List;

public class MultisigSwiftTransactionCosignParameter {
	

	private List<String> signers;
	private String multisigHash;
	private String multisigPublicKey;
	public List<String> getSigners() {
		return signers;
	}
	public void setSigners(List<String> signers) {
		this.signers = signers;
	}
	public String getMultisigHash() {
		return multisigHash;
	}
	public void setMultisigHash(String multisigHash) {
		this.multisigHash = multisigHash;
	}
	public String getMultisigPublicKey() {
		return multisigPublicKey;
	}
	public void setMultisigPublicKey(String multisigPublicKey) {
		this.multisigPublicKey = multisigPublicKey;
	}
	
	
	
	
	
}
