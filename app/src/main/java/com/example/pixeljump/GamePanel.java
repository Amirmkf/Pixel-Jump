package com.example.pixeljump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.pixeljump.characters.enemy.Bat;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private final SurfaceHolder holder;
    private float x, y;

    private float velocityY;

    private Bitmap groundBitmap;
    private Bitmap attackBitmap;
    private Bitmap jumpButtonBitmap;
    private Bitmap shieldButtonBitmap;
    private Bitmap gunButtonBitmap;

    private final MainCharacters mainCharacters;
    private final Bat bat;
    private final Blocks block;
    private Handler handler;
    private Runnable updateRunnable;


    private int blockSize; // Size of each block
    private int[] blockPositions; // Array to store the x positions of blocks
    private int screenWidth, screenHeight;

    private Context context;

    public GamePanel(Context context) {
        super(context);
        this.context = context;
        this.mainCharacters = new MainCharacters(context);
        this.bat = new Bat(context);
        this.block = new Blocks(context);

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

    }

    public void render() {
        Canvas background = holder.lockCanvas();
        background.drawColor(Color.BLACK);

//        groundBitmap = block.getFireBlockOff().getSprite();
//
//        int groundWidth = groundBitmap.getWidth();
//        int groundHeight = groundBitmap.getHeight();
//
//        int numTiles = (getWidth() / groundWidth) + 2;
//
//        for (int i = 0; i < numTiles; i++) {
//            int left = i * (groundWidth + 15);
//
//            background.drawBitmap(groundBitmap, left, (float) getHeight() / 2, null);
//
//        }

        groundBitmap = block.getFireBlockOff().getSprite();

        blockSize = groundBitmap.getWidth();

        if (blockPositions == null || blockPositions.length == 0) {
            // Initialize block positions based on screen width
            blockPositions = new int[getWidth() / blockSize];
            for (int i = 0; i < blockPositions.length; i++) {
                blockPositions[i] = i * blockSize;
            }
        }

        for (int x : blockPositions) {
            background.drawBitmap(groundBitmap, x, (float) getHeight() / 2, null);
        }


        background.drawBitmap(shieldButtonBitmap, getWidth() - shieldButtonBitmap.getWidth() - 40
                , getHeight() - shieldButtonBitmap.getHeight() - 200, null);

        background.drawBitmap(jumpButtonBitmap, (float) getWidth() / 3 * 2 - jumpButtonBitmap.getWidth() - 40
                , getHeight() - jumpButtonBitmap.getHeight() - 200, null);

        background.drawBitmap(gunButtonBitmap, (float) getWidth() / 3 - gunButtonBitmap.getWidth() - 40
                , getHeight() - gunButtonBitmap.getHeight() - 200, null);


        attackBitmap = mainCharacters.getDamageMotion().getSprite();

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
//            x = event.getX() - 180;
//            y = event.getY() - 135;
            velocityY = -.5f;
            jumpButton(event.getX(), event.getY());
        }

        return true;
    }

    public void jumpButton(float x, float y) {
        float temp = ((float) getWidth() / 3);
        if (((float) getWidth() / 3 - 40) < x && x < ((float) getWidth() / 3 + jumpButtonBitmap.getWidth() + 40)
                && y < getHeight() - 200 && y > getHeight() - gunButtonBitmap.getHeight() - 200) {
            Toast.makeText(context, "jumpButton", Toast.LENGTH_SHORT).show();
            moveBlocksLeftAndAddNewBlock();
        }
    }
//    (float) getWidth() / 3 - gunButtonBitmap.getWidth() - 40
//            , getHeight() - gunButtonBitmap.getHeight() - 200


    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        handler.removeCallbacks(updateRunnable);
    }

    private void moveBlocksLeftAndAddNewBlock() {
        for (int i = 0; i < blockPositions.length; i++) {
            blockPositions[i] -= 5; // Adjust the speed as needed
            if (blockPositions[i] + blockSize < 0) {
                // If the block moves off screen, reset its position to the right
                blockPositions[i] = getWidth();
            }
        }

        // Move blocks to the left
        for (int i = 0; i < blockPositions.length; i++) {
            blockPositions[i] -= blockSize; // Move one block width to the left
        }
        // Remove leftmost block
        System.arraycopy(blockPositions, 1, blockPositions, 0, blockPositions.length - 1);
        // Create a new block and add it to the right
        blockPositions[blockPositions.length - 1] = screenWidth - blockSize;
    }
}
