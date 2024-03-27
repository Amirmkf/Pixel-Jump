package com.example.pixeljump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private final SurfaceHolder holder;
    private float x, y;
    private final CharactersMotion attackMotion;
    private final GameLoop gameLoop;
    private int playerAniIndexX = 0;
    private int aniTick;
    private final int aniSpeed = 10;

    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        gameLoop = new GameLoop(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_attack, options);

        attackMotion = new CharactersMotion(spriteSheet, 4, 32, 73);
    }

    public void render() {
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

        c.drawBitmap(attackMotion.getSprite(playerAniIndexX), x, y, null);

        holder.unlockCanvasAndPost(c);
    }

    public void updateAnimation() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            playerAniIndexX++;
            if (playerAniIndexX >= 4)
                playerAniIndexX = 0;
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
