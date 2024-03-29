package com.example.pixeljump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.pixeljump.blocks.Blocks;
import com.example.pixeljump.characters.MainCharacters;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private final SurfaceHolder holder;
    private float x, y;

    private float velocityY;
    private Bitmap groundBitmap;
    private Bitmap attackBitmap;
    private final MainCharacters mainCharacters;
    private final Blocks block;

    private Handler handler;
    private Runnable updateRunnable;


    public GamePanel(Context context) {
        super(context);

        this.mainCharacters = new MainCharacters(context);
        this.block = new Blocks(context);

        holder = getHolder();
        holder.addCallback(this);


//        groundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.terrain);
//        groundBitmap = Bitmap.createScaledBitmap(groundBitmap, groundBitmap.getWidth() * 2, groundBitmap.getHeight() * 2, false);

//        groundBitmap = block.getFallBlock().getSprite(playerAniIndexX);

//        attackBitmap = mainCharacters.getIdleMotion().getSprite(playerAniIndexX);

    }

    public void render() {
        Canvas background = holder.lockCanvas();
        background.drawColor(Color.BLACK);

        groundBitmap = block.getFireBlockOff().getSprite();

        int groundWidth = groundBitmap.getWidth();
        int groundHeight = groundBitmap.getHeight();

        int numTiles = (getWidth() / groundWidth) + 2;

        for (int i = 0; i < numTiles; i++) {
            int left = i * (groundWidth + 15);

            background.drawBitmap(groundBitmap, left, (float) getHeight() / 2, null);
        }

        attackBitmap = mainCharacters.getIdleMotion().getSprite();

//        background.drawBitmap(charecterBitmap, x, getHeight() - groundHeight - charecterBitmap.getHeight(), null);
        background.drawBitmap(attackBitmap, x, y, null);


        holder.unlockCanvasAndPost(background);
    }

    public void updateAnimation() {
//        aniTick++;
//        if (aniTick >= aniDelay) {
//            aniTick = 0;
//            playerAniIndexX++;
//            if (playerAniIndexX >= 4)
//                playerAniIndexX = 0;
//        }


//        float gravity = 0.5f;
        velocityY += 0.5f;

        y += velocityY;

        if (y >= (float) getHeight() / 2 - attackBitmap.getHeight() - 10) {
            y = (float) getHeight() / 2 - attackBitmap.getHeight();
            velocityY = 0;
        }
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        handler = new Handler();
        updateRunnable = new Runnable() {

            long lastFPScheck = System.currentTimeMillis();
            int fps = 0;

            @Override
            public void run() {

//                updateAnimation();
                render();

                fps++;

                long now = System.currentTimeMillis();
                if (now - lastFPScheck >= 1000) {
                    System.out.println("FPS: " + fps);
                    fps = 0;
                    lastFPScheck += 1000;
                }

                // Schedule next update
                handler.postDelayed(this, 20);
            }
        };

        handler.post(updateRunnable);
    }


    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX() - 180;
            y = event.getY() - 135;
            velocityY = -.5f;
        }

        return true;
    }


    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        handler.removeCallbacks(updateRunnable);
    }
}
