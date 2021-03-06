package com.example.jizhangdemo.journalAccount;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jizhangdemo.R;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.layout.ExpandableLayout;

import java.util.Collection;

public class ExpandableListChildAdapter extends BaseRecyclerAdapter<LineElementChild> {
    private RecyclerView mRecyclerView;
    private Collection<LineElementChild> mDate;
    private Context mContext;

    public ExpandableListChildAdapter(RecyclerView recyclerView, Collection<LineElementChild> data, Context context) {
        super(data);
        mDate = data;
        mContext = context;
        mRecyclerView = recyclerView;
    }

    /**
     * 适配的布局
     *
     * @param viewType
     * @return
     */
    @Override
    protected int getItemLayoutId(int viewType) { return R.layout.adapter_expandable_list_child_item; }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position 索引
     * @param item     列表项
     */
    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, LineElementChild item) {
        ExpandableLayout expandableLayout = holder.findViewById(R.id.expandable_layout);
        AppCompatImageView ivIndicator = holder.findViewById(R.id.iv_indicator);
        expandableLayout.setInterpolator(new OvershootInterpolator());
        expandableLayout.setOnExpansionChangedListener((expansion, state) -> {
            if (mRecyclerView != null && state == ExpandableLayout.State.EXPANDING) {
                mRecyclerView.smoothScrollToPosition(position);
            }
            if (ivIndicator != null) {
                ivIndicator.setRotation(expansion * 90);
            }
        });

        boolean isSelected = position == mSelectPosition;
        expandableLayout.setExpanded(isSelected, false);

        holder.select(R.id.fl_title, isSelected);
        holder.text(R.id.tv_title,item.getTitle());
        if (!item.getSum().equals("")){
            holder.text(R.id.tv_sum,"结余");
            holder.text(R.id.tv_num_sum,item.getSum());
        }
        if (!item.getIn().equals("")){
            holder.text(R.id.tv_in,item.getIn());
        }
        if (!item.getOut().equals("")){
            holder.text(R.id.tv_out,item.getOut());
        }
        LinearLayout ll = (LinearLayout) holder.findView(R.id.ll);
        for(View b:item.getBill()){
            if(b.getParent()!=null){
                ((ViewGroup) b.getParent()).removeView(b);
            }
            ll.addView(b);
        }
        holder.click(R.id.fl_title, new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                onClickItem(v, expandableLayout, position);
            }
        });
    }

    private void onClickItem(View view, final ExpandableLayout expandableLayout, final int position) {
        RecyclerViewHolder holder = (RecyclerViewHolder) mRecyclerView.findViewHolderForAdapterPosition(mSelectPosition);
        if (holder != null) {
            holder.select(R.id.fl_title, false);
            ((ExpandableLayout) holder.findViewById(R.id.expandable_layout)).collapse();
        }

        if (position == mSelectPosition) {
            mSelectPosition = -1;
        } else {
            view.setSelected(true);
            expandableLayout.expand();
            mSelectPosition = position;
        }
    }
}
