package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.calculator.object.NumberObject;
import com.example.calculator.object.OpObject;
import com.example.calculator.object.UnitObject;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private TextView txtExpress;
    private TextView txtResult;

    private boolean isFirst;
    private boolean isLastNum;
    private boolean isLastOpenParen;

    private int openParenCount;

    private Queue<UnitObject> unitQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtExpress = findViewById(R.id.txtExpress);
        txtResult = findViewById(R.id.txtResult);

        isFirst = true;
        isLastNum = false;
        isLastOpenParen = false;
        openParenCount = 0;
        unitQ = new LinkedList<>();

        RecyclerView recBtn = findViewById(R.id.recBtn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);

        recBtn.setLayoutManager(layoutManager);

        Operations operations = new Operations();

        RecyclerAdapter_Op adapter = new RecyclerAdapter_Op();
        adapter.setItems(operations.getOperations());

        recBtn.setAdapter(adapter);
        recBtn.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.HORIZONTAL));
    }

    public void onBtnNumClicked(View view)
    {
        Button btnNum = view.findViewWithTag("btnNum");

        if(isFirst)
        {
            isFirst = false;

            if(btnNum != null)
                txtExpress.setText("");
        }

        if(btnNum!=null)
            txtExpress.append(btnNum.getText());
        else
            txtExpress.append(".");

        isLastNum = true;
        isLastOpenParen = false;
    }

    public void onBtnOpClicked(View view)
    {
        Button btnOp = view.findViewWithTag("btnOp");

        if(!isLastNum)
            deleteOne();

        txtExpress.append(btnOp.getText());

        isLastNum = false;
        isLastOpenParen = false;
    }

    public void onBtnEqualClicked(View view)
    {
        String strExpress = txtExpress.getText().toString();

        strExpress = getModExpress(strExpress);

        for(int i=0; i<strExpress.length(); i++)
        {
            char current_ch = strExpress.charAt(i);
            String tmpStr = "";

            boolean isCurNum = false;

            while(isNumber(String.valueOf(current_ch)) && i<strExpress.length())
            {
                tmpStr += current_ch;

                isCurNum = true;

                i++;
                if(i < strExpress.length())
                    current_ch = strExpress.charAt(i);

            }

            if(isCurNum)
            {
                i--;

                unitQ.add(new NumberObject(Double.parseDouble(tmpStr)));
            }
            else
            {
                unitQ.add(new OpObject(current_ch));
            }
        }

        Queue<UnitObject> postQ = makeToPost(unitQ);
        double result = getResult(postQ);

        txtResult.setText(Double.toString(result));
        unitQ.clear();
    }

    public void onBtnDeleteClicked(View view)
    {
        deleteOne();
    }

    public void onBtnClearClicked(View view)
    {
        txtExpress.setText("0");
        isFirst = true;
        unitQ.clear();
    }

    public void onBtnParenClicked(View view)
    {
        if(isFirst)
        {
            txtExpress.setText("");

            isFirst = false;
        }

        if(!isLastNum)
        {
            txtExpress.append("(");

            isLastOpenParen = true;
            openParenCount++;
        }
        else
        {
            if(openParenCount==0)
            {
                txtExpress.append("*(");

                isLastOpenParen = true;
                openParenCount++;
            }
            else if(isLastOpenParen)
            {
                txtExpress.append("(");

                isLastOpenParen = true;
                openParenCount++;
            }
            else
            {
                txtExpress.append(")");

                isLastOpenParen = false;
                openParenCount--;
            }
        }
    }

    private Queue<UnitObject> makeToPost(Queue<UnitObject> normalQ)
    {
        Queue<UnitObject> postQ = new LinkedList<>();
        Stack<OpObject> opStack = new Stack<>();

        for(UnitObject unit : normalQ)
        {
            if(unit.getIsNum())
            {
                postQ.add(unit);
            }
            else
            {
                OpObject currentOp = (OpObject)unit;

                if(currentOp.getOp() == ')')
                {
                    while(opStack.peek().getOp()!='(')
                    {
                        postQ.add(opStack.pop());
                    }

                    opStack.pop();
                }
                else
                {
                    while (!opStack.isEmpty() && opStack.peek().getPriority() <= currentOp.getPriority())
                    {
                        postQ.add(opStack.pop());
                    }
                }
                opStack.add(currentOp);
            }
        }

        while(!opStack.isEmpty())
        {
            if(opStack.peek().getOp()=='(')
                opStack.pop();
            else
                postQ.add(opStack.pop());
        }

        return postQ;
    }

    private double getResult(Queue<UnitObject> postQ)
    {
        Stack<NumberObject> numberStack = new Stack<>();
        double result = 0.0;

        for(UnitObject unit : postQ)
        {
            if(unit.getIsNum())
            {
                NumberObject numObj = (NumberObject)unit;

                numberStack.add(numObj);
            }
            else
            {
                OpObject opObj = (OpObject)unit;

                double num_second = numberStack.pop().getNumber();
                double num_first = numberStack.pop().getNumber();

                result = doCalculate(num_first, num_second, opObj.getOp());

                numberStack.add(new NumberObject(result));
            }
        }

        return result;
    }

    private String getModExpress(String express)
    {
        for(int i=express.length()-1; i>=0; i--)
        {
            if(isNumber(""+express.charAt(i)))
                return express.substring(0,i+1);
        }

        return "";
    }
    private double doCalculate(double num1, double num2, char op)
    {
        double result;

        switch(op)
        {
            case '+': result = num1+num2; break;
            case '-': result = num1-num2; break;
            case '*': result = num1*num2; break;
            case '/': result = num1/num2; break;
            case '%': result = num1%num2; break;
            default : result = 0.0;
        }

        return result;
    }
    private void deleteOne()
    {
        String getStr = txtExpress.getText().toString();

        if(!getStr.equals("")) {
            txtExpress.setText(getStr.subSequence(0, txtExpress.length() - 1));

            String changedStr = getStr.toString();
            int changedLen = changedStr.length();

            if (isNumber("" + changedStr.charAt(changedLen - 1)))
                isLastNum = true;
            else
                isLastNum = false;
        }
    }

    private boolean isNumber(String str)
    {
        return "0123456789.".contains(str);
    }
}
