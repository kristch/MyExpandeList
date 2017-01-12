package com.xyz.expande;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.xilehang.tencent.R;
import com.xyz.expande.tools.CycleShowView;
import com.xyz.expande.tools.GlobealSetting;

public class ExpandAdapter extends BaseExpandableListAdapter  {

    private Context mContext;
    private LayoutInflater mInflater = null;
    private String[]   mGroupStrings = null;
    private List<List<Item>>   mData = null;
 /*   private Handler mhandler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		if(msg.what==1){
    			
    		}
    	};
    };*/


	public ExpandAdapter(Context ctx, List<List<Item>> list) {
        mContext = ctx;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGroupStrings = mContext.getResources().getStringArray(R.array.groups);
        mData = list;
    }

    public ExpandAdapter() {
	}

	public void setData(List<List<Item>> list) {
        mData = list;
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.get(groupPosition).size();
    }

    @Override
    public List<Item> getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public Item getChild(int groupPosition, int childPosition) {
        return mData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.group_item_layout, null);
        }
        GroupViewHolder holder = new GroupViewHolder();
        holder.mGroupName = (TextView) convertView.findViewById(R.id.group_name);
        holder.mGroupName.setText(mGroupStrings[groupPosition]);
        
        holder.mGroupCount = (TextView) convertView.findViewById(R.id.group_count);
        holder.mGroupCount.setText("[" + mData.get(groupPosition).size() + "]");
        
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.child_item_layout, null);
        }
        final ChildViewHolder holder = new ChildViewHolder();
        holder.cycle = (CycleShowView) convertView.findViewById(R.id.cycleitem);
        holder.iv01 = (Button) convertView.findViewById(R.id.iv01);
        holder.iv02 = (Button) convertView.findViewById(R.id.iv02);
        holder.iv03 = (Button) convertView.findViewById(R.id.iv03);
        holder.iv01.setBackgroundResource(R.drawable.ic_launcher2);
        holder.iv02.setBackgroundResource(R.drawable.ic_launcher);
		holder.iv03.setBackgroundResource(R.drawable.ic_launcher);
        holder.cycle.setData(getChild(groupPosition, childPosition).getCycle(),holder.iv01,holder.iv02,holder.iv03);
       //回收资源
        if(GlobealSetting.mypos!=groupPosition){
        holder.cycle.destoryBitmaps();
        }
  holder.iv01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				holder.iv03.setBackgroundResource(R.drawable.ic_launcher);
				holder.iv02.setBackgroundResource(R.drawable.ic_launcher);
				holder.iv01.setBackgroundResource(R.drawable.ic_launcher2);
				//控制轮播
				holder.cycle.setmyCusselect(0);
			}
		});
  holder.iv02.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			holder.iv01.setBackgroundResource(R.drawable.ic_launcher);
			holder.iv03.setBackgroundResource(R.drawable.ic_launcher);
			holder.iv02.setBackgroundResource(R.drawable.ic_launcher2);
			//控制轮播
			holder.cycle.setmyCusselect(1);
		}
	});
  holder.iv03.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			holder.iv01.setBackgroundResource(R.drawable.ic_launcher);
			holder.iv02.setBackgroundResource(R.drawable.ic_launcher);
			holder.iv03.setBackgroundResource(R.drawable.ic_launcher2);
			//控制轮播
			holder.cycle.setmyCusselect(2);
		}
	});
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        /*很重要：实现ChildView点击事件，必须返回true*/
        return false;
    }

    private class GroupViewHolder {
        TextView mGroupName;
        TextView mGroupCount;
    }

    private class ChildViewHolder {
        CycleShowView cycle;
        Button iv01;
        Button iv02;
        Button iv03;
    }
    
    public interface MySelect {
    	public void setmyCusselect(int position);
    }
   // private boolean flagselected=true;
  //  private int position=0;
 /* public  void setMyflag(int position,boolean flag){
	  System.out.println("我的滑动真的"+position+""+flag);
	  this.position = position;
	  this.flagselected = flag;
  }*/
//private Button ivaa,ivbb,ivcc;
}
