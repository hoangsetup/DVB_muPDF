package com.test.dvb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.test.dvb.docviewer.MyPDFViewer;
import com.test.dvb.docviewer.PdfViewerActivity;
import com.test.dvb.var.Variables;

public class RecentActivity extends Activity {
	private Button btn_reback, btn_reopen;
	private ListView lv_recent;
	private ArrayAdapter<String> adp = null;
	private String selectedFile = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_recent);

		// new RecentFileAdapter().getFile2Vector(this);

		btn_reback = (Button) findViewById(R.id.button_reback);
		btn_reopen = (Button) findViewById(R.id.button_reOpen);
		lv_recent = (ListView) findViewById(R.id.listView_recentfile);
		lv_recent.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		getRecentFile();

		btn_reback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		btn_reopen.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					openPdfIntent(selectedFile);
				} catch (Exception ex) {

				}
			}
		});

		lv_recent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				// TODO Auto-generated method stub
				if (Variables.recentFilePDF.get(arg2).equals(selectedFile)) {
					try {
						openPdfIntent(selectedFile);
						return;
					} catch (Exception ex) {
						Toast.makeText(RecentActivity.this, "Dir do not Open!",
								Toast.LENGTH_SHORT).show();
					}
				}
				selectedFile = Variables.recentFilePDF.elementAt(arg2);
			}
		});
	}

	public void getRecentFile() {
		if (Variables.recentFilePDF.size() > 0) {
			adp = new ArrayAdapter<String>(RecentActivity.this,
					R.layout.item_listview_open, R.id.textView_name,
					Variables.recentFilePDF);
			lv_recent.setAdapter(adp);
			lv_recent.setItemChecked(0, true);
			selectedFile = Variables.recentFilePDF.elementAt(0);
		} else {
			Toast.makeText(RecentActivity.this, "Empty", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void openPdfIntent(String path) {
		// Save recent file
		int index = -1;
		index = Variables.recentFilePDF.indexOf(path);
		if (index > -1) {
			Variables.recentFilePDF.remove(index);
		}
		Variables.recentFilePDF.add(0, path);
		//
		try {
			final Intent intent = new Intent(RecentActivity.this,
					MyPDFViewer.class);
			intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		adp.notifyDataSetChanged();
		super.onResume();
	}
}
