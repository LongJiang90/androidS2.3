package com.longj.androids23.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by xp on 2017/11/30.
 */

public abstract class JLAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> datas;
    private int layoutId;//单一cell的ID
    private int[] layoutIDs;//多种cell的IDs
    private int[] types;//多种cell的IDs

    public JLAdapter(Context context, List<T> datas, int layoutId){
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    public JLAdapter(Context context, List<T> datas, int[] layoutIDs){
        this.context = context;
        this.datas = datas;
        this.layoutIDs = layoutIDs;
        types = getTypes();
    }

    public int[] getTypes() {
        int[] types = new int[layoutIDs.length];
        for(int i=0; i<layoutIDs.length; i++) {
            types[i] = 10010+i;
        }
        return types;
    }

    @Override
    public int getViewTypeCount() {
        return layoutIDs == null ? 1 : layoutIDs.length;
    }

    @Override
    public int getItemViewType(int position) {
        return layoutIDs == null ? 0 : type(position);
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            int layID = layoutIDs != null ? layoutIDs[type] : layoutId;
            convertView = LayoutInflater.from(context).inflate(layID, null);
        }

        T obj = (T) getItem(position);

        convertView(convertView, obj, position, type);

        return convertView;
    }

    protected abstract void convertView(View itemV, T obj, int position, int type);//相当于协议函数
    protected abstract int type(int position);

}
