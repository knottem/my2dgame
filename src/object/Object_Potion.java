package object;

import entity.Entity;
import main.GamePanel;

public class Object_Potion extends Entity {

    public Object_Potion(GamePanel gp){

        super(gp);

        name = "Potion";
        down1 = setup("/objects/Potions/Icon1", gp.tileSize, gp.tileSize);

    }
}
