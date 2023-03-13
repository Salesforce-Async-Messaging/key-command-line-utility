package com.keytools.cmd;

import com.google.common.collect.ImmutableMap;

import java.util.stream.Stream;

public enum CommandArgumentEnum {

    CMD_ARGUMENT_KID("-kid", "Kid"),
    CMD_ARGUMENT_ISSUER("-issuer", "Issuer"),
    CMD_ARGUMENT_SUBJECT("-subject", "Subject"),
    CMD_ARGUMENT_EXPIRY("-expiry", "Expiry"),
    CMD_ARGUMENT_PRIVATE_KEY_FILE("-privateKeyFile", "PrivateKeyFile"),
    CMD_ARGUMENT_PUBLIC_KEY_FILE("-publicKeyFile", "PublicKeyFile"),
    CMD_ARGUMENT_PUBLIC_CERT_FILE("-publicCertFile", "PublicCertFile");

    private static final com.google.common.collect.ImmutableMap<String, CommandArgumentEnum> $COMMAND_ARGUMENT;
    private String cmdLineArgumentString;
    private String cmdArgument;

    static {
        ImmutableMap.Builder<String, CommandArgumentEnum> commandArgumentMapBuilder = ImmutableMap.builder();

        Stream.of(values()).forEach(value -> {
            commandArgumentMapBuilder.put(value.cmdLineArgumentString, value);
        });

        $COMMAND_ARGUMENT = commandArgumentMapBuilder.build();
    }

    CommandArgumentEnum(String cmdLineArgumentString, String cmdArgument) {
        this.cmdLineArgumentString = cmdLineArgumentString;
        this.cmdArgument = cmdArgument;
    }

    public static CommandArgumentEnum fromCommandLineArgumentString(String cmdLineArgumentString) {
        return $COMMAND_ARGUMENT.get(cmdLineArgumentString);
    }
}
