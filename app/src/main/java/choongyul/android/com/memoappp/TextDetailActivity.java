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

import choongyul.android.com.memoappp.domain.Memo;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class TextDetailActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    EditText editText;
    FloatingActionButton fab;
    Memo memo;
    List<Memo> datas = new ArrayList<>();
    boolean modifyFlag = false;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        widgetSetting();
        listenerSetting();

        datas = DataLoader.getMemoDatas(TextDetailActivity.this);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        if(position >= 0) {
            memo = datas.get(position);
            editText.setText(memo.getMemo());
            modifyFlag = true;
        }
    }

    private void widgetSetting() {
        modifyFlag = false;
        editText = (EditText) findViewById(R.id.editText);
        editText.setText("");
        fab = (FloatingActionButton) findViewById(R.id.fabaddtext);
    }

    private void listenerSetting() {
        fab.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (modifyFlag && editText.getText().toString().equals("") ) {
                DialogSimple();

            } else if (modifyFlag) {
                Toast.makeText(TextDetailActivity.this, "수정되었습니다", Toast.LENGTH_SHORT).show();
                String str = editText.getText().toString();
                memo.setMemo(str);
                memo.setDate(new Date(System.currentTimeMillis()));

                try {
                    DataLoader.saveToTextModify(memo, TextDetailActivity.this);
                    TextDetailActivity.super.onBackPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if( !editText.getText().toString().equals("")) {
                Toast.makeText(TextDetailActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                memo = new Memo();
                String str = editText.getText().toString();
                memo.setMemo(str);
                memo.setDate(new Date(System.currentTimeMillis()));
                try {
                    DataLoader.saveToText(memo, TextDetailActivity.this);
                    TextDetailActivity.super.onBackPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(TextDetailActivity.this, "저장할 메모가 없습니다", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void DialogSimple(){
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
                    DataLoader.textDelete(position, TextDetailActivity.this);
                    TextDetailActivity.super.onBackPressed();
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
