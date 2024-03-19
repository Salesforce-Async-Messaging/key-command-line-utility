package com.keytools.cmd;

import com.nimbusds.jose.JWSAlgorithm;

import java.util.HashMap;

public class Command {
    private static final String CMD_ARGUMENT_PREFIX = "-";

    private CommandEnum cmd;
    private String kid;
    private String issuer;
    private String subject;
    private String expiry;
    private String privateKeyFile;
    private String publicKeyFile;
    private String publicCertFile;
    private JWSAlgorithm algorithm;

    private HashMap<CommandArgumentEnum, String> cmdArguments = new HashMap<>();

    public Command(String[] args) {

        if (args.length < 1) {
            System.out.println("Please specify a command");
            return;
        }
        this.cmd = CommandEnum.fromCommandLineString(args[0]);

        if (this.cmd == null) {
            System.out.println("Invalid Command " + args[0]);
            return;
        }

        for (int i = 1; i < args.length; i++) {
            CommandArgumentEnum cmdArgument = CommandArgumentEnum.fromCommandLineArgumentString(args[i]);
            if (cmdArgument != null) {
                this.cmdArguments.put(cmdArgument, getArgumentValue(args, ++i));
            }
        }

        this.kid = cmdArguments.get(CommandArgumentEnum.CMD_ARGUMENT_KID);
        this.issuer = cmdArguments.get(CommandArgumentEnum.CMD_ARGUMENT_ISSUER);
        this.subject = cmdArguments.get(CommandArgumentEnum.CMD_ARGUMENT_SUBJECT);
        this.expiry = cmdArguments.get(CommandArgumentEnum.CMD_ARGUMENT_EXPIRY);
        this.privateKeyFile = cmdArguments.get(CommandArgumentEnum.CMD_ARGUMENT_PRIVATE_KEY_FILE);
        this.publicKeyFile = cmdArguments.get(CommandArgumentEnum.CMD_ARGUMENT_PUBLIC_KEY_FILE);
        this.publicCertFile = cmdArguments.get(CommandArgumentEnum.CMD_ARGUMENT_PUBLIC_CERT_FILE);
        String algorithmArgValue = cmdArguments.get(CommandArgumentEnum.CMD_ARGUMENT_ALGORITHM);
        // Defaulting to JWSAlgorithm.RS256
        this.algorithm = algorithmArgValue == null ? JWSAlgorithm.RS256 : JWSAlgorithm.parse(algorithmArgValue);
    }

    private String getArgumentValue(String[] args, int argPosition) {
        String argValue = null;

        if (argPosition < args.length && !args[argPosition].startsWith(CMD_ARGUMENT_PREFIX)) {
            argValue = args[argPosition];

            if (argValue == null ||  argValue.isBlank()) {
                argValue = null;
            }
        }

        return argValue;
    }

    public boolean hasValidCommandArguments() {
        boolean isValid = false;

        if (this.cmd == null) {
            System.out.println("Please specify a valid command");
            return false;
        }

        if (CommandEnum.CMD_GENERATE_JWK_CMD.equals(cmd)) {
            isValid = hasValidJwkCommandArguments();
        } else if (CommandEnum.CMD_GENERATE_JWT_CMD.equals(cmd)) {
            isValid = hasValidJwtCommandArguments();
        }

        return isValid;
    }

    private boolean hasValidJwkCommandArguments() {
        if (this.kid == null) {
            System.out.println("To generate JWK these are required arguments: -kid");
            return false;
        }

        return true;
    }

    private boolean hasValidJwtCommandArguments() {
        if (this.kid == null || this.issuer == null
                || this.subject == null || this.expiry == null) {
            // || this.privateKeyFile == null) {
            System.out.println("To generate JWT these are required arguments: -kid, " +
                                       "-issuer, -subject, -expiry -privateKeyFile");
            return false;
        }

        return true;
    }

    public CommandEnum getCmd() {
        return cmd;
    }

    public String getKid() {
        return kid;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getSubject() {
        return subject;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getPrivateKeyFile() {
        return privateKeyFile;
    }

    public String getPublicKeyFile() {
        return publicKeyFile;
    }

    public String getPublicCertFile() {
        return publicCertFile;
    }

    public JWSAlgorithm getAlgorithm() {
        return algorithm;
    }
}
