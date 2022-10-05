package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class TIMERI implements ActionListener
{
	Timer t;
	public static int TIME;
	public static boolean j;
	TIMERI(){
		TIME=1;
		j=false;
		t=new Timer(1000,this);
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TIME--;
		if(TIME<=0) {
			System.out.println("Easter egg!");
			t.stop();
			j=true;
		}
	}
	
}
