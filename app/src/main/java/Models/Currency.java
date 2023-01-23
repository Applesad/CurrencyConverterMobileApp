package Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "currencyTable")
public class Currency {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int uid;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "code")
    public String code;

    public Currency(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public int getUid(){
        return uid;
    }
    public void setId(int id){
        this.uid= id;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description= description;
    }

    public String getCode(){
        return code;
    }
    public void setCode(String code){
        this.code= code;
    }




}
