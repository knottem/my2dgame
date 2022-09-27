package main;

import entity.NPC_oldMan;
import monster.Monster_GreenSlime;
import object.Object_Boots;
import object.Object_Chest;

public class AssetPlacer {

    GamePanel gp;

    public AssetPlacer(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        gp.obj[0] = new Object_Chest(gp);
        gp.obj[0].worldX = 21 * gp.tileSize;
        gp.obj[0].worldY = 11 * gp.tileSize;

    }
    public void setNPC(){

        gp.npc[0] = new NPC_oldMan(gp);
        gp.npc[0].worldX = 13 * gp.tileSize;
        gp.npc[0].worldY = 10 * gp.tileSize;

        gp.npc[1] = new NPC_oldMan(gp);
        gp.npc[1].worldX = 20 * gp.tileSize;
        gp.npc[1].worldY = 10 * gp.tileSize;

    }
    public void setMonsters(){

        gp.monster[0] = new Monster_GreenSlime(gp);
        gp.monster[0].worldX = 20 * gp.tileSize;
        gp.monster[0].worldY = 20 * gp.tileSize;

        gp.monster[1] = new Monster_GreenSlime(gp);
        gp.monster[1].worldX = 22 * gp.tileSize;
        gp.monster[1].worldY = 22 * gp.tileSize;
    }
}
