package com.example.pixeljump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private final SurfaceHolder holder;
    private float x, y;
    private final CharacterMotion attackMotion;
    private final GameLoop gameLoop;
    private int playerAniIndexX = 0;
    private int aniTick;
    private final int aniDelay = 10;


    private float velocityY;
    private Bitmap groundBitmap;
    private Bitmap charecterBitmap;

    public GamePanel(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);

        gameLoop = new GameLoop(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.owlet_attack, options);

        attackMotion = new CharacterMotion(spriteSheet, 4, 32, 73);

        groundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.terrain);
        groundBitmap = Bitmap.createScaledBitmap(groundBitmap, groundBitmap.getWidth() * 2, groundBitmap.getHeight() * 2, false);

        charecterBitmap = attackMotion.getSprite(playerAniIndexX);
    }

    public void render() {
        Canvas background = holder.lockCanvas();
        background.drawColor(Color.BLACK);


        int groundWidth = groundBitmap.getWidth();
        int groundHeight = groundBitmap.getHeight();

        int numTiles = (getWidth() / groundWidth) + 2;

        for (int i = 0; i < numTiles; i++) {
            int left = i * (groundWidth + 15);

            background.drawBitmap(groundBitmap, left, getHeight() - groundHeight, null);
        }

        charecterBitmap = attackMotion.getSprite(playerAniIndexX);

//        background.drawBitmap(charecterBitmap, x, getHeight() - groundHeight - charecterBitmap.getHeight(), null);
        background.drawBitmap(charecterBitmap, x, y, null);

        holder.unlockCanvasAndPost(background);
    }

    public void updateAnimation() {
        aniTick++;
        if (aniTick >= aniDelay) {
            aniTick = 0;
            playerAniIndexX++;
            if (playerAniIndexX >= 4)
                playerAniIndexX = 0;
        }


//        float gravity = 0.5f;
        velocityY += 0.5f;

        y += velocityY;

        if (y >= getHeight() - groundBitmap.getHeight() - charecterBitmap.getHeight() - 10) {
            y = getHeight() - groundBitmap.getHeight() - charecterBitmap.getHeight();
            velocityY = 0;
        }
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startGameLoop();
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

    }
}
