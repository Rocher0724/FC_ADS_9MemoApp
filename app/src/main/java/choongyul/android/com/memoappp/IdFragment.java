package choongyul.android.com.memoappp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;
import choongyul.android.com.memoappp.domain.Id;


/**
 * A simple {@link Fragment} subclass.
 */
public class IdFragment extends Fragment implements TextWatcher {


    public IdFragment() {
        // 프래그먼트의 생성자는 비워놔야한다.
    }

    View view;
    Context context;
    AdapterI adapter;
    EditText idSearchBar;
    RecyclerView recyclerView;
    List<Id> idDatas = new ArrayList();
    List<Id> idDataSearched = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    public void setData(List<Id> datas) {
        this.idDatas = datas;
    }

    private void widgetSetting() {
        recyclerView = (RecyclerView) view.findViewById(R.id.idRecyclerView);
        idSearchBar = (EditText) view.findViewById(R.id.idSearchBar);
        idSearchBar.setText("");
    }

    private void listenerSetting() {
        idSearchBar.addTextChangedListener(this);
    }

    public void refreshIdAdapter() {
        adapter = new AdapterI(context, idDatas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context)); //
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
