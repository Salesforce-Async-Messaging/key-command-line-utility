# JWT and JWK Command Line Utility

### Overview

This tool helps you create a JSON Web Key (JWK) and a JSON Web Token (JWT).

### Prerequisites

Be sure that you have a certificate, along with a public and private key, before running these instructions. If you'd like to create a certificate for testing purposes, see [Creating a Certificate](CreatingCertificates.md).

### Instructions

#### 1. Clone the repository

`git clone git@github.com:Salesforce-Async-Messaging/key-command-line-utility.git`

#### 2. Build the project

Be sure that you have Maven installed on your system and then enter the following command. 

`mvn clean install`

#### 3. Run the commands

Description: This tool generates a JSON Web Key (JWK) and a JSON Web Token (JWT).

Usage: `java -jar key-command-line-utility.jar --command [command argument]`

	COMMANDS
		--generateJwt 	 Generates JWT
		--generateJwk 	 Generates JWK

	COMMAND ARGUMENTS
		-kid 		 Key identifier, required for both generateJwt & generateJwk commands
		-issuer 	 JWT issuer, required for generateJwt command
		-subject 	 Subject, required for generateJwt command
		-expiry 	 JWT expiry, required for generateJwt command
		-privateKeyFile  Private key file name, required for generateJwt command
		-publicKeyFile 	 Public key file name, required for generateJwk command
		-publicCertFile  Public certificate file name, required for generateJwk command

	EXAMPLES
		java -jar key-command-line-utility.jar --generateJwt -kid 12345 -issuer testIssuer -subject user1 -expiry 6000 -privateKeyFile PrivateKeyFile.key
		java -jar key-command-line-utility.jar --generateJwk -kid 12345 -publicKeyFile PublicKeyFile.key -publicCertFile PublicCertFile.crt
