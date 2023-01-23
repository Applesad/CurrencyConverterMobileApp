package com.example.currencyconverterapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.currencyconverterapp.Adapters.CurrencyListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Models.Currency;

public class DBActivity extends AppCompatActivity {

    public CurrencyViewModel mCurrencyViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_currency);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(DBActivity.this, NewCurrencyActivity.class);
            startActivityForResult(intent,NEW_WORD_ACTIVITY_REQUEST_CODE);

        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        final CurrencyListAdapter adapter = new CurrencyListAdapter(new CurrencyListAdapter.CurrencyDiff());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCurrencyViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
        mCurrencyViewModel.getAllCurrencies().observe(this, currencies -> {

            Log.d("currencies", String.valueOf(currencies.size()));
            for(Currency list:currencies){
                Log.d("currencies",list.getDescription());
                adapter.submitList(currencies);
            }
        });




    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


        }
    };
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
