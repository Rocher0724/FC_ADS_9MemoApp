package choongyul.android.com.memoappp.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by myPC on 2017-02-16.
 */
@DatabaseTable(tableName = "account")
public class Account {

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    String accountNickname;

    @DatabaseField
    String bankName;

    @DatabaseField
    String accountNumber;

    @DatabaseField
    String ownerName;

    @DatabaseField
    String memo;

    @DatabaseField
    Date date;

    public Account(String accountNickname, String bankName, String accountNumber, String ownerName, String memo, Date date) {
        this.accountNickname = accountNickname;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.memo = memo;
        this.ownerName = ownerName;
        this.date = date;
    }

    public String getAccountNickname() {
        return accountNickname;
    }

    public void setAccountNickname(String accountNickname) {
        this.accountNickname = accountNickname;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Account() {
        //default
    }
}


