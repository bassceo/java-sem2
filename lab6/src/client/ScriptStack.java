package client;

import java.util.Stack;

public class ScriptStack {
    private Stack<String> stack;

    public ScriptStack() {
        this.stack = new Stack<String>();
    }

    public void push(String script) {
        this.stack.push(script);
        return;
    }

    public void pop() {
        this.stack.pop();
        return;
    }

    public boolean contains(String script) {
        return this.stack.contains(script);
    }

    public int size() {
        return this.stack.size();
    }
}
