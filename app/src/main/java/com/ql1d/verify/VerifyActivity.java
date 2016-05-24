package com.ql1d.verify;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ql1d.util.CustomToast;
import com.ql1d.verify.bgarefreshlayoutviewholder.VerifyHolder;
import com.ql1d.verify.model.VerifyList;
import com.ql1d.verify.recycleradapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class VerifyActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private BGARefreshLayout mBGARefreshLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;
    /** title */
    private TextView txt_title;
    private ImageView img_back;
    /** 数据 */
    private List<VerifyList> mListData = new ArrayList<VerifyList>();
    /** 一次加载数据的条数 */
    private int DATASIZE = 10;
    /** 数据填充adapter */
    private RecyclerViewAdapter mRecyclerViewAdapter = null;
    /** 设置一共请求多少次数据 */
    private int ALLSUM = 0;
    /** 设置刷新和加载 */
    private VerifyHolder mDefineBAGRefreshWithLoadView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        mContext = this;
        initView();
        setBgaRefreshLayout();
        setRecyclerView();
    }

    /** 进入页面首次加载数据 */
    @Override
    protected void onStart() {
        super.onStart();
        mBGARefreshLayout.beginRefreshing();
        onBGARefreshLayoutBeginRefreshing(mBGARefreshLayout);
    }

    private void initView() {
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("待审核");
        img_back=(ImageView)findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.define_bga_refresh_with_load_recycler);
        //得到控件
        mBGARefreshLayout = (BGARefreshLayout) findViewById(R.id.define_bga_refresh_with_load);
        //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
    /**
     * 设置 BGARefreshLayout刷新和加载
     * */
    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new VerifyHolder(mContext , true , true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载更多...");
    }
    /** 设置RecyclerView的布局方式 */
    private void setRecyclerView(){
        //垂直listview显示方式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }
    /** 模拟请求网络数据 */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mListData.clear();
                    setData();
                    mBGARefreshLayout.endRefreshing();
                    break;
                case 1:
                    setData();
                    mBGARefreshLayout.endLoadingMore();
                    break;
                case 2:
                    mBGARefreshLayout.endLoadingMore();
                    break;
                default:
                    break;

            }
        }
    };
    /**
     * 添加假数据
     * */
    private void setData() {
        for(int i = 0 ; i < DATASIZE ; i++){
            VerifyList verifyList=new VerifyList();
            verifyList.setContent("第" + (ALLSUM * 10 + i) +"条数据\n29日的67路早上7:20左右行驶到龙山路上时一位盲人乘客，师傅提示让座，等他坐稳后再启动车辆。");
            verifyList.setTime("04月13日 15:20:"+i);
            verifyList.setInner_id(String.valueOf(i));
            mListData.add(verifyList);
        }
        if(ALLSUM == 0){
            setRecyclerCommadapter();
        }else{
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
    /** 数据填充 */
    private void setRecyclerCommadapter() {
        mRecyclerViewAdapter = new RecyclerViewAdapter(mContext , mListData);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        //点击事件
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent =new Intent();
                intent.setClass(VerifyActivity.this,VerifyDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                CustomToast.showMessage(mContext, "长按了第"+position+"条数据",
                        Toast.LENGTH_SHORT, CustomToast.CENTER);
            }
        });
    }
    /** 刷新 */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载更多...");
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        ALLSUM = 0;
        handler.sendEmptyMessageDelayed(0 , 2000);
    }
    /** 加载 */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if(ALLSUM == 2){
            /** 设置文字 **/
            mDefineBAGRefreshWithLoadView.updateLoadingMoreText("已经到底了");
            /** 隐藏图片 **/
            mDefineBAGRefreshWithLoadView.hideLoadingMoreImg();
            handler.sendEmptyMessageDelayed(2 , 2000);
            return true;
        }
        ALLSUM++;
        handler.sendEmptyMessageDelayed(1 , 2000);
        return true;
    }
}
