package choongyul.android.com.memoappp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import choongyul.android.com.memoappp.data.DBHelper;
import choongyul.android.com.memoappp.domain.Id;
import choongyul.android.com.memoappp.domain.Memo;
import choongyul.android.com.memoappp.interfaces.GoTextInterface;
import choongyul.android.com.memoappp.interfaces.IDInterface;
import choongyul.android.com.memoappp.interfaces.DeleteInterface;
import choongyul.android.com.memoappp.interfaces.IdDetailInterface;
import choongyul.android.com.memoappp.interfaces.TextDetailInterface;
import choongyul.android.com.memoappp.interfaces.TextInterface;

public class MainActivity
        extends AppCompatActivity
        implements DeleteInterface, TextInterface, TextDetailInterface, GoTextInterface, IDInterface, IdDetailInterface {

    Button mBtnMemo, mBtnId, mBtnAccount, mBtnNew;

    DBHelper dbHelper;
    Dao<Memo, Integer> memoDao;
    List<Memo> datas = new ArrayList();
    FrameLayout layoutMain;
    MainFragment main;
    IdFragment idF;
    AccountFragment account;
    FragmentManager manager;
    TextDetailFragment textDetail;
    Dao<Id, Integer> idDao;
    List<Id> idDatas = new ArrayList();
    IdDetailFragment idDetailFragment;

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
        idF = new IdFragment();
        account = AccountFragment.newInstance(1);
        textDetail = new TextDetailFragment();
        idDetailFragment = new IdDetailFragment();


        try {
            loadTextData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        main.setData(datas);

        manager = getSupportFragmentManager();
        setMainFragment();
    }


    public void loadTextData () throws SQLException {
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
        transaction.remove(main);
        transaction.add(R.id.activity_main, textDetail);
        transaction.addToBackStack(null);
        textDetail.setPosition(-1);
        transaction.commit();
    }

    // 아이템 클릭시 디테일로 넘어가기기
    @Override
    public void goDetail(int position) {
        textDetailRefresh();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(main);
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
    public void fromIdToText() {
        // super.onBackPressed();
        Log.e("Mainactivity", " text로가자 :::::::::::::::::::::::");
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(idF);
        transaction.add(R.id.activity_main, main);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void fromAddTextToId() {
        Log.e("Mainactivity", " text로가자 :::::::::::::::::::::::");
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(textDetail);
        transaction.add(R.id.activity_main, idF);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void saveToList(Memo memo) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        memoDao = dbHelper.getMemoDao();

        memoDao.create(memo);

        loadTextData();
        main.setData(datas);
        super.onBackPressed();
        main.refreshAdapter();
    }

    @Override
    public void saveToTextModify(Memo memo) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        memoDao = dbHelper.getMemoDao();

        memoDao.update(memo);

        loadTextData();
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

        loadTextData();
        main.setData(datas);
        main.refreshAdapter();
    }

    @Override
    public void idDelete(int position) throws SQLException {
        Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_SHORT).show();
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        idDao = dbHelper.getIdDao();
        Id id = idDatas.get(position);
        idDao.delete(id);

        loadIDData();
        idF.setData(idDatas);
        idF.refreshIdAdapter();
    }

    @Override
    public void goID() throws SQLException {
        FragmentTransaction transaction = manager.beginTransaction();

        loadIDData();
        idF.setData(idDatas);

        textDetailRefresh();
        transaction.replace(R.id.activity_main, idF);
        transaction.addToBackStack(null);
        idF.setPosition(-1);
        transaction.commit();
    }

    public void loadIDData () throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        idDao = dbHelper.getIdDao();

        idDatas = idDao.queryForAll();
    }


    @Override
    public void goIdDetail() {

        FragmentTransaction transaction = manager.beginTransaction();
        idDetailRefresh();

        transaction.replace(R.id.activity_main, idDetailFragment);
        transaction.addToBackStack(null);
        idDetailFragment.setPosition(-1);
        transaction.commit();

    }

    private void idDetailRefresh (){
        idDetailFragment = null;
        idDetailFragment = new IdDetailFragment();
    }

    @Override
    public void goIdDetail(int position) {
        idF.refreshIdAdapter();
        Log.e("IdFragment", " 저장을하자 :::::::::::::::::::::::");
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.activity_main, idDetailFragment);
        transaction.addToBackStack(null);
        idDetailFragment.setPosition(position);
        idDetailFragment.setData(idDatas);
        transaction.commit();
    }

    @Override
    public void saveToId(Id id) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        idDao = dbHelper.getIdDao();

        idDao.create(id);


        super.onBackPressed();
        loadIDData();
        idF.setData(idDatas);
        idF.refreshIdAdapter();
    }


    @Override
    public void saveToIdModify(Id id) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        idDao = dbHelper.getIdDao();

        idDao.update(id);

        super.onBackPressed();

        // 셋데이터를 하고 refresh를 해야 세팅이 된다.
        loadIDData();
        idF.setData(idDatas);
        idF.refreshIdAdapter();
    }
}
