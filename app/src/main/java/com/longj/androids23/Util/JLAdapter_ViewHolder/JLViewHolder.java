package com.longj.androids23.Util.JLAdapter_ViewHolder;

import android.util.SparseArray;
import android.view.View;

/**
 * 通用ViewHolder
 *
 * Created by xp on 2017/12/1.
 */

public class JLViewHolder {
    /**
     * SparseArray(稀疏数组).他是Android内部特有的api,标准的jdk是没有这个类的.
     * 在Android内部用来替代HashMap<Integer,E>这种形式,使用SparseArray更加节省内存空间的使用,SparseArray也是以key和value对数据进行保存的.
     * 使用的时候只需要指定value的类型即可.并且key不需要封装成对象类型.
     */

    //获取View
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHoler = (SparseArray<View>) view.getTag();
        //如果RootView没有用来缓存view的集合
        if (viewHoler == null) {
            viewHoler = new SparseArray<>();
            view.setTag(viewHoler);//创建集合且和RootView关联
        }
        View childV = viewHoler.get(id);//获取RootView存储在集合中的子节点
        if (childV == null) { // 没找到，就创建且保存到RootView的集合中
            childV = view.findViewById(id);
            viewHoler.put(id, childV);
        }

        return (T)childV;
    }
}
