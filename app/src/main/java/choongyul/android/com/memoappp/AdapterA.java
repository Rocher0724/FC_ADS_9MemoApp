package choongyul.android.com.memoappp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import choongyul.android.com.memoappp.domain.Account;
import choongyul.android.com.memoappp.domain.Id;
import choongyul.android.com.memoappp.interfaces.AccountDetailInterface;
import choongyul.android.com.memoappp.interfaces.IdDetailInterface;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by myPC on 2017-02-14.
 */
public class AdapterA extends RecyclerView.Adapter<AdapterA.ViewHolder> implements DialogInterface.OnClickListener{

    private Context context;
    List<Account> accountDatas;
    AccountDetailInterface accountDetailInterface;
    int positionTemp;
    MainActivity mainActivity;

    public AdapterA(Context context, List<Account> accountDatas) {
        this.context = context;
        this.accountDatas = accountDatas;
        accountDetailInterface = (AccountDetailInterface) context;
        mainActivity = (MainActivity) context;
    }

    @Override
    public AdapterA.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_account_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterA.ViewHolder holder, int position) {
        Account account = accountDatas.get(position);
        holder.position = position;
        holder.textNickname.setText(account.getAccountNickname());
        holder.textBankName.setText(account.getBankName());
        holder.textAccountNumber.setText(account.getAccountNumber());
        holder.textOwnerName.setText(account.getOwnerName());
    }

    @Override
    public int getItemCount() {
        return accountDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNickname;
        TextView textBankName;
        TextView textAccountNumber;
        TextView textOwnerName;
        RelativeLayout relativeLayout;
        int position;

        public ViewHolder(View v) {
            super(v);
            textNickname = (TextView) v.findViewById(R.id.textNickname);
            textBankName = (TextView) v.findViewById(R.id.textBankName);
            textAccountNumber = (TextView) v.findViewById(R.id.textAccountNumber);
            textOwnerName = (TextView) v.findViewById(R.id.textOwnerName);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relatiACC);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accountDetailInterface.goAccountDetail(position);
                }
            });
            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogSimple(position);
                    return false;
                }
            });
        }
    }

    private void DialogSimple(int position){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
        alt_bld.setMessage("메모를 삭제하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("예", this)
                .setNegativeButton("아니오", this);
        positionTemp = position;

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
                    DataLoader.accountDelete(positionTemp, context);
                    mainActivity.RefreshAccountAdapter();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case BUTTON_NEGATIVE:
                dialog.cancel();
                break;
            default:
                dialog.cancel();
                break;
        }
    }
}
