package com.xyz.expande.tools;
/** 
 * @author wuweiqi
 * @version 创建时间：2016-7-21 下午5:37:47 
 * 类说明 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.lidroid.xutils.BitmapUtils;
import com.xyz.expande.ExpandAdapter;
import com.xyz.expande.ExpandAdapter.MySelect;
import com.xilehang.tencent.R;

public class CycleShowView extends FrameLayout implements MySelect {

	/**上下文*/
	private Context context;
	/**图片加载对象*/
	private FinalBitmap fb;
	/**链接地址集合*/
	private String[] imageUrls;
	/**轮播的图片控件集合*/
	private List<ImageView> imageViewsList;
	/**指示器圆点集合*/
	private List<View> dotViewsList;
	/**自动轮播启用开关*/
    private final static boolean isAutoPlay = true;
    /**定时任务*/
	private ScheduledExecutorService scheduledExecutorService;
	/***/
	private ViewPager viewPager; 
	/**当前轮播页*/
    private int currentItem  = 0;
   
    /**当前页面改变监听*/
    private MyPageOnClickItemListener mMyPageOnClickItemListener;
	public CycleShowView(Context context) {
		this(context, null);
		this.context = context;
	}

	public CycleShowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}

	public CycleShowView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		
		//初始化图片加载工具
		initImageLoader(context);
		initBitMap(context);
		initData();
		if(isAutoPlay){
		startPlay();
		}
		
	}
	
	private void initBitMap(Context context2) { 
		bitmapUtils = new BitmapUtils(context2);
		bitmapUtils.configDefaultBitmapMaxSize(1426, 4301);
	}

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}
	};
	private BitmapUtils bitmapUtils;
	private Button butleft;
	private Button butright;

	//初始化相关数据
	private void initData() {
		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<View>();
		
//		initUI();
	}
	

	private void initUI() {
	
	
		if(imageUrls == null || imageUrls.length == 0)
			return;
		//FrameLayout flayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.pic_cycle_layout, this, true);
		FrameLayout flayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.pic_cycle_layout, this, true);
		LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dot_layout);
		dotLayout.removeAllViews();
		
		//动态添加图片和点点控件
		for (int i = 0; i < imageUrls.length; i++) {
			ImageView mImageView = new ImageView(context);
			mImageView.setTag(imageUrls[i]);
			if(i== 0){
				//bitmapUtils.display(mImageView, "file:///mnt/sdcard/Soarup/www5/sharecoapp/xlhEdit/daishaupdate/lunbo/1_1.jpg");
				//mImageView.setBackgroundResource(R.drawable.la);
		}
			mImageView.setScaleType(ScaleType.FIT_XY);
			imageViewsList.add(mImageView);
			ImageView mDotImageView = new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 7),DensityUtil.dip2px(context, 7));
			params.leftMargin = 4;
			params.rightMargin = 4;
			mDotImageView.setLayoutParams(params);
			mDotImageView.setScaleType(ScaleType.FIT_XY);
			mDotImageView.setBackgroundResource(R.drawable.ic_focus);
			dotViewsList.add(mDotImageView);
			dotLayout.addView(mDotImageView);
		}
		viewPager = (ViewPager) findViewById(R.id.cycle_viewPager);
		viewPager.setFocusable(true);
		
		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setOnPageChangeListener(new MyPagerChangeListener());

		// 触摸时停止滚动
		viewPager.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startPlay();
				} else {
					stopPlay();
				}
				return false;
			}
		});
		butleft = (Button)flayout.findViewById(R.id.btleft);
		butleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			/*	if(viewPager.getCurrentItem() == viewPager.getAdapter().getCount() -1 && !isAutoPlay){
					//viewPager.setCurrentItem(0);
					currentItem = 0;
				}*/
				//当前为第一张，向左滑动则切换到最后一张
				 if(viewPager.getCurrentItem() == 0){
					currentItem = viewPager.getAdapter().getCount()-1;
					//viewPager.setCurrentItem(viewPager.getAdapter().getCount() -1);
				}else{
				  currentItem = (currentItem-1)%imageViewsList.size();
				}
		          handler.obtainMessage().sendToTarget();
			}
		});
		butleft.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startPlay();
				} else {
					stopPlay();
				}
				return false;
			}
		});

		butright = (Button)flayout.findViewById(R.id.btright);
		
		butright.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				  currentItem = (currentItem+1)%imageViewsList.size();
		          handler.obtainMessage().sendToTarget();
			}
		});
		butright.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startPlay();
				} else {
					stopPlay();
				}
				return false;
			}
		});

	
	}

	private void initImageLoader(Context context) {
		fb = FinalBitmap.create(context);
	}
	
	private Button but01,but02,but03;
	private boolean flag;
	/**设置图片数据*/
	public void setData(String[] imageUrls,boolean flag){
		this.imageUrls = imageUrls;
		this.flag  = flag;
		initUI();
	}
	public void setData(String[] imageUrls,Button but01,Button but02,Button but03){
		flag = true;
		this.but01 = but01;
		this.but02 = but02;
		this.but03 = but03;
		this.imageUrls = imageUrls;
		initUI();
	}
	
	/**开始轮播*/
	public void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 2, 3, TimeUnit.SECONDS);
	}
	
	/**停止轮播*/
	private void stopPlay(){
		scheduledExecutorService.shutdown();
	}
    /**
     *执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable{

        @Override
        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem+1)%imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
        
    }
    
    private class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager)container).removeView(imageViewsList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			final ImageView imageView = imageViewsList.get(position);
			
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "哈哈"+position, 100).show();
					//mMyPageOnClickItemListener.curSelect(position);
				}
			});
			String url = imageView.getTag()+"";
			bitmapUtils.display(imageView, url);
			((ViewPager)container).addView(imageViewsList.get(position));
			return imageViewsList.get(position);
		}
		
    }
    
    /**
     * ViewPager监听器
     * 页面状态发送改变时调用
     */
    private class MyPagerChangeListener implements OnPageChangeListener{

    	boolean isAutoPlay = false;
		//private int count;
    	
		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			case 1://手势滑动，空闲中
				isAutoPlay = false;
				break;
			case 2://界面切换中
				isAutoPlay = true;
				break;
			case 0://滑动结束，切换或者加载完成
				System.out.println("滑动--"+viewPager.getCurrentItem());
				//当前为最后一张就切换到第一张
				if(viewPager.getCurrentItem() == viewPager.getAdapter().getCount() -1 && !isAutoPlay){
					viewPager.setCurrentItem(0);
				}
				//当前为第一张，向左滑动则切换到最后一张
				else if(viewPager.getCurrentItem() == 0 && !isAutoPlay){
					viewPager.setCurrentItem(viewPager.getAdapter().getCount() -1);
				}
				
				int item = viewPager.getCurrentItem();
				if(flag){
				if(item%3==0){
				but01.setBackgroundResource(R.drawable.ic_launcher2);
				but02.setBackgroundResource(R.drawable.ic_launcher);
				but03.setBackgroundResource(R.drawable.ic_launcher);
				}else if(item%3==1){
					but02.setBackgroundResource(R.drawable.ic_launcher2);
					but01.setBackgroundResource(R.drawable.ic_launcher);
					but03.setBackgroundResource(R.drawable.ic_launcher);
				}else if(item%3==2){
					but03.setBackgroundResource(R.drawable.ic_launcher2);
					but01.setBackgroundResource(R.drawable.ic_launcher);
					but02.setBackgroundResource(R.drawable.ic_launcher);
				}
				}
				break;
			default:
				break;
			}
			
		}
	
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}

		@Override
		public void onPageSelected(int arg0) {
			currentItem = arg0;
			for (int i = 0; i < dotViewsList.size(); i++) {
				if(i == arg0){
					//当前点点的背景
					((View)dotViewsList.get(arg0)).setBackgroundResource(R.drawable.ic_focus_select);
				}else{
					((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.ic_focus);
				}
			}
		}

    	
    }
    
    /**
     * 销毁ImageView资源，回收内存
     * 
     */
    public void destoryBitmaps() {

        for (int i = 0; i < imageViewsList.size(); i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                //解除drawable对view的引用
                drawable.setCallback(null);
            }
        }
    }
    
    public interface MyPageOnClickItemListener {
    	public void curSelect(int position);
    }
    
    public void setMyPageOnClickItemListener(MyPageOnClickItemListener mMyPageOnClickItemListener){
    	this.mMyPageOnClickItemListener = mMyPageOnClickItemListener;
    }

	@Override
	public void setmyCusselect(int position) {
		viewPager.setCurrentItem(position,true);
		
	}


    
}
