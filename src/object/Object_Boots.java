package object;

import entity.Entity;
import main.GamePanel;

public class Object_Boots extends Entity {

    public Object_Boots(GamePanel gp) {

        super(gp);

        name = "Boots";
        down1 = setup("/objects/helmets/1", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 10;
        solidArea.y = 16;
        solidArea.width = 44;
        solidArea.height = 38;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
