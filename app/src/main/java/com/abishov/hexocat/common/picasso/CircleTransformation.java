package com.abishov.hexocat.common.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.graphics.Bitmap.Config.ARGB_8888;
import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Shader.TileMode.CLAMP;

@Singleton
final class CircleTransformation implements Transformation {

    @Inject
    CircleTransformation() {
        // keeping this constructor for dagger
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        int size = bitmap.getWidth();
        Bitmap rounded = Bitmap.createBitmap(size, size, ARGB_8888);
        Canvas canvas = new Canvas(rounded);

        BitmapShader shader = new BitmapShader(bitmap, CLAMP, CLAMP);
        Paint shaderPaint = new Paint(ANTI_ALIAS_FLAG);
        shaderPaint.setShader(shader);

        RectF rect = new RectF(0, 0, size, size);
        float radius = size / 2f;
        canvas.drawRoundRect(rect, radius, radius, shaderPaint);

        bitmap.recycle();
        return rounded;
    }

    @Override
    public String key() {
        return "circle";
    }
}