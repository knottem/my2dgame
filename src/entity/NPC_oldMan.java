package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_oldMan extends Entity{

    public NPC_oldMan(GamePanel gp){
        super(gp);

        direction = "standDown";
        speed = 1;

        //Collision size
        solidArea = new Rectangle();
        solidArea.x = 15;
        solidArea.y = 30;
        solidArea.width = 34;
        solidArea.height = 36;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


        getImage();
        setDialogue();
    }

    public void getImage(){

        up1 = setup("/npc/oldMan_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldMan_up2", gp.tileSize, gp.tileSize);
        up3 = setup("/npc/oldMan_up3", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldMan_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldMan_down2", gp.tileSize, gp.tileSize);
        down3 = setup("/npc/oldMan_down3", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldMan_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldMan_left2", gp.tileSize, gp.tileSize);
        left3 = setup("/npc/oldMan_left3", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldMan_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldMan_right2", gp.tileSize, gp.tileSize);
        right3 = setup("/npc/oldMan_right3", gp.tileSize, gp.tileSize);

    }
    public void setDialogue(){
        dialogues[0] = "Hello, young man.";
        dialogues[1] = "Get away from me you whippersnapper.\nGet away from me you whippersnapper.\nGet away from me you whippersnapper.";
        dialogues[2] = "OR PREPEARE TO DIE";
        dialogues[3] = " /spit";
    }
    public void setAction(){

        actionlockCounter++;

        if(actionlockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // Random number between 1-100
            int i2 = random.nextInt(100) + 1;

            if (i <= 25) {
                if(i2 <= 25) {
                    direction = "up";
                }
                if(i2 > 25){
                    direction ="standUp";
                }
            }
            if (i > 25 && i <= 50) {
                if(i2 <= 25) {
                    direction = "down";
                }
                if(i2 > 25){
                    direction ="standDown";
                }
            }
            if (i > 50 && i <= 75) {
                if(i2 <= 25) {
                    direction = "left";
                }
                if(i2 > 25){
                    direction ="standLeft";
                }
            }
            if (i > 75) {
                if(i2 <= 25) {
                    direction = "right";
                }
                if(i2 > 25){
                    direction ="standRight";
                }
            }
            actionlockCounter = 0;
        }


    }
    public void speak(){

        //CHARACTER SPECIFIC STUFF
        super.speak();
    }
    public void update(){

            setAction();

            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, false);
            gp.cChecker.checkPlayer(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter <= 1) {
                spriteNum = 1;
            }
            if (spriteCounter > 3 && spriteCounter <= 6) {
                spriteNum = 2;
            }
            if (spriteCounter > 6 && spriteCounter <= 9) {
                spriteNum = 3;
            }
            if (spriteCounter > 9 && spriteCounter <= 12) {
                spriteNum = 4;
            }
            if (spriteCounter > 12) {
                spriteCounter = 0;
            }
        }


}
