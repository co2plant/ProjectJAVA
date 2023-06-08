package Shooting;
public class Enemy {
	public int x,y,hp;
	public int attack_cool = 10;
	public int attack_cool_max;
	public int move_pattern; // 0오른쪽으로만 1위로만 2오른쪽 위 대각선 3왼쪽 위 대각선 4왼쪽으로만 5 위로만(오른쪽에서 나옴)
	public int value;
	Enemy(int hp,int pattern,int cool_max){
		this.hp=hp;move_pattern = pattern;attack_cool_max=cool_max;
		value = (int)(Math.random()*49) + 1;
		int ran =(int)(Math.random()*2);
		if(ran != 0)
			value *= -1;
		switch(move_pattern) {
		case 0:x=-50;y=200;break;
		case 1:x=0;y=900;break;
		case 2:x=-50;y=800;break;
		case 3:x=1330;y=800;break;
		case 4:x=1400;y=150;break;
		case 5:x=1205;y=850;break;
		}
	}
	public void move() {
		switch(move_pattern) {
		case 0:x += Main_class.enemy_speed;break;
		case 1:y -= Main_class.enemy_speed;break;
		case 2:x += (Main_class.enemy_speed);y -=(Main_class.enemy_speed);break;
		case 3:x -= (Main_class.enemy_speed);y -=(Main_class.enemy_speed);break;
		case 4:x -= Main_class.enemy_speed;break;
		case 5:y -= Main_class.enemy_speed;break;
		}
	}
}
