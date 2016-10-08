package com.beyondsoft.ep2p.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义旋转的textView
 * 
 * @author sfit0213
 * 
 */
public class RotateTextView extends TextView {

	public RotateTextView(Context context) {
		super(context);
	}

	public RotateTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RotateTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.rotate(30f, getWidth() / 2, getHeight() / 2);
		// canvas.save();
		// canvas.restore();
		super.onDraw(canvas);
	}

}
