package object;

import entity.Entity;
import main.GamePanel;

public class Object_Heart extends Entity {

    public Object_Heart(GamePanel gp){

        super(gp);

        name = "Heart";
        image = setup("/objects/hearts/heart_0", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/hearts/heart_1", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/hearts/heart_2", gp.tileSize, gp.tileSize);

        }
}
