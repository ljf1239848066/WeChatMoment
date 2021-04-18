package com.lxzh123.wechatmoment.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by lxzh on 2018/5/30.
 */

public class RoundBackgroundColorSpan extends ReplacementSpan {

    private int backColor;
    private int foreColor;

    public RoundBackgroundColorSpan(int backColor, int foreColor) {
        this.backColor = backColor;
        this.foreColor = foreColor;
    }

    @Override
    public void updateMeasureState(TextPaint p) {
        super.updateMeasureState(p);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fontMetricsInt) {
        return ((int) paint.measureText(text, start, end) + 60);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        int color1 = paint.getColor();
        if (this.backColor != 0) {
            Log.i("RoundBackgroundColorSpan", "this.backColor=" + this.backColor);
            paint.setColor(this.backColor);
            canvas.drawRoundRect(new RectF(x, top + 1, x + ((int) paint.measureText(text, start, end) + 40), bottom - 1), 15, 15, paint);
        }
        paint.setColor(this.foreColor);
        canvas.drawText(text, start, end, x + 20, y, paint);
        paint.setColor(color1);
    }
}
