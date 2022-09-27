package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Monster_GreenSlime extends Entity {

    GamePanel gp;

    public Monster_GreenSlime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = 2;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 12;
        solidArea.y = 26;
        solidArea.width = 40;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();

    }
    public void getImage(){

        up1 = setup("/monsters/slime/slime_green_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/monsters/slime/slime_green_up2", gp.tileSize, gp.tileSize);
        down1 = setup("/monsters/slime/slime_green_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/monsters/slime/slime_green_down2", gp.tileSize, gp.tileSize);
        left1 = setup("/monsters/slime/slime_green_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/monsters/slime/slime_green_left2", gp.tileSize, gp.tileSize);
        right1 = setup("/monsters/slime/slime_green_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/monsters/slime/slime_green_right2", gp.tileSize, gp.tileSize);

    }
    public void setAction(){
        actionlockCounter++;

                if(actionlockCounter == 120) {

                    Random random = new Random();
                    int i = random.nextInt(100) + 1; // Random number between 1-100

                    if (i <= 25) {
                        direction = "up";
                    }
                    if (i > 25 && i <= 50) {
                        direction = "down";
                    }
                    if (i > 50 && i <= 75) {
                        direction = "left";
                    }
                    if (i > 75) {
                        direction = "right";
                    }
                    actionlockCounter = 0;
                }


    }
    public void damageReaction(){

        actionlockCounter = 0;
        direction = gp.player.direction;
    }
}
