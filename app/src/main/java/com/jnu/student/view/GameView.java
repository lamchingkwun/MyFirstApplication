package com.jnu.student.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.jnu.student.R;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    public static final int NO_A_VALIDATE_POSITION = -1;
    private GameLoopThread gameLoopThread;
    private float touchX = NO_A_VALIDATE_POSITION;
    private float touchY = NO_A_VALIDATE_POSITION;

    int gameTimeRemaining = 30;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoopThread = new GameLoopThread();
        gameLoopThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        gameLoopThread.end();
    }
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event){

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 按下事件
                this.touchX = NO_A_VALIDATE_POSITION;
                this.touchY = NO_A_VALIDATE_POSITION;
                break;
            case MotionEvent.ACTION_MOVE:
                // 触摸移动事件
                break;
            case MotionEvent.ACTION_UP:
                this.touchX = event.getRawX()/this.getWidth();
                this.touchY = event.getRawY()/this.getHeight();
                //触模抬起事件
                break;
        }
        return true;
    }

    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            if (gameTimeRemaining > 0) {
                gameTimeRemaining--;
                timerHandler.postDelayed(this, 1000);
            } else {
                stopGame(); // 停止游戏
                // 显示得分
            }
        }
    };

    public void startGame() {
        // 设置游戏时间
        timerHandler.postDelayed(timerRunnable, 1000);
        // 其他游戏开始逻辑
    }

    public void stopGame() {
        timerHandler.removeCallbacks(timerRunnable);
        // 其他游戏停止逻辑
    }


    private class GameLoopThread extends Thread{
        boolean isLive=true;
        @Override
        public void run() {
            super.run();
            int hitNumber=0;
            ArrayList<GameSpriter> gameSpriters = new ArrayList<GameSpriter>();
            int iloop;
            for(iloop=0;iloop<2; ++iloop){
                gameSpriters.add(new GameSpriter(Math.random(),Math.random(),R.drawable.book_1));
                gameSpriters.add(new GameSpriter(Math.random(),Math.random(),R.drawable.book_2));
            }
            Paint circlePaint = new Paint();
            circlePaint.setColor(Color.BLACK);
            circlePaint.setAntiAlias(true);
            circlePaint.setStyle(Paint.Style.FILL);
            circlePaint.setTextSize(100);
            while(isLive){
                Canvas canvas = null;
                try {
                    canvas = GameView.this.getHolder().lockCanvas();
                    canvas.drawColor(Color.BLUE);
                    canvas.drawText("Your hit"+hitNumber,100,100,circlePaint);
                    for(GameSpriter gameSpriter : gameSpriters){
                        if(gameSpriter.detectCollection())
                            hitNumber++;
                        gameSpriter.move(canvas);
                    }
                    for(GameSpriter gameSpriter : gameSpriters){
                        gameSpriter.draw(canvas);
                    }

                }finally {
                        if (canvas != null) {
                            GameView.this.getHolder().unlockCanvasAndPost(canvas);
                        }
                }
                try{
                    Thread.sleep(100);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                }

            }

        public void end() {
            isLive = false;
        }
    }


    private class GameSpriter {
        private double relatedX;
        private double relatedY;
        private double direction;
        private final int iamgeResourceId;

        public GameSpriter(double relatedX, double relatedY, int iamgeResourceId){
            this.relatedX=relatedX;
            this.relatedY=relatedY;
            this.direction=Math.random()*2*Math.PI;
            this.iamgeResourceId=iamgeResourceId;
        }

        public void draw(Canvas canvas) {
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),this.iamgeResourceId);
            Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,100,100,true);
            canvas.drawBitmap(scaledBitmap,(int)(canvas.getWidth()*this.relatedX)
                    ,(int)(canvas.getHeight()*this.relatedY),null);
        }

        public void move(Canvas canvas) {
            this.relatedY += Math.sin(this.direction)*0.05;
            this.relatedX += Math.cos(this.direction)*0.05;
            if(this.relatedY>1) this.relatedY=0;
            if(this.relatedY<0) this.relatedY=1;
            if(this.relatedX>1) this.relatedX=0;
            if(this.relatedX<0) this.relatedX=1;
            if(Math.random()<0.1) this.direction=Math.random()*2*Math.PI;;
        }

        public boolean detectCollection() {
            double distanceX=Math.abs(this.relatedX-GameView.this.touchX);
            double distanceY=Math.abs(this.relatedY-GameView.this.touchY);
            if(distanceX<0.01 && distanceY<0.01){
                return true;

            }
            return false;
        }
    }
}

