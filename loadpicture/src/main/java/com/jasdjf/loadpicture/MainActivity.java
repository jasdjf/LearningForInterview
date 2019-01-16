package com.jasdjf.loadpicture;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private LoadPictureAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isUserScoll = false;
    public static OkHttpClient client = new OkHttpClient();
    private int pictureIndex = 0;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fresco.initialize(MainActivity.this);
        setContentView(R.layout.activity_main);
        init();
    }

    Handler m_Handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case 1:
                    int position = adapter.getItemCount();
                    adapter.notifyItemRangeChanged(position,20);//只刷新增加的部分，不刷新全部
                    break;
            }
            return false;
        }
    });

    private void init() {
        recyclerView = findViewById(R.id.recycler_view);
        //设置布局管理器为2列，纵向
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //mLayoutManager = new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL,false);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        adapter = new LoadPictureAdapter(this, list,metrics.widthPixels);
        ((StaggeredGridLayoutManager)mLayoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        //mLayoutManager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(mLayoutManager);
        PictureItemDecoration decoration = new PictureItemDecoration(10);
        recyclerView.addItemDecoration(decoration);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        Log.e("jasdjf","AutoMessure="+mLayoutManager.isAutoMeasureEnabled());
        Log.e("jasdjf","hasFixedSize="+recyclerView.hasFixedSize());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //Log.e("jasdjf","AAAAAAAAAAAAAA");
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    isUserScoll = true;
                    StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager)recyclerView.getLayoutManager();
                    int[] into = manager.findLastCompletelyVisibleItemPositions(null);
                    int lastPosition = getLastPosition(into);
                    if((lastPosition+1)==adapter.getItemCount()){
                        Log.e("jasdjf","can load more");
                        buildData("气质美女",pictureIndex);
                    }
                } else {
                    isUserScoll = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Log.e("jasdjf","BBBBBBBBBBBBBBBBBBBBBBBBBB");
                /*if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
                    StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager)recyclerView.getLayoutManager();
                    int[] into = manager.findLastCompletelyVisibleItemPositions(null);
                    //Log.e("jasdjf","position0="+into[0]+",position1="+into[1]);
                    //Log.e("jasdjf","count="+adapter.getItemCount());
                    if(isUserScoll){
                        *//*int lastPosition = getLastPosition(into);
                        if((lastPosition+1)==adapter.getItemCount()){
                            Log.e("jasdjf","can load more");
                            String[] imgUrs = {"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4128497002,1012426327&fm=11&gp=0.jpg",
                                    "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2804310489,3263190856&fm=27&gp=0.jpg",
                                    "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2246315156,1662169434&fm=27&gp=0.jpg",
                                    "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1628832912,3219060281&fm=27&gp=0.jpg",
                                    "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2544269114,2104066965&fm=27&gp=0.jpg",
                                    "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2210270529,1470505196&fm=11&gp=0.jpg",
                                    "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3467170169,1132186902&fm=200&gp=0.jpg",
                                    "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1977804817,1381775671&fm=200&gp=0.jpg",
                                    "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2610705528,1626175376&fm=200&gp=0.jpg",
                                    "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3423784967,2736259733&fm=200&gp=0.jpg",
                                    "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3668554427,3084213941&fm=200&gp=0.jpg",
                                    "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=190337165,4096841389&fm=200&gp=0.jpg"
                            };
                            //int position = (adapter.getItemCount()-1)<0 ? 0 : (adapter.getItemCount()-1);
                            int position = adapter.getItemCount();
                            for(int i=0;i<imgUrs.length;i++) {
                                PictureBean p = new PictureBean();
                                p.pictureUrl = imgUrs[i];
                                adapter.getDataList().add(p);
                            }
                            //adapter.notifyDataSetChanged();
                            adapter.notifyItemRangeChanged(position,imgUrs.length);//只刷新增加的部分，不刷新全部
                        }*//*
                        //int position = adapter.getItemCount();
                        //adapter.getDataList().addAll(list);
                        //adapter.notifyItemRangeChanged(position,20);//只刷新增加的部分，不刷新全部
                        int lastPosition = getLastPosition(into);
                        if((lastPosition+1)==adapter.getItemCount()){
                            Log.e("jasdjf","can load more");
                            buildData("气质美女",pictureIndex);
                        }
                    }
                }*/
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("jasdjf","refresh");
                adapter.notifyDataSetChanged();
                m_Handler.sendEmptyMessageDelayed(0,1000);
            }
        });
        buildData("气质美女",pictureIndex);
    }

    private int getLastPosition(int[] into) {
        int tmp = 0;
        for (int anInto : into) {
            if (tmp < anInto) {
                tmp = anInto;
            }
        }
        return tmp;
    }

    //生成6个明星数据，这些Url地址都来源于网络
    private void buildData(String word,int index) {
        //https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&sf=1&fmq=&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&fm=index&pos=history&word=
        //Request request = new Request().newBuilder().get().url("https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&sf=1&fmq=&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&fm=index&pos=history&word=");
        String url = "https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word="+word+"&pn="+index+"&ct=&ic=0&lm=-1&width=0&height=0";
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("jasdjf","Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strHTML = response.body().string();
                String[] strs = strHTML.split("\"");
                int pictureCount = 0;
                for (int i = 0; i < strs.length; i++) {
                    if(strs[i].toLowerCase().endsWith(".jpg")){
                        if(strs[i-2].equals("thumbURL")){
                            //Log.e("jasdjf",strs[i]);
                            adapter.getDataList().add(strs[i]);
                            pictureCount++;
                            if(pictureCount==20){
                                break;
                            }
                        }
                    }
                }
                pictureIndex += 20;
                m_Handler.sendEmptyMessage(1);
            }
        });
        /*String[] imgUrs = {"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4128497002,1012426327&fm=11&gp=0.jpg",
                "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2804310489,3263190856&fm=27&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2246315156,1662169434&fm=27&gp=0.jpg",
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1628832912,3219060281&fm=27&gp=0.jpg",
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2544269114,2104066965&fm=27&gp=0.jpg",
                "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2210270529,1470505196&fm=11&gp=0.jpg",
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3467170169,1132186902&fm=200&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1977804817,1381775671&fm=200&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2610705528,1626175376&fm=200&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3423784967,2736259733&fm=200&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3668554427,3084213941&fm=200&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=190337165,4096841389&fm=200&gp=0.jpg"
        };

        //List<String> list = new ArrayList<>();
        for(int i=0;i<imgUrs.length;i++) {
            adapter.getDataList().add(imgUrs[i]);
        }
        m_Handler.sendEmptyMessage(2);*/
        //return list;
    }
}
