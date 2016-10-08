package com.beyondsoft.ep2p.view;

import com.beyondsoft.ep2p.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义索引条
 * 
 * @author hardy.zhou
 *
 */
public class SideBar extends View {

	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 按住改变背景色
	private boolean showBkg;
	public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#" };
	/** 被选中位置 */
	int choose = -1;
	private Paint paint = new Paint();

	public SideBar(Context context) {
		super(context);
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(Color.TRANSPARENT);
		}

		float height = getHeight();
		float width = getWidth();
		// 计算单个字母高度
		float singleHeight = height / (b.length);
		for (int i = 0; i < b.length; i++) {
			paint.setAntiAlias(true);
			if (showBkg) {
				paint.setColor(Color.TRANSPARENT);
			} else {
				paint.setColor(getResources().getColor(R.color.sky_blue));
			}
			paint.setTextSize(22);
			paint.setFakeBoldText(true);
			if (i == choose) {
				// 选中的颜色
				paint.setColor(getResources().getColor(R.color.blue));
				// 加粗
				
			}else{
				paint.setColor(getResources().getColor(R.color.sky_blue));
			}
			// 设置文本坐标
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;

			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float y = event.getY();
		final int oldChoose = choose;
		final int c = (int) (y / getHeight() * b.length);
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < b.length) {
					listener.onTouchingLetterChanged(c);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < b.length) {
					listener.onTouchingLetterChanged(c);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(int s);
	}
}
