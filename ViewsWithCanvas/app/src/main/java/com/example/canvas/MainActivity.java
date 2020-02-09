package com.example.canvas;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvas.views.CustomView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customView = findViewById(R.id.customView);


        EditText inputDataEditText = findViewById(R.id.chartInputText);
        inputDataEditText.addTextChangedListener(new InputTextWatcher());
    }

    private class InputTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence sequence, int start, int before, int count) {
            String inputData = sequence.toString();

            if (!inputData.isEmpty()) {
                String[] stringValues = inputData.split("\\s");
                ArrayList<Integer> integers = new ArrayList<>();

                for (String value: stringValues) {
                    try{
                        int intValue = Integer.parseInt(value);
                        integers.add(intValue);
                    } catch (NumberFormatException e) {
                        e.getStackTrace();
                    }
                }

                customView.setChartValues(integers);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
