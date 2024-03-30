package com.example.pixeljump.blocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pixeljump.R;
import com.example.pixeljump.utils.Motion;

public class Blocks {
    private final Context context;

    private Bitmap defaultBlock;
    private Motion fireBlock;
    private Motion fallBlock;
    public Blocks(Context context) {
        this.context = context;

        setDefaultBlock();
        setFallBlock();
        setFireBlock();
    }

    private void setDefaultBlock() {
        Bitmap block = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_normal);

        defaultBlock = Bitmap.createScaledBitmap(block, block.getWidth() * 2, block.getHeight() * 2, false);
    }

    private void setFallBlock() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_fall, options);
        fallBlock = new Motion(spriteSheet, 4, 10, 32);
    }

    private void setFireBlock() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_box, options);

        fireBlock = new Motion(spriteSheet, 13, 32, 16);

    }

    public Bitmap getDefaultBlock() {
        return defaultBlock;
    }

    public Motion getFallBlock() {
        return fallBlock;
    }

    public Motion getFireBlock() {
        return fireBlock;
    }
}
