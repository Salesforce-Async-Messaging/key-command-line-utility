# JWT and JWK Command Line Utility

### Overview

This tool helps you create a JWT (JSON Web Token) and a JWK (JSON Web Key).

### Instructions

#### 1. Clone the repository

`git clone git@github.com:Salesforce-Async-Messaging/key-command-line-utility.git`

#### 2. Build the project

`mvn clean install`

#### 3. Run the commands

Use this tool to generate both the keys and the token.

Usage: `java -jar key-command-line-utility.jar --command [command argument]`

	COMMAND LIST
		--generateJwt 	 Generates JWT
		--generateJwk 	 Generates JWK

	COMMAND ARGUMENTS
		-kid 		 Key identifier, required for both generateJwt & generateJwk commands
		-issuer 	 JWT issuer, required for generateJwt command
		-subject 	 Subject, required for generateJwt command
		-expiry 	 JWT expiry, required for generateJwt command
		-privateKeyFile  Private key file name, optional for generateJwt command
		-publicKeyFile 	 Public key file name, optional for generateJwk command
		-publicCertFile  Public certificate file name, optional for generateJwk command

	EXAMPLES
		java -jar key-command-line-utility.jar --generateJwt -kid 12345 -issuer testIssuer -subject user1 -expiry 6000 -privateKeyFile PrivateKeyFile.key
		java -jar key-command-line-utility.jar --generateJwk -kid 12345 -publicKeyFile PublicKeyFile.key -publicCertFile PublicCertFile.crt

