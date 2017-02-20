package choongyul.android.com.memoappp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import choongyul.android.com.memoappp.domain.Account;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class AccountDetailActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    EditText etNickname;
    EditText etBank;
    EditText etAccount;
    EditText etOwner;
    FloatingActionButton fab;
    Account account;
    List<Account> accountDatas = new ArrayList<>();
    boolean modifyFlag = false;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        widgetSetting();
        listenerSetting();

        accountDatas = DataLoader.getAccountDatas(AccountDetailActivity.this);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        if (position >= 0) {
            account = accountDatas.get(position);
            etNickname.setText(account.getAccountNickname());
            etAccount.setText(account.getAccountNumber());
            etBank.setText(account.getBankName());
            etOwner.setText(account.getOwnerName());
            modifyFlag = true;
        }
    }

    private void widgetSetting() {
        modifyFlag = false;

        etNickname = (EditText) findViewById(R.id.etNick);
        etNickname.requestFocus();
        etAccount = (EditText) findViewById(R.id.etAccount);
        etBank = (EditText) findViewById(R.id.etBank);
        etOwner = (EditText) findViewById(R.id.etOwner);

        etNickname.setText("");
        etAccount.setText("");
        etBank.setText("");
        etOwner.setText("");

        fab = (FloatingActionButton) findViewById(R.id.fabAddAccount);
    }

    private void listenerSetting() {
        fab.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (modifyFlag
                    && etNickname.getText().toString().equals("")
                    && etAccount.getText().toString().equals("")
                    && etBank.getText().toString().equals("")
                    && etOwner.getText().toString().equals("")) {
                // 수정하러 들어왔는데 모두다 지우고 저장을 누른경우 삭제할거냐고 물어본다.
                DialogSimple();
            } else if (modifyFlag) {
                Toast.makeText(AccountDetailActivity.this, "수정되었습니다", Toast.LENGTH_SHORT).show();
                account.setAccountNickname(etNickname.getText().toString());
                account.setAccountNumber(etAccount.getText().toString());
                account.setBankName(etBank.getText().toString());
                account.setOwnerName(etOwner.getText().toString());
                account.setDate(new Date(System.currentTimeMillis()));
                try {
                    DataLoader.saveToAccountModify(account, AccountDetailActivity.this);
                    AccountDetailActivity.super.onBackPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (!etBank.getText().toString().equals("") && !etAccount.getText().toString().equals("")) {
                Toast.makeText(AccountDetailActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                account = new Account();
                account.setAccountNickname(etNickname.getText().toString());
                account.setAccountNumber(etAccount.getText().toString());
                account.setBankName(etBank.getText().toString());
                account.setOwnerName(etOwner.getText().toString());
                account.setDate(new Date(System.currentTimeMillis()));
                try {
                    DataLoader.saveToAccount(account, AccountDetailActivity.this);
                    AccountDetailActivity.super.onBackPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(AccountDetailActivity.this, "은행명과 계좌번호는 입력해야합니다", Toast.LENGTH_SHORT).show();
                AccountDetailActivity.super.onBackPressed();
            }
        }
    };

    private void DialogSimple() {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("메모를 삭제하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("예", this)
                .setNegativeButton("아니오", this);

        AlertDialog alert = alt_bld.create();
        alert.setTitle("알림");
        alert.setIcon(R.mipmap.ic_icon);
        alert.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int id) {
        switch (id) {
            case BUTTON_POSITIVE:
                try {
                    DataLoader.accountDelete(position, AccountDetailActivity.this);
                    AccountDetailActivity.super.onBackPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case BUTTON_NEGATIVE:
                dialog.cancel();
                break;
        }
    }
}