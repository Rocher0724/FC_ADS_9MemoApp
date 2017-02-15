package choongyul.android.com.memoappp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import choongyul.android.com.memoappp.domain.Memo;
import choongyul.android.com.memoappp.interfaces.GoTextInterface;
import choongyul.android.com.memoappp.interfaces.TextDetailInterface;
import choongyul.android.com.memoappp.interfaces.TextInterface;


public class TextDetailFragment extends Fragment implements View.OnClickListener{

    GoTextInterface goTextInterface;
    TextDetailInterface textDetailInterface;
    Context context = null;
    View view = null;
    Button btnSave, btnId, btnText, btnAccount;
    EditText editMemo;
    int position = -1;
    List<Memo> datas = new ArrayList();
    boolean modifyFlag = false;
    Memo memo;
    Memo memo2;

    public void setPosition(int position) {
        this.position = position;
    }
    public void setData(List<Memo> datas) {
        this.datas = datas;
    }


    public TextDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    private void setEditMemo() {
        memo2 = null;
        memo2 = datas.get(position);
        Log.e("setmemo", "세팅할 position은 ::::::::::: " + position + " :::::::::::: 입니다.");
        Log.e("setmemo", "세팅할 메모는 " + memo2.getMemo() + " :::::::::::: 입니다.");

        editMemo.setText(memo2.getMemo());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view!=null) {
            return view;
        }
        modifyFlag = false;
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnId = (Button) view.findViewById(R.id.btnId);
        btnText = (Button) view.findViewById(R.id.btnText);
        btnAccount = (Button) view.findViewById(R.id.btnAccount);

        editMemo = (EditText) view.findViewById(R.id.editText);
        editMemo.setText("");

        if( position >= 0) {
            Log.e("ifposition", "세팅");
            setEditMemo();
            modifyFlag = true;
        }

        btnSave.setOnClickListener(this);
        btnId.setOnClickListener(this);
        btnText.setOnClickListener(this);
        btnAccount.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.textDetailInterface = (TextDetailInterface) context;
        this.goTextInterface = (GoTextInterface) context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnText:
                textDetailInterface.backToText();
                break;
            case R.id.btnId:
                break;
            case R.id.btnAccount:
                break;
            case R.id.btnSave:
                if (modifyFlag) {
                    Log.e("textdetailF", " 수정되었습니다.");
                    Toast.makeText(context, "수정되었습니다", Toast.LENGTH_SHORT).show();
                    memo2.setMemo(editMemo.getText().toString());
                    memo2.setDate(new Date(System.currentTimeMillis()));
                    try {
                        textDetailInterface.saveToTextModify(memo2);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if( !editMemo.getText().toString().equals("")) {
                    Toast.makeText(context, "저장되었습니다", Toast.LENGTH_SHORT).show();
                    Log.e("textdetailF", " editMemo.getText 1" + editMemo.getText().toString() +"1 :::::::: 입니다.");
                    memo = new Memo();
                    String str = editMemo.getText().toString();
                    memo.setMemo(str);
                    memo.setDate(new Date(System.currentTimeMillis()));
                    try {
                        textDetailInterface.saveToList(memo);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "저장할 메모가 없습니다", Toast.LENGTH_SHORT).show();
                    textDetailInterface.backToText();
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("textdetailF", " onstop ::::::::::::::::::::::: 입니다.");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("textdetailF", " onDestoryV ::::::::::::::::::::::: 입니다.");
    }
}
