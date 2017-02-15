package choongyul.android.com.memoappp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import choongyul.android.com.memoappp.data.DBHelper;
import choongyul.android.com.memoappp.domain.Memo;
import choongyul.android.com.memoappp.interfaces.GoTextInterface;
import choongyul.android.com.memoappp.interfaces.MainInterface;
import choongyul.android.com.memoappp.interfaces.TextDetailInterface;
import choongyul.android.com.memoappp.interfaces.TextInterface;

public class MainActivity extends AppCompatActivity implements MainInterface, TextInterface, TextDetailInterface, GoTextInterface {

    Button mBtnMemo, mBtnId, mBtnAccount, mBtnNew;

    DBHelper dbHelper;
    Dao<Memo, Integer> memoDao;
    List<Memo> datas = new ArrayList();
    FrameLayout layoutMain;
    MainFragment main;
    IdFragment id;
    AccountFragment account;
    FragmentManager manager;
    TextDetailFragment textDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnMemo = (Button) findViewById(R.id.btnText);
        mBtnId = (Button) findViewById(R.id.btnId);
        mBtnAccount = (Button) findViewById(R.id.btnAccount);
        mBtnNew = (Button) findViewById(R.id.btnAddMemo);

        layoutMain = (FrameLayout) findViewById(R.id.frameLayout);
        main = MainFragment.newInstance(1);
        id = IdFragment.newInstance(1);
        account = AccountFragment.newInstance(1);
        textDetail = new TextDetailFragment();


        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        main.setData(datas);

        manager = getSupportFragmentManager();
        setMainFragment();





    }
    public void loadData () throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        memoDao = dbHelper.getMemoDao();

        datas = memoDao.queryForAll();
    }


    public void setMainFragment () {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_main, main);
        transaction.commit();
    }

    @Override
    public void goMainFragment () {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_main, main);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void textDetailRefresh (){
        textDetail = null;
        textDetail = new TextDetailFragment();
    }

    @Override
    public void goDetail() {
        FragmentTransaction transaction = manager.beginTransaction();

        textDetailRefresh();

        transaction.add(R.id.activity_main, textDetail);
        transaction.addToBackStack(null);
        textDetail.setPosition(-1);
        transaction.commit();
    }

    // 아이템 클릭시 디테일로 넘어가기기
    @Override
    public void goDetail(int position) {
        textDetailRefresh();
        Log.e("Mainactivity", " position은 ::::::::::: " + position + " :::::::::::: 입니다.");
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_main, textDetail);
        transaction.addToBackStack(null);
        textDetail.setPosition(position);
        textDetail.setData(datas);
        transaction.commit();
    }

    @Override
    public void backToText() {
        // super.onBackPressed();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(textDetail);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void saveToList(Memo memo) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        memoDao = dbHelper.getMemoDao();

        memoDao.create(memo);

        loadData();
        main.setData(datas);
        super.onBackPressed();
        main.refreshAdapter();
    }

    @Override
    public void saveToTextModify(Memo memo) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        memoDao = dbHelper.getMemoDao();

        memoDao.update(memo);

        loadData();
        main.setData(datas);
        super.onBackPressed();
        main.refreshAdapter();
    }

    @Override
    public void textDelete(int position) throws SQLException {
        Log.e("Mainactivity", " 삭제할 position은 ::::::::::: " + position + " :::::::::::: 입니다.");
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        memoDao = dbHelper.getMemoDao();
        Memo memo = datas.get(position);
        memoDao.delete(memo);

        loadData();
        main.setData(datas);
        main.refreshAdapter();
    }
}
