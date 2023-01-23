package Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Models.Currency;

@Dao
public interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Currency currency);

    @Query("DELETE FROM currencyTable")
    void deleteAll();

    @Query("SELECT * FROM currencyTable")
    LiveData<List<Currency>> getAllCurrencies();
}
