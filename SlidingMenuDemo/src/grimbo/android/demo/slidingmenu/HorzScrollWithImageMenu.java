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

import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This example uses a FrameLayout to display a menu View and a
 * HorizontalScrollView (HSV). The HSV has a transparent View as the first
 * child, which means the menu will show through when the HSV is scrolled.
 */
/**
 * @author diotek
 *
 */
@SuppressLint("ResourceAsColor")
public class HorzScrollWithImageMenu extends FragmentActivity {
	private PagerAdapterManager mPagerAdapter = null;
	private ViewPager mPager = null;
	
	private LayoutInflater mInflater = null;
	private Button mButton = null;
	private EditText mEditText = null;
	private View mLeft, mRight, mMain = null;
	private boolean mButtonClick = false;
	private float mInput = 1;
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageview);

		Vector<View> pages = new Vector<View>();
		mInflater = LayoutInflater.from(this);
		mMain = mInflater.inflate(R.layout.main, null);
		mLeft = mInflater.inflate(R.layout.left, null);
		mRight = mInflater.inflate(R.layout.right, null);
		pages.add (mLeft);
		pages.add (mMain);
		pages.add (mRight);

		mEditText = (EditText) findViewById(R.id.editText1);
		mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener (new OnClickListener () {
			@Override
			public void onClick (View v) {
				if (mEditText.getText().toString().equals("")) {
					mInput = 1;
				} else {
					mInput = Float.parseFloat(mEditText.getText().toString());
					Toast.makeText(HorzScrollWithImageMenu.this,
							"" + mInput + "%fix", Toast.LENGTH_SHORT).show();
				}
				mButtonClick = true;
			}
		});

		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new PagerAdapterManager(this, pages, mInput);
		mPager.setAdapter(mPagerAdapter);
		mPager.setOffscreenPageLimit(mPagerAdapter.getCount());
		mPager.setCurrentItem(1);
		mPager.setPageTransformer(true, new DepthPageTransformer());

	}  

	/**
	 * @author diotek
	 *
	 */
	private class PagerAdapterManager extends PagerAdapter {
		private Context mContext;

		private Vector<View> mPages;

		private float mInput;

		public PagerAdapterManager(Context context, Vector<View> pages,
				float input) {
			// TODO Auto-generated constructor stub
			this.mContext = context;
			this.mPages = pages;
			this.mInput = input;
		}

		/*
		 * return page size
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mPages.size();
		}

		@Override
		public Object instantiateItem(ViewGroup pager, int position) {
			// TODO Auto-generated method stub
			View page = mPages.get(position);
			pager.addView(page);
			return page;
		}

		@Override
		public void destroyItem(ViewGroup pager, int position, Object view) {
			// TODO Auto-generated method stub
			pager.removeView((View) view);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view.equals(object);
		}

		@Override
		public float getPageWidth(int position) {
			// TODO Auto-generated method stub

			if (position == 1) {
				return 1.0f;
			}
			// return input;
			return (float)0.8f;
		}

	}

}
