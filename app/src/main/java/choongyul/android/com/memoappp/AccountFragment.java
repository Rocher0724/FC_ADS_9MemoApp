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
import choongyul.android.com.memoappp.domain.Account;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements TextWatcher {

    public AccountFragment() {
        // 프래그먼트의 생성자는 비워놔야한다.
    }

    View view;
    RecyclerView recyclerView;
    Context context;
    AdapterA adapter;
    EditText accountSearchBar;
    List<Account> accountDatas = new ArrayList();
    List<Account> accountDataSearched = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if ( view != null) {
            return view;
        }
        view = inflater.inflate(R.layout.fragment_account, container, false);
        widgetSetting();
        listenerSetting();
        refreshAccountAdapter();

        return view;
    }

    public void setData(List<Account> datas) {
        this.accountDatas = datas;
    }

    private void widgetSetting() {
        recyclerView = (RecyclerView) view.findViewById(R.id.accountRecyclerView);
        accountSearchBar = (EditText) view.findViewById(R.id.accountSearchBar);
        accountSearchBar.setText("");
    }

    private void listenerSetting() {
        accountSearchBar.addTextChangedListener(this);
    }

    public void refreshAccountAdapter() {
        adapter = new AdapterA(context, accountDatas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context)); //
        adapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String searchedAccount = accountSearchBar.getText().toString();
        filter(searchedAccount);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void filter(String searchedAccount) {
        accountDataSearched.clear();
        if(searchedAccount.equals("")) {
            accountDataSearched.addAll(accountDatas);
        } else {
            for( Account item : accountDatas ) {
                if( item.getAccountNickname().contains(searchedAccount) ||
                        item.getAccountNumber().contains(searchedAccount) ||
                        item.getBankName().contains(searchedAccount) ||
                        item.getOwnerName().contains(searchedAccount)
                        ) {
                    accountDataSearched.add(item);
                }
            }
        }
        adapter = new AdapterA(context, accountDataSearched);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
