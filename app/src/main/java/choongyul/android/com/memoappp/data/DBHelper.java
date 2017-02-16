package choongyul.android.com.memoappp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import choongyul.android.com.memoappp.domain.Id;
import choongyul.android.com.memoappp.domain.Memo;

/**
 * Created by myPC on 2017-02-14.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 2;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 생성자에서 호출되는 super(context... 에서 database.db 파일이 생성되어 있지 않으면 호출된다
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            // memo.class 파일에 정의된 테이블을 생성한다.
            TableUtils.createTable(connectionSource, Memo.class);
            TableUtils.createTable(connectionSource, Id.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 파일이 존재하지만 DB_VERSION 이 증가하면 호출된다.
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            TableUtils.createTable(connectionSource, Id.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Dao<Memo, Integer> memoDao = null;
    // Dao 객체를 통해서
    // Long은 키값이고 Bbs는 자료이다.
    public Dao<Memo, Integer> getMemoDao() throws SQLException {
        if(memoDao != null) {
            return memoDao;
        }
        memoDao = getDao(Memo.class);
        return memoDao;
    }

    // 메모리에서 null 시켜주는게 보통 release 함수들의 역할이다.
    public void releaseMemoDao() {
        if (memoDao != null) {
            memoDao = null;
        }
    }

    private Dao<Id, Integer> idDao = null;
    // Dao 객체를 통해서
    // Long은 키값이고 Bbs는 자료이다.
    public Dao<Id, Integer> getIdDao() throws SQLException {
        if(idDao != null) {
            return idDao;
        }
        idDao = getDao(Id.class);
        return idDao;
    }
}
