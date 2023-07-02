package server;

import java.util.ArrayDeque;

public class CommandMemorizer {
    private ArrayDeque<String> commandHistory;

    public CommandMemorizer() {
        this.commandHistory = new ArrayDeque<>();
    }

    public void addCommand(String command) {
        if (commandHistory.size() > 13) {
            commandHistory.removeFirst();
        }
        this.commandHistory.addLast(command);
        return;
    }

    public ArrayDeque<String> getCommands() {
        return this.commandHistory;
    }
}
