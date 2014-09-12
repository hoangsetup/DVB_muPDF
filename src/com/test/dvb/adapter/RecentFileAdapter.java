package com.test.dvb.adapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;

import com.test.dvb.var.Variables;

public class RecentFileAdapter {
	public RecentFileAdapter() {

	}

	public void saveVector2File(Activity ctx) {
		String filename = "myRecentfile";
		FileOutputStream outputStream;

		try {
			outputStream = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
			
			if(Variables.recentFilePDF.size() > 10){
				for(int i = 10; i < Variables.recentFilePDF.size(); i++){
					Variables.recentFilePDF.remove(i);
				}
			}
			
			for (String s : Variables.recentFilePDF) {
				s += "\n";
				outputStream.write(s.getBytes());
			}
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getFile2Vector(Activity ctx) {
		Variables.recentFilePDF.clear();
		FileInputStream fis;
		try {
			fis = ctx.openFileInput("myRecentfile");
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
					Variables.recentFilePDF.add(line.toString());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
