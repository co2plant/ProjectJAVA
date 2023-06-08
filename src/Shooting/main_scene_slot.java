package Shooting;
public class main_scene_slot{
	public int slot = 0;
	public void slot_change(int key) {
		if(key == 0) {
			slot -= 1;if(slot <= -1)slot = 1;
		}
		else {
			slot += 1;if(slot >= 2)slot = 0;
		}
	}
	public void slot_choice() {
		switch(slot) {
		case 0:Main_class.scene_number = Main_class.scene_game;break;
		case 1:if(Main_class.exit_switch == false) {Main_class.exit_switch = true;Main_class.exit_frame();};return;
		}
	}
}
