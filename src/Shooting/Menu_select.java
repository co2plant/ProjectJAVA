package Shooting;
public class Menu_select{
	public int slot = 0;
	public void slot_change(int key) {
		if(key == 0) {
			slot -= 1;if(slot <= -1)slot = 1;
		}
		else {
			slot += 1;if(slot >= 2)slot = 0;
		}
	}
}
