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
    private Motion idleMotion;
    private Motion deadMotion;
    private int health = 3;


    public MainCharacters(Context context) {
        this.context = context;
        options.inScaled = false;
        setAttackMotion();
        setDamageMotion();
        setDeadMotion();
        setIdleMotion();
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
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_hurt, options);

        this.damageMotion = new Motion(spriteSheet, 4, 32, 32);
    }

    private void setIdleMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_idle, options);

        this.idleMotion = new Motion(spriteSheet, 4, 32, 32);
    }

    private void setDeadMotion() {
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.owlet_death, options);

        this.deadMotion = new Motion(spriteSheet, 8, 32, 29);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public Motion getIdleMotion() {
        return idleMotion;
    }

    public Motion getDeadMotion() {
        return deadMotion;
    }

}
