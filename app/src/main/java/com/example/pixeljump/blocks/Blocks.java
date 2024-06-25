package com.example.pixeljump.blocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pixeljump.R;
import com.example.pixeljump.utils.Motion;

public class Blocks {
    private final Context context;
    private final BitmapFactory.Options options;

    private Bitmap defaultBlock;
    private Motion fireBlock;
    private Motion fallBlock;
    private Motion fire;

    private int blockPositionX;
    private int blockPositionY;

    private int blockId = 10; //set big random number for default of switch case

    public Blocks(Context context) {
        this.context = context;

        options = new BitmapFactory.Options();
        options.inScaled = false;

        setDefaultBlock();
        setFallBlock();
        setFireBlock();
        setFire();
    }

    private void setDefaultBlock() {
        Bitmap block = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_normal);

        defaultBlock = Bitmap.createScaledBitmap(block, block.getWidth() * 2, block.getHeight() * 2, false);
    }

    private void setFallBlock() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_fall, options);

        fallBlock = new Motion(spriteSheet, 4, 10, 32);
    }

    private void setFireBlock() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_box, options);

        fireBlock = new Motion(spriteSheet, 13, 32, 16);
    }

    private void setFire() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire, options);

        fire = new Motion(spriteSheet, 13, 17, 16);
    }

    public Motion getFire() {
        return fire;
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


    public int getBlockPositionX() {
        return blockPositionX;
    }

    public void setBlockPositionX(int blockPositionX) {
        this.blockPositionX = blockPositionX;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public int getBlockPositionY() {
        return blockPositionY;
    }

    public void setBlockPositionY(int blockPositionY) {
        this.blockPositionY = blockPositionY;
    }
}
