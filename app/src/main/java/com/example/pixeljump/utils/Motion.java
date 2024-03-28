package com.example.pixeljump.utils;

import android.graphics.Bitmap;

public class Motion {

    private final Bitmap spriteSheet;
    private final int motionNumber;
    private final Bitmap[] sprites;


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

    public Bitmap getSprite(int index) {
//        TODO
        if ( index> motionNumber - 1)
            System.out.println("out of range 'getSprite'");
//            return sprites[motionNumber - 1];


        return sprites[index];
    }

    private Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 8, bitmap.getHeight() * 8, false);
    }

}
