package com.longj.androids23;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.longj.androids23.Util.JLAdapter_ViewHolder.JLAdapter;
import com.longj.androids23.Util.JLAdapter_ViewHolder.JLViewHolder;
import com.longj.androids23.Util.OkHttpUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UseGsonActivity extends AppCompatActivity {

    private static ListView listView;
    final Gson gson = new Gson();
    private List<Notice> notis = new ArrayList<Notice>();
    int curentPage = 1;
    boolean isClearAllObject = false;
    RefreshLayout refreshLayout = null;


    public Handler uiHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x110:
//                    listView.setAdapter(new NoticeListViewAdt(UseGsonActivity.this, notis));


                    listView.setAdapter(new JLAdapter<Notice>(UseGsonActivity.this, notis, R.layout.list_view_item) {
                        @Override
                        protected int type(int position) {
                            return 0;
                        }

                        @Override
                        protected void convertView(View itemV, Notice obj, int position, int type) {
//                            TextView tv = (TextView) item.findViewById(R.id.textView);//使用常用方法
                            TextView tv = JLViewHolder.get(itemV, R.id.textView);//使用通用ViewHolder
                            tv.setText(obj.Title +" - "+ obj.CreateTime);
                        }
                    });

                    if (isClearAllObject == true){
                        refreshLayout.finishRefresh();
                    }else {
                        refreshLayout.finishLoadmore();
                        listView.setSelection(notis.size() / 2);
                    }


                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_gson);

        Button getJsonBtn = (Button) findViewById(R.id.get_home_gglist_btn);
        listView = (ListView) findViewById(R.id.gg_list_view);

        int[] nums = gson.fromJson("[1,2,3,4,5]", int[].class);

//        http://api.winowe.com/Server/QueryObjectOut?jkid=301&pageindex=1&pagesize=20&userid=3204&version=1.8.1.170804

        getJsonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curentPage >= 4) {
                    curentPage = 1;
                    isClearAllObject = true;
                }else {
                    isClearAllObject = false;
                }
                getNoticeList();
                curentPage ++;
            }
        });

        refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        //设置 Header和Footer的样式
//        refreshLayout.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(true));//Material样式
//
//        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Translate));//球脉冲

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                curentPage = 1;
                isClearAllObject = true;
                getNoticeList();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);

                curentPage ++;
                isClearAllObject = false;
                getNoticeList();
            }
        });
        refreshLayout.autoRefresh();

    }

    public void getNoticeList() {


        Map<String,String> parms = new HashMap<String, String>() {
            {
                put("jkid","301");
                put("pageindex",String.valueOf(curentPage));
                put("pagesize","20");
                put("userid","3204");
                put("version","1.8.1.170804");
            }
        };

        OkHttpUtil.ok_AsynPostSend("", parms, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String allJsonStr = response.body().string();
                JSONObject allDic = null;
                try {
                    allDic = new JSONObject(allJsonStr);
                    String status = (String) allDic.get("status");
                    if (status.equals("0")) { //请求成功
                        String data = allDic.get("data").toString();

                        List<Notice> map = gson.fromJson(data, new TypeToken<List<Notice>>(){}.getType());

//                                for (Notice obj : map) {
//                                    String title = obj.Title;
//                                }

                        if (isClearAllObject == true) {
                            notis.clear();
                        }
                        notis.addAll(notis.size(), map);

                        uiHandler.sendEmptyMessage(0x110);
                    }else {

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        });
    }


    public class NoticeListViewAdt extends BaseAdapter {

        private List<Notice> strs;
        private LayoutInflater inflater;
        private Context context;
        private NoticeListViewAdt(Context context,List<Notice> nameArr) {
            this.strs=nameArr;
            this.context=context;
            this.inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return strs.size();
        }

        @Override
        public Object getItem(int position) {
            return strs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            ViewHolder viewHolder;
            if (convertView==null) {
                viewHolder=new ViewHolder();
                convertView=inflater.inflate(R.layout.list_view_item,null);
                viewHolder.tv= (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            Notice notice = strs.get(position);

            viewHolder.tv.setText(notice.Title +" - "+ notice.CreateTime);
            return convertView;
        }

        //缓存池 不同样式的listViewItem需要不同Holder来缓存 每一种不同的cell需要不同的holder来缓存
        class ViewHolder{
            private TextView tv;
        }
    }

    class Notice {
        public String ID;
        public String Title;
        public String CreateTime;
//    public OtherClass otsa;

        public Notice() {
            //无参构造函数
        }

//    public class OtherClass {
//        public String AddName;
//        public String AddTime;
//    }


        public void setID(String ID) {
            this.ID = ID;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }
    }

}



