package Shooting;
public class game_over_scene {
	public int x,y;
	game_over_scene(){
		x = 0;y = -1900;
	}
	public void move() {
		if(y < 0)
		y += 50;
	}
}
