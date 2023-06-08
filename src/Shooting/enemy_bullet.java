package Shooting;


public class enemy_bullet {
	public int x,y;
	private int partern;
	public int value;
	enemy_bullet(int player_x,int player_y,int class_partern,int value){
		x = player_x;
		y = player_y;
		partern=class_partern;
		this.value = value;
		if(this.value > 0)
			this.value *= -1;
	}
	public void move() {
		switch(partern) {
		case 0:y += Main_class.enemy_bullet_speed;break;
		case 1:x += Main_class.enemy_bullet_speed;break;
		case 2:y += Main_class.enemy_bullet_speed;x += Main_class.enemy_bullet_speed;break;
		case 3:x -= Main_class.enemy_bullet_speed;y += Main_class.enemy_bullet_speed;break;
		case 4:y += Main_class.enemy_bullet_speed;break;
		case 5:x -= Main_class.enemy_bullet_speed;break;
		}
	}
}
