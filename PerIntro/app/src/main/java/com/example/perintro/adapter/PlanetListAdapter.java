package com.example.perintro.adapter;

import java.util.ArrayList;

import com.example.perintro.R;
import com.example.perintro.bean.App;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class PlanetListAdapter extends BaseAdapter implements
        OnItemClickListener, OnItemLongClickListener {
    private Context mContext; // 声明一个上下文对象
    private ArrayList<App> mAppList; // 声明一个行星信息队列

    public Context getmContext() {
        return mContext;
    }

    public ArrayList<App> getmPlanetList() {
        return mAppList;
    }

    // 行星适配器的构造函数，传入上下文与行星队列
    public PlanetListAdapter(Context context, ArrayList<App> app_list) {
        mContext = context;
        mAppList = app_list;
    }

    // 获取列表项的个数
    public int getCount() {
        return mAppList.size();
    }

    // 获取列表项的数据
    public Object getItem(int arg0) {
        return mAppList.get(arg0);
    }

    // 获取列表项的编号
    public long getItemId(int arg0) {
        return arg0;
    }

    // 获取指定位置的列表项视图
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) { // 转换视图为空
            holder = new ViewHolder(); // 创建一个新的视图持有者
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.tv_time = convertView.findViewById(R.id.tv_time);
            holder.tv_state = convertView.findViewById(R.id.tv_state);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else { // 转换视图非空
            // 从转换视图中获取之前保存的视图持有者
            holder = (ViewHolder) convertView.getTag();
        }
        App app = mAppList.get(position);
        holder.iv_icon.setImageResource(app.image); // 显示行星的图片
        holder.tv_name.setText(app.name); // 显示行星的名称
        holder.tv_desc.setText(app.desc); // 显示行星的描述
        holder.tv_time.setText(app.time); // 显示行星的时间
        if(app.state == 1){
            holder.tv_state.setText("√");
        }else{
            holder.tv_state.setText("×");
        }

        return convertView;
    }

    // 定义一个视图持有者，以便重用列表项的视图资源
    public final class ViewHolder {
        public ImageView iv_icon; // 声明行星图片的图像视图对象
        public TextView tv_name; // 声明行星名称的文本视图对象
        public TextView tv_desc; // 声明行星描述的文本视图对象
        public TextView tv_time; // 声明时间的文本视图对象
        public TextView tv_state; // 声明状态的文本试图对象
    }

    // 处理列表项的点击事件，由接口OnItemClickListener触发
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }

    // 处理列表项的长按事件，由接口OnItemLongClickListener触发
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String desc = String.format("您长按了第%d个行星，它的名字是%s", position + 1,
                mAppList.get(position).name);
        Toast.makeText(mContext, desc, Toast.LENGTH_LONG).show();
        return true;
    }
}
