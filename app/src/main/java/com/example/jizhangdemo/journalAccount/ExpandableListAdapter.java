package com.example.jizhangdemo.journalAccount;

import android.content.Context;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.fragment.JournalAccountActivity;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.layout.ExpandableLayout;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ExpandableListAdapter extends BaseRecyclerAdapter<LineElement> {

    private RecyclerView mRecyclerView;
    private Collection<LineElement> mDate;
    private JournalAccountActivity journalAccountActivity;
    private Context mContext;

    public ExpandableListAdapter(RecyclerView recyclerView, Collection<LineElement> data, Context context) {
        super(data);
        mDate = data;
        mContext = context;
        mRecyclerView = recyclerView;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_expandable_list_item;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, LineElement item) {
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

        holder.select(R.id.ll_title, isSelected);
        holder.text(R.id.tv_Title,item.getTitle());
        holder.text(R.id.tvBalance,item.getSum());
        holder.text(R.id.tvIncome,item.getIn());
        holder.text(R.id.tvCost,item.getOut());
        RecyclerView recyclerView = (RecyclerView) holder.findView(R.id.recycler_child_view);
        List<LineElementChild> llec = new LinkedList<>();
//        for (LineElement le:mDate){
//            if (le != null && !le.getBill().isEmpty()){
//                mDate.remove(le);
//            }
//            llec.addAll(le.getBill());
//        }
        if (item.getBill()!=null && !item.getBill().isEmpty()){
            llec = item.getBill();
        }
        WidgetUtils.initRecyclerView(recyclerView);
        recyclerView.setAdapter(new ExpandableListChildAdapter(recyclerView, llec,mContext));
        holder.click(R.id.ll_title, new View.OnClickListener() {
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
            holder.select(R.id.ll_title, false);
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
