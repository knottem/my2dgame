package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int originalTileSize = 32; //32x32 tile
    final int scale = 2;
    public final int tileSize = originalTileSize * scale; //64x64 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1024 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 768 pixels

    //World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS THE GAME SHOULD RUN AT
    int FPS = 60;

    // SYSTEM
    TileManager tileManager = new TileManager(this, "/maps/world01.txt");
    TileManager tileManagerBackground = new TileManager(this, "/maps/world00.txt");
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetPlacer aPlacer = new AssetPlacer(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    //OBJECT and ENTITY
    public Player player = new Player(this,keyH);
    // 10 objects active at the same time
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;

    //DEBUG VALUES
    DecimalFormat drawTimeFormat = new DecimalFormat("#0.0000");
    int counter = 0;
    double checkFps;
    double drawEnd = 0;
    double drawStart = 0;
    int counterUpdate = 10;

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setupGame(){

       aPlacer.setObject();
       aPlacer.setNPC();
       aPlacer.setMonsters();
       // Initialize sound effect, so it won't lag first time used in game
       playSE(0);
       //Title song here
       //playMusic(0);
       //stopMusic();
       gameState = titleState;
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();


    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        double drawCount = 0;

        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            // check FPS
            if(timer >= 1000000000){
              checkFps = drawCount;
                drawCount = 0;
             timer = 0;
            }
        }
    }
    public void update(){
        if(gameState == playState) {
            //PLAYER
            player.update();
            //NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null){
                    if(monster[i].alive && !monster[i].dying){
                        monster[i].update();
                    }
                    if(!monster[i].alive){
                        monster[i] = null;
                    }
                }
            }
        }
        if(gameState == pauseState){
            // Nothing, no updates happening.
        }
    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //DEBUG
        if(keyH.DebugMenu) {
            if(counter <= counterUpdate){
                counter++;
            }
            if(counter > counterUpdate){
                drawStart = System.nanoTime();
            }
        }
        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }

        //OTHER STATE
        else {
            //draws in layers, so background first
            tileManagerBackground.draw(g2);
            tileManager.draw(g2);

            //ADD ENTITIES TO THE LIST
            entityList.add(player);

            for (int i = 0; i < npc.length; i++) {
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }

            }
            for (int i = 0; i < obj.length; i++) {
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }

            }
            for (int i = 0; i < monster.length; i++) {
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }

            }
            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {

                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                    entityList.get(i).draw(g2);
                if(keyH.DebugMenu) {
                    g2.setColor(Color.red);
                    g2.drawRect((entityList.get(i).worldX - player.worldX + player.screenX + entityList.get(i).solidArea.x), (entityList.get(i).worldY - player.worldY + player.screenY + entityList.get(i).solidArea.y), entityList.get(i).solidArea.width, entityList.get(i).solidArea.height);
                }
            }
            entityList.clear();

            //UI
            ui.draw(g2);
        }

        //DEBUG
        if (keyH.DebugMenu) {

            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.PLAIN, 40));
            g2.drawString("FPS: " + checkFps, 10, 600);
            double PositionX = (player.worldX / tileSize);
            double PositionY = (player.worldY / tileSize);
            g2.drawString("Upper left corner is position", 10, 640);
            g2.drawString("Player Position X: " + PositionX, 10, 680);
            g2.drawString("Player Position Y: " + PositionY, 10, 720);
            g2.setColor(Color.red);
            g2.setStroke(new BasicStroke(0));
            g2.drawRect(player.screenX + player.solidArea.x, player.screenY + player.solidArea.y, player.solidArea.width, player.solidArea.height);
            g2.setColor(Color.white);
            if(counter > counterUpdate){
                drawEnd = System.nanoTime();
                counter = 0;
            }
            double passed = (drawEnd - drawStart) / 1_000_000;
            g2.drawString("Draw Time: " + drawTimeFormat.format(passed) + " ms", 10, 760);


        }

        g2.dispose();

    }
    public void playMusic(int i){

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){

        music.stop();
    }
    public void playSE(int i){

        soundEffect.setFile(i);
        soundEffect.play();
    }
}
