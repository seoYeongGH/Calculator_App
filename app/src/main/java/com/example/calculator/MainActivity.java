package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView txtExpress;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtExpress = findViewById(R.id.txtExpress);
        txtResult = findViewById(R.id.txtResult);

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
        txtExpress.append(btnNum.getText());
    }

    public void onBtnOpClicked(View view)
    {
        Button btnOp = view.findViewWithTag("btnOp");
        txtExpress.append(btnOp.getText());
    }
}
