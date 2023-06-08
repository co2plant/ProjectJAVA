package Shooting;


public class Bullet {
		public int x,y;
		Bullet(int player_x,int player_y){
			x = player_x;
			y = player_y+10;
		}
		public void move() {
			y -= Main_class.bullet_speed;
		}
}
