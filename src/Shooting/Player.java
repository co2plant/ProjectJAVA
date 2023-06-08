package Shooting;
public class Player {
	public int x,y;
	public int player_attack_damege = 1;
	public int money;
	public float player_attack_speed = 1.2f; //공속인데 0.1 이하로 내려가게 하면 안됨
	Player(){
		x = 400;y = 650;money = 10000000;
	}
}
