package choongyul.android.com.memoappp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import choongyul.android.com.memoappp.data.DBHelper;
import choongyul.android.com.memoappp.domain.Account;
import choongyul.android.com.memoappp.domain.Id;
import choongyul.android.com.memoappp.domain.Memo;

/**
 * Created by myPC on 2017-02-18.
 */

public class DataLoader {
    private static List<Memo> memoDatas = new ArrayList<>();
    private static List<Id> idDatas = new ArrayList<>();
    private static List<Account> accountDatas = new ArrayList<>();
    static DBHelper dbHelper;
    static Dao<Memo, Integer> memoDao;
    static Dao<Id, Integer> idDao;
    static Dao<Account, Integer> accountDao;

    public static List<Memo> getMemoDatas(Context context) {
        try {
            loadTextData(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memoDatas;
    }

    public static List<Id> getIdDatas(Context context) {
        try {
            loadIDData(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idDatas;
    }

    public static List<Account> getAccountDatas(Context context) {
        try {
            loadAccountData(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountDatas;
    }


    public static void loadTextData(Context context) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        memoDao = dbHelper.getMemoDao();
        memoDatas = memoDao.queryForAll();
    }

    public static void saveToTextModify(Memo memo, Context context) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        memoDao = dbHelper.getMemoDao();
        memoDao.update(memo);
    }

    public static void saveToText(Memo memo, Context context) throws SQLException {
//        super.onBackPressed();

        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        memoDao = dbHelper.getMemoDao();
        memoDao.create(memo);
    }

    public static void textDelete(int position,Context context) throws SQLException {
        Toast.makeText(context, "삭제되었습니다", Toast.LENGTH_SHORT).show();
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        memoDao = dbHelper.getMemoDao();
        Memo memo = memoDatas.get(position);
        memoDao.delete(memo);
    }




    public static void loadIDData(Context context) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);

        idDao = dbHelper.getIdDao();

        idDatas = idDao.queryForAll();
    }

    public static void saveToId(Id id, Context context) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        idDao = dbHelper.getIdDao();
        idDao.create(id);
    }

    public static void saveToIdModify(Id id, Context context) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        idDao = dbHelper.getIdDao();
        idDao.update(id);
    }

    public static void idDelete(int position, Context context) throws SQLException {
        Toast.makeText(context, "삭제되었습니다", Toast.LENGTH_SHORT).show();

        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        idDao = dbHelper.getIdDao();
        Id id = idDatas.get(position);
        idDao.delete(id);
    }

    public static void loadAccountData(Context context) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        accountDao = dbHelper.getAccountDao();
        accountDatas = accountDao.queryForAll();
    }

    public static void saveToAccount(Account account, Context context) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        accountDao = dbHelper.getAccountDao();
        accountDao.create(account);
    }

    public static void saveToAccountModify(Account account, Context context) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        accountDao = dbHelper.getAccountDao();
        accountDao.update(account);
    }

    public static void accountDelete(int position, Context context) throws SQLException {
        Toast.makeText(context, "삭제되었습니다", Toast.LENGTH_SHORT).show();

        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        accountDao = dbHelper.getAccountDao();
        Account account = accountDatas.get(position);
        accountDao.delete(account);
    }
}
