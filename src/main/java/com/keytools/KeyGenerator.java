package com.keytools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keytools.cmd.Command;
import com.keytools.cmd.CommandEnum;
import com.keytools.resources.JWKey;
import com.keytools.utils.JwkUtil;
import com.keytools.utils.JwtUtil;
import com.keytools.utils.KeyUtil;

/**
 * Usage: java -jar {path/to/}key-command-line-utility.jar --command [command argument]
 *      where {path/to/} is the path to the command line utility jar file.
 *
 * 	COMMANDS
 * 		--generateJwt 	 Generates JWT
 * 		--generateJwk 	 Generates JWK
 *
 * 	COMMAND ARGUMENTS
 * 		-kid 		     Key identifier, required for both generateJwt & generateJwk commands
 * 		-issuer 	     JWT Issuer, required for generateJwt commands
 * 		-subject 	     Subject, required for generateJwt commands
 * 		-expiry 	     JWT expiry in seconds, required for generateJwt command
 * 		-alg             Algorithm to be used for signing JWT, Default is RS256 (optional)"
 * 		-privateKeyFile  Private key path and filename, required for generateJwt command
 * 		-publicKeyFile 	 Public key path and filename, required for generateJwk command
 * 		-publicCertFile  Public certificate path and filename, required for generateJwk command
 *
 * 	EXAMPLES
 * 		java -jar {path/to/}key-command-line-utility/target/key-command-line-utility.jar --generateJwt -kid 12345 -issuer testIssuer -subject user1 -expiry 6000 -alg RS256 -privateKeyFile {path/to/}PrivateKeyFile.key
 * 		java -jar {path/to/}key-command-line-utility/target/key-command-line-utility.jar --generateJwk -kid 12345 -alg RS256 -publicKeyFile {path/to/}PublicKeyFile.key -publicCertFile {path/to/}PublicCertFile.crt
 * 	In these examples, {path/to/} is the path to the specified file. Possible values for algorithm are mentioned here : https://www.javadoc.io/doc/com.nimbusds/nimbus-jose-jwt/5.1/com/nimbusds/jose/JWSAlgorithm.html
 *
 */
public class KeyGenerator {

    private static final String USAGE = generateUsage();

    public static void main(String[] args) throws Exception {

        System.out.println("Welcome to JWK and JWT generator");
        if (args.length < 1) {
            System.out.println("Invalid Usage. " + USAGE);
            return;
        }
        Command cmd = new Command(args);

        if (!cmd.hasValidCommandArguments()) {
            System.out.println("Invalid Usage.  " + USAGE);
            return;
        }

        if (CommandEnum.CMD_GENERATE_JWK_CMD.equals(cmd.getCmd())) {
            generateJwk(cmd);
        } else if (CommandEnum.CMD_GENERATE_JWT_CMD.equals(cmd.getCmd())) {
            generateJwt(cmd);
        } else {
            System.out.println("Invalid Command. ");
        }
    }

    private static void generateJwk(Command cmd) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("JWK generator");
        JWKey jwkey = JwkUtil.getJWK(
                cmd.getPublicKeyFile() == null ?
                        KeyUtil.readPublicKey() :
                        KeyUtil.readPublicKey(KeyUtil.getFilePath(cmd.getPublicKeyFile())),
                cmd.getKid(),
                cmd.getPublicCertFile() == null ?
                        KeyUtil.getX509Certificate() :
                        KeyUtil.getX509Certificate(KeyUtil.getFilePath(cmd.getPublicCertFile())),
                cmd.getAlgorithm());

        System.out.println("JWK : " + objectMapper.writeValueAsString(jwkey));
    }

    private static void generateJwt(Command cmd) throws Exception {
        System.out.println("JWT generator");
        String token = JwtUtil.getJWTToken(
                cmd.getIssuer(),
                cmd.getSubject(),
                Integer.parseInt(cmd.getExpiry()),
                cmd.getPrivateKeyFile() == null ?
                        KeyUtil.readPrivateKey() :
                        KeyUtil.readPrivateKey(KeyUtil.getFilePath(cmd.getPrivateKeyFile())),
                cmd.getKid(),
                cmd.getAlgorithm());

        System.out.println("JWT : " + token);
    }

    private static String generateUsage() {
        // java -jar key-command-line-utility.jar --generateJwt -kid 12345 -issuer testIssuer -subject user1 -expiry 6000 -privateKeyFile PrivateKeyFile.key
        // java -jar key-command-line-utility.jar --generateJwk -kid 12345 -publicKeyFile PublicKeyFile.key -publicCertFile PublicCertFile.crt

        StringBuilder sb = new StringBuilder("\n\n\nUsage: java -jar key-command-line-utility.jar --command [command argument]")
                .append("\n\n\tCOMMAND SUMMARY")
                .append("\n\t\t--generateJwt \t Generates JWT")
                .append("\n\t\t--generateJwk \t Generates JWK")
                .append("\n\n\tCOMMAND ARGUMENTS")
                .append("\n\t\t-kid \t\t Key identifier, required for both generateJwt & generateJwk commands")
                .append("\n\t\t-issuer \t JWT Issuer, required for generateJwt commands")
                .append("\n\t\t-subject \t Subject, required for generateJwt commands")
                .append("\n\t\t-expiry \t JWT expiry in seconds, required for generateJwt command")
                .append("\n\t\t-alg \t Optional value for specifying the algorithm used for signing JWT, Default is RS256")
                .append("\n\t\t-privateKeyFile Private key path and filename, required for generateJwt command")
                .append("\n\t\t-publicKeyFile \t Public key path and filename, required for generateJwk command")
                .append("\n\t\t-publicCertFile Public certificate path and filename, required for generateJwk command")
                .append("\n\n\tEXAMPLES")
                .append("\n\t\tjava -jar {path/to/}key-command-line-utility.jar --generateJwt -kid 12345 -issuer testIssuer -subject user1 -expiry 6000 -privateKeyFile {path/to/}PrivateKeyFile.key")
                .append("\n\t\tjava -jar {path/to/}key-command-line-utility.jar --generateJwk -kid 12345 -publicKeyFile {path/to/}PublicKeyFile.key -publicCertFile {path/to/}PublicCertFile.crt")
                .append("\n\tIn these examples, {path/to/} is the path to the specified file.");

        return sb.toString();
    }
}
