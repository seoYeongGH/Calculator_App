package com.example.calculator.object;

public class NumberObject extends UnitObject {
    private double number;

    public NumberObject(double number)
    {
        setIsNum(true);
        this.number = number;
    }

    public double getNumber()
    {
        return number;
    }

    public void setNumber(double number)
    {
        this.number = number;
    }

    public String getObj()
    {
        return ""+number;
    }
}
