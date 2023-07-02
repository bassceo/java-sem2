package common;

import common.model.StudyGroup;

import java.io.Serializable;

public class Request implements Serializable {
    private String command;
    private String args;
    private StudyGroup studyGroup;

    private String login;

    private String password;

    public Request(String command, String args, String login, String password){
        this.command = command;
        this.args = args;
        studyGroup = null;
        this.login = login;
        this.password = password;
    }

    public Request(String command, String args, StudyGroup studyGroup, String login, String password){
        this.command = command;
        this.args = args;
        this.studyGroup = studyGroup;
        this.login = login;
        this.password = password;
    }

    public Request(String command, String login, String password){
        this.command = command;
        this.login = login;
        this.password = password;
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
    public String getLogin(){ return login; }
    public String getPassword(){ return password; }
}
