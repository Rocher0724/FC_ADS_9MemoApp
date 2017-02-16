package choongyul.android.com.memoappp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import choongyul.android.com.memoappp.domain.Id;
import choongyul.android.com.memoappp.interfaces.DeleteInterface;
import choongyul.android.com.memoappp.interfaces.IdDetailInterface;
import choongyul.android.com.memoappp.interfaces.TextInterface;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by myPC on 2017-02-14.
 */
public class AdapterI extends RecyclerView.Adapter<AdapterI.ViewHolder> implements DialogInterface.OnClickListener{

    private Context context;
    List<Id> iddatas;
    DeleteInterface deleteInterface;
    IdDetailInterface idDetailInterface;
    int positionTemp;



    public AdapterI(Context context, List<Id> iddatas) {
        this.context = context;
        this.iddatas = iddatas;
        idDetailInterface = (IdDetailInterface) context;
        deleteInterface = (DeleteInterface) context;


//        textInterface = (TextInterface) context;
//        this.mainInter = (DeleteInterface) context;
    }

    @Override
    public AdapterI.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_id_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterI.ViewHolder holder, int position) {
        Id id = iddatas.get(position);
        holder.position = position;
        Log.d("AdapterI", "");
        holder.textSiteAdress.setText(id.getSiteAdress());
        holder.textId.setText(id.getSiteId());
    }

    @Override
    public int getItemCount() {
        return iddatas.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textSiteAdress;
        TextView textId;
        LinearLayout linearLayout;
        int position;

        public ViewHolder(View v) {
            super(v);
            textSiteAdress = (TextView) v.findViewById(R.id.TextAdress);
            textId = (TextView) v.findViewById(R.id.textId);
            linearLayout = (LinearLayout) v.findViewById(R.id.linearID);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idDetailInterface.goIdDetail(position);
                }
            });
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
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
        alert.setIcon(R.mipmap.ic_icon);
        alert.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int id) {
        switch (id) {
            case BUTTON_POSITIVE:
                // Action for 'Yes' Button
                try {
                    deleteInterface.idDelete(positionTemp);
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
