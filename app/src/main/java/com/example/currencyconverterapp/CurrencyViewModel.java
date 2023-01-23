package com.example.currencyconverterapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Models.Currency;
import Repositories.CurrencyRepository;

public class CurrencyViewModel  extends AndroidViewModel {
    private CurrencyRepository mRepository;

    private final LiveData<List<Currency>> mAllCurrencies;

    public CurrencyViewModel(Application application){
        super(application);
        mRepository = new CurrencyRepository(application);
        mAllCurrencies = mRepository.getAllCurrencies();
    }

    LiveData<List<Currency>> getAllCurrencies(){return mAllCurrencies;}
    public void insert(Currency currency){ mRepository.insert(currency);}
}
