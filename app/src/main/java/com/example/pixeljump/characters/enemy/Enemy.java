package com.example.pixeljump.characters.enemy;

import android.content.Context;

import com.example.pixeljump.characters.Actions;

public class Enemy {
    private final Bat bat;
    private final Mushroom mushroom;
    private int enemyBlockIndex;
    private Actions action;
    private int enemyMotionDelay;

    public Enemy(Context context) {
        bat = new Bat(context);
        mushroom = new Mushroom(context);

        enemyBlockIndex = 100;
        action = Actions.IDLE;
        enemyMotionDelay = 0;
    }

    public Bat getBat() {
        return bat;
    }

    public Mushroom getMushroom() {
        return mushroom;
    }

    public int getEnemyBlockIndex() {
        return enemyBlockIndex;
    }

    public void setEnemyBlockIndex(int enemyBlockIndex) {
        this.enemyBlockIndex = enemyBlockIndex;
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
