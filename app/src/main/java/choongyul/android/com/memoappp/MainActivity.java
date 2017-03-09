package choongyul.android.com.memoappp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import java.util.ArrayList;
import java.util.List;
import choongyul.android.com.memoappp.domain.Account;
import choongyul.android.com.memoappp.domain.Id;
import choongyul.android.com.memoappp.domain.Memo;
import choongyul.android.com.memoappp.interfaces.AccountDetailInterface;
import choongyul.android.com.memoappp.interfaces.IdDetailInterface;
import choongyul.android.com.memoappp.interfaces.TextInterface;

public class MainActivity
        extends AppCompatActivity
        implements TextInterface, IdDetailInterface, AccountDetailInterface {

    private static final int REQ_ADD_MEMO = 100;
    private static final int REQ_MEDIFY_MEMO = 101;
    private static final int REQ_ADD_ID = 102;
    private static final int REQ_MEDIFY_ID = 103;
    private static final int REQ_ADD_ACCOUNT = 104;
    private static final int REQ_MEDIFY_ACCOUNT = 105;

    TextFragment textFragment;
    IdFragment idFragment;
    AccountFragment accountFragment;

    final int TAB_COUNT = 3;

    List<Memo> memoDatas = new ArrayList();
    List<Id> idDatas = new ArrayList();
    List<Account> accountDatas = new ArrayList();

    private int page_position = 0; // 프래그먼트 이동시 스택 저장하는거 만들때 쓰는것. 필요없다면 나중엔 지우자

    ViewPager viewPager;
    TabLayout tabLayout;
    Intent intent;

    private FloatingActionButton fabText;
    private FloatingActionButton fabId;
    private FloatingActionButton fabAcc;
    private FloatingActionMenu  fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        widgetSetting();

        textDataSetting();
        idDataSetting();
        accountDataSetting();

        viewPagerSetting();
    }

    private void widgetSetting() {
        textFragment = new TextFragment();
        idFragment = new IdFragment();
        accountFragment = new AccountFragment();

        fabText = (FloatingActionButton) findViewById(R.id.fabText);
        fabId = (FloatingActionButton) findViewById(R.id.fabId);
        fabAcc = (FloatingActionButton) findViewById(R.id.fabAcc);
        fabMenu = (FloatingActionMenu) findViewById(R.id.fabMenu);

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addTab( tabLayout.newTab().setText("텍스트"));
        tabLayout.addTab( tabLayout.newTab().setText("아이디"));
        tabLayout.addTab( tabLayout.newTab().setText("계좌"));

        fabId.setOnClickListener(clickListener);
        fabText.setOnClickListener(clickListener);
        fabAcc.setOnClickListener(clickListener);
    }

    public void textDataSetting() {
        memoDatas = DataLoader.getMemoDatas(this);
        textFragment.setData(memoDatas);
    }

    private void idDataSetting() {
        idDatas = DataLoader.getIdDatas(this);
        idFragment.setData(idDatas);
    }

    private void accountDataSetting() {
        accountDatas = DataLoader.getAccountDatas(this);
        accountFragment.setData(accountDatas);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fabText:
                    goTextDetail();
                    break;
                case R.id.fabId:
                    goIdDetail();
                    break;
                case R.id.fabAcc:
                    goAccountDetail();
                    break;
            }
        }
    };

    private void viewPagerSetting() {

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //아래 두줄이 fragment이동과 뷰페이져를 맞추어준다.
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public void RefreshTextAdapter() {
        memoDatas = DataLoader.getMemoDatas(this);
        textFragment.setData(memoDatas);
        textFragment.refreshAdapter();
    }

    public void RefreshIdAdapter() {
        idDatas = DataLoader.getIdDatas(this);
        idFragment.setData(idDatas);
        idFragment.refreshIdAdapter();
    }

    public void RefreshAccountAdapter() {
        accountDatas = DataLoader.getAccountDatas(this);
        accountFragment.setData(accountDatas);
        accountFragment.refreshAccountAdapter();
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0: fragment = textFragment; break;
                case 1: fragment = idFragment; break;
                case 2: fragment = accountFragment; break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

    }

    @Override
    public void goTextDetail() {
        intent = new Intent(this, TextDetailActivity.class);
        intent.putExtra("position", -1);
        fabMenu.close(true);

        startActivityForResult(intent,REQ_ADD_MEMO);
    }

    @Override
    public void goTextDetail(int position) {
        intent = new Intent(this, TextDetailActivity.class);
        intent.putExtra("position", position);

        startActivityForResult(intent,REQ_MEDIFY_MEMO);
    }

    @Override
    public void goIdDetail() {
        intent = new Intent(this, IdDetailActivity.class);
        intent.putExtra("position", -1);
        fabMenu.close(true);

        startActivityForResult(intent,REQ_ADD_ID);
    }

    @Override
    public void goIdDetail(int position) {
        intent = new Intent(this, IdDetailActivity.class);
        intent.putExtra("position", position);

        startActivityForResult(intent,REQ_MEDIFY_ID);
    }

    @Override
    public void goAccountDetail() {
        intent = new Intent(this, AccountDetailActivity.class);
        intent.putExtra("position", -1);
        fabMenu.close(true);

        startActivityForResult(intent,REQ_ADD_ACCOUNT);
    }

    @Override
    public void goAccountDetail(int position) {
        intent = new Intent(this, AccountDetailActivity.class);
        intent.putExtra("position", position);

        startActivityForResult(intent,REQ_MEDIFY_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_ADD_MEMO:
                textDataSetting();
                textFragment.refreshAdapter();
                break;
            case REQ_MEDIFY_MEMO:
                textDataSetting();
                textFragment.refreshAdapter();
                break;
            case REQ_ADD_ID:
                idDataSetting();
                idFragment.refreshIdAdapter();
                break;
            case REQ_MEDIFY_ID:
                idDataSetting();
                idFragment.refreshIdAdapter();
                break;
            case REQ_ADD_ACCOUNT:
                accountDataSetting();
                accountFragment.refreshAccountAdapter();
                break;
            case REQ_MEDIFY_ACCOUNT:
                accountDataSetting();
                accountFragment.refreshAccountAdapter();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
