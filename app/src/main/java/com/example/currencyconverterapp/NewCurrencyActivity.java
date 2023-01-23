package com.example.currencyconverterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Models.Currency;

public class NewCurrencyActivity extends AppCompatActivity {

    public CurrencyViewModel mCurrencyViewModel;
    public static final String EXTRA_REPLY = "com.example.currencyconverterapp.REPLY";

    private EditText mEditWordView;
    private EditText mEDitFirst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_currency);
        mEditWordView = findViewById(R.id.edit_word);
        mEDitFirst = findViewById(R.id.des);

        mCurrencyViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);



        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditWordView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = mEditWordView.getText().toString();
                String des = mEDitFirst.getText().toString();
                Currency currency = new Currency(des,word);
                mCurrencyViewModel.insert(currency);
                Toast.makeText(this,"New currency in DB: " + des+" "+ word,Toast.LENGTH_SHORT).show();

                replyIntent.putExtra(EXTRA_REPLY, word);

                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}