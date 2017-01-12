package com.xyz.expande;

import com.lidroid.xutils.view.annotation.event.OnGroupExpand;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/** 
 * @author wuweiqi
 * @version 创建时间：2016-11-25 下午11:50:32 
 * 类说明 
 */
public class MyExpandListView extends ExpandableListView {  
	  
    public MyExpandListView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
        MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
    }  
}
