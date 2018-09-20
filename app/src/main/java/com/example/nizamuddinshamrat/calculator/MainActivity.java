package com.example.nizamuddinshamrat.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView displayTV,resultTV,deleteTv;
    private String display = "",currentOperator = "";
    private  double result = 0;
    private double newResult = 0;
    private boolean pressEqual = false;
    private String presentEquation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayTV = findViewById(R.id.screenTV);
        resultTV = findViewById(R.id.resultTv);
        deleteTv = findViewById(R.id.deleteTv);
        displayTV.setText(display);
        updateScreen();

        deleteTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //On long click delete Button
                clear();
                updateScreen();
                return true;
            }
        });

    }
    public void updateScreen(){
        //update screen As current Operation
        displayTV.setText(display);
        resultTV.setText(String.valueOf(result));

    }
    public void clear(){
        //Clear all value display text and current Operator
        display = "";
        currentOperator = "";
        result = 0.0;
        pressEqual = false;
    }

    public boolean isLastCharacterContainOperator(){
        //check last input is operator or not
        String lastCharacter = display.substring(display.length()-1);
        if (lastCharacter.equals("+") || lastCharacter.equals("-") ||
                lastCharacter.equals("×") || lastCharacter.equals("÷") || display.equals("")){

            return true;
        }
        else {
            return false;
        }
    }

    public double operate(String numberOne, String numberTwo, String operator){
        //Operation As operator
        switch (operator){
            case "+":

                    return Double.valueOf(numberOne)+Double.valueOf(numberTwo);
                //Toast.makeText(this, ""+String.valueOf(Double.valueOf(numberOne)+Double.valueOf(numberTwo)), Toast.LENGTH_SHORT).show();

            case "-":
                return Double.valueOf(numberOne)-Double.valueOf(numberTwo);
            case "×":
                return Double.valueOf(numberOne)*Double.valueOf(numberTwo);
            case "÷":
                try {
                    return  Double.valueOf(numberOne)/Double.valueOf(numberTwo);
                }
                catch (Exception e){
                    Toast.makeText(this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

        }
        return 0;
    }
    public void getResult(String cureentLine){
        /*
        1st Separate String and find two different String
        Then operate as current operator
         */
        String[]operation = cureentLine.split("-|\\+|\\×|\\÷");
        String secondNumber = operation[operation.length-1];
//        Toast.makeText(this, "Current: "+cureentLine+" Result: "+String.valueOf(result)
//                +"Second Number: "+secondNumber, Toast.LENGTH_LONG).show();

        if (operation.length >2){
            //there are more than two string to operate

                Toast.makeText(this, "Result"+String.valueOf(result)+"\n"
                        +"Second:"+secondNumber+"\n"+
                        "Current Operator"+currentOperator, Toast.LENGTH_SHORT).show();
                newResult = operate(String.valueOf(result),secondNumber,currentOperator);
                result = newResult;


            //Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();

            newResult = operate(operation[0],operation[1],currentOperator);
            result = newResult;
            Log.d("Result",String.valueOf(result));


        }
        resultTV.setText(String.valueOf(result));

    }

    public void onClickEqual(View view) {
        if (!isLastCharacterContainOperator()){
            pressEqual = true;
            presentEquation = display;
            getResult(presentEquation);
            updateScreen();
        }
        else {
            Toast.makeText(this, "Your Last character is a operator", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickNumber(View view) {
        TextView textView = (TextView) view;
        display += textView.getText().toString();
        updateScreen();
        pressEqual = false;
    }

    public void onClickOperator(View view) {

        try {
            if (!isLastCharacterContainOperator()){

                //Last character doesn't contain Operator

                TextView textView = (TextView) view;
                display += textView.getText().toString();
                updateScreen();
                if (!currentOperator.equals("")){

                    //Current operator is = not null
                    //Toast.makeText(this, ""+display, Toast.LENGTH_LONG).show();
                    if (!pressEqual){

                            presentEquation = display.substring(0, display.length() - 1);
                            //Toast.makeText(this, ""+presentEquation+currentOperator, Toast.LENGTH_SHORT).show();
                            getResult(presentEquation);
                            updateScreen();
                            currentOperator = textView.getText().toString();
                            pressEqual = true;
                            //Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show()
                    }
                    else {
                        updateScreen();
                        currentOperator = textView.getText().toString();
                    }
                    // no else
                }
                else {
                    currentOperator = textView.getText().toString();
                   // pressEqual = false;
                   // Toast.makeText(this, "Current operator : "+currentOperator, Toast.LENGTH_SHORT).show();
                }

            }
        }
        catch (Exception e){

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

}
