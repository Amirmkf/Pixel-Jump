package com.example.pixeljump.blocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pixeljump.R;
import com.example.pixeljump.utils.Motion;

public class Blocks {
    Context context;
    Bitmap groundBitmap;

    public Blocks(Context context) {
        this.context = context;
    }

    public Bitmap defaultBlock() {
        groundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.terrain);
        return groundBitmap = Bitmap.createScaledBitmap(groundBitmap, groundBitmap.getWidth() * 2, groundBitmap.getHeight() * 2, false);
    }

    public Motion blockFall() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_fall, options);

        return new Motion(spriteSheet, 4, 10, 32);
    }
}
