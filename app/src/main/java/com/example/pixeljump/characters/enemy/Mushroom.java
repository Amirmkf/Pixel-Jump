package com.example.pixeljump.characters.enemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pixeljump.R;
import com.example.pixeljump.utils.Motion;

public class Mushroom {

    private final Context context;
    private final BitmapFactory.Options options = new BitmapFactory.Options();

    private Motion attackMotion;
    private Motion idleMotion;
    private Motion deadMotion;


    public Mushroom(Context context) {
        this.context = context;
        options.inScaled = false;

        setAttackMotion();
        setDeadMotion();
        setIdleMotion();
    }


    private void setAttackMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.mushroom_attack, options);

        this.attackMotion = new Motion(spriteSheet, 8, 150, 150);
    }

    private void setIdleMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.mushroom_idle, options);

        this.idleMotion = new Motion(spriteSheet, 4, 150, 150);
    }

    private void setDeadMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.mushroom_death, options);

        this.deadMotion = new Motion(spriteSheet, 11, 150, 150);
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
