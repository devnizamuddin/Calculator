package com.example.nizamuddinshamrat.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private String display = "";
    private TextView displayTV,resultTV;
    private String currentOperator = "";
    private TextView deleteTv;
    private  double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayTV = findViewById(R.id.screenTV);
        resultTV = findViewById(R.id.resultTv);
        deleteTv = findViewById(R.id.deleteTv);
        displayTV.setText(display);
        deleteTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clear();
                updateScreen();
                return true;
            }
        });


    }
    public void updateScreen(){
        displayTV.setText(display);
    }
    public void clear(){
        display = "";
        currentOperator = "";
    }

    public void onClickNumber(View view) {
        TextView textView = (TextView) view;
        display += textView.getText().toString();
        updateScreen();
    }

    public void onClickOperator(View view) {
        try {
            if (!isLastCharacterContainOperator()){
                TextView textView = (TextView) view;
                display += textView.getText().toString();
                currentOperator = textView.getText().toString();
                updateScreen();
            }
        }
        catch (Exception e){

        }

    }
    public boolean isLastCharacterContainOperator(){
        String lastCharacter = display.substring(display.length()-1);
        if (lastCharacter.equals("+") || lastCharacter.equals("-") ||
                lastCharacter.equals("×") || lastCharacter.equals("÷") || display.equals("")){

            return true;
        }
        else {
            return false;
        }
    }

    public void onClickPoint(View view) {
        if (!display.contains(".")){
            TextView textView = (TextView) view;
            display += textView.getText().toString();
            updateScreen();
        }
    }

    public void onClickDelete(View view) {
        try {
            display = display.substring(0, display.length() - 1);
            updateScreen();
        }
        catch (Exception e){

        }

    }
    public double operate(String numberOne, String numberTwo, String operator){
        switch (operator){
            case "+":
                return Double.valueOf(numberOne)+Double.valueOf(numberTwo);
            case "-":
                return Double.valueOf(numberOne)-Double.valueOf(numberTwo);
            case "×":
                return Double.valueOf(numberOne)*Double.valueOf(numberTwo);
            case "÷":
                return Double.valueOf(numberOne)/Double.valueOf(numberTwo);
        }
        return 0;
    }

    public void onClickEqual(View view) {
        String[]operation = display.split(Pattern.quote(currentOperator));
        String firstNumber = operation[0];
        String secondNumber = operation[operation.length-1];
        if (result >0){
            result = operate(String.valueOf(result),secondNumber,currentOperator);
        }
        else {
            result = operate(operation[0],operation[1],currentOperator);
        }
        resultTV.setText(String.valueOf(result));
        Toast.makeText(this, ""+operation[operation.length-1], Toast.LENGTH_SHORT).show();
    }
}
