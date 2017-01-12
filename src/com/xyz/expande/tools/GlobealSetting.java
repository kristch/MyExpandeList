package com.xyz.expande.tools;

import java.io.File;

import android.os.Environment;
import android.widget.ImageView;

/** 
 * @author wuweiqi
 * @version 创建时间：2016-11-23 下午1:57:27 
 * 类说明 
 */
public class GlobealSetting {
	public static String RES_PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath()
			+ File.separator
			+ "Soarup"
			+ File.separator
			+ "www5"
			+ File.separator
			+ "sharecoapp"
			+ File.separator
			+ "xlhEdit" + File.separator+"daishaupdate"+File.separator ;
	public static String BACKGROUND_PATH = RES_PATH+"background/";
	public static String LUNBO_PATH = RES_PATH+"lunbo/";
	public static int mypos;
//	public static CycleShowView mycycle;
//	public static int item=0;
//	public static boolean selectItem = true;

}
