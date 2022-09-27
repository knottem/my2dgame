package object;

import entity.Entity;
import main.GamePanel;

public class Object_StrawHat extends Entity {

    public Object_StrawHat(GamePanel gp){

        super(gp);

        name = "StrawHat";
        down1 = setup("/objects/helmets/12", gp.tileSize, gp.tileSize);
    }
}
