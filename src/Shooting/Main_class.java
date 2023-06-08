package Shooting;
import java.awt.Color;

import javax.swing.JLabel;



public class Main_class {
	final static int scene_main = 0;
	final static int scene_game = 1;
	final static int default_attack_cool = 10;
	public static int scene_number=scene_main;
	final static int summon_time = 5;
	public static int summon_count_max = 0;
	public static boolean menu_open = false;
	public static int bullet_speed = 30;
	public static int enemy_bullet_speed = 10;
	public static int enemy_speed = 10;
	public static int round = 0;
	public static Jframe j;
	public static boolean exit_switch = false;

	public Main_class()
	{

		j = new Jframe();
	}
	public static void exit_frame()
	{
		new Kiosk.Main_Frame();
		j.dispose();
		return;
	}
	

}
