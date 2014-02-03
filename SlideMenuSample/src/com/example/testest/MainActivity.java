package com.example.testest;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT_RIGHT);
//		menu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); // �¿� ��ũ�ѽ� ��ġ���� ����
		menu.setAboveOffset(70);
		menu.setBehindOffset(70);
		menu.setBehindScrollScale(0.2f); // �¿� ��ũ�ѽ� �¿쿡 �ִ� �䰡 �¿�� �̵��ϴ� ������ ��
		
		menu.setSelectorDrawable(R.drawable.ic_launcher);
		menu.setSelectorEnabled(true);
		menu.setSelectedView(findViewById(R.id.center_listview));
		
//		menu.setShadowDrawable(R.drawable.ic_launcher);
		menu.setShadowWidth(30);
		
		menu.setFadeDegree(0.35f); // �������� ������� ���ذ� �׸���
		
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT); // actionbar���� �̵����� ����
		menu.setMenu(R.layout.leftmenu);
		
		menu.setSecondaryMenu(R.layout.rightmenu);
//		menu.setSecondaryShadowDrawable(R.drawable.ic_launcher);
		
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			arr.add(Integer.toString(i));
		}
		
		ListView listView = (ListView)findViewById(R.id.leftmenu_listview);
		
		if (null != listView) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
			listView.setAdapter(adapter);
		}
		
		listView = (ListView)findViewById(R.id.rightmenu_listview);
		
		if (null != listView) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
			listView.setAdapter(adapter);
		}
		
		listView = (ListView)findViewById(R.id.center_listview);
		
		if (null != listView) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
			listView.setAdapter(adapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
