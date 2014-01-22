/*
 * #%L
 * SlidingMenuDemo
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 Paul Grime
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package grimbo.android.demo.slidingmenu;

import grimbo.android.demo.slidingmenu.MyHorizontalScrollView.MyGestureDetector;
import grimbo.android.demo.slidingmenu.MyHorizontalScrollView.SizeCallback;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.gesture.Gesture;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
/**
 * This demo uses a custom HorizontalScrollView that ignores touch events, and therefore does NOT allow manual scrolling.
 * 
 * The only scrolling allowed is scrolling in code triggered by the menu button.
 * 
 * When the button is pressed, both the menu and the app will scroll. So the menu isn't revealed from beneath the app, it
 * adjoins the app and moves with the app.
 */
public class HorzScrollWithListMenu extends Activity {
    MyHorizontalScrollView scrollView;
    View menu;
    View app;
    ImageView btnSlide;
	boolean menuOut = false;
	Handler handler = new Handler();
	int btnWidth;
	// ///////////////////////mycode///////////////////////////////////////////
	float xDown = 0.0f;
	float xUp = 0.0f;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		menu.invalidate();
		app.invalidate();
		scrollView.invalidate();
		System.out.println("Hello!!");
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			xDown = event.getX(); // Down���� ���������� x��ǥ
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			xUp = event.getY(); // Up���� ������ ���� Y��ǥ
		}

		// move event motion event
		// Ensure menu is visible
		menu.setVisibility(View.VISIBLE);
		int menuWidth = menu.getMeasuredWidth();
		if (xUp < xDown) {
			// Scroll to 0 to reveal menu
			int left = 0;
			scrollView.smoothScrollTo(left, 0);
		} else {
			// Scroll to menuWidth so menu isn't on screen.
			int left = menuWidth;
			scrollView.smoothScrollTo(left, 0);
		}

		return super.onTouchEvent(event);
	}

	///////////////////////mycode END/////////////////////////////////////////////
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// �ǒ� : �ǹ��� ���赵(xml ����) -> inflate(��Ǯ��) -> ���� �ǹ�(view)
		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (MyHorizontalScrollView) inflater.inflate(
				R.layout.horz_scroll_with_list_menu, null);
		setContentView(scrollView);

		menu = inflater.inflate(R.layout.horz_scroll_menu, null);
        app = inflater.inflate(R.layout.horz_scroll_app, null);
        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);
        
        //app{List(Item x 30){}} �ʱ�ȭ
        ListView listView = (ListView) app.findViewById(R.id.list);
        ViewUtils.initListView(this, listView, "Item ", 30, android.R.layout.simple_list_item_1);
        
        ///////////////////////////mycode/////////////////////////////////////////////
        //���� ���Ӵ��� ����ħ
        //MyGestureDetector gesture = new MyGestureDetector(menu, scrollView);
        final GestureDetector gestureDetector = new GestureDetector(new MyGestureDetector(menu, scrollView, listView));
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
        	public boolean onTouch(View v, MotionEvent event) {
        		boolean flag = gestureDetector.onTouchEvent(event);
        		
        		return flag;
        	}
        };
        
        listView.setOnTouchListener(gestureListener);
        ///////////////////////////mycode END//////////////////////////////////////////

        //menu{List(Menu x 30){}} �ʱ�ȭ
        listView = (ListView) menu.findViewById(R.id.list);
        ViewUtils.initListView(this, listView, "Menu ", 30, android.R.layout.simple_list_item_1);
        ///////////////////////////mycode//////////////////////////////////////////////
        listView.setOnTouchListener(gestureListener);
        ///////////////////////////mycode END//////////////////////////////////////////
        
        
        //ImageView���̾ƿ� (�̹��� ǥ��)
        btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide);
        btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, menu));
        
        //final�� ���. -> Listener�� ���� ����
        //( Listener�� �����ϰ� ���� ������������ final�� ó�� )
        final View[] children = new View[] { menu, app };

        // Scroll to app (view[1]) when layout finished.
        int scrollToViewIdx = 1;
        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
        listView.invalidate();

    }

    /**
     * Helper for examples with a HSV that should be scrolled by a menu View's width.
     */
    static class ClickListenerForScrolling implements OnClickListener {
        HorizontalScrollView scrollView;
        View menu;
        /**
         * Menu must NOT be out/shown to start with.
         */
        boolean menuOut = false;

        public ClickListenerForScrolling(HorizontalScrollView scrollView, View menu) {
            super();
            this.scrollView = scrollView;
            this.menu = menu;
        }
        
        @Override
        public void onClick(View v) {
            Context context = menu.getContext();
            String msg = "Slide " + new Date();
            Toast.makeText(context, msg, 1000).show();
            System.out.println(msg);

            int menuWidth = menu.getMeasuredWidth();

            // Ensure menu is visible
            menu.setVisibility(View.VISIBLE);
            
            if (!menuOut) {
                // Scroll to 0 to reveal menu
                int left = 0;
                scrollView.smoothScrollTo(left, 0);
            } else {
                // Scroll to menuWidth so menu isn't on screen.
                int left = menuWidth;
                scrollView.smoothScrollTo(left, 0);
            }
            
            menuOut = !menuOut;
        }
    }

    /**
     * Helper that remembers the width of the 'slide' button, so that the 'slide' button remains in view, even when the menu is
     * showing.
     */
    static class SizeCallbackForMenu implements SizeCallback {
        int btnWidth;
        View btnSlide;

        public SizeCallbackForMenu(View btnSlide) {
            super();
            this.btnSlide = btnSlide;
        }

        @Override
        public void onGlobalLayout() {
            btnWidth = btnSlide.getMeasuredWidth();
            System.out.println("btnWidth=" + btnWidth);
        }

        @Override
        public void getViewSize(int idx, int w, int h, int[] dims) {
            dims[0] = w;
            dims[1] = h;
            final int menuIdx = 0;
            if (idx == menuIdx) {
                dims[0] = w - btnWidth;
            }
        }
    }
}
