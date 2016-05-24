package com.ql1d.verify.recycleradapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ql1d.util.CustomDialog;
import com.ql1d.util.CustomToast;
import com.ql1d.verify.R;
import com.ql1d.verify.model.VerifyList;

import java.util.List;

/**
 * Created by fml on 2015/12/3 0003.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private LayoutInflater mLayoutInflater;
    protected List<VerifyList> mListData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    //定义接口
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public RecyclerViewAdapter(Context context, List<VerifyList> datas) {
        this.mListData = datas;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("TAG", "Hellow");
        View v = mLayoutInflater.inflate(R.layout.verify_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        VerifyList verifyList = mListData.get(position);
        holder.txt_content.setText(verifyList.getContent());
        holder.txt_time.setText(verifyList.getTime());
        holder.txt_id.setText(verifyList.getInner_id());
        holder.btn_reject.setOnClickListener(this);
        holder.btn_agree.setOnClickListener(this);
        setOnListtener(holder);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reject:
                CustomDialog.CreateOKCancelDialogWithQuestion(mContext, "否决确认", "确定否决该情报吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomToast.showMessage(mContext, "确定",
                                Toast.LENGTH_SHORT, CustomToast.CENTER);
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomToast.showMessage(mContext, "取消",
                                Toast.LENGTH_SHORT, CustomToast.CENTER);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.btn_agree:
                CustomDialog.CreateOKCancelDialogWithQuestion(mContext, "通过确认", "确定通过该情报吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomToast.showMessage(mContext, "确定",
                                Toast.LENGTH_SHORT, CustomToast.CENTER);
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomToast.showMessage(mContext, "取消",
                                Toast.LENGTH_SHORT, CustomToast.CENTER);
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    //触发
    protected void setOnListtener(final RecyclerView.ViewHolder holder) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, layoutPosition);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_content;
        TextView txt_time;
        TextView txt_id;
        Button btn_reject;
        Button btn_agree;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_content = (TextView) itemView.findViewById(R.id.txt_content);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
            txt_id = (TextView) itemView.findViewById(R.id.txt_id);
            btn_reject = (Button) itemView.findViewById(R.id.btn_reject);
            btn_agree = (Button) itemView.findViewById(R.id.btn_agree);
        }
    }
}
