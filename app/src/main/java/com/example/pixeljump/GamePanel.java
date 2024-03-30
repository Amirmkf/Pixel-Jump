package com.example.pixeljump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.pixeljump.blocks.Blocks;
import com.example.pixeljump.characters.MainCharacters;
import com.example.pixeljump.characters.enemy.Bat;
import com.example.pixeljump.utils.Motion;

import java.util.Arrays;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private final SurfaceHolder holder;

//    private float velocityY;

    private Bitmap backgroundBitmap;
    private Bitmap jumpButtonBitmap;
    private Bitmap shieldButtonBitmap;
    private Bitmap gunButtonBitmap;

    private final MainCharacters mainCharacters;
    private final Bat bat;
    private final Blocks[] blocks;

    private Handler handler;
    private Runnable updateRunnable;

    private final int[] blockPositions = new int[4];
    private final int[] blocksBitmap = new int[4];


    enum actions {IDLE, JUMP, ATTACK, DAMAGE, DEAD}

    actions characterAction = actions.IDLE;

    private int actionDelay = 0;
//    Context context;

    public GamePanel(Context context) {
        super(context);

//        this.context = context;

        this.mainCharacters = new MainCharacters(context);
        this.bat = new Bat(context);

        this.blocks = new Blocks[4];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new Blocks(context);
        }


        holder = getHolder();
        holder.addCallback(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        jumpButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.play, options);
        jumpButtonBitmap = Bitmap.createScaledBitmap(jumpButtonBitmap, jumpButtonBitmap.getWidth() * 14, jumpButtonBitmap.getHeight() * 14, false);

        shieldButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shield, options);
        shieldButtonBitmap = Bitmap.createScaledBitmap(shieldButtonBitmap, shieldButtonBitmap.getWidth() * 5, shieldButtonBitmap.getHeight() * 5, false);

        gunButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gun, options);
        gunButtonBitmap = Bitmap.createScaledBitmap(gunButtonBitmap, gunButtonBitmap.getWidth() * 5, gunButtonBitmap.getHeight() * 5, false);

        backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_brown, options);


        Arrays.fill(blocksBitmap, 3);
    }

    public void render() {
        Canvas background = holder.lockCanvas();
        background.drawColor(Color.BLACK);

//        for (int i = 0; i < getWidth() / backgroundBitmap.getWidth() + 1; i++) {
//            for (int j = 0; j < getHeight() / backgroundBitmap.getHeight() + 1; j++) {
//                background.drawBitmap(backgroundBitmap,
//                        i * backgroundBitmap.getWidth()
//                        , j * backgroundBitmap.getHeight(), null);
//            }
//        }

        Rect srcRect = new Rect(0, 0, backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
        Rect destRect = new Rect(0, 0, background.getWidth(), background.getHeight());
        background.drawBitmap(backgroundBitmap, srcRect, destRect, null);

        //Draw background
        for (int i = 0; i < blockPositions.length; i++) {
            switch (blocksBitmap[i]) {
                case 0:
                    background.drawBitmap(blocks[i].getFallBlock().getSprite(), blockPositions[i], (float) getHeight() / 2, null);
                    break;

                case 1:
                    background.drawBitmap(blocks[i].getFireBlock().getSprite(), blockPositions[i], (float) getHeight() / 2, null);
                    break;

                default:
                    background.drawBitmap(blocks[i].getDefaultBlock(), blockPositions[i], (float) getHeight() / 2, null);

            }
        }

        background.drawBitmap(shieldButtonBitmap,
                getWidth() - shieldButtonBitmap.getWidth() - 40
                , getHeight() - shieldButtonBitmap.getHeight() - 200, null);

        background.drawBitmap(jumpButtonBitmap,
                (float) getWidth() / 3 * 2 - jumpButtonBitmap.getWidth() - 40
                , getHeight() - jumpButtonBitmap.getHeight() - 200, null);

        background.drawBitmap(gunButtonBitmap,
                (float) getWidth() / 3 - gunButtonBitmap.getWidth() - 40
                , getHeight() - gunButtonBitmap.getHeight() - 200, null);


        //Draw character base of action
        switch (characterAction) {
            case IDLE:
                Bitmap idleBitmap = mainCharacters.getIdleMotion().getSprite();
                background.drawBitmap(idleBitmap, 0, (float) getHeight() / 2 - idleBitmap.getHeight(), null);

               if (actionDelay != 0)
                   actionDelay = 0;

                break;

            case JUMP:
                Motion jumpMotion = mainCharacters.getJumpMotion();
                Bitmap jumpBitmap = jumpMotion.getSprite();
                background.drawBitmap(jumpBitmap, 0, (float) getHeight() / 2 - jumpBitmap.getHeight(), null);

                actionDelay++;

                if (actionDelay == jumpMotion.getMotionNumber() * jumpMotion.getMotionDelay()) {
                    characterAction = actions.IDLE;
                    actionDelay = 0;
                }

                break;

            case ATTACK:
                Motion attackMotion = mainCharacters.getAttackMotion();
                Bitmap attackBitmap = attackMotion.getSprite();
                background.drawBitmap(attackBitmap, 0, (float) getHeight() / 2 - attackBitmap.getHeight(), null);

                actionDelay++;

                if (actionDelay == attackMotion.getMotionNumber() * attackMotion.getMotionDelay()) {
                    characterAction = actions.IDLE;
                    actionDelay = 0;
                }

                break;
        }


        holder.unlockCanvasAndPost(background);
    }

    public void updateAnimation() {

//        float gravity = 0.5f;
//        velocityY += 0.5f;
//
//        y += velocityY;
//
//        if (y >= (float) getHeight() / 2 - attackBitmap.getHeight() - 10) {
//            y = (float) getHeight() / 2 - attackBitmap.getHeight();
//            velocityY = 0;
//        }
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        for (int i = 0; i < blockPositions.length; i++) {
            blockPositions[i] = i * getWidth() / blockPositions.length;
        }

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
                handler.postDelayed(this, 10);
            }
        };

        handler.post(updateRunnable);
    }


    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            x = event.getX() - 180;
//            y = event.getY() - 135;
//            velocityY = -.5f;
            jumpButton(event.getX(), event.getY());
            attackButton(event.getX(), event.getY());
        }

        return true;
    }

    public void jumpButton(float x, float y) {
        if (((float) getWidth() / 3 * 2 - jumpButtonBitmap.getWidth() - 40) < x && x < ((float) getWidth() / 3 * 2 - 40)
                && y < getHeight() - 200 && y > getHeight() - jumpButtonBitmap.getHeight() - 200) {

            moveBlocksLeftAndAddNewBlock();
            characterAction = actions.JUMP;
        }
    }

    public void attackButton(float x, float y) {
        if ((float) getWidth() / 3 - 40 - gunButtonBitmap.getWidth() < x && x < (float) getWidth() / 3 - 60
                && y < getHeight() - 200 && y > getHeight() - gunButtonBitmap.getHeight() - 200) {

            characterAction = actions.ATTACK;
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        handler.removeCallbacks(updateRunnable);
    }

    private void moveBlocksLeftAndAddNewBlock() {
        for (int i = 0; i < blockPositions.length; i++) {

            if (blockPositions[i] + getWidth() / blocksBitmap.length < 0) {
                // If the block moves off screen, reset its position to the right
                blockPositions[i] = getWidth();
            }
        }

        // Move blocks to the left
        for (int i = 0; i < blockPositions.length; i++) {
            blockPositions[i] -= getWidth() / blocksBitmap.length; // Move one block width to the left
        }

        // Remove leftmost block
        System.arraycopy(blockPositions, 1, blockPositions, 0, blockPositions.length - 1);
        System.arraycopy(blocksBitmap, 1, blocksBitmap, 0, blocksBitmap.length - 1);

        // Create a new block and add it to the right
        blockPositions[blockPositions.length - 1] = getWidth() - getWidth() / blocksBitmap.length;
        blocksBitmap[blocksBitmap.length - 1] = new Random().nextInt(3);
    }
}
