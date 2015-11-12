# DRecyclerViewAdapter

- DBaseRecyclerViewAdapter   是项目中使用Adapter的基类  替换原本的RecyclerView.Adapter导致的代码量 与DBaseRecyclerViewHolder 配合使用     

- 也可以不替换不影响 DRecyclerViewAdapter的使用

- 项目中自定义的Adapter
        class MyAdapter extends DBaseRecyclerViewAdapter<String> {
        public MyAdapter(List<String> mDatas, Context mContext, String type1) {
            super(mDatas, mContext);
        }
        @Override
        public DBaseRecyclerViewHolder onCreateViewHolder1(ViewGroup parent, 
        int viewType, DBaseRecyclerViewAdapter dBaseRecyclerViewAdapter) {
            return new MyViewHolder(parent, R.layout.item_icon, this);
        }
        }


- ViewHolder 使用
        class MyViewHolder extends DBaseRecyclerViewHolder<String> {
        public MyViewHolder(View itemView, int item_icon, DBaseRecyclerViewAdapter mDBaseRecyclerViewAdapter) {
            super(itemView, mDBaseRecyclerViewAdapter);
        }
        public MyViewHolder(ViewGroup parent, @LayoutRes int res, DBaseRecyclerViewAdapter mDBaseRecyclerViewAdapter) {
            super(parent, res, mDBaseRecyclerViewAdapter);
        }
        @Override
        public void setData(String data, int position) {
            super.setData(data, position);
        }
        }

//lists----List<String> 



- 效果图

![GIF.gif](https://github.com/Daemon1993/DRecyclerViewAdapter/blob/master/GIF.gif)


- 相关使用操作

1  LinearLayoutManager 使用  

        MyAdapter myAdapter = new MyAdapter(lists, this);
        dRecyclerViewAdapter = new DRecyclerViewAdapter(myAdapter);

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = View.inflate(this,R.layout.item_top,null);

        //添加都和尾部
        dRecyclerViewAdapter.addHeaderView(view);
        View view1 = layoutInflater.inflate(R.layout.item_foot, null);
        dRecyclerViewAdapter.addFooterView(view1);
        rcv.setAdapter(dRecyclerViewAdapter);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rcv.setLayoutManager(linearLayoutManager);


 2 GridLayoutManager 使用
  
        MyAdapter myAdapter = new MyAdapter(lists, this);
        dRecyclerViewAdapter = new DRecyclerViewAdapter(myAdapter);

        GridLayoutManager dGridLayoutManager = new GridLayoutManager(this, 2);
        //设置列数 自定义SpanSizeLookup控制显示列表
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

3 DStaggeredGridLayoutManager 使用 自定义StaggeredGridLayoutManager

        MyAdapter myAdapter = new MyAdapter(lists, this, TYPE3);
        dRecyclerViewAdapter = new DRecyclerViewAdapter(myAdapter);

        DStaggeredGridLayoutManager dStaggeredGridLayoutManager 
        =new DStaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        dStaggeredGridLayoutManager.setSpanSizeLookup(
        new DSpanSizeLookup(dRecyclerViewAdapter, dStaggeredGridLayoutManager.getSpanCount()));

        rcv.setLayoutManager(dStaggeredGridLayoutManager);

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_top, null);
        dRecyclerViewAdapter.addHeaderView(view);

        View view1 = layoutInflater.inflate(R.layout.item_foot, null);
        dRecyclerViewAdapter.addFooterView(view1);
        rcv.setAdapter(dRecyclerViewAdapter);


#License

    Copyright 2015 Daemon
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.