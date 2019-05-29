package tools;

public class CollisionRect {

    int x,y;
    int width, height;

     public CollisionRect(int x, int y, int width, int height){
         this.x = x;
         this.y = y;
         this.width = width;
         this.height = height;
     }

     public void move (int x, int y){
         this.x = x;
         this.y = y;
     }

     public boolean collideWith (CollisionRect rect){
         return x < rect.x + rect.width && y < rect.y + rect.height && x + width > rect.x && y + height > rect.y;
     }
}
