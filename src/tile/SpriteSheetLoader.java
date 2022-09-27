package tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheetLoader {

   BufferedImage spriteSheet = ImageIO.read(new File("/tiles/pipo-map001.png"));

   int width;
   int height;
   int rows;
   int columns;
   BufferedImage[] sprites = new BufferedImage[rows * columns];

   public SpriteSheetLoader(int width, int height, int rows, int columns) throws IOException {
      this.width = width;
      this.height = height;
      this.rows = rows;
      this.columns = columns;

      for(int i = 0; i < rows; i++) {
         for(int j = 0; j < columns; j++) {
            sprites[(i * columns) + j] = spriteSheet.getSubimage(i * width, j * height, width, height);
         }
      }
   }
   public void paint(Graphics g) {
      g.drawImage(sprites[1], 32, 32, null);
   }
}