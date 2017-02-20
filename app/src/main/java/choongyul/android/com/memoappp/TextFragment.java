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

import choongyul.android.com.memoappp.domain.Memo;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment implements TextWatcher {

    View view;
    Context context;
    AdapterT adapter;
    EditText searchBar;
    RecyclerView recyclerView;
    private List<Memo> memoDatas = new ArrayList<>();
    private List<Memo> searchedData = new ArrayList<>();

    public TextFragment() {
        // 프래그먼트의 생성자는 비워놔야한다.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if( view != null ) {
            return view;
        }
        view = inflater.inflate(R.layout.fragment_main, container, false);

        widgetSetting();
        listenerSetting();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new AdapterT(context, memoDatas);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void setData(List<Memo> datas) {
        memoDatas = datas;
    }

    //메모 삭제시 업데이트를 위한 메소드
    public void refreshAdapter(){
        adapter = new AdapterT(context, memoDatas);
        recyclerView.setAdapter(adapter);
    }
    //검색시 업데이트를 위한 메소드
    public void refreshAdapter(List<Memo> datas){
        adapter = new AdapterT(context, datas);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void widgetSetting(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        searchBar = (EditText) view.findViewById(R.id.searchBar);
        searchBar.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void listenerSetting() {
        searchBar.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String textSearch = searchBar.getText().toString();
        filter(textSearch);
    }

    private void filter(String textSearch) {
        searchedData.clear();
        if (textSearch.equals("")){
            searchedData.addAll(memoDatas);
        } else {
            for(Memo item : memoDatas) {
                if( item.getMemo().contains(textSearch)) {
                    searchedData.add(item);
                }
            }
        }
        refreshAdapter(searchedData);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
