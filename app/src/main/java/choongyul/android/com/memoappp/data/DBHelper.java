package choongyul.android.com.memoappp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import choongyul.android.com.memoappp.domain.Memo;

/**
 * Created by myPC on 2017-02-14.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;

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
            // Bbs.class 파일에 정의된 테이블을 생성한다.
            TableUtils.createTable(connectionSource, Memo.class);
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
            // Bbs.class 에서 정의된 테이블을 삭제
            TableUtils.dropTable(connectionSource, Memo.class, false);
            // 데이터를 보존해야 될 필요성이 있으면 중간처리를 해줘야만 한다.
            // TODO : 임시테이블을 생성한 후 데이터를 먼저 저장하고 onCreate 이후에 데이터를 입력해준다.
            // onCreate를 호출해서 테이블을 다시 생성한다.
            onCreate(database, connectionSource);
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
}
