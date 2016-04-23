package com.example.kalli.camerahw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by kalli on 4/19/16.
 */
public class PhotoActivity extends AppCompatActivity implements View.OnClickListener{

    MyCanvas canvas;
    TouchHandler touchHandler;

    ImageView imageView;
    Button red;
    Button blue;
    Button green;
    Button undo;
    Button clear;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_activity);
        red = (Button)findViewById(R.id.red);
        red.setOnClickListener(this);
        blue = (Button)findViewById(R.id.blue);
        blue.setOnClickListener(this);
        green = (Button)findViewById(R.id.green);
        green.setOnClickListener(this);
        undo = (Button)findViewById(R.id.undo);
        undo.setOnClickListener(this);
        clear = (Button)findViewById(R.id.clear);
        clear.setOnClickListener(this);
        done = (Button)findViewById(R.id.done);
        done.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.image);

        Bitmap bm = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
        imageView.setImageBitmap(bm);

        touchHandler = new TouchHandler(this);

        canvas = (MyCanvas) findViewById(R.id.myCanvas);
        canvas.setOnTouchListener(touchHandler);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == red.getId()) {
            canvas.changeColor(Color.RED);

        } else if (view.getId() == blue.getId()) {
            canvas.changeColor(Color.BLUE);

        } else if (view.getId() == green.getId()) {
            canvas.changeColor(Color.GREEN);

        } else if (view.getId() == undo.getId()){
            canvas.undo();

        } else if (view.getId() == clear.getId()){
            canvas.clear();

        } else if (view.getId() == done.getId()){
            canvas.clear();

            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);

        }

    }

    public void onDoubleTap(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        Loc loc = new Loc(x, y);

        canvas.addDoubleTapLoc(loc);
    }

    public void onLongPress(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        Loc loc = new Loc(x, y);

        canvas.addLongPressLoc(loc);
    }

    public void addNewPath(int id, int x, int y) {
        canvas.addPath(id, x, y);
    }

    public void updatePath(int id, int x, int y) {
        canvas.updatePath(id, x, y);
    }

    public void removePath(int id) {
        canvas.removePath(id);
    }


}