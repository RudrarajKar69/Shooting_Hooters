package main;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class WINDOW extends JFrame {
	
	PLAYER gg;
	ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("ICON.png"));
	boolean shot=false;
	int time;
	
	WINDOW(){
		try {
			time=Math.abs(Integer.parseInt(JOptionPane.showInputDialog("Enter the number of seconds you \nwant each round to be \n(recommended 60 seconds)")));
			gg=new PLAYER(time);
			gg.bullet=new ImageIcon(getClass().getClassLoader().getResource("BULLET.png"));
			gg.enemy=new ImageIcon(getClass().getClassLoader().getResource("Player-1.png"));
			gg.player=new ImageIcon(getClass().getClassLoader().getResource("PLAYER.png"));
		}
		catch(Exception e)
		{
			gg=new PLAYER();
			gg.bullet=new ImageIcon(getClass().getClassLoader().getResource("BULLET.png"));
			gg.enemy=new ImageIcon(getClass().getClassLoader().getResource("Player-1.png"));
			gg.player=new ImageIcon(getClass().getClassLoader().getResource("PLAYER.png"));
		}
		this.setTitle("Shooter 2D");
		this.setMinimumSize(new Dimension(720,720/12*9));
		this.setLocationRelativeTo(null);
		this.setIconImage(logo.getImage());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(gg);
		
	}
	@SuppressWarnings("unused")
	public void setShot(boolean shot)
	{
		this.shot=shot;
		int temp = 10;
	}
	
	public boolean getShot()
	{
		return this.shot;
	}
}
