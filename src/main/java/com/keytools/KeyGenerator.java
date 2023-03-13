package com.keytools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keytools.cmd.Command;
import com.keytools.cmd.CommandEnum;
import com.keytools.resources.JWKey;
import com.keytools.utils.JwkUtil;
import com.keytools.utils.JwtUtil;
import com.keytools.utils.KeyUtil;

/**
 * Usage: java -jar key-command-line-utility.jar --command [command argument]
 *
 * 	COMMAND SUMMARY
 * 		--generateJwt 	 Generates JWT
 * 		--generateJwk 	 Generates JWK
 *
 * 	COMMAND ARGUMENTS
 * 		-kid 		     Key identifier, required for both generateJwt & generateJwk commands
 * 		-issuer 	     JWT Issuer, required for generateJwt commands
 * 		-subject 	     Subject, required for generateJwt commands
 * 		-expiry 	     JWT expiry, required for generateJwt commands
 * 		-privateKeyFile  Privatekey file name, optional for generateJwt commands
 * 		-publicKeyFile 	 Publickey file name, optional for generateJwk commands
 * 		-publicCertFile  Public certificate file name, optional for generateJwk commands
 *
 * 	EXAMPLES
 * 		java -jar key-command-line-utility/target/key-command-line-utility.jar --generateJwt -kid 12345 -issuer testIssuer -subject user1 -expiry 6000 -privateKeyFile PrivateKeyFile.key
 * 		java -jar key-command-line-utility/target/key-command-line-utility.jar --generateJwk -kid 12345 -publicKeyFile PublicKeyFile.key -publicCertFile PublicCertFile.crt
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
                        KeyUtil.getX509Certificate(KeyUtil.getFilePath(cmd.getPublicCertFile())));

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
                cmd.getKid());

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
                .append("\n\t\t-expiry \t JWT expiry, required for generateJwt commands")
                .append("\n\t\t-privateKeyFile  Privatekey file name, optional for generateJwt commands")
                .append("\n\t\t-publicKeyFile \t Publickey file name, optional for generateJwk commands")
                .append("\n\t\t-publicCertFile  Public certificate file name, optional for generateJwk commands")
                .append("\n\n\tEXAMPLES")
                .append("\n\t\tjava -jar key-command-line-utility.jar --generateJwt -kid 12345 -issuer testIssuer -subject user1 -expiry 6000 -privateKeyFile PrivateKeyFile.key")
                .append("\n\t\tjava -jar key-command-line-utility.jar --generateJwk -kid 12345 -publicKeyFile PublicKeyFile.key -publicCertFile PublicCertFile.crt");

        return sb.toString();
    }
}
