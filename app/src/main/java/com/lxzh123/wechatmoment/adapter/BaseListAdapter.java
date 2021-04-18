package com.lxzh123.wechatmoment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lxzh123.wechatmoment.holder.BaseListHolder;
import com.lxzh123.wechatmoment.model.BaseBean;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.List;


/**
 * description 简易基础 ListView Adapter
 * author      Created by lxzh
 * date        2020-09-03
 * @param <TBean>
 * @param <KHolder>
 */
public abstract class BaseListAdapter<TBean extends BaseBean, KHolder extends BaseListHolder> extends BaseAdapter {
    private Context mContext;
    private List<TBean> mItemList;
    private int mLayoutId;

    public BaseListAdapter(Context context, List<TBean> itemList, int layoutId) {
        this.mContext = context;
        this.mItemList = itemList;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return getCount() == 0 ? null : mItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private KHolder onCreateViewHolder(View root, TBean item, int index) {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> beanType = (Class<?>) superClass.getActualTypeArguments()[0];
        Class<?> holderType = (Class<?>) superClass.getActualTypeArguments()[1];
        try {
            Constructor constructor = holderType.getConstructor(new Class[]{View.class, beanType, int.class});
            Object object = constructor.newInstance(root, item, index);
            return (KHolder) object;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("create ViewHolder of type:" + holderType + " failed, error info:" + e.getMessage());
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        KHolder holder;
        TBean item = mItemList.get(i);
        if (view == null || view.getTag() == null || ((KHolder) view.getTag()).getIndex() != i) {
            view = LayoutInflater.from(mContext).inflate(mLayoutId, null);
            holder = onCreateViewHolder(view, item, i);
            view.setTag(holder);
        } else {
            holder = (KHolder) view.getTag();
        }
        holder.refresh();
        return view;
    }
}
