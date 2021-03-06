package choongyul.android.com.memoappp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.sql.SQLException;
import java.util.List;
import choongyul.android.com.memoappp.domain.Id;
import choongyul.android.com.memoappp.interfaces.IdDetailInterface;
import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by myPC on 2017-02-14.
 */
public class AdapterI extends RecyclerView.Adapter<AdapterI.ViewHolder> implements DialogInterface.OnClickListener{

    private Context context;
    List<Id> iddatas;
    IdDetailInterface idDetailInterface;
    int positionTemp;
    MainActivity mainActivity;

    public AdapterI(Context context, List<Id> iddatas) {
        this.context = context;
        this.iddatas = iddatas;
        idDetailInterface = (IdDetailInterface) context;
        mainActivity = (MainActivity) context;
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
            textSiteAdress = (TextView) v.findViewById(R.id.textNickname);
            textId = (TextView) v.findViewById(R.id.textBankName);
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
                    DataLoader.idDelete(positionTemp, context);
                    mainActivity.RefreshIdAdapter();
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
