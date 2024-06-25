package com.example.pixeljump.utils;

import android.graphics.Bitmap;

public class Motion {

    private final int motionNumber;
    private final Bitmap[] sprites;

    private int motionDelay = 5;
    private int motionIndex = 0;
    private int motionTick = 0;

    public Motion(Bitmap spriteSheet, int motionNumber, int imageHeight, int motionsWidth) {
        this.motionNumber = motionNumber;

        sprites = new Bitmap[motionNumber];

        for (int i = 0; i < motionNumber; i++)
            sprites[i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, motionsWidth * i, 0, motionsWidth, imageHeight));
    }

    public int getMotionNumber() {
        return motionNumber;
    }

    public Bitmap getSprite() {

        motionTick++;
        if (motionTick >= motionDelay) {
            motionTick = 0;
            motionIndex++;
            if (motionIndex >= motionNumber)
                motionIndex = 0;
        }

        return sprites[motionIndex];
    }

    public int getMotionDelay() {
        return motionDelay;
    }

    private Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 8, bitmap.getHeight() * 8, false);
    }

    public void setMotionDelay(int motionDelay) {
        this.motionDelay = motionDelay;
    }

    public int getMotionIndex() {
        return motionIndex;
    }

    public int getMotionTick() {
        return motionTick;
    }
}
