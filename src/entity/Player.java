package entity;
import main.GamePanel;
import main.KeyHandler;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        //player collision size
        solidArea = new Rectangle();
        solidArea.x = 15;
        solidArea.y = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 37;
        solidArea.height = 32;

        //Weapon collision size
        attackArea.width = 32;
        attackArea.height = 32;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValues(){

        //Starting Values
        worldX = gp.tileSize * 12;
        worldY = gp.tileSize * 8;
        speed = 4;
        diagonalSpeed = 3;
        direction = "down";

        // PLAYER START STATUS
        maxLife = 6;
        life = maxLife;
    }
    public void getPlayerImage(){

        up1 = setup("/player/playerup1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/playerup2", gp.tileSize, gp.tileSize);
        up3 = setup("/player/playerup3", gp.tileSize, gp.tileSize);
        down1 = setup("/player/playerdown1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/playerdown2", gp.tileSize, gp.tileSize);
        down3 = setup("/player/playerdown3", gp.tileSize, gp.tileSize);
        left1 = setup("/player/playerleft1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/playerleft2", gp.tileSize, gp.tileSize);
        left3 = setup("/player/playerleft3", gp.tileSize, gp.tileSize);
        right1 = setup("/player/playerright1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/playerright2", gp.tileSize, gp.tileSize);
        right3 = setup("/player/playerright3", gp.tileSize, gp.tileSize);

    }

    public void getPlayerAttackImage(){

        attackUp1 = setup("/player/sword/attack/playerswordup_0", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/player/sword/attack/playerswordup_1", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/player/sword/attack/playersworddown_0", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/player/sword/attack/playersworddown_1", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/player/sword/attack/playerswordleft_0", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/player/sword/attack/playerswordleft_1",gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/player/sword/attack/playerswordright_0",gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/player/sword/attack/playerswordright_1",gp.tileSize*2, gp.tileSize);
    }

    public void update() {

        if(attacking){
            attacking();

        }

        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed || keyH.spacePressed) {

            if (keyH.upPressed && keyH.leftPressed) {
                direction = "upLeft";
            }
            if (keyH.upPressed && keyH.rightPressed) {
                direction = "upRight";
            }
            if (keyH.downPressed && keyH.leftPressed) {
                direction = "downLeft";
            }
            if (keyH.downPressed && keyH.rightPressed) {
                direction = "downRight";
            }
            if (keyH.downPressed && keyH.upPressed) {
                direction = "dance";
            }
            if (keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed) {
                direction = "up";
            }
            if (keyH.downPressed && !keyH.upPressed && !keyH.leftPressed && !keyH.rightPressed) {
                direction = "down";
            }
            if (keyH.leftPressed && !keyH.downPressed && !keyH.upPressed && !keyH.rightPressed) {
                direction = "left";
            }
            if (keyH.rightPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.upPressed) {
                direction = "right";

            }

            collisionOn = false;

            //Checks tiles collision
            gp.cChecker.checkTile(this);

            // Check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // Check npc collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // Check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // Check event
            gp.eventHandler.checkEvent();

            //If collision is false, player will still move

                if (!collisionOn && (!keyH.enterPressed || !keyH.spacePressed)) {

                    switch (direction) {
                        case "up" ->
                                worldY -= speed;
                        case "upLeft" -> {
                                worldY -= diagonalSpeed;
                                worldX -= diagonalSpeed;
                        }
                        case "upRight" -> {
                                worldY -= diagonalSpeed;
                                worldX += diagonalSpeed;
                        }
                        case "down" ->
                                worldY += speed;
                        case "downLeft" -> {
                                worldY += diagonalSpeed;
                                worldX -= diagonalSpeed;
                        }
                        case "downRight" -> {
                                worldY += diagonalSpeed;
                                worldX += diagonalSpeed;
                        }
                        case "left" ->
                                worldX -= speed;
                        case "right" ->
                                worldX += speed;
                    }
                }

                gp.keyH.enterPressed = false;
                gp.keyH.spacePressed = false;

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
            } else {
                standCounter++;
                if (standCounter == 24) {
                    spriteNum = 5;    // Idle sprite
                    standCounter = 0;
                }
            }
            if(invincible){
                invincibleFadeCounter++;
                if(invincibleFadeCounter > 24){
                    invincibleFadeCounter = 0;
                }
                invincibleCounter++;
                if(invincibleCounter > 60){
                    invincible = false;
                    invincibleCounter = 0;
                }
            }

        }
    public void attacking(){

            spriteCounter++;

            if(spriteCounter <=5){
                spriteNum = 1;
            }
            if(spriteCounter > 5 && spriteCounter <= 25) {
                spriteNum = 2;

                // Save the current worldX, world Y, solidArea
                int currentWorldX = worldX;
                int currentWorldY = worldY;
                int solidAreaWidth = solidArea.width;
                int solidAreaHeight = solidArea.height;

                //Adjust player's worldX/Y for the attackArea
                switch (direction) {
                    case "up", "upLeft", "upRight" -> worldY -= attackArea.height;
                    case "down", "downLeft", "downRight" -> worldY += gp.tileSize;
                    case "left" -> worldX -= attackArea.width;
                    case "right" -> worldX += gp.tileSize;
                }

                // attackarea becomes solidArea
                solidArea.width = attackArea.width;
                solidArea.height = attackArea.height;

                // checks monster collision with the new updated collision point

                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                damageMonster(monsterIndex);


                // Resets back
                worldX = currentWorldX;
                worldY = currentWorldY;
                solidArea.width = solidAreaWidth;
                solidArea.height = solidAreaHeight;


            }
            if(spriteCounter > 25){
                spriteNum = 1;
                spriteCounter = 0;
                attacking = false;
            }
        }

    public void pickUpObject(int i){

        if(i != 999) {

        }
    }
    public void interactNPC(int i) {

        if (gp.keyH.enterPressed || gp.keyH.spacePressed) {

            if (i != 999) {
                gp.gameState = gp.dialogState;
                gp.npc[i].speak();
            }
            else {
                gp.playSE(6);
                attacking = true;
            }
        }
    }

    public void contactMonster(int i){

        if(i != 999){
            if(!invincible) {
                gp.playSE(7);
                life -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i){

        if(i != 999){

            if(!gp.monster[i].invincible){

                gp.playSE(5);
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if(gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                }
            }
        }

    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up", "upLeft", "upRight" -> {
                if(!attacking){
                    if (spriteNum == 1 || spriteNum == 2) {image = up1;}
                    if (spriteNum == 3 || spriteNum == 4) {image = up2;}
                    if (spriteNum == 5) {image = up3;}}
                if(attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }}
            case "down", "downRight", "downLeft" -> {
                if(!attacking){
                    if (spriteNum == 1 || spriteNum == 2) {image = down1;}
                    if (spriteNum == 3 || spriteNum == 4) {image = down2;}
                    if (spriteNum == 5) {image = down3;}}
                if(attacking) {
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }}
            case "left" -> {
                if(!attacking){
                    if (spriteNum == 1 || spriteNum == 2) {image = left1;}
                    if (spriteNum == 3 || spriteNum == 4) {image = left2;}
                    if (spriteNum == 5) {image = left3;}}
                if(attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }}
            case "right" -> {
                if(!attacking){
                    if (spriteNum == 1 || spriteNum == 2) {image = right1;}
                    if (spriteNum == 3 || spriteNum == 4) {image = right2;}
                    if (spriteNum == 5) {image = right3;}}
                if(attacking) {
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }}
            case "dance" -> {
                if (spriteNum == 1) {image = left1;}
                if (spriteNum == 2) {image = down3;}
                if (spriteNum == 3) {image = right2;}
                if (spriteNum == 4) {image = up3;}
                if (spriteNum == 5) {image = up3;}
            }
        }
        if(invincible){
            if(invincibleFadeCounter <= 12) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
            if(invincibleFadeCounter > 12){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //debug
        if(keyH.DebugMenu) {
            // AttackArea
            tempScreenX = screenX + solidArea.x;
            tempScreenY = screenY + solidArea.y;
            switch (direction) {
                case "up", "upRight", "upLeft" -> tempScreenY = screenY - attackArea.height;
                case "down", "downRight", "downLeft" -> tempScreenY = screenY + gp.tileSize;
                case "left" -> tempScreenX = screenX - attackArea.width;
                case "right" -> tempScreenX = screenX + gp.tileSize;
            }
            g2.setColor(Color.red);
            g2.setStroke(new BasicStroke(1));
            g2.drawRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);

            g2.setFont(new Font("Arial", Font.PLAIN, 26));
            g2.setColor(Color.white);
            g2.drawString("Invincible:" + invincibleCounter, 10, 400);
        }
    }
}
