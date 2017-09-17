# MPSP/DF - Web Service 

A sample web service app written using Spring Boot to showcase sending a Swift Transaction and as well as decoding them.

<h3>Pre-requisites</h2>
Java 1.8  
nem-apps-lib  

<h2>Clone and Build nem-apps-lib</h2>

```bash
git clone https://github.com/NEMPH/nem-apps-lib.git
cd nem-apps-lib
mvn clean install
```

Import it as a maven dependency

```xml
<dependency>
    <groupId>io.nem.apps</groupId>
    <artifactId>nem-apps-lib</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

<h3>Usage</h2>
<h3>Configuration</h3>
Go to Application.java to set the configuration.

```java
ConfigurationBuilder.nodeNetworkName("mijinnet").nodeNetworkProtocol("http")
.nodeNetworkUri("a1.dfintech.com").nodeNetworkPort("7895")
.setup();
```
		
<h3>Create Swift Message MultisigTransaction</h3>

Method: POST  
Endpoint URL: mpmsf/transaction/transfer/multisig/announce

Post Parameter format:
```bash

{"multisigPrivateKey":"<multisig account private key>","senderPrivateKey":"<sender private key>","recepientPublicKey":"<recepient public key>","accountNumber":"<bank account number>","swiftMessage":"<Swift>"}
```

<h3>Cosign a Swift Message MultisigTransaction</h3>

Method: POST  
Endpoint URL: mpmsf/transaction/transfer/multisig/cosign

Post Parameter format:
```bash

{"signers":[{"<signer private key1>","<signer private key2>"},"multisigHash":"<multisig hash>","multisigPublicKey":"<multisig account public key>"}
```

Output: Transaction Hash		
<h3>Create Swift Message Transaction</h3>

Method: POST  
Endpoint URL: mpmsf/transaction/transfer/announce 

Post Parameter format:
```bash

{"senderPrivateKey":"<sender private key>","recepientPublicKey":"<recepient public key>","accountNumber":"<bank account number>","swiftMessage":"<Swift>"}
```

Output: Transaction Hash

<h3>Decode Swift Message Transaction</h3>

Method: POST  
Endpoint URL: mpmsf/transaction/hash/decode

Post Parameter format:
```bash
{"senderPrivateKey":"<sender private key>","recepientPublicKey":"<recepient public key>","hash":"<transaction hash>"}
```

Output: Decoded Transaction Message (Swift Message)  

<h3>Test locally</h3>
The project is a Spring Boot Web Service Project. It can be excuted by running the spring boot command to bring up the embedded tomcat. 

<h3>Build and Deploy</h3>
```bash
mvn clean install
```
<sub>Copyright (c) 2017</sub>
