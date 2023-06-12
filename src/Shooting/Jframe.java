package Shooting;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JLabel;

import Kiosk.CSV_manager;
import Kiosk.GameOver;
public class Jframe extends JFrame implements KeyListener, Runnable {
	private int summon_enemy = 0;
	private int summon_cool;
	final private int select_cool = 5;
	private float bullet_time;
	private int Score = 0;
	private int Score_point = 0;
	final private int move_speed = 10;
	private int bullet_w,bullet_h,enemy_w,enemy_h;
	private boolean is_game_over = false;
	final int width = 1280; final int height = 720;final int Left = 0;final int Right = 1;
	boolean Key_Up = false;
	boolean Key_Down = false;
	boolean Key_Left = false;
	Player player;
	boolean Key_Right = false;
	private int game_over_cool = 0 ;
	boolean Key_Z = false;
	private int start_cool;
	Thread th;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image[] main_text = new Image[3];
	Image character;
	Image gray;
	Image enemy_bullet;
	Image enemy; //占쎌읅 占쎈늄嚥≪뮉�꽅占쏙옙占쎌뿯
	Image guide;
	Image bullet_image;
	Image main_logo;
	Image neo_enemy;
	Image[] menu_text = new Image[2];
	Image buffImage; Graphics buffg;
	Image game_over;
	main_scene_slot msl = new main_scene_slot();
	private int select_time = 0;
	Menu_select menu;
	Enemy enemy_class;
	enemy_bullet bullet_enemy;
	enemy_bullet bullet_enemy_check;
	Bullet bullet;
	private boolean set_font_switch = false;
	Font font;
	Bullet bullet_check;
	game_over_scene gos;
	ArrayList bullet_List = new ArrayList();
	ArrayList Enemy_List = new ArrayList();
	ArrayList enemy_bullet_List = new ArrayList();
	CSV_manager CSV;
	Jframe(){
	CSV = new CSV_manager();
	msl.slot = 0;
	Main_class.exit_switch = false;
	start();
	image_set();
	setSize(width, height);
	setTitle("Shooting");
	Dimension screen = tk.getScreenSize();
	int f_xpos = (int)(screen.getWidth() / 2 - width / 2);
	int f_ypos = (int)(screen.getHeight() / 2 - height / 2);
	setUndecorated(true);//占쏙옙占쎌뵠占쏙옙獄쏉옙 占쎈씨占쎈막占쎈뮉椰꾬옙
	setLocation(f_xpos, f_ypos);//占쎌맊占쎈즲占쎌뒭筌∽옙 餓λ쵐釉곤옙肉� 占쎄문占쎄쉐
	setResizable(false);//占쎄텢占쎌뵠筌앾옙 鈺곌퀣�쟿 �겫�뜃占쏙옙�뮟
	setVisible(true);
	
	}
	

	public void image_set() {
		main_text[0] = tk.getImage("src/Shooting/Image/start.png"); 
		main_text[1] = tk.getImage("src/Shooting/Image/OPTION.PNG"); 
		main_text[2] = tk.getImage("src/Shooting/Image/exit.png"); 
		menu_text[0] = tk.getImage("src/Shooting/Image/continue.png"); 
		menu_text[1] = tk.getImage("src/Shooting/Image/exit_menu.png"); 
		character = tk.getImage("src/Shooting/Image/character.png"); 
		gray = tk.getImage("src/Shooting/Image/gray.png"); 
		enemy = tk.getImage("src/Shooting/Image/enemy.png"); 
		bullet_image = tk.getImage("src/Shooting/Image/bullet.png"); 
		enemy_bullet = tk.getImage("src/Shooting/Image/enemy_bullet.png"); 
		main_logo = tk.getImage("src/Shooting/Image/main_logo.png"); 
		guide = tk.getImage("src/Shooting/Image/guide.png"); 
		neo_enemy = tk.getImage("src/Shooting/Image/neo_enemy.png"); 
		game_over = tk.getImage("src/Shooting/Image/XGAMEOVER.png"); 
		bullet_w=21;bullet_h = 37;
		enemy_w=75;enemy_h=75;
		font = new Font("SanSerif",font.BOLD,30);
	}
	
	public void start(){
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	addKeyListener(this);
	th = new Thread(this); 
	th.start(); 
	

	}

	public void run(){ 
	try{ 
	while(is_game_over == false){
	KeyProcess(); 
	repaint();
	if(Main_class.scene_number == Main_class.scene_main || Main_class.menu_open == true) {
		if(select_time == 0)
			slot_check();
		else
			select_time -= 1;
	}
	if(Main_class.scene_number == Main_class.scene_game && Main_class.menu_open == false) {
		bullet();
		if(bullet_time > 0 && Main_class.menu_open == false)
			bullet_time -= 0.1f;
	}
	if(start_cool > 0)
		start_cool--;
	
	if(Enemy_List.isEmpty()) {
		if(summon_enemy == 0) {
			Main_class.round++;
			if((Main_class.round%3) == 0)
				Main_class.summon_count_max += 2;
			summon_enemy = Main_class.summon_count_max;
		}
	}
	if(summon_enemy > 0 && summon_cool<=0 && Main_class.menu_open == false) {
		enemy_class = new Enemy((int)(Math.random()*Main_class.summon_count_max)+1,(int)(Math.random()*6),(int)(Math.random()*20)+Main_class.default_attack_cool);
		Enemy_List.add(enemy_class);
		summon_enemy--;
		summon_cool = Main_class.summon_time;
	}
	else if(summon_cool > 0 && Main_class.menu_open == false)
		summon_cool--;
	else
		if(is_game_over == false)
			enemy();
	if(game_over_cool > 0)
		game_over_cool--;
	Thread.sleep(20); 
	if(Main_class.menu_open == false)
		Score_point++;
	if(Score_point >= 50)
	{
		Score_point = 0;
		Score++;
	}
	}
	}catch (Exception e){}
	}

	
	//�솮占�
	public void paint(Graphics g){
	buffImage = createImage(width, height); 
	buffg = buffImage.getGraphics(); 
	
	update(g);
	}

	
	public void update(Graphics g){
		if(is_game_over == false) {
		Draw_test();
		if(Main_class.scene_number == Main_class.scene_game && start_cool == 0)
			Draw_bullet();
		if(Main_class.scene_number == Main_class.scene_game) {
			Draw_enemy();
			Draw_enemy_bullet();
		}
		if(Main_class.menu_open == true && is_game_over == false)
			Draw_menu();
		
		if(Main_class.scene_number == Main_class.scene_game && is_game_over == false)
			Draw_text();
		g.drawImage(buffImage, 0, 0, this); 
		}
		}

	public void Draw_over() {
		buffg.drawImage(game_over,gos.x,gos.y, this);
		gos.move();
	}
	
	public void Draw_text() {
			buffg.setFont(font);
			buffg.drawString(Integer.toString(player.money) + " Life Point", 0, 30);
			buffg.drawString(Integer.toString(Score) + " Score", 0, 60);
	}
	
	public void Draw_test(){ 
	buffg.clearRect(0, 0, width, height);
	if(Main_class.scene_number == Main_class.scene_main) {
		buffg.drawImage(guide,410,450, this);
	for(int i =0;i<2;i++)
		buffg.drawImage(main_text[i],i*810+200,i==msl.slot?550:650, this);
	}
	else if (Main_class.scene_number == Main_class.scene_game)
		buffg.drawImage(character,player.x,player.y, this);
	}
	
	
	public void Draw_menu() {
		buffg.drawImage(gray,0,0, this);
		for(int i=0;i<2;i++) {
		buffg.drawImage(menu_text[i],i == menu.slot?900:1000,i==0?500:600, this);
		}
	}

	public void Draw_bullet() {
		for (int i = 0 ; i < bullet_List.size(); i++) {
			bullet = (Bullet) (bullet_List.get(i));
			buffg.drawImage(bullet_image,bullet.x,bullet.y, this);
			if(Main_class.menu_open == false)
				bullet.move();
			if(bullet.y > (height+50))
				bullet_List.remove(i);
		}
	}
	
	public void Draw_enemy() {
		for (int i = 0 ; i < Enemy_List.size(); i++) {
			enemy_class = (Enemy) (Enemy_List.get(i));
			if(enemy_class.value < 0)
			buffg.drawImage(neo_enemy,enemy_class.x,enemy_class.y, this);
			else
				buffg.drawImage(enemy,enemy_class.x,enemy_class.y, this);
			if(Main_class.menu_open == false && is_game_over == false)
				enemy_class.move();
			if(enemy_class.x > (width+200))
				Enemy_List.remove(i);
			else if(enemy_class.x < (-200))
				Enemy_List.remove(i);
			else if(enemy_class.y > (height+200))
				Enemy_List.remove(i);
			else if(enemy_class.y < (-200))
				Enemy_List.remove(i);
			if(enemy_class.attack_cool <= 0 && Main_class.menu_open == false) {
				bullet_enemy = new enemy_bullet(enemy_class.x+18,enemy_class.y+18,enemy_class.move_pattern,enemy_class.value);
			enemy_bullet_List.add(bullet_enemy);
			enemy_class.attack_cool = enemy_class.attack_cool_max;
			}
			else if (enemy_class.attack_cool > 0 && Main_class.menu_open == false)
				enemy_class.attack_cool--;
				
		}
	}
	
	public void Draw_enemy_bullet() {
		for (int i = 0 ; i < enemy_bullet_List.size(); i++) {
			bullet_enemy_check = (enemy_bullet) (enemy_bullet_List.get(i));
			buffg.drawImage(enemy_bullet,bullet_enemy_check.x,bullet_enemy_check.y, this);
			if(Main_class.menu_open == false && is_game_over == false)
				bullet_enemy_check.move();
			if(bullet_enemy_check.y > (height+50))
				enemy_bullet_List.remove(i);
			else if(bullet_enemy_check.y < (-50))
				enemy_bullet_List.remove(i);
			else if(bullet_enemy_check.y >(width+50))
				enemy_bullet_List.remove(i);
			else if(bullet_enemy_check.y <(-50))
				enemy_bullet_List.remove(i);
			if(crash(bullet_enemy_check.x,bullet_enemy_check.y,player.x+18,player.y+18,15,15,enemy_w/2,enemy_h/2)) {
				player.money += (bullet_enemy_check.value * 100000);
				enemy_bullet_List.remove(i);
				if(player.money <= 0 && is_game_over == false) {
					CSV.CSV_Write("Shooting", Integer.toString(Score));
					scene_change(Main_class.scene_main);
					is_game_over = true;
					game_over_cool = 30;
					dispose();
					new GameOver("Score : "+Integer.toString(Score));
				}
			}
		}
	}

	public void keyPressed(KeyEvent e){ // 占쎄텕 占쎈땭占쏙옙占쎌뱽占쎈르
	switch(e.getKeyCode()){
	case KeyEvent.VK_UP :
	Key_Up = true;
	break;
	case KeyEvent.VK_DOWN :
	Key_Down = true;
	break;
	case KeyEvent.VK_LEFT :
	Key_Left = true;
	break;
	case KeyEvent.VK_RIGHT :
	Key_Right = true;
	break;
	case KeyEvent.VK_X : //X
		if(Main_class.scene_number == Main_class.scene_game && is_game_over == false)
			Main_class.menu_open = true;
		else if (Main_class.scene_number == Main_class.scene_game && is_game_over == true && game_over_cool <= 0) {
			is_game_over = false;
			Main_class.menu_open = false;
			scene_change(Main_class.scene_main);
		}
	break;
	case KeyEvent.VK_ESCAPE : //ESC
		if(Main_class.scene_number == Main_class.scene_game)
			Main_class.menu_open = true;
		else
		{
			Main_class.exit_frame();
			
		}
	break;
	case 0x5A: // Z占쎄텕
		Key_Z = true;
		if(Main_class.scene_number == Main_class.scene_main && Key_Z == true) {
			start_cool = 5;
			msl.slot_choice();
			if(msl.slot == 0) {
				player = new Player();
				menu = new Menu_select();
				Score = 0;
				Score_point = 0;
			}
		}
	break;
	}
	}
	public void keyReleased(KeyEvent e){ //占쎄텕 占쎈막占쎌뱽占쎈르
	switch(e.getKeyCode()){
	case KeyEvent.VK_UP :
	Key_Up = false;
	break;
	case KeyEvent.VK_DOWN :
	Key_Down = false;
	break;
	case KeyEvent.VK_LEFT :
	Key_Left = false;
	break;
	case KeyEvent.VK_RIGHT :
	Key_Right = false;
	break;
	case 0x5A: // Z占쎄텕
		Key_Z = false;
	break;
	}
	}
	public void keyTyped(KeyEvent e){} // 占쎈쿈占쎌뵬占쏙옙 占쎈씨占쎈뮉占쎈쑓 占쎈씨占쎌몵筌롳옙 占쎌궎�몴�꼷源�繹먲옙
	public void KeyProcess(){
		
		if(Main_class.scene_number == Main_class.scene_game && Main_class.menu_open == true && Key_Z == true) {
			switch(menu.slot) {
			case 0 :Key_Z = false;Main_class.menu_open = false;break;
			case 1 :Key_Z = false;scene_change(Main_class.scene_main);Main_class.menu_open = false;break;
			}
		}
		if(Main_class.scene_number == Main_class.scene_game && Main_class.menu_open == false && is_game_over == false) {
			if(Key_Up == true) player.y -= move_speed;
			if(Key_Down == true) player.y += move_speed;
			if(Key_Left == true) player.x -= move_speed;
			if(Key_Right == true) player.x += move_speed;
		}
		if(Main_class.scene_number == Main_class.scene_game) {
		if(player.x < 0)
			player.x = 0;
		else if((player.x + 75)> width)
			player.x = width-75;
		if(player.y < 0)
			player.y = 0;
		else if((player.y + 75) > height)
			player.y = height-75;
		}
	}
	public void slot_check() {
		if(Main_class.scene_number == Main_class.scene_main) {
		if(Key_Left == true) {
			msl.slot_change(Left);
			select_time = select_cool;
			}
		else if(Key_Right == true) {
			msl.slot_change(Right);
			select_time = select_cool;
		}
		}
		else if (Main_class.scene_number == Main_class.scene_game && Main_class.menu_open == true && is_game_over == false) {
			if(Key_Up == true) {
				menu.slot_change(Left);
				select_time = select_cool;
				}
			else if(Key_Down == true) {
				menu.slot_change(Right);
				select_time = select_cool;
			}
		}
	}
	
	public void scene_change(int scene) {
		Main_class.scene_number = scene;
		if(scene == Main_class.scene_main) {
			player = null;
			menu = null;
			gos = null;
			bullet_List.clear();
			Enemy_List.clear();
			enemy_bullet_List.clear();
			msl.slot = 0;
			Main_class.summon_count_max = 0;
			Main_class.round = 0;
			Key_Z = false;
			Key_Left = false;
			Key_Right = false;
	}
	}
	public void bullet() {
		if(Key_Z == true && bullet_time <= 0 && start_cool == 0) {
			bullet_time = player.player_attack_speed;
			bullet = new Bullet(player.x+25,player.y-45);
			bullet_List.add(bullet);
		}
	}
	
	public void enemy() {
		for(int i=0;i<Enemy_List.size();++i) {
			enemy_class = (Enemy) (Enemy_List.get(i));
			for(int j=0;j<bullet_List.size();++j) {
				bullet_check = (Bullet) (bullet_List.get(j));
					if(crash(bullet_check.x,bullet_check.y,enemy_class.x,enemy_class.y,bullet_w,bullet_h,enemy_w,enemy_h)){
						if((enemy_class.hp - player.player_attack_damege) > 0)
							enemy_class.hp -= player.player_attack_damege;
						else {
							Enemy_List.remove(i);
							player.player_attack_damege++;
							if(is_game_over == false)
								player.money += (int)(Math.random()*20000)+10000;
						}
						bullet_List.remove(j);
						return;
					}
					
				
			}
		}
	}

	public boolean crash(int x1, int y1, int x2, int y2, int w1, int h1, int w2, int h2){  // �빊�뫖猷� 筌ｋ똾寃�
		boolean check = false;
		if ( Math.abs( ( x1 + w1 / 2 )  - ( x2 + w2 / 2 ))  <  ( w2 / 2 + w1 / 2 )  
		&& Math.abs( ( y1 + h1 / 2 )  - ( y2 + h2 / 2 ))  <  ( h2 / 2 + h1/ 2 ) ){
		check = true;
		}else{ check = false;}

		return check;
}
	
}