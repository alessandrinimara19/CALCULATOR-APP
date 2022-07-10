package com.example.calculator_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculator_app.utils.Operation;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvExpression;

    Button btnDigit0;
    Button btnDigit1;
    Button btnDigit2;
    Button btnDigit3;
    Button btnDigit4;
    Button btnDigit5;
    Button btnDigit6;
    Button btnDigit7;
    Button btnDigit8;
    Button btnDigit9;

    Button btnDelete;
    Button btnAdd;
    Button btnSubtract;
    Button btnMultiply;
    Button btnDivide;
    Button btnPeriod;

    Button btnReset;
    Button btnEqual;

    boolean isPeriodAdded = false;
    Button btnOperation;
    String elementToAdd = "";
    Operation op = new Operation();
    StringBuilder sbCurrentExpression = new StringBuilder("");
    StringBuilder sb = new StringBuilder("");
    int elementsNotZero;
    boolean firstCharacterMinus;
    int numberOfOperations;

    ArrayList<String> operators = new ArrayList<>();
    ArrayList<Double> numbers = new ArrayList<>();
    StringBuilder sbNumberToConvert = new StringBuilder("");
    Double operationResult;

    String[] div;
    int nrDecimals = 0;
    int ct = 0;
    int lastDecimal = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        setListners();
    }

    private void setListners() {
        btnDigit0.setOnClickListener(addNumber());
        btnDigit1.setOnClickListener(addNumber());
        btnDigit2.setOnClickListener(addNumber());
        btnDigit3.setOnClickListener(addNumber());
        btnDigit4.setOnClickListener(addNumber());
        btnDigit5.setOnClickListener(addNumber());
        btnDigit6.setOnClickListener(addNumber());
        btnDigit7.setOnClickListener(addNumber());
        btnDigit8.setOnClickListener(addNumber());
        btnDigit9.setOnClickListener(addNumber());

        btnPeriod.setOnClickListener(addPeriod());

        btnDelete.setOnClickListener(delete());
        btnReset.setOnClickListener(reset());

        btnAdd.setOnClickListener(addOperation());
        btnSubtract.setOnClickListener(addOperation());
        btnMultiply.setOnClickListener(addOperation());
        btnDivide.setOnClickListener(addOperation());
        btnEqual.setOnClickListener(getResult());
    }

    private void updateLists(double operationResult) {
        operators.remove(numberOfOperations);
        numbers.set(numberOfOperations, operationResult);
        numbers.remove(numberOfOperations + 1);
    }

    public int getDecimals(Double number){
        nrDecimals = 0;
        ct = 0;
        lastDecimal = 1;
        div = number.toString().split("\\.");
        sb = new StringBuilder(div[1]);

        if (div[1] != null){
            while (div[1].length() - lastDecimal >= 0){
                if (div[1].charAt(div[1].length() - lastDecimal) == '0'){
                    ct++;
                    sb.deleteCharAt(div[1].length() - lastDecimal);
                    lastDecimal++;
                } else {
                    break;
                }
            }
            nrDecimals = sb.length();
        }
        return nrDecimals;
    }

    private View.OnClickListener getResult() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operators.clear();
                numbers.clear();
                sbNumberToConvert.delete(0, sbNumberToConvert.length());

                buildLists();

                if (!sbCurrentExpression.toString().equals("Error")){
                    numberOfOperations = 0;

                    if (numbers.size() == 1){
                        operationResult = numbers.get(0);
                    }

                    while(numberOfOperations < operators.size()){
                        if (operators.get(numberOfOperations).equals("x")){
                            operationResult = op.multiplyNumbers(numbers.get(numberOfOperations), numbers.get(numberOfOperations + 1));
                            updateLists(operationResult);
                        } else if (operators.get(numberOfOperations).equals("/")){
                            operationResult = op.divideNumbers(numbers.get(numberOfOperations), numbers.get(numberOfOperations + 1));
                            updateLists(operationResult);
                        } else {
                            numberOfOperations++;
                        }
                    }

                    numberOfOperations = 0;

                    while (operators.size() > 0){
                        if (operators.get(numberOfOperations).equals("+")){
                            operationResult = op.addNumbers(numbers.get(numberOfOperations), numbers.get(numberOfOperations + 1));
                            updateLists(operationResult);
                        } else if (operators.get(numberOfOperations).equals("-")){
                            operationResult = op.substractNumbers(numbers.get(numberOfOperations), numbers.get(numberOfOperations + 1));
                            updateLists(operationResult);
                        }
                    }
                    sbCurrentExpression.delete(0, sbCurrentExpression.length());
                    if (String.valueOf(operationResult).equals("NaN") || String.valueOf(operationResult).equals("Infinity") || String.valueOf(operationResult).equals(".")){
                        sbCurrentExpression.append("Error");
                        isPeriodAdded = false;
                    }
                    else if (getDecimals(operationResult) == 0){

                        sbCurrentExpression.append(String.valueOf(operationResult));
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        tvExpression.setText(sbCurrentExpression);
                        isPeriodAdded = false;
                    }
                    else {

                        sbCurrentExpression.append(String.valueOf(operationResult));
                        isPeriodAdded = true;
                    }

                    if (String.valueOf(operationResult).equals("-0.0")){
                        sbCurrentExpression.deleteCharAt(0);
                    }
                }
                tvExpression.setText(sbCurrentExpression);

            }
        };
    }

    private void buildLists() {

        firstCharacterMinus = false;

        if (!sbCurrentExpression.toString().equals("")) {
            if (sbCurrentExpression.toString().equals(".") || sbCurrentExpression.toString().equals("-")) {
                sbCurrentExpression.delete(0, sbCurrentExpression.length());
                sbCurrentExpression.append("Error");
            } else {
                if (sbCurrentExpression.length() > 0) {
                    if (isOperator(sbCurrentExpression, sbCurrentExpression.length() - 1)) {
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        if (sbCurrentExpression.length() > 0) {
                            if (isOperator(sbCurrentExpression, sbCurrentExpression.length() - 1)) {
                                sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                            }
                        }
                    }
                }

                if (sbCurrentExpression.length() > 0) {
                    if (String.valueOf(sbCurrentExpression.charAt(0)).equals("-")) {
                        firstCharacterMinus = true;
                        sbNumberToConvert.append("-");
                        sbCurrentExpression.deleteCharAt(0);
                    }
                    while (sbCurrentExpression.length() > 0) {

                        if (isOperator(sbCurrentExpression, 0)) {
                            numbers.add(Double.valueOf(sbNumberToConvert.toString()));
                            sbNumberToConvert.delete(0, sbNumberToConvert.length());
                            operators.add(String.valueOf(sbCurrentExpression.charAt(0)));
                            sbCurrentExpression.deleteCharAt(0);
                            if (String.valueOf(sbCurrentExpression.charAt(0)).equals("-")) {
                                sbCurrentExpression.deleteCharAt(0);
                                sbNumberToConvert.append("-");
                            }
                        } else {
                            sbNumberToConvert.append(sbCurrentExpression.charAt(0));
                            sbCurrentExpression.deleteCharAt(0);
                        }
                    }
                    numbers.add(Double.parseDouble(sbNumberToConvert.toString()));
                }
            }
        } else {
            sbCurrentExpression.append("Error");
        }
    }


    private View.OnClickListener reset() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sbNumberToConvert.delete(0, sbNumberToConvert.length());
                sbCurrentExpression.delete(0, sbCurrentExpression.length());
                isPeriodAdded = false;
                sbCurrentExpression.append("");
                tvExpression.setText(sbCurrentExpression);
            }
        };
    }

    private View.OnClickListener delete() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sbCurrentExpression.length() > 0){
                    if (String.valueOf(sbCurrentExpression.charAt(sbCurrentExpression.length() - 1)).equals(".")){
                        isPeriodAdded = false;
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        tvExpression.setText(sbCurrentExpression);
                    } else if (isOperator(sbCurrentExpression, sbCurrentExpression.length() - 1)){
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        tvExpression.setText(sbCurrentExpression);
                        if (periodAdded()){
                            isPeriodAdded = true;
                        } else {
                            isPeriodAdded = false;
                        }
                    } else {
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        tvExpression.setText(sbCurrentExpression);
                    }
                }
            }
        };
    }

    private View.OnClickListener addOperation() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOperation = (Button) view;
                elementToAdd = btnOperation.getText().toString();
                isPeriodAdded = false;

                if (sbCurrentExpression.toString().equals("Error")){
                    sbCurrentExpression.delete(0, sbCurrentExpression.length());
                }
                if (sbCurrentExpression.toString().equals("")){
                    if (elementToAdd.equals("-")){
                        sbCurrentExpression.append("-");
                        tvExpression.setText(sbCurrentExpression);
                    }
                } else if (sbCurrentExpression.toString().equals("-")){
                    if (!elementToAdd.equals("-")){
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        tvExpression.setText(sbCurrentExpression);
                    }
                } else if (isPriorityOperator(sbCurrentExpression, sbCurrentExpression.length() - 1)){
                    if (elementToAdd.equals("-")){
                        sbCurrentExpression.append("-");
                        tvExpression.setText(sbCurrentExpression);
                    } else {
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        sbCurrentExpression.append(elementToAdd);
                        tvExpression.setText(sbCurrentExpression);
                    }
                } else if (String.valueOf(sbCurrentExpression.charAt(sbCurrentExpression.length() - 1)).equals("+")){
                    sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                    sbCurrentExpression.append(elementToAdd);
                    tvExpression.setText(sbCurrentExpression);
                } else if (String.valueOf(sbCurrentExpression.charAt(sbCurrentExpression.length() - 1)).equals("-")){
                    if (isPriorityOperator(sbCurrentExpression, sbCurrentExpression.length() - 2)){
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        sbCurrentExpression.append(elementToAdd);
                        tvExpression.setText(sbCurrentExpression);
                    } else {
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        sbCurrentExpression.append(elementToAdd);
                        tvExpression.setText(sbCurrentExpression);
                    }
                } else {
                    sbCurrentExpression.append(elementToAdd);
                    tvExpression.setText(sbCurrentExpression);
                }
            }
        };
    }

    public boolean isPriorityOperator(StringBuilder sbCurrentExpression, int index){
        if (String.valueOf(sbCurrentExpression.charAt(index)).equals("x")
                || String.valueOf(sbCurrentExpression.charAt(index)).equals("/")){
            return true;
        } return false;
    }

    private View.OnClickListener addPeriod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sbCurrentExpression.toString().equals("Error")){
                    sbCurrentExpression.delete(0, sbCurrentExpression.length());
                }
                if (!isPeriodAdded){
                    sbCurrentExpression.append(".");
                    isPeriodAdded = true;
                    tvExpression.setText(sbCurrentExpression);
                }
            }
        };
    }

    public boolean periodAdded(){
        sb.delete(0, sb.length());
        sb.append(sbCurrentExpression);

        while (sb.length() > 0){
            if (isOperator(sb, sb.length() - 1)){
                return false;
            } else if (String.valueOf(sb.charAt(sb.length() - 1)).equals(".")){
                return true;
            } else {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return false;
    }

    private View.OnClickListener addNumber() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOperation = (Button) view;
                elementToAdd = btnOperation.getText().toString();
                if (sbCurrentExpression.toString().equals("Error")){
                    sbCurrentExpression.delete(0, sbCurrentExpression.length());
                }
                if (sbCurrentExpression.toString().equals("0")){
                    if (!elementToAdd.equals("0")){
                        sbCurrentExpression.deleteCharAt(sbCurrentExpression.length() - 1);
                        sbCurrentExpression.append(elementToAdd);
                        tvExpression.setText(sbCurrentExpression);
                    }
                } else if (elementToAdd.equals("0") && sbCurrentExpression.length() > 0 && numberEqualZero()){
                    tvExpression.setText(sbCurrentExpression);
                }
                else {
                    sbCurrentExpression.append(elementToAdd);
                    tvExpression.setText(sbCurrentExpression);
                }
            }
        };
    }

    public boolean isOperator(StringBuilder sb, int index){
        if (String.valueOf(sb.charAt(index)).equals("-")
                || String.valueOf(sb.charAt(index)).equals("+")
                || String.valueOf(sb.charAt(index)).equals("x")
                || String.valueOf(sb.charAt(index)).equals("/")){
            return true;
        }
        else return false;
    }

    private boolean numberEqualZero() {

        sb.delete(0, sb.length());
        sb.append(sbCurrentExpression);
        elementsNotZero = 0;

        if (isOperator(sb, sb.length() - 1) || String.valueOf(sb.charAt(sb.length() - 1)).equals(".")){
            return false;
        }

        while(sb.length() > 0){
            if (String.valueOf(sb.charAt(sb.length() - 1)).equals("0")){
                sb.deleteCharAt(sb.length() - 1);
            } else if (isOperator(sb, sb.length() - 1)){
                if (elementsNotZero > 0){
                    return false;
                } else return true;
            } else {
                sb.deleteCharAt(sb.length() - 1);
                elementsNotZero++;
            }
        }
        return false;
    }

    private void initComponents() {
        tvExpression = findViewById(R.id.tv_expression);

        btnDigit0 = findViewById(R.id.btn_digit_0);
        btnDigit1 = findViewById(R.id.btn_digit_1);
        btnDigit2 = findViewById(R.id.btn_digit_2);
        btnDigit3 = findViewById(R.id.btn_digit_3);
        btnDigit4 = findViewById(R.id.btn_digit_4);
        btnDigit5 = findViewById(R.id.btn_digit_5);
        btnDigit6 = findViewById(R.id.btn_digit_6);
        btnDigit7 = findViewById(R.id.btn_digit_7);
        btnDigit8 = findViewById(R.id.btn_digit_8);
        btnDigit9 = findViewById(R.id.btn_digit_9);

        btnDelete = findViewById(R.id.btn_delete);
        btnAdd = findViewById(R.id.btn_add);
        btnSubtract = findViewById(R.id.btn_substract);
        btnMultiply = findViewById(R.id.btn_multiply);
        btnDivide = findViewById(R.id.btn_divide);
        btnPeriod = findViewById(R.id.btn_decimal);

        btnReset = findViewById(R.id.btn_reset);
        btnEqual = findViewById(R.id.btn_equals);
    }
}