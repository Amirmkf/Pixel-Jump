package com.example.pixeljump;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CharactersMotion {

    private final Bitmap spriteSheet;
    private final int motionNumber;
    private final Bitmap[] sprites;

    public CharactersMotion(Bitmap spriteSheet, int motionNumber, int imageHeight, int motionsWidth) {
        this.spriteSheet = spriteSheet;
        this.motionNumber = motionNumber;

        sprites = new Bitmap[motionNumber];

        for (int i = 0; i < sprites.length; i++)
            sprites[i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, motionsWidth * i, 0, motionsWidth, imageHeight));
    }


    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public Bitmap getSprite(int xPos) {
        if (xPos > motionNumber - 1)
            return sprites[motionNumber - 1];

        return sprites[xPos];
    }

    private Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 7, bitmap.getHeight() * 7, false);
    }

}
