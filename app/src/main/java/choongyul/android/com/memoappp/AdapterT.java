package choongyul.android.com.memoappp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import choongyul.android.com.memoappp.domain.Memo;
import choongyul.android.com.memoappp.interfaces.MainInterface;
import choongyul.android.com.memoappp.interfaces.TextDetailInterface;
import choongyul.android.com.memoappp.interfaces.TextInterface;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by myPC on 2017-02-14.
 */
public class AdapterT extends RecyclerView.Adapter<AdapterT.ViewHolder> implements DialogInterface.OnClickListener{

    private Context context;
    List<Memo> datas;
    TextInterface textInterface;
    MainInterface mainInter;
    int positionTemp;



    public AdapterT(Context context, List<Memo> datas) {
        this.context = context;
        this.datas = datas;
        textInterface = (TextInterface) context;
        this.mainInter = (MainInterface) context;
    }

    @Override
    public AdapterT.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_text_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterT.ViewHolder holder, int position) {
        Memo memo = datas.get(position);
        holder.position = position;
        Log.d("AdapterT", "");
        holder.textView.setText(memo.getMemo());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        int position;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textItem);
            cardView = (CardView) v.findViewById(R.id.textCard);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textInterface.goDetail(position);
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //TODO 롱클릭리스너
                    // 롱클릭시 다이얼로그가 떠야하고 다이얼로그 후에 예 이면 아래 실행
                    DialogSimple(position);
//                    mainInter.textDelete(position);
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
        // Title for AlertDialog
        alert.setTitle("알림");
        // Icon for AlertDialog
        alert.setIcon(R.mipmap.ic_launcher);
        alert.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int id) {
        switch (id) {
            case BUTTON_POSITIVE:
                // Action for 'Yes' Button
                try {
                    mainInter.textDelete(positionTemp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case BUTTON_NEGATIVE:
                // Action for 'NO' Button
                dialog.cancel();
                break;
        }
    }
}
