package com.RandNprograming.tapemeasurecalculator.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.RandNprograming.tapemeasurecalculator.interfaces.Calculator;

public class AndroidFastRenderView extends SurfaceView implements Runnable {

    public static Calculator getCalculator() {
        return calculator;
    }

    private static AndroidTapemeasureCalculator calculator;

    private Bitmap frameBuffer;
    private Thread renderThread = null;
    private SurfaceHolder holder;
    volatile boolean running = false;

    public AndroidFastRenderView(AndroidTapemeasureCalculator calc, Bitmap frameBuffer) {
        super(calc);
        calculator = calc;
        this.frameBuffer = frameBuffer;
        this.holder = getHolder();
    }

    public void resume() {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

     @Override
    public void run() {
        Rect dstRect = new Rect();
         long startTime = System.nanoTime();
         while(running) {
             if (!holder.getSurface().isValid()) continue;

             float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
             startTime = System.nanoTime();

             calculator.getCurrentScreen().update(deltaTime);
             calculator.getCurrentScreen().present(deltaTime);

             Canvas canvas = holder.lockCanvas();
             canvas.getClipBounds(dstRect);
             canvas.drawBitmap(frameBuffer, null, dstRect, null);
             holder.unlockCanvasAndPost(canvas);
         }
    }

    public void pause() {
        running = false;
        while(true) {
            try {
                renderThread.join();
                return;
            }
            catch (InterruptedException e) {
                //retry
            }
        }
    }

}
