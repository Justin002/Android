package com.beyondsoft.ep2p.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
public class BlurBitmapDisplayer implements BitmapDisplayer {

	protected final int radius;
	protected Context context;

	public BlurBitmapDisplayer(Context context,int radius) {
		this(radius,context,0);
	}

	public BlurBitmapDisplayer(int radius,Context context,int marginPixels) {
		this.radius = radius;
		this.context=context;
	}

	@Override
	public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
		if (!(imageAware instanceof ImageViewAware)) {
			throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
		}
		//bitmap=Blur.fastblur(context, bitmap,radius, false);
//		Bitmap overlay = Bitmap.createBitmap(100,50,Bitmap.Config.RGB_565);
//		Paint paint = new Paint();
//		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
//		Canvas canvas = new Canvas(overlay);
//		canvas.drawBitmap(bitmap, 0, 0, paint);
//		imageAware.setImageBitmap(FastBlur.doBlur(overlay,60, true));

		
//	        Bitmap blurred = blurRenderScript(bitmap,radius);//second parametre is radius
//	        imageAware.setImageBitmap(blurred);                          //radius decide blur amount
		
		blur(bitmap, imageAware);
		
		
//		float width_bkg = bkg.getWidth();
//		float height_bkg = bkg.getHeight();
//		float measuredWidth_view = view.getMeasuredWidth();
//		float measuredHeight_view = view.getMeasuredHeight();
//		float x = measuredWidth_view / width_bkg;
//		float y = measuredHeight_view / height_bkg;
//
//		Bitmap overlay = Bitmap.createBitmap(100,50,Bitmap.Config.RGB_565);
//		Canvas canvas = new Canvas(overlay);
//		canvas.translate(-view.getLeft(), -view.getTop());
//		//canvas.scale(1 / y, 1 / x);
//		Paint paint = new Paint();
//		//paint.setARGB(50, 0, 0, 0);
//		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
//		canvas.drawBitmap(bkg, 0, 0, paint);

	}
	
	 private Bitmap blurRenderScript(Bitmap smallBitmap, int radius) {

	        try {
	            smallBitmap = RGB565toARGB888(smallBitmap);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }


	        Bitmap bitmap = Bitmap.createBitmap(
	                smallBitmap.getWidth(), smallBitmap.getHeight(),
	                Bitmap.Config.ARGB_8888);

	        RenderScript renderScript = RenderScript.create(context);

	        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
	        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

	        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
	                Element.U8_4(renderScript));
	        blur.setInput(blurInput);
	        blur.setRadius(radius); // radius must be 0 < r <= 25
	        blur.forEach(blurOutput);

	        blurOutput.copyTo(bitmap);
	        renderScript.destroy();

	        return bitmap;

	    }
	 
	 private Bitmap RGB565toARGB888(Bitmap img) throws Exception {
	        int numPixels = img.getWidth() * img.getHeight();
	        int[] pixels = new int[numPixels];

	        //Get JPEG pixels.  Each int is the color values for one pixel.
	        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

	        //Create a Bitmap of the appropriate format.
	        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

	        //Set RGB pixels.
	        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
	        return result;
	    }
	 
	 private void blur(Bitmap bkg, ImageAware view) {
		// long startMs = System.currentTimeMillis(); 
		    float scaleFactor = 1; 
		   // float radius = 20; 
		    //if (downScale.isChecked()) { 
		        scaleFactor = 8; 
		       // radius = 2; 
		    //} 
		  
		    Bitmap overlay = Bitmap.createBitmap((int) (bkg.getWidth()/scaleFactor), 
		            (int) (bkg.getHeight()/scaleFactor), Bitmap.Config.ARGB_8888); 
		    Canvas canvas = new Canvas(overlay); 
		   // canvas.translate(-bkg.getLeft()/scaleFactor, -view.getTop()/scaleFactor); 
		    canvas.scale(1 / scaleFactor, 1 / scaleFactor); 
		    //Paint paint = new Paint(); 
		    //paint.setFlags(Paint.FILTER_BITMAP_FLAG); 
		    canvas.drawBitmap(bkg, 0, 0, null); 	  
		    overlay = FastBlur.doBlur(overlay, (int)radius, true); 
		    view.setImageBitmap(overlay);
	}
}
