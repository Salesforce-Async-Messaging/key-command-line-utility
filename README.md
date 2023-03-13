# key-command-line-utility

### Overview

####2. Clone

git clone git@github.com:Salesforce-Async-Messaging/key-command-line-utility.git

####4. Build

        mvn clean install

####5. Run

Usage: java -jar key-command-line-utility.jar --command [command argument]

	COMMAND SUMMARY
		--generateJwt 	 Generates JWT
		--generateJwk 	 Generates JWK

	COMMAND ARGUMENTS
		-kid 		 Key identifier, required for both generateJwt & generateJwk commands
		-issuer 	 JWT Issuer, required for generateJwt commands
		-subject 	 Subject, required for generateJwt commands
		-expiry 	 JWT expiry, required for generateJwt commands
		-privateKeyFile  Privatekey file name, optional for generateJwt commands
		-publicKeyFile 	 Publickey file name, optional for generateJwk commands
		-publicCertFile  Public certificate file name, optional for generateJwk commands

	EXAMPLES
		java -jar key-command-line-utility.jar --generateJwt -kid 12345 -issuer testIssuer -subject user1 -expiry 6000 -privateKeyFile PrivateKeyFile.key
		java -jar key-command-line-utility.jar --generateJwk -kid 12345 -publicKeyFile PublicKeyFile.key -publicCertFile PublicCertFile.crt

