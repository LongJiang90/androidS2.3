package com.longj.androids23;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LinearLayout topLinearLay = (LinearLayout) findViewById(R.id.h_top_lyout);
        LinearLayout midLinearLay = (LinearLayout) findViewById(R.id.h_mid_lyout);
        LinearLayout bottomLinearLay = (LinearLayout) findViewById(R.id.h_bottom_lyout);

        //ListView、GridView的用法都很相似，需要先写Adapter然后设置给View就可以展示想要的页面
        GridView topGridLay = (GridView) findViewById(R.id.top_grid_view);
        GridView botGridLay = (GridView) findViewById(R.id.bottom_grid_view);

        topGridLay.setAdapter(new ImageAdapter(this, 0));
        botGridLay.setAdapter(new ImageAdapter(this, 1));
    }


    public class ImageAdapter extends BaseAdapter {
        private Context context;
        int section = 0;
        public ImageAdapter(Context context, int section) {
            this.context = context;
            this.section = section;
        }

        private int[] images = {
                R.drawable.shourufenxi,
                R.drawable.yewufenxi,
                R.drawable.yuangongfenxi,
                R.drawable.lirunfenxi,
                R.drawable.leixingtongji,
                R.drawable.fenbutongji,
                R.drawable.kaoqingtongji,
                R.drawable.qiehuanshezhi,
                R.drawable.yingxiaohuodong,
                R.drawable.yijianbaoxiu,
                R.drawable.baoyangjilu,
                R.drawable.guobiaoxiazai,
                R.drawable.ruanjianshouce,
                R.drawable.shikuangluxiang
        };
        private String[] names = {
                "收入分析",
                "业务分析",
                "员工能力分析",
                "利润分析",
                "车检数据",
                "客户分布",
                "考勤统计",
                "切换设置",
                "营销活动",
                "一键报修",
                "保养记录",
                "国标下载",
                "软件手册下载",
                "实时监控"
        };



        @Override
        public int getCount() {
            if (this.section == 0) {
                return 8;
            }else{
                return 6;
            }
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImgTxtHolder imgTxtHolder;
            if (convertView == null){
                imgTxtHolder = new ImgTxtHolder();
                LayoutInflater layoutF = LayoutInflater.from(context);
                convertView = layoutF.inflate(R.layout.menu_item, null);
                convertView.setTag(imgTxtHolder);
                convertView.setPadding(15,15,15,15);

                imgTxtHolder.imageView = (ImageView) convertView.findViewById(R.id.item_image_view);
                imgTxtHolder.textView = (TextView) convertView.findViewById(R.id.item_name);
            }else {
                imgTxtHolder = (ImgTxtHolder) convertView.getTag();
            }
            if (section == 0) {
                imgTxtHolder.imageView.setImageResource(images[position]);
                imgTxtHolder.textView.setText(names[position]);
            }else {
                imgTxtHolder.imageView.setImageResource(images[position+8]);
                imgTxtHolder.textView.setText(names[position+8]);
            }

            return convertView;
        }
    }

    class ImgTxtHolder {
        ImageView imageView;
        TextView textView;
    }
}


