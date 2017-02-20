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

import choongyul.android.com.memoappp.domain.Id;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class IdDetailActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    EditText editAdress;
    EditText editId;
    EditText editMemo;
    FloatingActionButton fab;
    Id id;
    List<Id> idDatas = new ArrayList<>();
    boolean modifyFlag = false;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_detail);

        widgetSetting();
        listenerSetting();

        idDatas = DataLoader.getIdDatas(IdDetailActivity.this);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        if(position >= 0) {
            id = idDatas.get(position);
            editAdress.setText(id.getSiteAdress());
            editId.setText(id.getSiteId());
            editMemo.setText(id.getMemo());
            modifyFlag = true;
        }
    }

    private void widgetSetting() {
        modifyFlag = false;
        editAdress = (EditText) findViewById(R.id.etNick);
        editAdress.requestFocus();
        editId = (EditText) findViewById(R.id.etBank);
        editMemo = (EditText) findViewById(R.id.etMemo);
        editAdress.setText("");
        editId.setText("");
        editMemo.setText("");

        fab = (FloatingActionButton) findViewById(R.id.fabAddId);
    }

    private void listenerSetting() {
        fab.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (modifyFlag && editAdress.getText().toString().equals("") && editId.getText().toString().equals("") ) {
                DialogSimple();

            } else if (modifyFlag) {
                Log.e("textdetailF", " 수정되었습니다.");
                Toast.makeText(IdDetailActivity.this, "수정되었습니다", Toast.LENGTH_SHORT).show();
                id.setSiteAdress(editAdress.getText().toString());
                id.setSiteId(editId.getText().toString());
                id.setMemo(editMemo.getText().toString());
                id.setDate(new Date(System.currentTimeMillis()));

                try {
                    DataLoader.saveToIdModify(id, IdDetailActivity.this);
                    IdDetailActivity.super.onBackPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if( !editAdress.getText().toString().equals("") && !editId.getText().toString().equals("") ) {
                Toast.makeText(IdDetailActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                id = new Id();
                id.setSiteAdress(editAdress.getText().toString());
                id.setSiteId(editId.getText().toString());
                id.setMemo(editMemo.getText().toString());
                id.setDate(new Date(System.currentTimeMillis()));
                try {
                    DataLoader.saveToId(id, IdDetailActivity.this);
                    IdDetailActivity.super.onBackPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(IdDetailActivity.this, "주소와 아이디는 입력해야합니다", Toast.LENGTH_SHORT).show();
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
                    DataLoader.idDelete(position, IdDetailActivity.this);
                    IdDetailActivity.super.onBackPressed();
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
