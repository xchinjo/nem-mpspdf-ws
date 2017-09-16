package io.nem.mpspdfs.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.nem.core.crypto.KeyPair;
import org.nem.core.crypto.PrivateKey;
import org.nem.core.crypto.PublicKey;
import org.nem.core.messages.SecureMessage;
import org.nem.core.model.Account;
import org.nem.core.model.TransferTransaction;
import org.nem.core.model.ncc.NemAnnounceResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.nem.apps.api.TransactionApi;
import io.nem.apps.builders.TransferTransactionBuilder;
import io.nem.apps.crypto.SecureMessageDecoder;
import io.nem.apps.crypto.SecureMessageEncoder;
import io.nem.apps.factories.AttachmentFactory;
import io.nem.apps.util.GzipUtils;
import io.nem.apps.util.JsonUtils;
import io.nem.mpspdf.model.SwiftTransferTransaction;
import io.nem.mpspdf.paramobj.SwiftTransactionDecodeParameter;
import io.nem.mpspdf.paramobj.SwiftTransactionParameter;

@RestController
@RequestMapping("/transaction")
@EnableAsync
@CrossOrigin(origins = { "http://localhost:4200", "http://alvinpreyes.com", "http://arcabots.com" })
public class TransactionService {

	@RequestMapping(method = RequestMethod.POST, path = "/transfer")
	public String sendTransaction(@RequestBody SwiftTransactionParameter request) {

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

			return JsonUtils.toJson(nemAnnounceResult);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}

	@RequestMapping(method = RequestMethod.POST, path = "/transfer/decode")
	public SwiftTransferTransaction decodeTransaction(@RequestBody SwiftTransactionDecodeParameter request) {

		final Account senderAccount = new Account(new KeyPair(PublicKey.fromHexString(request.getSenderPublicKey())));
		final Account recipientAccount = new Account(
				new KeyPair(PrivateKey.fromHexString(request.getReceipientPrivateKey())));

		try {

			TransferTransaction transaction = (TransferTransaction) TransactionApi.getTransaction(request.getHash())
					.getEntity();

			String decode = SecureMessageDecoder.decode(senderAccount, recipientAccount,
					transaction.getMessage().getEncodedPayload());
			String decodedDecompressed = GzipUtils.decompress(decode.getBytes());

			return JsonUtils.fromJson(decodedDecompressed, SwiftTransferTransaction.class);

		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;

	}

}
