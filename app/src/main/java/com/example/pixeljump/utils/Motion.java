package com.example.pixeljump.utils;

import android.graphics.Bitmap;

public class Motion {

    private final Bitmap spriteSheet;
    private final int motionNumber;
    private final Bitmap[] sprites;
    private int playerAniIndexX = 0;
    private int aniTick;
    private final int aniDelay = 10;

    private int motionIndex = 0;
    private int motionTick;


    public Motion(Bitmap spriteSheet, int motionNumber, int imageHeight, int motionsWidth) {
        this.spriteSheet = spriteSheet;
        this.motionNumber = motionNumber;

        sprites = new Bitmap[motionNumber];

        for (int i = 0; i < motionNumber; i++)
            sprites[i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, motionsWidth * i, 0, motionsWidth, imageHeight));
    }

    public int getMotionNumber() {
        return motionNumber;
    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public Bitmap getSprite() {

        motionTick++;
        if (motionTick >= 5) {
            motionTick = 0;
            motionIndex++;
            if (motionIndex >= motionNumber)
                motionIndex = 0;
        }

        return sprites[motionIndex];
    }
    public void updateAnimation() {
        aniTick++;
        if (aniTick >= aniDelay) {
            aniTick = 0;
            playerAniIndexX++;
            if (playerAniIndexX >= 4)
                playerAniIndexX = 0;
        }


////        float gravity = 0.5f;
//        velocityY += 0.5f;
//
//        y += velocityY;
//
//        if (y >= (float) getHeight() / 2 - attackBitmap.getHeight() - 10) {
//            y = (float) getHeight() / 2 - attackBitmap.getHeight();
//            velocityY = 0;
//        }
    }


    private Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 8, bitmap.getHeight() * 8, false);
    }
}
