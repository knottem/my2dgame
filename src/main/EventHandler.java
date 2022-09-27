package main;
public class EventHandler {

    GamePanel gp;
    EventRect eventRectangle[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRectangle = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){

            eventRectangle[col][row]= new EventRect();
            eventRectangle[col][row].x = 32;
            eventRectangle[col][row].y = 32;
            eventRectangle[col][row].width = 2;
            eventRectangle[col][row].height = 2;
            eventRectangle[col][row].eventRectangleDefaultX = eventRectangle[col][row].x;
            eventRectangle[col][row].eventRectangleDefaultY = eventRectangle[col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent(){

        //CHECK IF THE PLAYER IS MORE THAN 1 TILE AWAY FROM THE LAST EVENT
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent) {
            if (hit(15, 9, "down")) {damagePit(15, 9, gp.dialogState);}
            if (hit(17, 9, "any")) {damagePit(17, 9, gp.dialogState);}
            if (hit(20, 9, "any")){teleport(20,9, gp.dialogState);}
            if (hit(15, 7, "up")) {healingPool(15, 9, gp.dialogState);}
        }

    }
    public boolean hit(int col, int row, String reqDirection){

        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRectangle[col][row].x = (col * gp.tileSize) + eventRectangle[col][row].x;
        eventRectangle[col][row].y = (row * gp.tileSize) + eventRectangle[col][row].y;

        if(gp.player.solidArea.intersects(eventRectangle[col][row]) && !eventRectangle[col][row].eventDone){
            if(gp.player.direction.equals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRectangle[col][row].x = eventRectangle[col][row].eventRectangleDefaultX;
        eventRectangle[col][row].y = eventRectangle[col][row].eventRectangleDefaultY;

        return hit;
    }
    public void teleport(int col, int row, int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport!";
        gp.player.worldX = gp.tileSize*30;
        gp.player.worldY = gp.tileSize*20;
        eventRectangle[col][row].eventDone = true;
    }
    public void damagePit(int col, int row, int gameState){

        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall in to a trap!";
        gp.player.life -= 1;
        canTouchEvent = false;
    }

    public void healingPool(int col, int row, int gameState){

        if(gp.keyH.enterPressed || gp.keyH.spacePressed){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drank som water.\n your life has been recovered";
            gp.player.life = gp.player.maxLife;

        }
    }


}
