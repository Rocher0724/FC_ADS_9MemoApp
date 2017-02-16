package choongyul.android.com.memoappp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;
import choongyul.android.com.memoappp.domain.Id;
import choongyul.android.com.memoappp.interfaces.IDInterface;
import choongyul.android.com.memoappp.interfaces.IdDetailInterface;
import choongyul.android.com.memoappp.interfaces.TextDetailInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class IdFragment extends Fragment implements View.OnClickListener, TextWatcher {


    public IdFragment() {
        // Required empty public constructor
    }

    List<Id> idDatas = new ArrayList();
    List<Id> idDataSearched = new ArrayList();
    View view;
    RecyclerView recyclerView;
    Button btnText,btnId,btnAccount,btnAddId;
    int position;
    Context context;
    TextDetailInterface textDetailInterface;
    IDInterface idInterface;
    AdapterI adapter;
    IdDetailInterface idDetailInterface;
    EditText idSearchBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        idInterface = (IDInterface) context;
        textDetailInterface = (TextDetailInterface) context;
        idDetailInterface = (IdDetailInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("IdFragment", "onCreateView :::::::::::::::::::::::");
        // Inflate the layout for this fragment
        if(view != null) {
            return view;
        }
        view = inflater.inflate(R.layout.fragment_id, container, false);

        widgetSetting();
        listenerSetting();


        refreshIdAdapter();
        return view;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public void setData(List<Id> datas) {
        this.idDatas = datas;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnText:
                Log.e("IdFragment", " text로가자 :::::::::::::::::::::::");
                idInterface.fromIdToText();
                break;
            case R.id.btnId:
                break;
            case R.id.btnAccount:
                break;
            case R.id.btnAddMemo:
                idDetailInterface.goIdDetail();
                break;
        }
    }

    private void widgetSetting() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerID);
        btnText = (Button) view.findViewById(R.id.btnText);
        btnId = (Button) view.findViewById(R.id.btnId);
        btnAccount = (Button) view.findViewById(R.id.btnAccount);
        btnAddId = (Button) view.findViewById(R.id.btnAddMemo);
        idSearchBar = (EditText) view.findViewById(R.id.idSearchBar);
        idSearchBar.setText("");
    }
    private void listenerSetting() {
        btnText.setOnClickListener(this);
        btnId.setOnClickListener(this);
        btnAccount.setOnClickListener(this);
        btnAddId.setOnClickListener(this);
        idSearchBar.addTextChangedListener(this);

    }


    public void refreshIdAdapter() {
        Log.e("IdFragment", "refreshIdAdapter :::::::::::::::::::::::");
        adapter = new AdapterI(context, idDatas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context)); //
        adapter.notifyDataSetChanged();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String searchedId = idSearchBar.getText().toString();
        filter(searchedId);
    }

    private void filter(String searchedId) {
        idDataSearched.clear();
        if(searchedId.equals("")) {
            idDataSearched.addAll(idDatas);
        } else {
            for( Id item : idDatas ) {
                if( item.getSiteAdress().contains(searchedId) ||
                        item.getSiteId().contains(searchedId) ||
                        item.getMemo().contains(searchedId)
                        ) {
                    idDataSearched.add(item);
                }
            }
        }

        adapter = new AdapterI(context, idDataSearched);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
