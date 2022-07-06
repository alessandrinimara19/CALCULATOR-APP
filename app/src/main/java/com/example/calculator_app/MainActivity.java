package com.example.calculator_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText tietNumber;

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
    Button btnDecimal;

    Button btnReset;
    Button btnEqual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        tietNumber = findViewById(R.id.tiet_number);

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
        btnDecimal = findViewById(R.id.btn_decimal);

        btnReset = findViewById(R.id.btn_reset);
        btnEqual = findViewById(R.id.btn_equals);

    }
}