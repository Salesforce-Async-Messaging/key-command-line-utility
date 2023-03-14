# JWT and JWK Command Line Utility

### Overview

This tool helps you create a JSON Web Key (JWK) and a JSON Web Token (JWT).

### Prerequisites

Be sure that you have a certificate, along with a public and private key, before running these instructions. If you'd like to create a certificate for testing purposes, see [Creating a Certificate](CreatingCertificates.md).

### Instructions

#### 1. Clone or download the repository

To clone, the repository, run the following command.

`git clone git@github.com:Salesforce-Async-Messaging/key-command-line-utility.git`

Alternatively, you can download a ZIP of the repository, unzip it, and build the project that way.

#### 2. Build the project

Be sure that you have Maven and Java 11 or more recent installed on your system and then enter the following command. 

`mvn clean install`

This command creates the `key-command-line-utility.jar` file, which resides in the `target` folder.

#### 3. Run the commands to create a JWT and JWK

This tool generates a JSON Web Key (JWK) and a JSON Web Token (JWT).

**Usage:** `java -jar {path/to/}key-command-line-utility.jar --command [command argument]`

where `{path/to/}` is the path to the command line utility jar file.

	COMMANDS
		--generateJwt 	 Generates JWT
		--generateJwk 	 Generates JWK

	COMMAND ARGUMENTS
		-kid 		 Key identifier, required for both generateJwt & generateJwk commands
		-issuer 	 JWT issuer, required for generateJwt command
		-subject 	 Subject, required for generateJwt command
		-expiry 	 JWT expiry in seconds, required for generateJwt command
		-privateKeyFile  Private key path and filename, required for generateJwt command
		-publicKeyFile 	 Public key path and filename, required for generateJwk command
		-publicCertFile  Public certificate path and filename, required for generateJwk command

	EXAMPLES
		java -jar {path/to/}key-command-line-utility.jar --generateJwt -kid 12345 -issuer testIssuer -subject user1 -expiry 6000 -privateKeyFile {path/to/}PrivateKeyFile.key
		java -jar {path/to/}key-command-line-utility.jar --generateJwk -kid 12345 -publicKeyFile {path/to/}PublicKeyFile.key -publicCertFile {path/to/}PublicCertFile.crt
		
	In these examples, {path/to/} is the path to the specified file.
	
These commands output a JWK and JWT. When creating a JWT, copy and paste the output to a `.json` file.
