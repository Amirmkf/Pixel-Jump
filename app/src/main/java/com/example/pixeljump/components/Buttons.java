package com.example.pixeljump.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.pixeljump.R;

public class Buttons {

    private Bitmap jumpButtonBitmap;
    private Bitmap shieldButtonBitmap;
    private Bitmap swordButtonBitmap;

    public Buttons(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        jumpButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.play, options);
        jumpButtonBitmap = Bitmap.createScaledBitmap(jumpButtonBitmap, jumpButtonBitmap.getWidth() * 14, jumpButtonBitmap.getHeight() * 14, false);

        shieldButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shield, options);
        shieldButtonBitmap = Bitmap.createScaledBitmap(shieldButtonBitmap, shieldButtonBitmap.getWidth() * 5, shieldButtonBitmap.getHeight() * 5, false);

        swordButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gun, options);
        swordButtonBitmap = Bitmap.createScaledBitmap(swordButtonBitmap, swordButtonBitmap.getWidth() * 5, swordButtonBitmap.getHeight() * 5, false);
    }

    public void drawJumpButton(Canvas background, int screenWidth, int screenHeight) {
        background.drawBitmap(jumpButtonBitmap,
                (float) screenWidth / 3 * 2 - jumpButtonBitmap.getWidth() - 40
                , screenHeight - jumpButtonBitmap.getHeight() - 200, null);
    }

    public void drawShieldButton(Canvas background, int screenWidth, int screenHeight) {
        background.drawBitmap(shieldButtonBitmap,
                screenWidth - shieldButtonBitmap.getWidth() - 40
                , screenHeight - shieldButtonBitmap.getHeight() - 200, null);
    }

    public void drawSwordButton(Canvas background, int screenWidth, int screenHeight) {
        background.drawBitmap(swordButtonBitmap,
                (float) screenWidth / 3 - swordButtonBitmap.getWidth() - 40
                , screenHeight - swordButtonBitmap.getHeight() - 200, null);
    }

    public Bitmap getJumpButtonBitmap() {
        return jumpButtonBitmap;
    }

    public Bitmap getShieldButtonBitmap() {
        return shieldButtonBitmap;
    }

    public Bitmap getSwordButtonBitmap() {
        return swordButtonBitmap;
    }
}
