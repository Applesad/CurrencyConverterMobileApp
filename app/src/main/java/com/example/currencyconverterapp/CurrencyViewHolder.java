package com.example.currencyconverterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import Models.Currency;

public class CurrencyViewHolder extends RecyclerView.ViewHolder {
    private final RecyclerView currencyItemView;

    private CurrencyViewHolder(View currencyView) {
        super(currencyView);
        currencyItemView = currencyView.findViewById(R.id.recyclerview);
    }

    //public void bind(String text) {currencyItemView.setText(text); }

    public static CurrencyViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_currency, parent, false);
        return new CurrencyViewHolder(view);
    }


}
