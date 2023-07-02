package common;

import java.io.Serializable;

public class CommandRequest implements Serializable {
    private String commandName;
    private String args;

    public CommandRequest(String commandName, String args) {
        this.commandName = commandName;
        this.args = args;
        return;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgs() {
        return args;
    }
}
