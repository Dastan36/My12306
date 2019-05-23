package com.neusoft.my12306.stations;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ListLetterView extends View {


    String[] b = { "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z" };
    Paint paint = new Paint();
    boolean showBkg = false;
    int choose = 0;

    public ListLetterView(Context context) {

        super(context);
    }

    public ListLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    //当父控件 需要绘制子控件
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (showBkg) {
            canvas.drawColor(Color.parseColor("#400000ff"));
        }

        int height = getHeight();
        int width = getWidth();

        int singleHeight = height / b.length;

        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.RED);//字体颜色
            paint.setTypeface(Typeface.DEFAULT_BOLD);//设置粗体
            paint.setAntiAlias(true); //是否取消锯齿
            paint.setTextSize(25);//字体大小

            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }

            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;


            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();
        }
    }


    /**
     * 接收 MotionEvent
     * 字母颜色..
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {


        final int action = event.getAction();//触碰事件类型
        final float y = event.getY();
        final int oldChoose = choose;
//        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length); //计算的选择的字母

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBkg = true;

                if (c >= 0 && c < b.length) {
//                        listener.onTouchingLetterChanged(b[c]);
                    listener.onLetterClick(b[c]);
                    choose = c;
                    invalidate();//重新绘制对象

                }
//                if (oldChoose != c && listener != null) {
//                    if (c >= 0 && c < b.length) {
//                        listener.onTouchingLetterChanged(b[c]);
//                        choose = c;
//                        invalidate();
//                    }
//                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (c >= 0 && c < b.length) {
//                        listener.onTouchingLetterChanged(b[c]);
                    choose = c;
                    invalidate();
                }
//                if (oldChoose != c && listener != null) {
//                    if (c >= 0 && c < b.length) {
//                        listener.onTouchingLetterChanged(b[c]);
//                        choose = c;
//                        invalidate();
//                    }
//                }
                break;
            case MotionEvent.ACTION_UP:
                showBkg = false;
                choose = -1;
                invalidate();
                break;
        }
        return true;
    }
    LetterOnClickListener listener = null;

    public void setListener(LetterOnClickListener listener) {
        this.listener = listener;
    }

    interface LetterOnClickListener{

        public void onLetterClick(String letter);
    }





}


