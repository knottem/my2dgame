package entity;

import main.GamePanel;
import main.UtilityTool;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.Objects;

public class Entity {

    GamePanel gp;
    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0,0, 64,64);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    String[] dialogues = new String[20];

    //STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    //COUNTERS
    public int actionlockCounter = 0;
    public int spriteCounter = 0;
    public int standCounter = 0;
    public int invincibleCounter = 0;
    public int invincibleFadeCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;

    // CHARACTER ATTRIBUTES
    public String name;
    public int maxLife;
    public int life;
    public int type; // 0 = player, 1 = npc, 2 = monster
    public int speed;
    public int diagonalSpeed;


    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){}
    public void damageReaction(){}
    public void speak(){
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up" -> direction = "standDown";
            case "down" -> direction = "standUp";
            case "left" -> direction = "standRight";
            case "right" -> direction = "standLeft";
        }
    }
    public void update(){

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer){
            if(!gp.player.invincible){
                gp.playSE(7);
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        spriteCounter++;
        if (spriteCounter <= 6) {
            spriteNum = 1;
        }
        if (spriteCounter > 6 && spriteCounter <= 12) {
            spriteNum = 2;
        }
        if (spriteCounter > 12 && spriteCounter <= 18) {
            spriteNum = 3;
        }
        if (spriteCounter > 18 && spriteCounter <= 24) {
            spriteNum = 4;
        }
        if (spriteCounter > 24) {
            spriteCounter = 0;
        }

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up", "upRight", "upLeft" -> {
                    if (spriteNum == 1 || spriteNum == 2) {image = up1;}
                    if (spriteNum == 3 || spriteNum == 4) {image = up2;}
                }
                case "down", "downRight", "downLeft" -> {
                    if (spriteNum == 1 || spriteNum == 2) {image = down1;}
                    if (spriteNum == 3 || spriteNum == 4) {image = down2;}
                }
                case "left" -> {
                    if (spriteNum == 1 || spriteNum == 2) {image = left1;}
                    if (spriteNum == 3 || spriteNum == 4) {image = left2;}
                }
                case "right" -> {
                    if (spriteNum == 1 || spriteNum == 2) {image = right1;}
                    if (spriteNum == 3 || spriteNum == 4) {image = right2;}
                }
                case "standUp" -> image = up3;
                case "standDown" -> image = down3;
                case "standLeft" -> image = left3;
                case "standRight" -> image = right3;
            }

            //Monster HPbar
            if(type == 2 && hpBarOn) {

                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY - 11, gp.tileSize+1, 11);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 10, (int)hpBarValue, 10);

                hpBarCounter++;

                if(hpBarCounter > 300){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if(invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
            }
            if(dying){
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

            }
        }

    public void dyingAnimation(Graphics2D g2){

        dyingCounter++;
        int i = 5;

        if(dyingCounter <= i) {changeAlpha(g2,0f);}
        if(dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2,1f);}
        if(dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2,0f);}
        if(dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2,1f);}
        if(dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2,0f);}
        if(dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2,1f);}
        if(dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2,0f);}
        if(dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2,1f);}
        if(dyingCounter > i*8){
            dying = false;
            alive = false;
        }


    }
    public void changeAlpha(Graphics2D g2, float alphaValue){

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height){

        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try{

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = utilityTool.scaleImage(image, width, height);

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;

    }
}
