# DRecyclerViewAdapter

针对RecyclerView的Adapter 
添加了如同ListView的 addHeadView  addFootView  滑动底部加载更多等功能


同时为了减少每次都要自己实现RecyclerView的Adapter写很多代码  也有了下面两个Base类 不使用也不影响

- Gradle 依赖 

		compile 'com.daemon.library:drecyclerviewadapterlib:1.0.1'	

- DBaseRecyclerViewAdapter  是项目中使用Adapter的基类  替换原本继承RecyclerView.Adapter 的写法
- 与DBaseRecyclerViewHolder 配合使用      模拟EasyRecyclerView 写法


- 项目中自定义的Adapater 继承DBaseRecyclerViewAdapter

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


- ViewHolder继承 DBaseRecyclerViewHolder使用

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

最后 用DRecyclerViewAdapter(Mydapater) 装饰



- 具体相关 操作实现 

1  LinearLayoutManager 使用  

        MyAdapter myAdapter = new MyAdapter(lists, this);
        
        //DRecyclerViewAdapter装饰
        dRecyclerViewAdapter = new DRecyclerViewAdapter(myAdapter);

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = View.inflate(this,R.layout.item_top,null);

        //添加都和尾部
        dRecyclerViewAdapter.addHeaderView(view);
        View view1 = layoutInflater.inflate(R.layout.item_foot, null);
        dRecyclerViewAdapter.addFooterView(view1);
        

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rcv.setLayoutManager(linearLayoutManager);
		rcv.setAdapter(dRecyclerViewAdapter);
		
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
        
        
- 效果图

![GIF.gif](https://github.com/Daemon1993/DRecyclerViewAdapter/blob/master/GIF.gif)

详细情况使查看Demo

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
