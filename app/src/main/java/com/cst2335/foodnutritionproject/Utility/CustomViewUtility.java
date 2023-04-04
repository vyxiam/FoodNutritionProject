package com.cst2335.foodnutritionproject.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;

/**
 * This class contains methods that are used to modify the appearance of VIew and be used in
 * the activity.
 *
 * @author Van Vy Nguyen
 * @version 1.0
 */
public class CustomViewUtility {
    private int screenHeight;
    private static final CustomViewUtility utility = new CustomViewUtility();

    private CustomViewUtility(){
        screenHeight = 0;
    }

    public static CustomViewUtility getViewUtilityClass(){
        return utility;
    }

    /**
     * This method is used to crop a Bitmap into a rounded circle shape in the Activity.
     * The method creates a new Bitmap from the original given Bitmap. The Canvas is used to draw
     * a circle with the radius equals half of the bitmap, then use the Paint to fill up the inside
     * area of the circle drawn by the Canvas. The Paint uses the BitmapShader object, which uses the
     * original bitmap, to fill.
     * There is an option to use the offset value to reduce the radius of the circle to have a space
     * for the effect/border of the ImageView.
     * Note:
     * - The method requires the bitmap to has a background attribute to be set as transparent
     * - The width and height of the bitmap is assumed to be equal, and be set in the layout
     * @param bitmap the bitmap of source image that needs to be cropped.
     * @param offset the value to reduce the radius of the circle.
     * @return the cropped bitmap version
     */
    public Bitmap getRoundedBitmap(Bitmap bitmap, float offset){
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true); //enable anti-aliasing
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        float radius = Math.min(bitmap.getWidth(),bitmap.getHeight()) / 2f - offset;
        canvas.drawCircle(bitmap.getWidth()/2f,bitmap.getHeight() /2f,radius,paint);
        
        return result;
    }

    public int getDefaultDeviceHeight(FragmentActivity fragmentActivity){
        WindowManager windowManager = fragmentActivity.getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getScreenHeight(){
        return screenHeight;
    }
}
