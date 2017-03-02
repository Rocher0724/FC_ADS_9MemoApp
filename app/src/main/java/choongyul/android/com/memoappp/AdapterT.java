package choongyul.android.com.memoappp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;
import choongyul.android.com.memoappp.domain.Memo;
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
    int positionTemp;
    MainActivity mainActivity;
    SimpleDateFormat sdf;

    public AdapterT(Context context, List<Memo> datas) {
        this.context = context;
        this.datas = datas;
        textInterface = (TextInterface) context;
        mainActivity = (MainActivity) context;
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
        holder.textView.setText(memo.getMemo());
        Date date = memo.getDate();
        sdf = new SimpleDateFormat("yy년 MM월 dd일");
        holder.textDate.setText(sdf.format(date).toString());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textDate;
        CardView cardView;
        int position;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textItem);
            textDate = (TextView) v.findViewById(R.id.textDate);
            cardView = (CardView) v.findViewById(R.id.textCard);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textInterface.goTextDetail(position);
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
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
                    DataLoader.textDelete(positionTemp, context);
                    mainActivity.RefreshTextAdapter();
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
