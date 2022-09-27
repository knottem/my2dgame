package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2, tileNum3, tileNum4;

        switch (entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "upLeft":
                entityTopRow = (entityTopWorldY - entity.diagonalSpeed)/gp.tileSize;
                entityLeftCol = (entityLeftWorldX - entity.diagonalSpeed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum3 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision || gp.tileManager.tile[tileNum3].collision){
                    entity.collisionOn = true;
                }
                break;
            case "upRight":
                entityTopRow = (entityTopWorldY - entity.diagonalSpeed)/gp.tileSize;
                entityRightCol = (entityRightWorldX + entity.diagonalSpeed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum3 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum4 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision || gp.tileManager.tile[tileNum3].collision || gp.tileManager.tile[tileNum4].collision){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "downLeft":
                entityBottomRow = (entityBottomWorldY + entity.diagonalSpeed)/gp.tileSize;
                entityLeftCol = (entityLeftWorldX - entity.diagonalSpeed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                tileNum3 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum4 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision || gp.tileManager.tile[tileNum3].collision || gp.tileManager.tile[tileNum4].collision){
                    entity.collisionOn = true;
                }
                break;
            case "downRight":
                entityBottomRow = (entityBottomWorldY + entity.diagonalSpeed)/gp.tileSize;
                entityRightCol = (entityRightWorldX + entity.diagonalSpeed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                tileNum3 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum4 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision || gp.tileManager.tile[tileNum3].collision || gp.tileManager.tile[tileNum4].collision){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
        }

    }
    public int checkObject(Entity entity, boolean player){

        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {

            if(gp.obj[i] != null){

                //Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch(entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "upRight":
                        entity.solidArea.y -= entity.diagonalSpeed;
                        entity.solidArea.x += entity.diagonalSpeed;
                        break;
                    case "upLeft":
                        entity.solidArea.y -= entity.diagonalSpeed;
                        entity.solidArea.x -= entity.diagonalSpeed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "downLeft":
                        entity.solidArea.y += entity.diagonalSpeed;
                        entity.solidArea.x -= entity.diagonalSpeed;
                        break;
                    case "downRight":
                        entity.solidArea.y += entity.diagonalSpeed;
                        entity.solidArea.x += entity.diagonalSpeed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;

                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                    }
                    if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                                     if(gp.obj[i].collision){
                                         entity.collisionOn = true;
                                     }
                                     if(player){
                                         index = i;
                                     }
                                 }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
                }
            }
        return index;
    }
    //NPC OR MONSTER COLLISION
    public int checkEntity(Entity entity, Entity[] target){

        int index = 999;

           for (int i = 0; i < target.length; i++) {

               if(target[i] != null){

                   //Get entity's solid area position
                   entity.solidArea.x = entity.worldX + entity.solidArea.x;
                   entity.solidArea.y = entity.worldY + entity.solidArea.y;
                   //Get the object's solid area position
                   target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                   target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                   switch(entity.direction){
                       case "up":
                           entity.solidArea.y -= entity.speed;
                           break;
                       case "upRight":
                           entity.solidArea.y -= entity.diagonalSpeed;
                           entity.solidArea.x += entity.diagonalSpeed;
                           break;
                       case "upLeft":
                           entity.solidArea.y -= entity.diagonalSpeed;
                           entity.solidArea.x -= entity.diagonalSpeed;
                           break;
                       case "down":
                           entity.solidArea.y += entity.speed;
                           break;
                       case "downLeft":
                           entity.solidArea.y += entity.diagonalSpeed;
                           entity.solidArea.x -= entity.diagonalSpeed;
                           break;
                       case "downRight":
                           entity.solidArea.y += entity.diagonalSpeed;
                           entity.solidArea.x += entity.diagonalSpeed;
                           break;
                       case "left":
                           entity.solidArea.x -= entity.speed;
                           break;

                       case "right":
                           entity.solidArea.x += entity.speed;
                           break;

                        }
                   if(entity.solidArea.intersects(target[i].solidArea)) {
                       if(target[i] != entity) {
                           entity.collisionOn = true;
                           index = i;
                       }
                   }
                   entity.solidArea.x = entity.solidAreaDefaultX;
                   entity.solidArea.y = entity.solidAreaDefaultY;
                   target[i].solidArea.x = target[i].solidAreaDefaultX;
                   target[i].solidArea.y = target[i].solidAreaDefaultY;
                   }
               }
           return index;

    }
    public boolean checkPlayer(Entity entity){

        boolean contactPlayer = false;
        //Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        //Get the object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch(entity.direction){
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
            }

        if(entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }
}
