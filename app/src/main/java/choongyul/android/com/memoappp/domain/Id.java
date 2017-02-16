package choongyul.android.com.memoappp.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by myPC on 2017-02-16.
 */
@DatabaseTable(tableName = "Id")
public class Id {

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    String siteAdress;

    @DatabaseField
    String siteId;

    @DatabaseField
    String memo;

    @DatabaseField
    Date date;

    public Id(String siteAdress, String siteId, String memo, Date date) {
        this.siteAdress = siteAdress;
        this.siteId = siteId;
        this.memo = memo;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSiteAdress() {
        return siteAdress;
    }

    public void setSiteAdress(String siteAdress) {
        this.siteAdress = siteAdress;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Id() {
        //default
    }
}


