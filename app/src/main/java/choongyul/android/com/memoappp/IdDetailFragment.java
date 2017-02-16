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
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import choongyul.android.com.memoappp.domain.Id;
import choongyul.android.com.memoappp.interfaces.GoTextInterface;
import choongyul.android.com.memoappp.interfaces.IDInterface;
import choongyul.android.com.memoappp.interfaces.IdDetailInterface;
import choongyul.android.com.memoappp.interfaces.TextDetailInterface;


public class IdDetailFragment extends Fragment implements View.OnClickListener{

    GoTextInterface goTextInterface;
    TextDetailInterface textDetailInterface;
    IDInterface idInterface;
    IdDetailInterface idDetailInterface;
    IdFragment idFragment;
    Context context = null;
    View view = null;
    Button btnSave, btnId, btnText, btnAccount;
    EditText editAdress;
    EditText editId;
    EditText editMemo;
    int position = -1;
    List<Id> idDatas = new ArrayList();
    boolean modifyFlag = false;
    Id id;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setData(List<Id> datas) {
        this.idDatas = datas;
    }


    public IdDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    private void setEditId() {
        id = null;
        id = idDatas.get(position);
        Log.e("setEditId", "세팅할 position은 ::::::::::: " + position + " :::::::::::: 입니다.");
        Log.e("setEditId", "세팅할 메모는 " + id.getMemo() + " :::::::::::: 입니다.");

        editAdress.setText(id.getSiteAdress());
        editId.setText(id.getSiteId());
        editMemo.setText(id.getMemo());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view!=null) {
            return view;
        }
        modifyFlag = false;

        view = inflater.inflate(R.layout.fragment_id_detail, container, false);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnId = (Button) view.findViewById(R.id.btnId);
        btnText = (Button) view.findViewById(R.id.btnText);
        btnAccount = (Button) view.findViewById(R.id.btnAccount);



        editMemo = (EditText) view.findViewById(R.id.etMemo);
        editAdress = (EditText) view.findViewById(R.id.etAdress);
        editId = (EditText) view.findViewById(R.id.etID);

        editMemo.setText("");
        editAdress.setText("");
        editId.setText("");



        if( position >= 0) {
            Log.e("ifposition", "세팅");
            setEditId();
            modifyFlag = true;
        }

        btnSave.setOnClickListener(this);
        btnId.setOnClickListener(this);
        btnText.setOnClickListener(this);
        btnAccount.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnText:
                textDetailInterface.backToText();
                break;
            case R.id.btnId:
                editMemo.setText("");
                idInterface.fromAddTextToId();
                break;
            case R.id.btnAccount:
                break;
            case R.id.btnSave:
                if (modifyFlag) {

//                    Log.e("textdetailF", " 수정되었습니다.");

                    Toast.makeText(context, "수정되었습니다", Toast.LENGTH_SHORT).show();
                    id.setSiteAdress(editAdress.getText().toString());
                    id.setSiteId(editId.getText().toString());
                    id.setMemo(editMemo.getText().toString());
                    id.setDate(new Date(System.currentTimeMillis()));

                    try {
                        idDetailInterface.saveToIdModify(id);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else if( !editAdress.getText().toString().equals("") && !editId.getText().toString().equals("")) {
                    Toast.makeText(context, "저장되었습니다", Toast.LENGTH_SHORT).show();
//                    Log.e("textdetailF", " editMemo.getText 1" + editMemo.getText().toString() +"1 :::::::: 입니다.");

                    Id id = new Id();

                    String adress = editAdress.getText().toString();
                    String siteId = editId.getText().toString();
                    String siteMemo = editMemo.getText().toString();

                    id.setSiteAdress(adress);
                    id.setSiteId(siteId);
                    id.setMemo(siteMemo);
                    id.setDate(new Date(System.currentTimeMillis()));

                    try {
                        idDetailInterface.saveToId(id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "주소와 아이디는 입력해야합니다", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        textDetailInterface = (TextDetailInterface) context;
        goTextInterface = (GoTextInterface) context;
        idInterface = (IDInterface) context;
        idDetailInterface = (IdDetailInterface) context;

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
