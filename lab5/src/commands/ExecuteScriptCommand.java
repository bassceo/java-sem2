package com.ifmo.commands;

import com.ifmo.CommandMemorizer;
import com.ifmo.CommandProcessor;
import com.ifmo.ScriptStack;
import com.ifmo.collection.StudyGroupCollectionManager;
import com.ifmo.exceptions.ExitException;
import com.ifmo.exceptions.RecursiveException;
import com.ifmo.exceptions.WrongComandException;
import com.ifmo.io.ErrorHandler;
import com.ifmo.io.FileIO;
import com.ifmo.io.UserIO;

import java.io.IOException;

public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand(UserIO userIO, StudyGroupCollectionManager studyGroupCollectionManager, ScriptStack scriptStack, CommandMemorizer commandMemorizer) {
        super(userIO, studyGroupCollectionManager, scriptStack, commandMemorizer);
    }

    @Override
    public void execute(String args) throws Exception {
        if(args==null){
            ErrorHandler.logError("Не указан скрипт. Попробуйте еще раз.");
            return;
        }
        try {
            String script = args.trim();
            if (script.isEmpty()) {
                ErrorHandler.logError("Не указан скрипт. Попробуйте еще раз.");
                return;
            }
            if(scriptStack.contains(script)){
                throw new RecursiveException();
            }
            UserIO fileIO = new FileIO(script);
            scriptStack.push(script);
            CommandProcessor commandProcessor = new CommandProcessor(collectionManager, fileIO, commandMemorizer,scriptStack, collectionManager.getFileName());
            try {
                String next_line = fileIO.readLine();
                while (next_line != null) {
                    if(next_line.trim().isEmpty()){
                        next_line = fileIO.readLine();
                        continue;
                    }
                    commandProcessor.processCommand(next_line, true);
                    next_line = fileIO.readLine();
                }
            }catch (ExitException e){
                scriptStack.pop();
                return;
            }catch (RecursiveException e) {
                if(scriptStack.size() == 1) {
                    scriptStack.pop();
                    ErrorHandler.logError("Вызван рекурсивный скрипт. Выполнение скриптов остановлено.");
                    return;
                }else {
                    scriptStack.pop();
                    throw new RecursiveException();
                }
            }catch (WrongComandException e){

                if(scriptStack.size() == 1) {
                    scriptStack.pop();
                    ErrorHandler.logError("Неизвестная команда  " + e.getMessage() +". Выполнение скриптов остановлено.");
                    return;
                }else {
                    scriptStack.pop();
                    throw new WrongComandException(e.getMessage());
                }
            }
        } catch (IOException e) {
            ErrorHandler.logError(e.getMessage());
            return;
        }
        scriptStack.pop();
    }
}
