package io.nem.mpspdfs.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.nem.core.crypto.Hash;
import org.nem.core.crypto.KeyPair;
import org.nem.core.crypto.PrivateKey;
import org.nem.core.crypto.PublicKey;
import org.nem.core.messages.SecureMessage;
import org.nem.core.model.Account;
import org.nem.core.model.HashUtils;
import org.nem.core.model.MultisigSignatureTransaction;
import org.nem.core.model.TransferTransaction;
import org.nem.core.model.ncc.NemAnnounceResult;
import org.nem.core.serialization.JsonSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.nem.apps.api.TransactionApi;
import io.nem.apps.builders.MultisigSignatureTransactionBuilder;
import io.nem.apps.builders.MultisigTransactionBuilder;
import io.nem.apps.builders.TransferTransactionBuilder;
import io.nem.apps.crypto.SecureMessageDecoder;
import io.nem.apps.crypto.SecureMessageEncoder;
import io.nem.apps.factories.AttachmentFactory;
import io.nem.apps.util.GzipUtils;
import io.nem.apps.util.JsonUtils;
import io.nem.mpspdf.model.SwiftTransferTransaction;
import io.nem.mpspdf.paramobj.MultisigSwiftTransactionCosignParameter;
import io.nem.mpspdf.paramobj.MultisigSwiftTransactionParameter;
import io.nem.mpspdf.paramobj.SwiftTransactionDecodeParameter;
import io.nem.mpspdf.paramobj.SwiftTransactionParameter;

@RestController
@RequestMapping("/transaction")
@EnableAsync
@CrossOrigin(origins = { "http://localhost:4200", "http://alvinpreyes.com", "http://arcabots.com" })
public class TransactionService {

	@RequestMapping(method = RequestMethod.POST, path = "/transfer/multisig/announce")
	public ResponseEntity<String> sendMultisigTransaction(@RequestBody MultisigSwiftTransactionParameter request) {

		final Account senderAccount = new Account(new KeyPair(PrivateKey.fromHexString(request.getSenderPrivateKey())));
		final Account multisigAccount = new Account(
				new KeyPair(PrivateKey.fromHexString(request.getMultisigPrivateKey())));
		final Account recipientAccount = new Account(
				new KeyPair(PublicKey.fromHexString(request.getRecepientPublicKey())));

		SecureMessage message;
		SwiftTransferTransaction transaction = new SwiftTransferTransaction();
		transaction.setAccountNumber(request.getAccountNumber());
		transaction.setSwiftMessage(request.getSwiftMessage());

		try {

			message = SecureMessageEncoder.encode(senderAccount, recipientAccount,
					GzipUtils.compress(JsonUtils.toJson(transaction)));

			TransferTransaction transferTransaction = TransferTransactionBuilder.sender(multisigAccount)
					.recipient(recipientAccount)
					.attachment(AttachmentFactory.createTransferTransactionAttachment(message)).buildTransaction();

			NemAnnounceResult nemAnnounceResult = MultisigTransactionBuilder.sender(senderAccount)
					.otherTransaction(transferTransaction).buildAndSendMultisigTransaction();

			return ResponseEntity.accepted().body(nemAnnounceResult.getTransactionHash().toString());
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}

	@RequestMapping(method = RequestMethod.POST, path = "/multisig/cosign")
	public ResponseEntity<String> cosignMulitisgTransaction(
			@RequestBody MultisigSwiftTransactionCosignParameter request) {
		try {

			MultisigSignatureTransaction multisigSignatureTrans = MultisigSignatureTransactionBuilder
					.multisig(new Account(new KeyPair(PublicKey.fromHexString(request.getMultisigPublicKey()))))
					.addSigners(convertToAccounts(request.getSigners()))
					.otherTransaction(Hash.fromHexString(request.getMultisigHash())).coSign();

			return ResponseEntity.accepted().body(JsonUtils.toJson(multisigSignatureTrans));
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}

	@RequestMapping(method = RequestMethod.POST, path = "/transfer/announce")
	public ResponseEntity<String> sendTransaction(@RequestBody SwiftTransactionParameter request) {

		final Account senderAccount = new Account(new KeyPair(PrivateKey.fromHexString(request.getSenderPrivateKey())));
		final Account recipientAccount = new Account(
				new KeyPair(PublicKey.fromHexString(request.getRecepientPublicKey())));

		SecureMessage message;
		SwiftTransferTransaction transaction = new SwiftTransferTransaction();
		transaction.setAccountNumber(request.getAccountNumber());
		transaction.setSwiftMessage(request.getSwiftMessage());

		try {

			message = SecureMessageEncoder.encode(senderAccount, recipientAccount,
					GzipUtils.compress(JsonUtils.toJson(transaction)));

			NemAnnounceResult nemAnnounceResult = TransferTransactionBuilder.sender(senderAccount)
					.recipient(recipientAccount)
					.attachment(AttachmentFactory.createTransferTransactionAttachment(message))
					.buildAndSendTransaction(); // build and send it!

			return ResponseEntity.accepted().body(nemAnnounceResult.getTransactionHash().toString());
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}

	@RequestMapping(method = RequestMethod.POST, path = "/hash/decode")
	public SwiftTransferTransaction decodeTransaction(@RequestBody SwiftTransactionDecodeParameter request) {

		final KeyPair senderAccount = new KeyPair(PublicKey.fromHexString(request.getSenderPublicKey()));
		final KeyPair recipientAccount = new KeyPair(PrivateKey.fromHexString(request.getReceipientPrivateKey()));

		try {

			TransferTransaction transaction = (TransferTransaction) TransactionApi.getTransaction(request.getHash())
					.getEntity();

			String decode = SecureMessageDecoder.decode(senderAccount, recipientAccount,
					transaction.getAttachment().getMessage().getEncodedPayload());

			String decodedDecompressed = GzipUtils.decompress(decode.getBytes());

			return JsonUtils.fromJson(decodedDecompressed, SwiftTransferTransaction.class);

		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(method = RequestMethod.GET, path = "/hello/sample")
	public ResponseEntity<String> sampleHello() {

		final Account senderAccount = new Account(new KeyPair(
				PrivateKey.fromHexString("90951d4f876e3a15b8507532a051857e933a87269bc0da7400d1604bedc93aec")));
		final Account recipientAccount = new Account(new KeyPair(
				PublicKey.fromHexString("8043f36622be5c91e00d9977c870935c887ff9050ba0a62207db76dba1a87385")));

		SecureMessage message = null;
		try {
			message = SecureMessageEncoder.encode(senderAccount, recipientAccount,
					GzipUtils.compress(JsonUtils.toJson("hello")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		NemAnnounceResult nemAnnounceResult = TransferTransactionBuilder.sender(senderAccount)
				.recipient(recipientAccount).attachment(AttachmentFactory.createTransferTransactionAttachment(message))
				.buildAndSendTransaction(); // build and send it!

		;
		return ResponseEntity.status(401).body(nemAnnounceResult.getTransactionHash().toString());
	}
	
	private List<Account> convertToAccounts(List<String> accountStrings) {
		List<Account> accountList = new ArrayList<Account>();
		for(String account:accountStrings) {
			accountList.add(new Account(new KeyPair(PrivateKey.fromHexString(account))));
		}
		
		return accountList;
	}

}
