package choongyul.android.com.memoappp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import choongyul.android.com.memoappp.domain.Memo;
import choongyul.android.com.memoappp.interfaces.MainInterface;
import choongyul.android.com.memoappp.interfaces.TextInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    View view;
    RecyclerView recyclerView;
    Button btnText, btnId, btnAccount, btnAddMemo;
    Context context;
    private List<Memo> datas = new ArrayList<>();
    TextInterface textInterface;
    AdapterT adapter;


    private static final String ARG_COLUMN_COUNT = "column-count";


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(int columnCount) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        btnText = (Button) view.findViewById(R.id.btnText);
        btnId = (Button) view.findViewById(R.id.btnId);
        btnAccount = (Button) view.findViewById(R.id.btnAccount);
        btnAddMemo = (Button) view.findViewById(R.id.btnAddMemo);

        btnText.setOnClickListener(this);
        btnId.setOnClickListener(this);
        btnAccount.setOnClickListener(this);
        btnAddMemo.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new AdapterT(context, datas);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void setData(List<Memo> datas) {
        this.datas = datas;
    }
    // 메모 생성후 다시 돌아왔을 때 리스트 업데이트를 위한 메소드
    public void refreshAdapter(){
        adapter = new AdapterT(context, datas);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.textInterface = (TextInterface) context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnText:
                break;
            case R.id.btnId:
                break;
            case R.id.btnAccount:
                break;
            case R.id.btnAddMemo:
                textInterface.goDetail();
                break;
        }
    }
}
