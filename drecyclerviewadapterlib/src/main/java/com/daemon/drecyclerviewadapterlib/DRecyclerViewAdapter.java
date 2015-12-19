package com.daemon.drecyclerviewadapterlib;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 实现AddHead AddFoot 下滑加载更多的RecyclerView
 * Created by h2h on 2015/11/10.
 */
public class DRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int LayoutManager_Linear = 0;
    public static final int LayoutManager_Grid = 1;
    public static final int LayoutManager_Staggered = 2;

    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER_VIEW = Integer.MIN_VALUE + 100;



    private LinearLayoutManager linearLayoutManager;

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;

    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    private boolean isDefaultFoot = false;

    private TextView tv_text;

    private ProgressBar pb_loading;

    private String data_over = "没有更多了...";
    private String load_more = "加载更多...";


    public  DRecyclerViewAdapter(DBaseRecyclerViewAdapter myAdapter, RecyclerView rcv, int layoutManager) {
            this(myAdapter,rcv,layoutManager,0);
    }

    public DRecyclerViewAdapter(DBaseRecyclerViewAdapter myAdapter, RecyclerView rcv, int layoutManager, int spanSize) {

        if(layoutManager!=LayoutManager_Linear && spanSize==0){
            throw new RuntimeException("SpanSize must not 0");
        }

        if (myAdapter != null) {
            if (!(myAdapter instanceof RecyclerView.Adapter))
                throw new RuntimeException("your adapter must be a RecyclerView.Adapter");
        }

        switch (layoutManager) {
            case LayoutManager_Linear:
                linearLayoutManager = new LinearLayoutManager(rcv.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rcv.setLayoutManager(linearLayoutManager);

                break;
            case LayoutManager_Grid:

                GridLayoutManager dGridLayoutManager = new GridLayoutManager(rcv.getContext(), spanSize);
                dGridLayoutManager.setSpanSizeLookup(new DSpanSizeLookup(this, dGridLayoutManager.getSpanCount()));
                rcv.setLayoutManager(dGridLayoutManager);

                break;
            case LayoutManager_Staggered:

                DStaggeredGridLayoutManager dStaggeredGridLayoutManager =
                        new DStaggeredGridLayoutManager(spanSize, StaggeredGridLayoutManager.VERTICAL);
                dStaggeredGridLayoutManager.
                        setSpanSizeLookup(new DSpanSizeLookup(this, dStaggeredGridLayoutManager.getSpanCount()));


                rcv.setLayoutManager(dStaggeredGridLayoutManager);

                break;
        }

        this.mInnerAdapter = myAdapter;
        myAdapter.setmDRecyclerViewAdapter(this);

    }

//    private void setAdapter(DBaseRecyclerViewAdapter myAdapter) {
//
//
//        if (myAdapter != null) {
//            if (!(myAdapter instanceof RecyclerView.Adapter))
//                throw new RuntimeException("your adapter must be a RecyclerView.Adapter");
//        }
//
//        this.mInnerAdapter = myAdapter;
//        myAdapter.setmDRecyclerViewAdapter(this);
//
//
//    }


    public RecyclerView.Adapter getInnerAdapter() {
        return mInnerAdapter;
    }

    /**
     * addHead to RecyclerView
     *
     * @param header
     */
    public void addHeaderView(View header) {
        if (header == null) {
            throw new IllegalArgumentException("header is null");
        }
        mHeaderViews.add(header);
    }

    /**
     * addFoot to RecyclerView
     *
     * @param footer
     */
    public void addFooterView(View footer) {

        if (footer == null) {
            throw new IllegalArgumentException("footer is null");
        }

        //如果设置底部加载更多 不允许在在底部添加
        if (mFooterViews != null && mFooterViews.size() > 0) {
            return;
        }

        mFooterViews.add(footer);
    }


    /**
     * 设置 底部加载更多显示的View
     *
     * @param loamoreView
     */
    public void setLoadMoreView(View loamoreView) {
        if (loamoreView == null) {
            throw new IllegalArgumentException("footer is null");
        }

        //如果设置 底部加载更多 就清楚原有的foot集合
        mFooterViews.clear();

        mFooterViews.add(mFooterViews.size(), loamoreView);

        isDefaultFoot = false;
    }

    /**
     * 设置默认的加载更多
     */
    public void setDefaultLoadMoreView(Context context) {

        if (mFooterViews != null && mFooterViews.size() > 0) {
            return;
        }

        isDefaultFoot = true;

        View view = View.inflate(context, R.layout.item_loadmore, null);
        tv_text = (TextView) view.findViewById(R.id.tv_text);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);

        mFooterViews.add(mFooterViews.size(), view);
    }


    /**
     * 底部数据加载完毕 没有更多 改变View 的显示
     */
    public void loadMoreOver() {
        if (!isDefaultFoot) {
            return;
        }

        //默认View的时候 加载完毕后UI改变
        tv_text.setText(data_over);
        pb_loading.setVisibility(View.GONE);
    }

    /**
     * 比如下拉刷新后 希望加载更多复原
     */
    public void setLoadMoreInit() {
        tv_text.setText(load_more);
        pb_loading.setVisibility(View.VISIBLE);
    }

    /**
     * 加载中的文本
     *
     * @param text
     */
    public void setLoadMoreText(String text) {
        load_more = text;
    }

    /**
     * 加载完毕 文本
     *
     * @param text
     */
    public void setLoadOverText(String text) {
        data_over = text;
    }

    /**
     * 返回第一个FoView
     *
     * @return
     */
    public View getFooterView() {
        return getFooterViewsCount() > 0 ? mFooterViews.get(0) : null;
    }

    /**
     * 返回第一个HeaderView
     *
     * @return
     */
    public View getHeaderView() {
        return getHeaderViewsCount() > 0 ? mHeaderViews.get(0) : null;
    }

    public void removeHeaderView(View view) {
        mHeaderViews.remove(view);
    }

    public void removeFooterView(View view) {
        mFooterViews.remove(view);
    }

    public boolean isHeader(int position) {
        return getHeaderViewsCount() > 0 && position < getHeaderViewsCount();
    }

    public boolean isTopHeader(int position) {
        return getHeaderViewsCount() > 0 && position == 0;
    }


    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - 1;
        return getFooterViewsCount() > 0 && position > lastPosition - getFooterViewsCount() && position <= lastPosition;
    }

    public boolean isLastFooter(int position) {
        int lastPosition = getItemCount() - 1;
        return getFooterViewsCount() > 0 && position == lastPosition;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int headerViewsCountCount = getHeaderViewsCount();
        if (viewType < TYPE_HEADER_VIEW + headerViewsCountCount) {
            return new DViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
        } else if (viewType >= TYPE_FOOTER_VIEW && viewType < Integer.MAX_VALUE / 2) {
            return new DViewHolder(mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
        } else {
            return mInnerAdapter.onCreateViewHolder(parent, viewType - Integer.MAX_VALUE / 2);
        }

    }


    @Override
    public int getItemViewType(int position) {

        int innerCount = mInnerAdapter.getItemCount();
        int headerViewsCountCount = getHeaderViewsCount();

        if (position < headerViewsCountCount) {
            return TYPE_HEADER_VIEW + position;
        } else if (headerViewsCountCount <= position && position < headerViewsCountCount + innerCount) {

            int innerItemViewType = mInnerAdapter.getItemViewType(position - headerViewsCountCount);
            if (innerItemViewType >= Integer.MAX_VALUE / 2) {
                throw new IllegalArgumentException("your adapter's return value of getViewTypeCount() must < Integer.MAX_VALUE / 2");
            }
            return innerItemViewType + Integer.MAX_VALUE / 2;

        } else {
            return TYPE_FOOTER_VIEW + position - headerViewsCountCount - innerCount;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int headerViewsCountCount = getHeaderViewsCount();

        //是数据item 就调用原本的
        if (position >= headerViewsCountCount && position < headerViewsCountCount + mInnerAdapter.getItemCount()) {
            mInnerAdapter.onBindViewHolder(holder, position - headerViewsCountCount);
        } else {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }

    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + getFooterViewsCount() + mInnerAdapter.getItemCount();
    }


    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }


    public static class DViewHolder extends RecyclerView.ViewHolder {

        public DViewHolder(View itemView) {
            super(itemView);
        }
    }


}

