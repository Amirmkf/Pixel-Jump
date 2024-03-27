package com.example.pixeljump;

public class GameLoop implements Runnable {

    private final Thread gameThread;
    private final GamePanel gamePanel;

    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gameThread = new Thread(this);
    }

    @Override
    public void run() {

        long lastFPScheck = System.currentTimeMillis();
        int fps = 0;

        while (true) {

            gamePanel.updateAnimation();
            gamePanel.render();

            fps++;

            long now = System.currentTimeMillis();
            if (now - lastFPScheck >= 1000) {
                System.out.println("FPS: " + fps);
                fps = 0;
                lastFPScheck += 1000;
            }


        }

    }

    public void startGameLoop() {
        gameThread.start();
    }
}
