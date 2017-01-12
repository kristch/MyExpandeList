package com.xyz.expande;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.view.annotation.event.OnGroupExpand;
import com.xilehang.tencent.R;
import com.xyz.expande.tools.CycleShowView;
import com.xyz.expande.tools.GlobealSetting;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class HomeActivity extends Activity implements OnChildClickListener {
	private ExpandableListView mExpandListView,mExpandListView2,mExpandListView3,mExpandListView4;
	private ExpandAdapter mAdapter = null;
	private List<List<Item>> mData = new ArrayList<List<Item>>();
	private CycleShowView cycleview;
	String[] imageUrls1 = new String[] { GlobealSetting.LUNBO_PATH + "1_1.jpg",
			GlobealSetting.LUNBO_PATH + "1_2.jpg",
			GlobealSetting.LUNBO_PATH + "1_3.jpg", };

	private int[] mGroupArrays = new int[] { R.array.tianlongbabu,
			R.array.shediaoyingxiongzhuan, R.array.shendiaoxialv,
			R.array.shendiaoxialv };

	private int[] mDetailIds = new int[] { R.array.tianlongbabu_detail,
			R.array.shediaoyingxiongzhuan_detail, R.array.shendiaoxialv_detail,
			R.array.shendiaoxialv_detail };

	private String[] lunboitema =new String[] { GlobealSetting.LUNBO_PATH + "1_1.jpg",
			GlobealSetting.LUNBO_PATH + "1_2.jpg",
			GlobealSetting.LUNBO_PATH + "1_3.jpg" };
	private int[] childs = new int[] { R.drawable.ic_launcher2,
			R.drawable.ic_launcher2, R.drawable.ic_launcher2 };
	private int[] details = new int[] { R.drawable.ic_launcher2,
			R.drawable.ic_launcher2, R.drawable.ic_launcher2 };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		cycleview = (CycleShowView) findViewById(R.id.cycleview);
		initLunBo(cycleview, imageUrls1);
		initData();
		mExpandListView = (ExpandableListView) findViewById(R.id.expandlistview);
		initExpandListView(mExpandListView);
		mExpandListView2 = (ExpandableListView) findViewById(R.id.expandlistview2);
		initExpandListView(mExpandListView2);
		mExpandListView3 = (ExpandableListView) findViewById(R.id.expandlistview3);
		initExpandListView(mExpandListView3);
		mExpandListView4 = (ExpandableListView) findViewById(R.id.expandlistview4);
		initExpandListView(mExpandListView4);
		//测试tag
			/*	if(GlobealSetting.mychuandi%3==0){
				mExpandListView.findViewWithTag("iv01").setBackgroundResource(R.drawable.ic_launcher2);
				mExpandListView.findViewWithTag("iv02").setBackgroundResource(R.drawable.ic_launcher);
				mExpandListView.findViewWithTag("iv03").setBackgroundResource(R.drawable.ic_launcher);
				}else if(GlobealSetting.mychuandi%3==1){
					mExpandListView.findViewWithTag("iv02").setBackgroundResource(R.drawable.ic_launcher2);
					mExpandListView.findViewWithTag("iv01").setBackgroundResource(R.drawable.ic_launcher);
					mExpandListView.findViewWithTag("iv03").setBackgroundResource(R.drawable.ic_launcher);
				}else if(GlobealSetting.mychuandi%3==1){
					mExpandListView.findViewWithTag("iv02").setBackgroundResource(R.drawable.ic_launcher);
					mExpandListView.findViewWithTag("iv01").setBackgroundResource(R.drawable.ic_launcher);
					mExpandListView.findViewWithTag("iv03").setBackgroundResource(R.drawable.ic_launcher);
				
				}*/
	}

	private void initExpandListView(final ExpandableListView mExpandListView) {
		
		mExpandListView.setGroupIndicator(getResources().getDrawable(
				R.drawable.expander_floder));
		mAdapter = new ExpandAdapter(this, mData);
		mExpandListView.setAdapter(mAdapter);
		mExpandListView
				.setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS);
		mExpandListView.setOnChildClickListener(this);
		// 这里是控制只有一个group展开的效果  
		mExpandListView.setOnGroupExpandListener(new OnGroupExpandListener() {  
		    @Override  
		    public void onGroupExpand(int groupPosition) {  
		        for (int i = 0; i < mAdapter.getGroupCount(); i++) {  
		            if (groupPosition != i) {  
		            	mExpandListView.collapseGroup(i);  
		            //	GlobealSetting.mypos = i;
		            }  
		        } 
		        if(!mExpandListView.isGroupExpanded(groupPosition)){
		        	//回收资源
		        	GlobealSetting.mypos = groupPosition;
		        }
		    }  
		});  
		mExpandListView.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				Toast.makeText(HomeActivity.this, groupPosition+"我点击了哪一个", 100).show();
				return false;
			}
		});
	}

	/*
	 * ChildView 设置 布局很可能onChildClick进不来，要在 ChildView layout 里加上
	 * android:descendantFocusability="blocksDescendants",
	 * 还有isChildSelectable里返回true
	 */
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Item item = mAdapter.getChild(groupPosition, childPosition);
		new AlertDialog.Builder(this)
				.setTitle("")
				.setMessage("")
				.setIcon(android.R.drawable.ic_menu_more)
				.setNegativeButton(android.R.string.cancel,
						new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).create().show();
		return true;
	}

	private void initData() {
		for (int i = 0; i < mGroupArrays.length; i++) {
			List<Item> list = new ArrayList<Item>();
			//String[] childs = getStringArray(mGroupArrays[i]);
			//String[] details = getStringArray(mDetailIds[i]);
			for (int j = 0; j < 1; j++) {
				Item item = new Item(lunboitema,childs[j],childs[j],details[j]);
				list.add(item);
			}
			mData.add(list);
		}

	}

	private String[] getStringArray(int resId) {
		return getResources().getStringArray(resId);
	}
	/**
	 * 第一层轮播 Title: initLunBo Description:
	 * 
	 * @param cycleView
	 */
	private void initLunBo(CycleShowView cycleView, String[] imageUrls) {

		cycleView.setData(imageUrls,false);
		cycleView.startPlay();
	}
	 public interface MyLunBoStop {
	    	public void isStop(CycleShowView cycle,int position);
	    }
	
}