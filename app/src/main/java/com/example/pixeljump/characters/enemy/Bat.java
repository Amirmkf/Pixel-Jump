package com.example.pixeljump.characters.enemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pixeljump.R;
import com.example.pixeljump.utils.Motion;

public class Bat {

    private final Context context;
    private final BitmapFactory.Options options;

    private Motion attackMotion;
    private Motion idleMotion;
    private Motion deadMotion;

    public Bat(Context context) {
        this.context = context;

        options = new BitmapFactory.Options();
        options.inScaled = false;

        setAttackMotion();
        setDeadMotion();
        setIdleMotion();
    }

    private void setAttackMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bat_attack, options);

        this.attackMotion = new Motion(spriteSheet, 7, 64, 62);
    }

    private void setIdleMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bat_fly, options);

        this.idleMotion = new Motion(spriteSheet, 4, 64, 64);
    }

    private void setDeadMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bat_death, options);

        this.deadMotion = new Motion(spriteSheet, 11, 69, 62);
    }

    public Motion getAttackMotion() {
        return attackMotion;
    }

    public Motion getIdleMotion() {
        return idleMotion;
    }

    public Motion getDeadMotion() {
        return deadMotion;
    }
}