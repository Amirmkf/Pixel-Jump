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

    private final Bitmap backgroundBitmap;
    private final Buttons buttons;

    private final MainCharacters mainCharacters;

    private Handler handler;
    private Runnable gameLoop;
    private Runnable damageLoop;

    //Blocks in screen
    private final int blockCount = 4;
    private final Blocks[] blocks;

    Actions characterAction = Actions.IDLE;

    private final Enemy[] enemies;

    boolean attackDelayFlag = true;

    private final Context context;

    public GamePanel(Context context) {
        super(context);

        this.context = context;

        blocks = new Blocks[blockCount];
        enemies = new Enemy[blockCount];

        this.mainCharacters = new MainCharacters(context);
        this.buttons = new Buttons(context);

        for (int i = 0; i < blockCount; i++) {
            blocks[i] = new Blocks(context);
            enemies[i] = new Enemy(context);
        }

        holder = getHolder();
        holder.addCallback(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_brown, options);
    }

    public void render() {

        Canvas background;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            background = holder.lockHardwareCanvas();

            for (int i = 0; i < getWidth() / backgroundBitmap.getWidth() + 1; i++) {
                for (int j = 0; j < getHeight() / backgroundBitmap.getHeight() + 1; j++) {
                    background.drawBitmap(backgroundBitmap, i * backgroundBitmap.getWidth(), j * backgroundBitmap.getHeight(), null);
                }
            }

        } else {
            background = holder.lockCanvas();

            background.drawColor(Color.rgb(216, 189, 155));
        }

        //Draw block and enemy
        for (int i = 0; i < blockCount; i++) {
            Blocks block = blocks[i];
            switch (block.getBlockId()) {
                case 0: //FallBlock
                    background.drawBitmap(block.getFallBlock().getSprite(), block.getBlockPositionX(), block.getBlockPositionY(), null);

                    if (i == 0) {
                        handler.postDelayed(() -> {
                            int y = block.getBlockPositionY();

                            if (y > getHeight() / 2 + 450) {
                                mainCharacters.setHealth(0);
                                block.setBlockPositionY(y + 1);
                            } else {
                                block.setBlockPositionY(y + 3);
                            }

                        }, 1000);
                    }

                    break;

                case 1: //FireBlock
                    Motion fireBlockMotion = block.getFireBlock();
                    fireBlockMotion.setMotionDelay(13);

                    Bitmap fireBlock = fireBlockMotion.getSprite();
                    fireBlock = Bitmap.createScaledBitmap(fireBlock, fireBlock.getWidth() * 2, fireBlock.getHeight() * 2, false);

                    Motion fireMotion = block.getFire();
                    fireMotion.setMotionDelay(13);

                    Bitmap fire = fireMotion.getSprite();
                    fire = Bitmap.createScaledBitmap(fire, fire.getWidth() * 2, fire.getHeight() * 2, false);

                    background.drawBitmap(fire, block.getBlockPositionX(), (float) getHeight() / 2 - fire.getHeight(), null);
                    background.drawBitmap(fireBlock, block.getBlockPositionX(), (float) getHeight() / 2 - (float) fireBlock.getHeight() / 2, null);

                    if (i == 0 && characterAction == Actions.IDLE && fireMotion.getMotionIndex() >= 9) {
                        characterAction = Actions.DAMAGE;
                        int currentHealth = mainCharacters.getHealth();

                        if (currentHealth > 0) mainCharacters.setHealth(currentHealth - 1);
                    }

                    break;

                default:
                    Bitmap normalBlock = block.getDefaultBlock();
                    Enemy enemy = enemies[i];

                    switch (enemy.getEnemyId()) {
                        case 0: // Bat Enemy
                            Motion batMotion;
                            Bitmap batBitmap;

                            switch (enemy.getAction()) {
                                case ATTACK:
                                    batMotion = enemy.getBat().getAttackMotion();
                                    batBitmap = batMotion.getSprite();

                                    break;

                                case DEAD:
                                    batMotion = enemy.getBat().getDeadMotion();
                                    batBitmap = batMotion.getSprite();

                                    if (batMotion.getMotionIndex() == batMotion.getMotionNumber() - 1)
                                        enemy.setEnemyId(10);

                                    break;

                                default:
                                    batBitmap = enemy.getBat().getIdleMotion().getSprite();
                            }

                            background.drawBitmap(batBitmap, block.getBlockPositionX() - (float) normalBlock.getWidth() / 2, (float) getHeight() / 2 - batBitmap.getHeight(), null);

                            enemyAttack(i);
                            break;

                        case 1: // Mushroom Enemy
                            Motion mushroomMotion;
                            Bitmap mushroomBitmap;

                            switch (enemy.getAction()) {
                                case ATTACK:
                                    mushroomMotion = enemy.getMushroom().getAttackMotion();
                                    mushroomBitmap = mushroomMotion.getSprite();

                                    break;

                                case DEAD:
                                    mushroomMotion = enemy.getMushroom().getDeadMotion();
                                    mushroomMotion.setMotionDelay(10);
                                    mushroomBitmap = mushroomMotion.getSprite();

                                    if (mushroomMotion.getMotionIndex() == mushroomMotion.getMotionNumber() - 1)
                                        enemy.setEnemyId(10);

                                    break;

                                default:
                                    mushroomBitmap = enemy.getMushroom().getIdleMotion().getSprite();
                            }

                            background.drawBitmap(mushroomBitmap, block.getBlockPositionX(), (float) getHeight() / 2 - mushroomBitmap.getHeight(), null);

                            enemyAttack(i);
                            break;
                    }

                    background.drawBitmap(normalBlock, block.getBlockPositionX(), (float) getHeight() / 2, null);
            }
        }

        BitmapFactory.Options healthBarOptions = new BitmapFactory.Options();
        healthBarOptions.inScaled = false;

        Bitmap healthBar;

        switch (mainCharacters.getHealth()) {
            case 1:
                healthBar = BitmapFactory.decodeResource(context.getResources(), R.drawable.health_min, healthBarOptions);

                break;

            case 2:
                healthBar = BitmapFactory.decodeResource(context.getResources(), R.drawable.health_mide, healthBarOptions);

                break;

            default:
                healthBar = BitmapFactory.decodeResource(context.getResources(), R.drawable.health_full, healthBarOptions);
        }

        healthBar = Bitmap.createScaledBitmap(healthBar, healthBar.getWidth() * 8, healthBar.getHeight() * 8, false);
        background.drawBitmap(healthBar, 25, 25, null);

        //Draw character by action
        Motion characterActionMotion = null;
        Bitmap characterActionBitmap;

        switch (characterAction) {
            case JUMP:
                characterActionMotion = mainCharacters.getJumpMotion();
                characterActionBitmap = characterActionMotion.getSprite();

                break;

            case ATTACK:
                characterActionMotion = mainCharacters.getAttackMotion();
                characterActionBitmap = characterActionMotion.getSprite();

                break;

            case DAMAGE:
                characterActionMotion = mainCharacters.getDamageMotion();
                characterActionBitmap = characterActionMotion.getSprite();

                break;

            case DEAD:
                characterActionMotion = mainCharacters.getDeadMotion();
                characterActionBitmap = characterActionMotion.getSprite();

                break;

            default:
                characterActionBitmap = mainCharacters.getIdleMotion().getSprite();
        }

        if (characterActionMotion != null && characterActionMotion.getMotionIndex() == characterActionMotion.getMotionNumber() - 1 && characterActionMotion.getMotionTick() == characterActionMotion.getMotionDelay() - 1) {
            characterAction = Actions.IDLE;
        }

        background.drawBitmap(characterActionBitmap, 0, blocks[0].getBlockPositionY() - characterActionBitmap.getHeight(), null);

        //Draw game over page
        if (mainCharacters.getHealth() == 0) {
            Bitmap gameOver = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_over, healthBarOptions);
            gameOver = Bitmap.createScaledBitmap(gameOver, getWidth(), getHeight(), false);

            background.drawBitmap(gameOver, 0, 0, null);
        }

        //Draw buttons
        buttons.drawJumpButton(background, getWidth(), getHeight());
        if (mainCharacters.getHealth() > 0) {
            buttons.drawShieldButton(background, getWidth(), getHeight());
            buttons.drawSwordButton(background, getWidth(), getHeight());
        }

        holder.unlockCanvasAndPost(background);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        for (int i = 0; i < blockCount; i++) {
            blocks[i].setBlockPositionX(i * getWidth() / (blockCount));
            blocks[i].setBlockPositionY(getHeight() / 2);
        }

        handler = new Handler();
        gameLoop = new Runnable() {

//            long lastFPScheck = System.currentTimeMillis();
//            int fps = 0;

            @Override
            public void run() {

                render();

//                fps++;

//                long now = System.currentTimeMillis();
//                if (now - lastFPScheck >= 1000) {
//                    System.out.println("FPS: " + fps);
//                    fps = 0;
//                    lastFPScheck += 1000;
//                }

                // Schedule next update
                handler.postDelayed(this, 5);
            }
        };

        handler.post(gameLoop);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (characterAction != Actions.JUMP) {
                jumpButton(event.getX(), event.getY());

                if (mainCharacters.getHealth() != 0) attackButton(event.getX(), event.getY());
            }
        }

        return true;
    }

    public void jumpButton(float x, float y) {
        if (((float) getWidth() / 3 * 2 - buttons.getJumpButtonBitmap().getWidth() - 40) < x && x < ((float) getWidth() / 3 * 2 - 40) && y < getHeight() - 200 && y > getHeight() - buttons.getJumpButtonBitmap().getHeight() - 200) {

            moveBlocks();
            characterAction = Actions.JUMP;

            if (mainCharacters.getHealth() == 0) {
                mainCharacters.setHealth(3);
            }
        }
    }

    public void attackButton(float x, float y) {
        if ((float) getWidth() / 3 - 40 - buttons.getSwordButtonBitmap().getWidth() < x && x < (float) getWidth() / 3 - 60 && y < getHeight() - 200 && y > getHeight() - buttons.getSwordButtonBitmap().getHeight() - 200) {

            characterAction = Actions.ATTACK;

            handler.removeCallbacks(damageLoop);

            if (enemies[1].getEnemyId() == 1 || enemies[1].getEnemyId() == 0) {
                enemies[1].setAction(Actions.DEAD);
            }
            if (enemies[0].getEnemyId() == 1 || enemies[0].getEnemyId() == 0) {
                enemies[0].setAction(Actions.DEAD);
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
                    int newPosition = blocks[i].getBlockPositionX() - 8;
                    blocks[i].setBlockPositionX(newPosition);
                }

                // Schedule next update
                handler.postDelayed(this, 10);

                if (blocks[1].getBlockPositionX() <= 0) {
                    handler.removeCallbacks(this);

                    System.arraycopy(blocks, 1, blocks, 0, blockCount - 1);
                    System.arraycopy(enemies, 1, enemies, 0, blockCount - 1);

                    //create new object for last item of array
                    blocks[blockCount - 1] = new Blocks(context);
                    enemies[blockCount - 1] = new Enemy(context);

                    // Create a new block and add it to the right
                    blocks[blockCount - 1].setBlockPositionX(getWidth() - (getWidth() / blockCount));
                    blocks[blockCount - 1].setBlockPositionY(getHeight() / 2);

                    //Random block bitmap
                    blocks[blockCount - 1].setBlockId(new Random().nextInt(6));

                    //random enemy bitmap
                    enemies[blockCount - 1].setEnemyId(new Random().nextInt(6));

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

                    int currentHealth = mainCharacters.getHealth();

                    if (currentHealth > 0) mainCharacters.setHealth(currentHealth - 1);
                }

                attackDelayFlag = true;
            };

            if (attackDelayFlag) {
                handler.postDelayed(damageLoop, 1700);
                attackDelayFlag = false;
            }
        }
    }
}
