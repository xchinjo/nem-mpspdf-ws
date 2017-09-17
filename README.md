# MPSP/DF - Web Service 

A sample web service app written using Spring Boot to showcase sending a Swift Transaction and as well as decoding them.

<h2>Usage</h2>
<h3>Configuration</h3>
Go to Application.java to set the configuration.

```java
	ConfigurationBuilder.nodeNetworkName("mijinnet").nodeNetworkProtocol("http")
	.nodeNetworkUri("a1.dfintech.com").nodeNetworkPort("7895")
	.setup();
```
		
		
<h3>Create Swift Message Transaction</h3>

Method: POST  
Endpoint URL: mpmsf/transaction/transfer/announce 

Post Parameter format:
```bash

{"senderPrivateKey":"<>","recepientPublicKey":"<>","accountNumber":"<bank account number>","swiftMessage":"<Swift>"}
```

Output: Transaction Hash

<h3>Decode Swift Message Transaction</h3>

Method: POST  
Endpoint URL: mpmsf/transaction/hash/decode

Post Parameter format:
```bash
{"senderPrivateKey":"<>","recepientPublicKey":"<>","hash":"<transaction hash>"}
```

Output: Decoded Transaction Message (Swift Message)
<sub>Copyright (c) 2017</sub>
