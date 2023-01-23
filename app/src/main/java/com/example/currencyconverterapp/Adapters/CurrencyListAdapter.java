package com.example.currencyconverterapp.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.currencyconverterapp.CurrencyViewHolder;

import Models.Currency;

public class CurrencyListAdapter extends ListAdapter<Currency, CurrencyViewHolder> {
    public CurrencyListAdapter(@NonNull DiffUtil.ItemCallback<Currency> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CurrencyViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Currency current = getItem(position);
        //holder.bind(current.getCode());

    }
    public static class CurrencyDiff extends DiffUtil.ItemCallback<Currency>{

        @Override
        public boolean areItemsTheSame(@NonNull Currency oldItem, @NonNull Currency newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Currency oldItem, @NonNull Currency newItem) {
            return oldItem.getCode().equals(newItem.getCode());
        }
    }
}
