package br.com.unirio.cr.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class ListAdapter<T> extends BaseAdapter {
    protected Activity activity;

    protected List<T> list;

    public ListAdapter()
    {
    }

    public ListAdapter(Activity activity)
    {
        super();
        this.activity = activity;
        this.list = new ArrayList<T>();
    }

    public ListAdapter(Activity activity, List<T> list)
    {
        super();
        this.activity = activity;
        this.list = list;
    }

    public ListAdapter(List<T> list)
    {
        super();
        this.list = list;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public T getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void add(T row)
    {
        list.add(row);
    }

    public void remove(T row)
    {
        list.remove(row);
    }

    public List<T> getList()
    {
        if (list == null)
        {
            list = new ArrayList<T>();
        }
        return list;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
