# DRecyclerViewAdapter

- DBaseRecyclerViewAdapter   ʹ����Adapter�Ļ���  ����ÿ�μ̳�RecyclerView.Adapter���µĴ�����
��DBaseRecyclerViewHolder ���ʹ��

      class MyAdapter extends DBaseRecyclerViewAdapter<String> {
        public MyAdapter(List<String> mDatas, Context mContext, String type1) {
            super(mDatas, mContext);
        }
        @Override
        public DBaseRecyclerViewHolder onCreateViewHolder1(ViewGroup parent, int viewType, DBaseRecyclerViewAdapter dBaseRecyclerViewAdapter) {
            return new MyViewHolder(parent, R.layout.item_icon, this);
        }
        }

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



- Ч��ͼ
![GIF.gif](http://upload-images.jianshu.io/upload_images/831873-2379510019629ef8.gif?imageMogr2/auto-orient/strip)

- LinearLayoutManager ʹ��

        MyAdapter myAdapter = new MyAdapter(lists, this);
        dRecyclerViewAdapter = new DRecyclerViewAdapter(myAdapter);

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = View.inflate(this,R.layout.item_top,null);

        //��Ӷ���β��
        dRecyclerViewAdapter.addHeaderView(view);
        View view1 = layoutInflater.inflate(R.layout.item_foot, null);
        dRecyclerViewAdapter.addFooterView(view1);
        rcv.setAdapter(dRecyclerViewAdapter);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rcv.setLayoutManager(linearLayoutManager);


- GridLayoutManager ʹ��
  
        MyAdapter myAdapter = new MyAdapter(lists, this);
        dRecyclerViewAdapter = new DRecyclerViewAdapter(myAdapter);

        GridLayoutManager dGridLayoutManager = new GridLayoutManager(this, 2);
        //�������� �Զ���SpanSizeLookup������ʾ�б�
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

-DStaggeredGridLayoutManager ʹ�� �Զ���StaggeredGridLayoutManager

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