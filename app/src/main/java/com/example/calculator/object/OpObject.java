package com.example.calculator.object;

public class OpObject extends UnitObject {
    private char op;
    private int priority;

    public OpObject(char op)
    {
        this.op = op;
        priority = operation_priority();
    }

    public char getOp() {
        return op;
    }

    public void setOp(char op) {
        this.op = op;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    private int operation_priority()
    {
        switch(op)
        {
            case '+':
            case '-': return 2;
            case '*':
            case '/':
            case '%': return 1;
            case '(': return 0;
            default: return -1;
        }
    }

    public String getObj()
    {
        return ""+op;
    }
}
