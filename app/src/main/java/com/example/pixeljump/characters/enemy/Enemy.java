package com.example.pixeljump.characters.enemy;

import android.content.Context;

import com.example.pixeljump.characters.Actions;

public class Enemy {
    private final Bat bat;
    private final Mushroom mushroom;

    private int enemyId;
    private Actions action;
    private int enemyMotionDelay;

    public Enemy(Context context) {
        bat = new Bat(context);
        mushroom = new Mushroom(context);

        enemyId = 10; //set big random number for default of switch case
        action = Actions.IDLE;
        enemyMotionDelay = 0;
    }

    public Bat getBat() {
        return bat;
    }

    public Mushroom getMushroom() {
        return mushroom;
    }

    public int getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public int getEnemyMotionDelay() {
        return enemyMotionDelay;
    }

    public void setEnemyMotionDelay(int enemyMotionDelay) {
        this.enemyMotionDelay = enemyMotionDelay;
    }
}
