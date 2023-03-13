package com.keytools.cmd;

import com.google.common.collect.ImmutableMap;

import java.util.stream.Stream;

public enum CommandEnum {
    CMD_GENERATE_JWK_CMD("--generateJwk", "JWK"),
    CMD_GENERATE_JWT_CMD("--generateJwt", "JWT");

    private static final com.google.common.collect.ImmutableMap<String, CommandEnum> $COMMAND;

    private String commandLineString;
    private String cmd;

    static {
        ImmutableMap.Builder<String, CommandEnum> cmdMapBuilder = ImmutableMap.builder();

        Stream.of(values()).forEach(value -> {
            cmdMapBuilder.put(value.commandLineString, value);
        });

        $COMMAND = cmdMapBuilder.build();
    }

    CommandEnum(String commandLineString, String cmd) {
        this.commandLineString = commandLineString;
        this.cmd = cmd;
    }

    public static CommandEnum fromCommandLineString(String commandLineString) {
        return $COMMAND.get(commandLineString);
    }
}
