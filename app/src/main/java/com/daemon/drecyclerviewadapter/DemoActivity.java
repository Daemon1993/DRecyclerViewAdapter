package com.daemon.drecyclerviewadapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daemon.drecyclerviewadapterlib.DBaseRecyclerViewAdapter;
import com.daemon.drecyclerviewadapterlib.DBaseRecyclerViewHolder;
import com.daemon.drecyclerviewadapterlib.DRecyclerViewAdapter;
import com.daemon.drecyclerviewadapterlib.DRecyclerViewScrollListener;
import com.daemon.drecyclerviewadapterlib.DSpanSizeLookup;
import com.daemon.drecyclerviewadapterlib.DStaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    public static final String TYPE1 = "1";
    public static final String TYPE2 = "2";
    public static final String TYPE3 = "3";
    private RecyclerView rcv;
    private LinearLayoutManager linearLayoutManager;
    private List<String> lists = new ArrayList<String>();
    private DRecyclerViewAdapter dRecyclerViewAdapter;


    private boolean isLoading;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int old = msg.what;

            dRecyclerViewAdapter.notifyDataSetChanged();
            isLoading = false;

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        this.rcv = (RecyclerView) findViewById(R.id.rcv);


        String type = getIntent().getStringExtra("type");

        switch (type) {
            case TYPE1:
                showL();
                break;
            case TYPE2:

                showG();
                break;
            case TYPE3:

                showS();
                break;
        }


        rcv.addOnScrollListener(new DRecyclerViewScrollListener() {
            @Override
            public void onLoadNextPage(View view) {

                if (isLoading) {
                    return;
                }

                isLoading = true;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int old = lists.size();
                        SystemClock.sleep(2000);

                        for (int i = 0; i < 20; i++) {
                            lists.add("Daemon" + (old + i));
                        }

                        Message message = Message.obtain();
                        message.what = old;
                        handler.sendMessage(message);

                    }
                }).start();

            }
        });

    }

    private void showS() {
        lists.clear();
        for (int i = 0; i < 20; i++) {
            lists.add("123" + i);
        }


        MyAdapter myAdapter = new MyAdapter(lists, this, TYPE3);
        dRecyclerViewAdapter = new DRecyclerViewAdapter(myAdapter);

        DStaggeredGridLayoutManager dStaggeredGridLayoutManager = new DStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        dStaggeredGridLayoutManager.setSpanSizeLookup(new DSpanSizeLookup(dRecyclerViewAdapter, dStaggeredGridLayoutManager.getSpanCount()));
        rcv.setLayoutManager(dStaggeredGridLayoutManager);


        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_top, null);

        dRecyclerViewAdapter.addHeaderView(view);

        View view1 = layoutInflater.inflate(R.layout.item_foot, null);
        dRecyclerViewAdapter.addFooterView(view1);
        rcv.setAdapter(dRecyclerViewAdapter);

    }

    private void showG() {

        lists.clear();
        for (int i = 0; i < 20; i++) {
            lists.add("Daemon" + i);
        }

        MyAdapter myAdapter = new MyAdapter(lists, this, TYPE2);
        dRecyclerViewAdapter = new DRecyclerViewAdapter(myAdapter);

        GridLayoutManager dGridLayoutManager = new GridLayoutManager(this, 2);
        dGridLayoutManager.setSpanSizeLookup(new DSpanSizeLookup(dRecyclerViewAdapter, 2));
        rcv.setLayoutManager(dGridLayoutManager);

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_top, null);
        View view2 = layoutInflater.inflate(R.layout.item_top, null);
        View view3 = layoutInflater.inflate(R.layout.item_top, null);

        dRecyclerViewAdapter.addHeaderView(view);
        dRecyclerViewAdapter.addHeaderView(view2);
        dRecyclerViewAdapter.addHeaderView(view3);

        View view1 = layoutInflater.inflate(R.layout.item_foot, null);

        dRecyclerViewAdapter.addFooterView(view1);

        rcv.setAdapter(dRecyclerViewAdapter);
    }

    private void showL() {


        lists.clear();
        for (int i = 0; i < 20; i++) {
            lists.add("123" + i);
        }

        MyAdapter myAdapter = new MyAdapter(lists, this, TYPE1);

        dRecyclerViewAdapter = new DRecyclerViewAdapter(myAdapter);

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = View.inflate(this,R.layout.item_top,null);

        dRecyclerViewAdapter.addHeaderView(view);

        View view1 = layoutInflater.inflate(R.layout.item_foot, null);
        dRecyclerViewAdapter.addFooterView(view1);

        rcv.setAdapter(dRecyclerViewAdapter);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rcv.setLayoutManager(linearLayoutManager);

    }


    class MyAdapter extends DBaseRecyclerViewAdapter<String> {

        private String type;

        public MyAdapter(List<String> mDatas, Context mContext, String type1) {
            super(mDatas, mContext);
            this.type = type1;
        }

        @Override
        public DBaseRecyclerViewHolder onCreateViewHolder1(ViewGroup parent, int viewType, DBaseRecyclerViewAdapter dBaseRecyclerViewAdapter) {
            return new MyViewHolder(parent, R.layout.item_icon, this, type);
        }
    }

    class MyViewHolder extends DBaseRecyclerViewHolder<String> implements View.OnClickListener {
        private String type;
        public CardView cardview;
        TextView tv_show;
        int k = 10;

        public MyViewHolder(View itemView, int item_icon, DBaseRecyclerViewAdapter mDBaseRecyclerViewAdapter, String type) {
            super(itemView, mDBaseRecyclerViewAdapter);
        }

        public MyViewHolder(ViewGroup parent, @LayoutRes int res, DBaseRecyclerViewAdapter mDBaseRecyclerViewAdapter, String type) {
            super(parent, res, mDBaseRecyclerViewAdapter);
            tv_show = $(R.id.tv_show);

            this.type = type;
            itemView.setOnClickListener(this);

        }

        @Override
        public void setData(String data, int position) {
            super.setData(data, position);

            if (DemoActivity.TYPE3.equals(type)) {
                cardview = $(R.id.cv);
                ViewGroup.LayoutParams layoutParams = cardview.getLayoutParams();

                layoutParams.height = (position + 100);

                cardview.setLayoutParams(layoutParams);
            } else {

            }
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(DemoActivity.this, "position " + getAdapterItemPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}
