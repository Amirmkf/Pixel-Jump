package com.example.pixeljump.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.pixeljump.R;
import com.example.pixeljump.utils.Motion;

public class MainCharacters {
    private final Context context;
    private final BitmapFactory.Options options = new BitmapFactory.Options();

    private Motion attackMotion;
    private Motion jumpMotion;
    private Motion damageMotion;
    private Motion idealMotion;
    private Motion deadMotion;


    public MainCharacters(Context context) {
        this.context = context;
        options.inScaled = false;

        setAttackMotion();
        setDamageMotion();
        setDeadMotion();
        setIdealMotion();
        setJumpMotion();
    }


    private void setJumpMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_jump, options);

        this.jumpMotion = new Motion(spriteSheet, 8, 53, 30);
    }


    private void setAttackMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_attack, options);

        this.attackMotion = new Motion(spriteSheet, 4, 32, 73);
    }

    private void setDamageMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_attack, options);

        this.damageMotion = new Motion(spriteSheet, 4, 32, 32);
    }

    private void setIdealMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_attack, options);

        this.idealMotion = new Motion(spriteSheet, 4, 32, 32);
    }

    private void setDeadMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_attack, options);

        this.deadMotion = new Motion(spriteSheet, 8, 32, 29);
    }


    public Motion getAttackMotion() {
        return attackMotion;
    }

    public Motion getJumpMotion() {
        return jumpMotion;
    }

    public Motion getDamageMotion() {
        return damageMotion;
    }

    public Motion getIdealMotion() {
        return idealMotion;
    }

    public Motion getDeadMotion() {
        return deadMotion;
    }

}
