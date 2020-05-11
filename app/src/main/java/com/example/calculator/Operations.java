package com.example.calculator;

import java.util.ArrayList;

public class Operations {
    private ArrayList<String> operations;

    public Operations()
    {
        operations = new ArrayList<>();

        operations.add("sin");
        operations.add("cos");
        operations.add("tan");
        operations.add("√");
        operations.add("π");
    }

    public ArrayList<String> getOperations()
    {
        return operations;
    }
}
