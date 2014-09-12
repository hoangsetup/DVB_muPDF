package com.test.dvb.var;

import java.util.Vector;

import android.graphics.Bitmap;

public class Variables {
	public static String Selected = "";
	public static String BackDir = "";
	public static String ForwardDir = "";
	public static int selectedindex = 0;
	public static Vector<String> recentFilePDF = new Vector<String>();

	
	//this is bitmap for next page function
	public static Bitmap PAGENEXT = null;
	public static Vector<Bitmap> allDoc = new Vector<Bitmap>();

	public void insertRecent(Vector<String> vt_recent, String file) {
		int index = -1;
		index = vt_recent.indexOf(file);
		if (index > -1) {
			vt_recent.remove(index);
		}
		vt_recent.add(0, file);
	}
}
