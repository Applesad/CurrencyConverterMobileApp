package Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import Data.CurrencyDao;
import Data.CurrencyDatabase;
import Models.Currency;

public class CurrencyRepository {
    private CurrencyDao mCurrencyDao;
    private LiveData<List<Currency>> mAllCurrencies;

    public CurrencyRepository(Application application){
        CurrencyDatabase db = CurrencyDatabase.getDatabase(application);
        mCurrencyDao = db.currencyDao();
        mAllCurrencies = mCurrencyDao.getAllCurrencies();

    }
    public LiveData<List<Currency>> getAllCurrencies(){
        return mAllCurrencies;
    }

    public void insert(Currency currency){
        CurrencyDatabase.databaseWriteExecutor.execute(() ->{
            mCurrencyDao.insert(currency);
        });
    }

}
