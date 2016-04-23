package com.example.kalli.camerahw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kalli on 4/19/16.
 */
public class MyCanvas extends View {

    List<Path> paths;
    Paint pathPaint;
    int currentColor;
    List<Integer> colors;
    List<Loc> doubleTapIcons;
    List<Loc> longPressIcons;
    List<Integer> order;

    public MyCanvas(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        paths = new ArrayList<>();
        colors = new ArrayList<>();
        doubleTapIcons = new ArrayList<>();
        longPressIcons = new ArrayList<>();
        order = new ArrayList<>();
        currentColor = Color.RED;
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.RED);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(15);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < paths.size(); i++)
        {
            Path path = paths.get(i);
            int color = colors.get(i);
            pathPaint.setColor(color);
            canvas.drawPath(path, pathPaint);
        }
        for(int i = 0; i < doubleTapIcons.size(); i++)
        {
            Matrix m = new Matrix();
            m.setTranslate(doubleTapIcons.get(i).getX() - 75, doubleTapIcons.get(i).getY() - 75);
            Bitmap thumbBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getResources(), R.drawable.mustache), 150, 150);
            canvas.drawBitmap(thumbBitmap, m, null);
        }
        for(int i = 0; i < longPressIcons.size(); i++)
        {
            Matrix m = new Matrix();
            m.setTranslate(longPressIcons.get(i).getX()-75, longPressIcons.get(i).getY()-75);
            Bitmap thumbBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getResources(), R.drawable.star), 150, 150);
            canvas.drawBitmap(thumbBitmap, m, null);
        }
        pathPaint.setColor(currentColor);
    }

    public void changeColor(int color)
    {
        pathPaint.setColor(color);
        currentColor = color;
    }

    public void addPath(int id, int x, int y) {
        Path path = new Path();
        path.moveTo(x, y);
        paths.add(id, path);
        colors.add(id, pathPaint.getColor());
        order.add(id, 0);
        invalidate();
    }

    public void updatePath(int id, int x, int y) {
        if(paths.size() > 0) {
            Path path = paths.get(id);//paths.get(paths.size() - 1);
            if (path != null) {
                path.lineTo(x, y);
            }
            invalidate();
        }
    }

    public void removePath(int id) {

        if(paths.get(id) != null)
        {
            paths.remove(id);
            colors.remove(id);
        }
        invalidate();
    }

    public void undo()
    {
        if(order.size() > 0)
        {
            if(order.get(order.size() -1) == 0)
            {
                paths.remove(paths.size() - 1);
                colors.remove(colors.size() - 1);
            }
            else if(order.get(order.size() -1) == 1)
            {
                doubleTapIcons.remove(doubleTapIcons.size() - 1);
            }
            else
            {
                longPressIcons.remove(longPressIcons.size() - 1);
            }
            order.remove(order.size() - 1);
            invalidate();
        }

    }

    public void clear() {
        paths.clear();
        colors.clear();
        order.clear();
        doubleTapIcons.clear();
        longPressIcons.clear();
        invalidate();
    }

    public void addDoubleTapLoc(Loc loc){
        doubleTapIcons.add(loc);
        order.add(1);
        invalidate();
    }

    public void addLongPressLoc(Loc loc){
        longPressIcons.add(loc);
        order.add(2);
        invalidate();
    }


}
