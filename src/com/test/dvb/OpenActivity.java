package com.test.dvb;

import java.io.File;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.dvb.adapter.OpenAdapter;
import com.test.dvb.docviewer.MyPDFViewer;
import com.test.dvb.docviewer.PdfViewerActivity;
import com.test.dvb.units.Item;
import com.test.dvb.var.Variables;

public class OpenActivity extends Activity {
	private ListView lv_item;
	private OpenAdapter adp = null;
	private Button btn_actback, btn_select, btn_brback, btn_brhome,
			btn_brforward;
	private TextView tv_path;
	private File file;
	private Vector<Item> myVector = new Vector<Item>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_open);
		lv_item = (ListView) findViewById(R.id.listView_item);
		btn_actback = (Button) findViewById(R.id.button_backActivity);
		btn_select = (Button) findViewById(R.id.button_select);
		btn_brback = (Button) findViewById(R.id.button_brback);
		btn_brhome = (Button) findViewById(R.id.button_brhome);
		btn_brforward = (Button) findViewById(R.id.button_brforward);
		tv_path = (TextView) findViewById(R.id.textView_currentDir);

		openHome();

		btn_actback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		btn_select.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					openSubFolder(Variables.Selected);
				} catch (Exception ex) {
					Toast.makeText(OpenActivity.this, "Dir do not Open!",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		btn_brback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Variables.ForwardDir = file.getName();
				backFolder();

				try {
					lv_item.setItemChecked(Variables.selectedindex, true);
					lv_item.setSelection(Variables.selectedindex);
					Variables.Selected = myVector.get(
							lv_item.getCheckedItemPosition()).getName();
				} catch (Exception ex) {

				}

			}
		});

		btn_brforward.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					File tmp = new File(file, Variables.ForwardDir);
					if (tmp.isDirectory()) {
						openSubFolder(Variables.ForwardDir);
					}
				} catch (Exception e) {

				}

			}
		});

		btn_brhome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				openHome();
			}
		});

		lv_item.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if (myVector.get(arg2).getName().equals(Variables.Selected)) {
					try {
						openSubFolder(Variables.Selected);
						return;
					} catch (Exception ex) {
						Toast.makeText(OpenActivity.this, "Dir do not Open!",
								Toast.LENGTH_SHORT).show();
					}
				}

				arg1.setSelected(true);
				Variables.Selected = myVector.elementAt(arg2).getName();
				Variables.selectedindex = arg2;
			}

		});
	}

	public void openHome() {
		String root_sd = Environment.getExternalStorageDirectory().toString();
		if (root_sd == null) {
			Toast.makeText(OpenActivity.this, "SDCARD dose not suport!",
					Toast.LENGTH_SHORT).show();
			return;
		}

		file = new File(root_sd);

		File list[] = file.listFiles();
		
		myVector.clear();

		for (int i = 0; i < list.length; i++) {
			// myVector.add(list[i].getName());
			Item tmp = new Item();
			tmp.setName(list[i].getName());

			File temp_file = new File(file, list[i].getName());

			// Loai bo file khong doc duoc
			boolean ck = true;

			if (temp_file.isFile()) {
				tmp.setFile(true);
				if (!temp_file.getAbsolutePath().endsWith(".pdf")) {
					ck = false;
				}
			} else {
				tmp.setFile(false);
			}

			if (ck) {
				myVector.add(tmp);
			}
		}

		adp = new OpenAdapter(OpenActivity.this, R.layout.item_listview_open,
				myVector);

		lv_item.setAdapter(adp);

		lv_item.setItemChecked(0, true);
		Variables.Selected = myVector.elementAt(0).getName();
		// Set path to view
		tv_path.setText(file.getAbsolutePath().toString()
				.replaceAll("storage/emulated/0", "SDCard")
				+ "/");
	}

	public void openSubFolder(String subdir) {
		File temp_file = new File(file, subdir);

		if (!temp_file.isFile()) {
			file = new File(file, subdir);
			File list[] = file.listFiles();

			myVector.clear();

			for (int i = 0; i < list.length; i++) {
				// myVector.add(list[i].getName());
				Item tmp = new Item();
				tmp.setName(list[i].getName());

				File temp_file2 = new File(file, list[i].getName());

				// loai bo file . #
				boolean ck = true;

				if (temp_file2.isFile()) {
					tmp.setFile(true);
					if (!temp_file2.getAbsolutePath().endsWith(".pdf")) {
						ck = false;
					}
				} else {
					tmp.setFile(false);
				}

				if (ck) {
					myVector.add(tmp);
				}
			}

			adp.notifyDataSetChanged();
			lv_item.setItemChecked(0, true);

			if (myVector.size() > 0) {
				Variables.Selected = myVector.elementAt(0).getName();
			}
			// Set path to view
			tv_path.setText(file.getAbsolutePath().toString()
					.replaceAll("storage/emulated/0", "SDCard")
					+ "/");

		} else {
			// OpenFIle
			if (temp_file.getAbsolutePath().endsWith(".pdf")) {
				openPdfIntent(temp_file.getAbsolutePath());
				// Save recent file
				int index = -1;
				index = Variables.recentFilePDF.indexOf(temp_file
						.getAbsolutePath());
				if (index > -1) {
					Variables.recentFilePDF.remove(index);
				}
				Variables.recentFilePDF.add(0, temp_file.getAbsolutePath());
				//
			} else {
				Toast.makeText(OpenActivity.this, "File dose not support",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	public void backFolder() {
		try {
			String parent = file.getParent().toString();
			file = new File(parent);
			File list[] = file.listFiles();

			myVector.clear();

			for (int i = 0; i < list.length; i++) {
				// myVector.add(list[i].getName());
				Item tmp = new Item();
				tmp.setName(list[i].getName());

				File temp_file2 = new File(file, list[i].getName());
				// loai bo file . #
				boolean ck = true;

				if (temp_file2.isFile()) {
					tmp.setFile(true);
					if (!temp_file2.getAbsolutePath().endsWith(".pdf")) {
						ck = false;
					}
				} else {
					tmp.setFile(false);
				}
				if (ck) {
					myVector.add(tmp);
				}
			}

			adp.notifyDataSetChanged();
			lv_item.setItemChecked(0, true);

			if (myVector.size() > 0) {
				Variables.Selected = myVector.elementAt(0).getName();
			}
			// Set path to view
			tv_path.setText(file.getAbsolutePath().toString()
					.replaceAll("storage/emulated/0", "SDCard")
					+ "/");

		} catch (Exception e) {

		}
	}

	private void openPdfIntent(String path) {
		try {
			final Intent intent = new Intent(OpenActivity.this,
					MyPDFViewer.class);
			intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// Toast.makeText(OpenActivity.this, "DDDD", Toast.LENGTH_SHORT).show();
		//
		// new RecentFileAdapter().saveVector2File(this);
		//
		super.onDestroy();
	}
}
