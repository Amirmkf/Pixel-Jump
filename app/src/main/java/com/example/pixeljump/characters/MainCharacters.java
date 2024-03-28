package com.example.pixeljump.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pixeljump.R;
import com.example.pixeljump.utils.Motion;

public class MainCharacters  {
    private Context context;
    private BitmapFactory.Options options = new BitmapFactory.Options();

    public Motion attackMotion;
    public Motion jumpMotion;
    public Motion damageMotion;
    public Motion idealMotion;
    public Motion deadMotion;

    public MainCharacters(Context context) {
        this.context = context;
        options.inScaled = false;

        attackMotion = attackMotion();
    }


    private Motion jumpMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_jump, options);

        return new Motion(spriteSheet, 8, 53, 30);
    }


    private Motion attackMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_attack, options);

        return new Motion(spriteSheet, 4, 32, 73);
    }

    private Motion damageMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_attack, options);

        return new Motion(spriteSheet, 4, 32, 32);
    }

    private Motion idealMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_attack, options);

        return new Motion(spriteSheet, 4, 32, 32);
    }

    private Motion deadMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_attack, options);

        return new Motion(spriteSheet, 8, 32, 29);
    }
}
