package common;

import common.model.StudyGroup;

import java.io.Serializable;

public class Request implements Serializable {
    private String command;
    private String args;
    private StudyGroup studyGroup;

    public Request(String command, String args){
        this.command = command;
        this.args = args;
        studyGroup = null;
    }

    public Request(String command, String args, StudyGroup studyGroup){
        this.command = command;
        this.args = args;
        this.studyGroup = studyGroup;
    }

    public String getCommand() {
        return command;
    }

    public String getArgs() {
        return args;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }
}
