package com.longj.androids23;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= this.getLayoutInflater().inflate(R.layout.activity_main,null);
        setContentView(view);

        ListView listV = (ListView)findViewById(R.id.all_list_view);

        String[] nameArr = new String[]{"图片浏览器","手势检测","各种布局","item3","item4","item5","item6","item7"};
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
                    case 0:
                        Intent intent1 = new Intent(MainActivity.this, PhotoBrowserActivity.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(MainActivity.this, GestureActivity.class);
                        startActivity(intent2);
                        break;

                    default:
                        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setView(view);
                        builder.show();
                        break;
                }

            }
        });


//        final 不可变的参数


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
            ViewHolder holder;
            if (convertView==null) {
                holder=new ViewHolder();
                convertView=inflater.inflate(R.layout.list_view_item,null);
                holder.tv= (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(strs[position]);
            return convertView;
        }

        //缓存池 不同样式的listViewItem需要不同Holder来缓存 每一种不同的cell需要不同的holder来缓存
        class ViewHolder{
            private TextView tv;
        }
    }

}
