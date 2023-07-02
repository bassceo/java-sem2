package server.commands;

import common.Request;
import server.CommandMemorizer;
import client.ScriptStack;
import common.collection.StudyGroupCollectionManager;
import common.io.UserIO;

public class HistoryCommand extends Command {
    public HistoryCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(Request request) {
        if(commandMemorizer.getCommands().isEmpty()) {
            userIO.println("[WARNING] No commands!");
        }
        userIO.println("Вот последние команды: ");
        commandMemorizer.getCommands().forEach(userIO::println);
    }
}
