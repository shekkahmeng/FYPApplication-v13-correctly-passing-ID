package com.shekkahmeng.fypapplication.utility;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.shekkahmeng.fypapplication.R;

/**
 * Created by vc on 8/17/15.
 */
public class ImageUtility {

    private static Bitmap defaultBitmap;
    public static Bitmap getDefaultCircleBitmap(Resources resources) {
        if(defaultBitmap == null) {
            defaultBitmap = ImageUtility.createCircleBitmap(BitmapFactory.decodeResource(resources, R.drawable.icon), defaultBitmap);
        }
        return defaultBitmap;
    }

    public static Bitmap createCircleBitmap(Bitmap bitmap, Bitmap defaultBitmap) {
        if (bitmap == null) {
            bitmap = defaultBitmap;
        }

        int radius = 100;

        Bitmap scaledBitmap = bitmap;

        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
            scaledBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);

        Bitmap output = Bitmap.createBitmap(scaledBitmap.getWidth(), scaledBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 50;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(scaledBitmap, rect, rect, paint);

        return output;
    }
}
