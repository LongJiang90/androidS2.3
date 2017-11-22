package com.longj.androids23;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
//        第一种，在Activity所在类中
//        this.getLayoutInflater().inflater(R.layout.布局文件名,null);
//        第二种，在非Activity所在类中
//        Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE).inflater(R.layout.布局文件名,null);

    //实例表单 该页面下包含了所有学习过程中的实例
    //图片浏览器、
    public String[] names = new String[]{};
    ListView listV;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view= this.getLayoutInflater().inflate(R.layout.activity_main,null);
        setContentView(view);


        listV = (ListView)findViewById(R.id.all_list_view);
        //为listView添加headerView
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.image4);
        //设置图片显示方式
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //设置图片大小
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400);
        imageView.setLayoutParams(lp);
        listV.addHeaderView(imageView);

        final TextView title_textV = (TextView) findViewById(R.id.title_textV);
        //为ListView设置监听函数
        listV.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                float scrollY = getListViewScrollY();
                //x:当前横向滑动距离 y：当前纵向滑动距离 oldX：之前横向滑动距离 oldY：之前纵向滑动距离
                if (scrollY <= 0) {
                    title_textV.setBackgroundColor(Color.argb(0, 144, 151, 155));

                }else if (scrollY>0 && scrollY<=400) {
                    float scale = scrollY/400;
                    float alp = 255 * scale;
                    title_textV.setTextColor(Color.argb((int) alp, 255,255,255));
                    title_textV.setBackgroundColor(Color.argb( (int) alp , 144, 151, 155));
                }else {    //滑动到banner下面设置普通颜色
                    title_textV.setBackgroundColor(Color.argb( 255, 144,151,166));
                }
            }
        });


        String[] nameArr = new String[]{"图片浏览器","手势检测","常用布局","站长首页","刮刮乐"
                , "高级控件使用", "动态改变布局","各种动画","文件、资源操作","音视频","网络简单请求"
                , "多线程编程", "传感器运用","GPS应用、高德地图","Fresco图片加载库使用"
                , "item1","item2","item3","item4","item5","item6","item7","item8"};

        names = nameArr;
        int[] idArr = new int[]{R.id.textView};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, this.getItem(nameArr), R.layout.list_view_item, nameArr, idArr);

        listV.setAdapter(new ListViewAdapter(this,nameArr));

        //处理item的点击事件 需要跳转传值：intent.putExtra("extra_data", data);//相当于键值对 //
        //在被传的vc里面接收Intent intent = getIntent();String data = intent.getStringExtra("extra_data");
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, PhotoBrowserActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, GestureActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, LayoutActivity.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(MainActivity.this, GgkAndChangeLayoutActivity.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(MainActivity.this, HightControlsActivity.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(MainActivity.this, ChangeLayoutActivity.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(MainActivity.this, AnimationActivity.class);
                        startActivity(intent8);
                        break;
                    case 9:
                        Intent intent9 = new Intent(MainActivity.this, FileManagerActivity.class);
                        startActivity(intent9);
                        break;
                    case 10:
                        Intent intent10 = new Intent(MainActivity.this, AudioAndVideoActivity.class);
                        startActivity(intent10);
                        break;
                    case 11:
                        Intent intent11 = new Intent(MainActivity.this, InternetActivity.class);
                        startActivity(intent11);
                        break;
                    case 12:
                        Intent intent12 = new Intent(MainActivity.this, MultiThreadingActivity.class);
                        startActivity(intent12);
                        break;
                    case 13:
                        Intent intent13 = new Intent(MainActivity.this, SensorActivity.class);
                        startActivity(intent13);
                        break;
                    case 14:
                        Intent intent14 = new Intent(MainActivity.this, GPSAndGaoDeMapActivity.class);
                        startActivity(intent14);
                        break;

                    default:
                        Toast.makeText(view.getContext(), "开发中...",3 ).show();


                        break;
                }

            }
        });


//        final 不可变的参数



    }

    public int getListViewScrollY() {
        View v = listV.getChildAt(0);//获取listview的最底层
        if (v == null) { return 0; }
        int firstPosition = listV.getFirstVisiblePosition();
        int top = v.getTop();
        return -top + firstPosition * v.getHeight();

    }

    public ArrayList<HashMap<String, Object>> getItem(String[] itemArr) {
        ArrayList<HashMap<String,Object>> item = new ArrayList<HashMap<String,Object>>();
        for (int i=0;i<itemArr.length;i++) {
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("textView",itemArr[i]);
            item.add(map);
        }
        return item;

    }

    //相当于列表的协议
    public class ListViewAdapter extends BaseAdapter {

        private String[] strs;
        private LayoutInflater inflater;
        private Context context;
        private ListViewAdapter(Context context,String[] nameArr) {
            this.strs=nameArr;
            this.context=context;
            this.inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return strs.length;
        }

        @Override
        public Object getItem(int position) {
            return strs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int type = getItemViewType(position);

            FzItemHolder fzItemHolder;
            ViewHolder viewHolder;
            switch (type) {
                case 0:
                    if (convertView==null ) {
                        fzItemHolder=new FzItemHolder();

                        convertView=inflater.inflate(R.layout.list_view_fz_item,null);
                        fzItemHolder.imageView= (ImageView) convertView.findViewById(R.id.home_item_imageView);
                        fzItemHolder.titleTV= (TextView) convertView.findViewById(R.id.home_item_titleTextV);
                        fzItemHolder.detialTV= (TextView) convertView.findViewById(R.id.home_item_detialTextV);
                        fzItemHolder.fbTimeTV= (TextView) convertView.findViewById(R.id.home_item_fbTimeTextV);
                        convertView.setTag(R.id.tag_second, fzItemHolder);
                    }else {
                        fzItemHolder= (FzItemHolder) convertView.getTag(R.id.tag_second);
                    }

                    fzItemHolder.imageView.setImageDrawable(getDrawable(R.drawable.zhinanzhen));
                    fzItemHolder.titleTV.setText("标题"+position);
//                holder.detialTV
                    fzItemHolder.fbTimeTV.setText("发布时间：2017-11-22 "+(position+1));
                    break;
                case 1:
                    if (convertView==null) {
                        viewHolder=new ViewHolder();
                        convertView=inflater.inflate(R.layout.list_view_item,null);
                        viewHolder.tv= (TextView) convertView.findViewById(R.id.textView);
                        convertView.setTag(R.id.tag_frist, viewHolder);
                    }else {
                        viewHolder= (ViewHolder) convertView.getTag(R.id.tag_frist);
                    }
                    viewHolder.tv.setText(strs[position]);
                    break;
            }
            return convertView;
        }


        //实现下面2个协议方法，会告诉协议我有几种不同的item，且每种item的type标识，且在不同的type下会读取不同的缓存池
        @Override
        public int getViewTypeCount() {
            return 2;
        }
        @Override
        public int getItemViewType(int position) {
            return position>16?0:1;
        }

        //缓存池 不同样式的listViewItem需要不同Holder来缓存 每一种不同的cell需要不同的holder来缓存
        class ViewHolder{
            private TextView tv;
        }

        class FzItemHolder {

            private ImageView imageView;
            private TextView titleTV;
            private TextView detialTV;
            private TextView fbTimeTV;

        }
    }

}
