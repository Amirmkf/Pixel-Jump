package com.example.pixeljump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.pixeljump.blocks.Blocks;
import com.example.pixeljump.characters.Actions;
import com.example.pixeljump.characters.MainCharacters;
import com.example.pixeljump.characters.enemy.Enemy;
import com.example.pixeljump.components.Buttons;
import com.example.pixeljump.utils.Motion;

import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private final SurfaceHolder holder;

//    private float velocityY;

    private final Bitmap backgroundBitmap;
    private final Buttons buttons;
//    private Bitmap jumpButtonBitmap;
//    private Bitmap shieldButtonBitmap;
//    private Bitmap gunButtonBitmap;

    private final MainCharacters mainCharacters;

    private Handler handler;
    private Runnable gameLoop;
    private Runnable damageLoop;

    //Blocks in screen
    private final int blockCount = 4;
    private final Blocks[] blocks = new Blocks[blockCount];

//    enum actions {IDLE, JUMP, ATTACK, DAMAGE, DEAD}

    Actions characterAction = Actions.IDLE;
    private int actionDelay = 0;

    //    private final Mushroom mushroom;
//    private final Bat bat;
//    private final int[] enemyBlock = new int[blockCount];
//    private final actions[] enemyAction = new actions[blockCount];
//    private int enemyMotionDelay = 0;
    private final Enemy[] enemies = new Enemy[blockCount];

    private final Context context;

    public GamePanel(Context context) {
        super(context);

        this.context = context;

        this.mainCharacters = new MainCharacters(context);
        this.buttons = new Buttons(context);
//        this.bat = new Bat(context);
//        this.mushroom = new Mushroom(context);

        for (int i = 0; i < blockCount; i++) {
            blocks[i] = new Blocks(context);
            enemies[i] = new Enemy(context);
        }


        holder = getHolder();
        holder.addCallback(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

//        jumpButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.play, options);
//        jumpButtonBitmap = Bitmap.createScaledBitmap(jumpButtonBitmap, jumpButtonBitmap.getWidth() * 14, jumpButtonBitmap.getHeight() * 14, false);
//
//        shieldButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shield, options);
//        shieldButtonBitmap = Bitmap.createScaledBitmap(shieldButtonBitmap, shieldButtonBitmap.getWidth() * 5, shieldButtonBitmap.getHeight() * 5, false);
//
//        gunButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gun, options);
//        gunButtonBitmap = Bitmap.createScaledBitmap(gunButtonBitmap, gunButtonBitmap.getWidth() * 5, gunButtonBitmap.getHeight() * 5, false);

        backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_brown, options);


//        Arrays.fill(blocksBitmap, 3);
//        Arrays.fill(enemyBlock, 10);
//        Arrays.fill(enemyAction, actions.IDLE);
    }

    public void render() {

//        Canvas background = holder.lockCanvas();
        Canvas background;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            background = holder.lockHardwareCanvas();

            for (int i = 0; i < getWidth() / backgroundBitmap.getWidth() + 1; i++) {
                for (int j = 0; j < getHeight() / backgroundBitmap.getHeight() + 1; j++) {
                    background.drawBitmap(backgroundBitmap,
                            i * backgroundBitmap.getWidth()
                            , j * backgroundBitmap.getHeight(), null);
                }
            }

        } else {
            background = holder.lockCanvas();

            background.drawColor(Color.rgb(216, 189, 155));
        }

        //Draw background
        for (int i = 0; i < blockCount; i++) {
            Blocks block = blocks[i];
            switch (block.getBlockId()) {
                case 0:
                    background.drawBitmap(blocks[i].getFallBlock().getSprite(), block.getBlockPosition(), (float) getHeight() / 2, null);
                    break;

                case 1:
                    Bitmap fireBlock = blocks[i].getFireBlock().getSprite();
                    fireBlock = Bitmap.createScaledBitmap(fireBlock, fireBlock.getWidth() * 2, fireBlock.getHeight() * 2, false);

                    background.drawBitmap(fireBlock, block.getBlockPosition(), (float) getHeight() / 2 - (float) fireBlock.getHeight() / 2, null);
                    break;

                default:
                    Bitmap normalBlock = blocks[i].getDefaultBlock();
                    Enemy enemy = enemies[i];
                    switch (enemy.getEnemyBlockIndex()) {
                        case 0:
                            Motion batMotion;
                            Bitmap batBitmap;

                            switch (enemy.getAction()) {
                                case ATTACK:
                                    batMotion = enemy.getBat().getAttackMotion();
//                                    batMotion.setMotionDelay(7);
                                    batBitmap = batMotion.getSprite();

//                                    enemyMotionDelay++;
//
//                                    if (enemyMotionDelay == batMotion.getMotionNumber() * batMotion.getMotionDelay()) {
//                                        enemyAction[i] = actions.IDLE;
//                                        enemyMotionDelay = 0;
//                                    }

                                    break;

                                case DEAD:
                                    batMotion = enemy.getBat().getDeadMotion();
                                    batBitmap = batMotion.getSprite();

                                    int enemyMotionDelay = enemy.getEnemyMotionDelay() + 1;
                                    enemy.setEnemyMotionDelay(enemyMotionDelay);

                                    if (enemyMotionDelay == batMotion.getMotionNumber() * batMotion.getMotionDelay()) {
                                        enemy.setEnemyBlockIndex(10);
                                        enemy.setEnemyMotionDelay(0);
                                    }

                                    break;

                                default:
                                    batBitmap = enemy.getBat().getIdleMotion().getSprite();
                            }

                            background.drawBitmap(batBitmap, block.getBlockPosition() - (float) normalBlock.getWidth() / 2, (float) getHeight() / 2 - batBitmap.getHeight(), null);

                            enemyAttack(i);
                            break;

                        case 1:
                            Motion mushroomMotion;
                            Bitmap mushroomBitmap;

                            switch (enemy.getAction()) {
                                case ATTACK:
                                    mushroomMotion = enemy.getMushroom().getAttackMotion();
//                                    mushroomMotion.setMotionDelay(7);
                                    mushroomBitmap = mushroomMotion.getSprite();
//
//                                    enemyMotionDelay++;
//
//                                    if (enemyMotionDelay == mushroomMotion.getMotionNumber() * mushroomMotion.getMotionDelay()) {
//                                        enemyBlock[i] = 10;
//                                        enemyMotionDelay = 0;
//                                    }

                                    break;

                                case DEAD:
                                    mushroomMotion = enemy.getMushroom().getDeadMotion();
                                    mushroomMotion.setMotionDelay(10);
                                    mushroomBitmap = mushroomMotion.getSprite();


                                    int enemyMotionDelay = enemy.getEnemyMotionDelay() + 1;
                                    enemy.setEnemyMotionDelay(enemyMotionDelay);


                                    if (enemyMotionDelay == mushroomMotion.getMotionNumber() * mushroomMotion.getMotionDelay()) {
                                        enemy.setEnemyBlockIndex(10);
                                        enemy.setEnemyMotionDelay(0);
                                    }

                                    break;

                                default:
                                    mushroomBitmap = enemy.getMushroom().getIdleMotion().getSprite();
                            }

                            background.drawBitmap(mushroomBitmap, block.getBlockPosition(), (float) getHeight() / 2 - mushroomBitmap.getHeight(), null);

                            enemyAttack(i);
                            break;
                    }

                    background.drawBitmap(normalBlock, block.getBlockPosition(), (float) getHeight() / 2, null);
            }
        }

        //Draw buttons
        buttons.drawJumpButton(background, getWidth(), getHeight());
        buttons.drawShieldButton(background, getWidth(), getHeight());
        buttons.drawSwordButton(background, getWidth(), getHeight());
//        background.drawBitmap(shieldButtonBitmap,
//                getWidth() - shieldButtonBitmap.getWidth() - 40
//                , getHeight() - shieldButtonBitmap.getHeight() - 200, null);
//
//        background.drawBitmap(jumpButtonBitmap,
//                (float) getWidth() / 3 * 2 - jumpButtonBitmap.getWidth() - 40
//                , getHeight() - jumpButtonBitmap.getHeight() - 200, null);
//
//        background.drawBitmap(gunButtonBitmap,
//                (float) getWidth() / 3 - gunButtonBitmap.getWidth() - 40
//                , getHeight() - gunButtonBitmap.getHeight() - 200, null);


        //Draw character base of action
        switch (characterAction) {
            case IDLE:
                Bitmap idleBitmap = mainCharacters.getIdleMotion().getSprite();
                background.drawBitmap(idleBitmap, 0, (float) getHeight() / 2 - idleBitmap.getHeight(), null);

                if (actionDelay != 0)
                    actionDelay = 0;

                break;

            case JUMP:
                Motion jumpMotion = mainCharacters.getJumpMotion();
                Bitmap jumpBitmap = jumpMotion.getSprite();
                background.drawBitmap(jumpBitmap, 0, (float) getHeight() / 2 - jumpBitmap.getHeight(), null);

                actionDelay++;

                if (actionDelay == jumpMotion.getMotionNumber() * jumpMotion.getMotionDelay()) {
                    characterAction = Actions.IDLE;
                    actionDelay = 0;
                }

                break;

            case ATTACK:
                Motion attackMotion = mainCharacters.getAttackMotion();
                Bitmap attackBitmap = attackMotion.getSprite();
                background.drawBitmap(attackBitmap, 0, (float) getHeight() / 2 - attackBitmap.getHeight(), null);

                actionDelay++;

                if (actionDelay == attackMotion.getMotionNumber() * attackMotion.getMotionDelay()) {
                    characterAction = Actions.IDLE;
                    actionDelay = 0;
                }

                break;

            case DAMAGE:
                Motion damageMotion = mainCharacters.getDamageMotion();
                Bitmap damageBitmap = damageMotion.getSprite();
                background.drawBitmap(damageBitmap, 0, (float) getHeight() / 2 - damageBitmap.getHeight(), null);

                actionDelay++;

                if (actionDelay == damageMotion.getMotionNumber() * damageMotion.getMotionDelay()) {
                    characterAction = Actions.IDLE;
                    actionDelay = 0;
                }

                break;

            case DEAD:
                Motion deadMotion = mainCharacters.getDeadMotion();
                Bitmap deadBitmap = deadMotion.getSprite();
                background.drawBitmap(deadBitmap, 0, (float) getHeight() / 2 - deadBitmap.getHeight(), null);

                actionDelay++;

                if (actionDelay == deadMotion.getMotionNumber() * deadMotion.getMotionDelay()) {
                    characterAction = Actions.IDLE;
                    actionDelay = 0;
                }

                break;
        }


        holder.unlockCanvasAndPost(background);
    }

    public void updateAnimation() {

//        float gravity = 0.5f;
//        velocityY += 0.5f;
//
//        y += velocityY;
//
//        if (y >= (float) getHeight() / 2 - attackBitmap.getHeight() - 10) {
//            y = (float) getHeight() / 2 - attackBitmap.getHeight();
//            velocityY = 0;
//        }
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        for (int i = 0; i < blockCount; i++) {
            blocks[i].setBlockPosition(i * getWidth() / (blockCount));
        }

        handler = new Handler();
        gameLoop = new Runnable() {

            long lastFPScheck = System.currentTimeMillis();
            int fps = 0;

            @Override
            public void run() {

                render();

                fps++;

                long now = System.currentTimeMillis();
                if (now - lastFPScheck >= 1000) {
                    System.out.println("FPS: " + fps);
                    fps = 0;
                    lastFPScheck += 1000;
                }

                // Schedule next update
                handler.postDelayed(this, 10);
            }
        };

        handler.post(gameLoop);
    }


    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            velocityY = -.5f;
            if (actionDelay == 0) {
                jumpButton(event.getX(), event.getY());
            }

            if (characterAction != Actions.JUMP)
                attackButton(event.getX(), event.getY());
        }

        return true;
    }

    public void jumpButton(float x, float y) {
        if (((float) getWidth() / 3 * 2 - buttons.getJumpButtonBitmap().getWidth() - 40) < x && x < ((float) getWidth() / 3 * 2 - 40)
                && y < getHeight() - 200 && y > getHeight() - buttons.getJumpButtonBitmap().getHeight() - 200) {

            moveBlocks();
            characterAction = Actions.JUMP;
        }
    }

    public void attackButton(float x, float y) {
        if ((float) getWidth() / 3 - 40 - buttons.getSwordButtonBitmap().getWidth() < x && x < (float) getWidth() / 3 - 60
                && y < getHeight() - 200 && y > getHeight() - buttons.getSwordButtonBitmap().getHeight() - 200) {

//            actionDelay = 0;
            characterAction = Actions.ATTACK;

            handler.removeCallbacks(damageLoop);
            if (enemies[1].getEnemyBlockIndex() == 1 || enemies[1].getEnemyBlockIndex() == 0) {
                enemies[1].setAction(Actions.DEAD);
            }

        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        handler.removeCallbacks(gameLoop);
    }

    private void moveBlocks() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < blockCount; i++) {
                    int newPosition = blocks[i].getBlockPosition() - 7;
                    blocks[i].setBlockPosition(newPosition);
                }

                // Schedule next update
                handler.postDelayed(this, 10);

                if (blocks[1].getBlockPosition() <= 0) {
                    handler.removeCallbacks(this);

//                    System.arraycopy(blockPositions, 1, blockPositions, 0, blockCount - 1);
//                    System.arraycopy(blocksBitmap, 1, blocksBitmap, 0, blockCount - 1);

                    System.arraycopy(blocks, 1, blocks, 0, blockCount - 1);

//                    System.arraycopy(enemyBlock, 1, enemyBlock, 0, blockCount - 1);
//                    System.arraycopy(enemyAction, 1, enemyAction, 0, blockCount - 1);

                    System.arraycopy(enemies, 1, enemies, 0, blockCount - 1);

                    blocks[blockCount - 1] = new Blocks(context);
                    enemies[blockCount - 1] = new Enemy(context);

                    // Create a new block and add it to the right
                    blocks[blockCount - 1].setBlockPosition(getWidth() - (getWidth() / blockCount));

                    //Random block bitmap
                    blocks[blockCount - 1].setBlockId(new Random().nextInt(6));

                    //random enemy bitmap
                    enemies[blockCount - 1].setEnemyBlockIndex(new Random().nextInt(5));

                    enemies[blockCount - 1].setAction(Actions.IDLE);
                }
            }
        });
    }

    private void enemyAttack(int blockIndex) {
        if (blockIndex == 1 && characterAction == Actions.IDLE) {
            damageLoop = () -> {
                if (enemies[1].getAction() != Actions.DEAD && characterAction == Actions.IDLE) {
                    enemies[1].setAction(Actions.ATTACK);
                    characterAction = Actions.DAMAGE;
                }
            };

            handler.postDelayed(damageLoop, 1500);
        }
    }

//    private void enemyDamage(int blockIndex) {
//        if (blockIndex == 1) {
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    enemyAction[1] = actions.DAMAGE;
//                }
//            }, 500);
//        }
//    }
}
