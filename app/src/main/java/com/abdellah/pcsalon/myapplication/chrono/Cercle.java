package com.abdellah.pcsalon.myapplication.chrono;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Cercle extends View {

    private Paint paint;

    public Cercle(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public Cercle(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public Cercle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setColor(Color.RED);

        float cx = getMeasuredWidth() / 2;
        float cy = getMeasuredHeight() / 2;
        float radius = Math.min(cx, cy);

        canvas.drawCircle(cx, cy, radius, paint);

        super.onDraw(canvas);
    }

    public void repaint(int c){
        this.paint.setColor(c);
    }

}