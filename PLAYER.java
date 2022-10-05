package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class PLAYER extends JPanel implements ActionListener, MouseListener {

	int TIMEE=60,speed=5,temp,score2,score;
	Timer t;
	Random rnd = new Random();
	public int bsx = rnd.nextInt(30)+10, bsy = rnd.nextInt(30)+10, bx, by, mx, my;
	static int playerWidth, playerHeight,bWIDTH = 25, bHEIGHT = 25;
	int x = 0, y = 0, ex = 100, ey = 100;
	Point mouse;
	boolean start=false,death = false,PLAYERDIES=false;
	int[] bulx = new int[bWIDTH], buly = new int[bHEIGHT], enx, eny;
	TIMERI tttt;
	public ImageIcon bullet,enemy,player;
	double bangle;
	private int highscore=-1;
	boolean ebul=false;
	
	PLAYER() {
		if(player!=null) {
			playerWidth= player.getImage().getWidth(null);
			playerHeight=player.getImage().getHeight(null);
		}
		tttt=new TIMERI();
		JOptionPane.showMessageDialog(null,"GUIDE:- \n1)Direct bullet with mouse and hit the enemy \n2)Each round lasts for 60 seconds");
		this.addMouseListener(this);
		t = new Timer(75, this);
		t.start();
	}
	
	PLAYER(int time) {
		TIMEE=time;
		if(player!=null) {
			playerWidth= player.getImage().getWidth(null);
			playerHeight=player.getImage().getHeight(null);
		}
		tttt=new TIMERI();
		JOptionPane.showMessageDialog(null,"GUIDE:- \n1)Direct bullet with mouse and hit the enemy \n2)Each round lasts for "+time+" seconds");
		this.addMouseListener(this);
		t = new Timer(75, this);
		t.start();
	}

	protected void paintComponent(Graphics g) {
		if(highscore==-1)
			highscore=this.GetHS();
			
			draw(g);
	}
	
	public int GetHS() 
	{
		System.out.println(highscore);
		FileReader fr = null;
		BufferedReader read = null;
		try {
			fr = new FileReader("high.txt");
			read = new BufferedReader(fr);
			try {
				return read.read();
			} catch (IOException e) {
				return 0;
			}
		} catch (FileNotFoundException e) {
			CheckScore();
			return 0;
		}
		finally {
			try {
				if(read!=null)
					read.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
	
	void draw(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, super.getWidth(), super.getHeight());
		
		if(TIMERI.TIME==0&&start==true)
		{
			GAMEOVER(g);
		}
		else if(TIMERI.TIME!=0 && start==true)
		{			
			if(TIMERI.TIME>30) 
			{
				
				g.setColor(Color.white);
				g.setFont( new Font("Ink Free",Font.BOLD, 40));
				FontMetrics metrics = getFontMetrics(g.getFont());
				g.drawString("Timer :-"+TIMERI.TIME, (super.getWidth() - metrics.stringWidth("Timer :-"+TIMERI.TIME))/2, g.getFont().getSize());
			}
			else if(TIMERI.TIME>15)
			{
				g.setColor(Color.orange);
				g.setFont( new Font("Ink Free",Font.BOLD, 45));
				FontMetrics metrics = getFontMetrics(g.getFont());
				g.drawString("Timer :-"+TIMERI.TIME, (super.getWidth() - metrics.stringWidth("Timer :-"+TIMERI.TIME))/2, g.getFont().getSize());
			}
			else if(TIMERI.TIME<15)
			{
				g.setColor(Color.red);
				g.setFont( new Font("Ink Free",Font.BOLD, 60));
				FontMetrics metrics = getFontMetrics(g.getFont());
				g.drawString("Timer :-"+TIMERI.TIME, (super.getWidth() - metrics.stringWidth("Timer :-"+TIMERI.TIME))/2, g.getFont().getSize());
			}
		
			Player(g);
	
			if (K.shot == true)
				Bullet(g,getangle());
			if (death == false)
				enemy(ex, ey, g);
			if (death == true) {
				K.shot = false;
				death = false;
				x= rnd.nextInt((super.getWidth()-playerWidth) - 100) + 100;
				y= rnd.nextInt((super.getHeight()-playerHeight) - 100) + 100;
				bsx = rnd.nextInt(30)+10;
				bsy = rnd.nextInt(30)+10;
				ex = rnd.nextInt(super.getWidth() - 200) + 100;
				ey = rnd.nextInt(super.getHeight() - 200) + 100;
			}
		}
		
		else if(!start)
			GAMEOVER(g);
	}

	void CheckScore()
	{
		if(score>highscore) {
			highscore=score;
			File scoreF=null;
			try {
				scoreF = new File(getClass().getClassLoader().getResource("high.txt").getFile());	
				if(!scoreF.exists()) 
					scoreF.createNewFile();
		
			} catch (Exception e) {
					// TODO Auto-generated catch block
				System.out.println(":"+highscore);
				}
			FileWriter wf = null;
			BufferedWriter w = null;
			try
			{
				wf=new FileWriter(scoreF);
				w=new BufferedWriter(wf);
				w.write(this.highscore);
			}
			catch (Exception e)
			{
				
			}
			finally
			 {
				try
				{
					if(w!=null)
						w.close();
				}
				catch (Exception e) {}			
			}
		}
	}
	
	void GAMEOVER(Graphics g) 
	{
		
		score2=score;
		g.setColor(Color.pink);
		g.setFont( new Font("BOLD",Font.BOLD, 100));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("SCORE ="+score2, ((super.getWidth() - metrics.stringWidth("SCORE ="+score2)))/2, super.getHeight()/3);
		
		CheckScore();
		g.setColor(Color.BLUE);
		g.setFont( new Font("BOLD",Font.BOLD, 25));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("H.SCORE ="+highscore, ((super.getWidth() - metrics1.stringWidth("H.SCORE ="+highscore)))/2, super.getHeight()/8);

		g.setColor(Color.RED);
		g.setFont( new Font("BOLD",Font.BOLD, 25));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Time taken:"+TIMEE+" seconds", ((super.getWidth() - metrics2.stringWidth("Time taken:"+TIMEE+" seconds")))/2, (super.getHeight()/2)+60);
		
		start=false;
		x=0;y=0;bx=0;by=0;
		
		g.setColor(Color.CYAN);
		g.setFont( new Font("BOLD",Font.BOLD, 75));
		FontMetrics metrics3 = getFontMetrics(g.getFont());
		g.drawString("CLICK TO PLAY", ((super.getWidth() - metrics3.stringWidth("CLICK TO PLAY")))/2, super.getHeight()/2+10);
		
	}
	
	void COLLISION() {
		boolean xx = false, yy = false,m=true,mm=true;
		if(enemy.getImage().getWidth(null)>0)
		enx = new int[enemy.getImage().getWidth(null)];
		else
			enx=new int[64];
		if(enemy.getImage().getWidth(null)>0)
		eny = new int[enemy.getImage().getHeight(null)];
		else
			eny=new int[64];
		for (int i = 0; i < enx.length; i++) {
			enx[i] = ex + i;
		}
		for (int i = 0; i < eny.length; i++) {
			eny[i] = ey + i;
		}
		for (int i = 0; i < bulx.length; i++) {
			bulx[i] = bx + i;
			for (int j = 0; j < enx.length; j++) {

				if (enx[j] == bulx[i]) {
					xx = true;
					break;
				}
				if(bulx[i]==mx) {
					m=false;
					break;
				}
			}
		}
		for (int i = 0; i < buly.length; i++) {
			buly[i] = by + i;
			for (int j = 0; j < eny.length; j++) {
				if (eny[j] == buly[i]) {
					yy = true;
					break;
				}
				if(buly[i]==my) {
					mm=false;
					break;
				}
			}
		}
		if (xx == true && yy == true) {
			death = true;
			score++;
		}
		
		if (m == false && mm == false) {
			K.shot = false;
		}
	}
	void enemy(int ex, int ey, Graphics g) {
		
		if(enemy.getImage().getWidth(null)>0&&enemy.getImage().getHeight(null)>0)
		g.drawImage(enemy.getImage(), ex, ey, enemy.getImage().getWidth(null), enemy.getImage().getHeight(null), null);
		else
		{
			g.setColor(Color.cyan);
			g.fillRect(ex, ey, 64, 64);
		}
		if(ebul==false)
			eBullet(g,ex,ey);
		validate();
	}
	
	 void eBullet(Graphics g,int hx,int hy) {
			if(bullet.getImage().getWidth(null)>0&&bullet.getImage().getHeight(null)>0)
			{
				int ebx=hx,eby=hy;
				//g.drawImage(bullet.getImage(), bx, by, bWIDTH, bHEIGHT, null);
			 	Graphics2D g2d = (Graphics2D) g;
			 	AffineTransform old = g2d.getTransform();
			 	g2d.drawImage(bullet.getImage(), ebx, eby, bWIDTH, bHEIGHT, null);
			 	g2d.setTransform(old);
			 	if(ebx<hx)
			 		ebx+=10;
			 	if(eby<hy)
			 		eby+=10;
			 	if(ebx>hx)
			 		ebx-=10;
			 	if(eby>hy)
			 		eby-=10;
			 	if(ebx==hx||eby==hy)
			 		ebul=true;
			}
			else
				{
					g.setColor(Color.red);
					g.fillRect(bx, by, 25, 15);
				}
		
			validate();
	}
	
	void Player(Graphics g) {
		if(player.getImage().getWidth(null)>0&&player.getImage().getHeight(null)>0) {
			 double xd=mx-(x+(playerWidth/2));
			 double yd=my-(y+(playerHeight/2));
			 double degree=Math.toDegrees(Math.atan2(yd, xd));
			 g.setColor(Color.green);
			 Graphics2D g2d = (Graphics2D) g;
			 AffineTransform old = g2d.getTransform();
			 g2d.rotate(Math.toRadians(degree+90), x+(playerWidth/2), y+(playerHeight/2));
			 //draw shape/image (will be rotated)
			 g2d.drawImage(player.getImage(), x, y, playerWidth, playerHeight, null);
			 g2d.setTransform(old);
		}
		else
			{
				g.setColor(Color.white);
				g.fillRect(x, y, 64, 64);
			}
//		g.setColor(new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));
//		g.fillRect(x+rnd.nextInt(150)+50, y+rnd.nextInt(150)+50, 2,2);
		validate();
	}

	double getangle()
	{
		double xd=mx-(bx+(bWIDTH/2));
		double yd=my-(by+(bHEIGHT/2));
		double degree=(Math.toDegrees(Math.atan2(yd, xd)));
		return degree;
	}
	
	void Bullet(Graphics g,double degree) {
		if(bullet.getImage().getWidth(null)>0&&bullet.getImage().getHeight(null)>0)
		{
			//g.drawImage(bullet.getImage(), bx, by, bWIDTH, bHEIGHT, null);
			 Graphics2D g2d = (Graphics2D) g;
			 AffineTransform old = g2d.getTransform();
			 g2d.rotate(Math.toRadians(degree+90), bx+(bWIDTH/2), by+(bHEIGHT/2));
			 g2d.drawImage(bullet.getImage(), bx, by, bWIDTH, bHEIGHT, null);
			 g2d.setTransform(old);
		}
		else
			{
				g.setColor(Color.red);
				g.fillRect(bx, by, 25, 15);
			}
		
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (ex>x)
			ex=ex-speed;
		if(ex<x)
			ex=ex+speed;
		if(ey>y)
			ey=ey-speed;
		if(ey<y)
			ey=ey+speed;
		if(Math.round(ex/speed)*speed==Math.round(x/speed)*speed&&Math.round(ey/speed)*speed==Math.round(y/speed)*speed) {
			 temp=score;
			if(temp==score||temp<score) {
				score--;
				System.out.println(score);
			}
			System.out.println(temp+";"+score);
			playerWidth=playerWidth/2;
			playerHeight=playerHeight/2;
			death=true;
		}
		else if ((Math.round(ex/speed)*speed)-speed==Math.round(x/speed)*speed&&(Math.round(ex/speed)*speed)==Math.round(y/speed)*speed) 
		{
			 temp=score;
			if(temp==score||temp<score) {
				score--;
				System.out.println(score);
			}
			System.out.println(temp+";"+score);
			playerWidth=playerWidth/2;
			playerHeight=playerHeight/2;
			death=true;
		}
		else if ((Math.round(ex/speed)*speed)+speed==Math.round(x/speed)*speed&&(Math.round(ey/speed)*speed)==Math.round(y/speed)*speed) 
		{
			 temp=score;
			if(temp==score||temp<score) {
				score--;
				System.out.println(score);
			}
			System.out.println(temp+";"+score);
			playerWidth=playerWidth/2;
			playerHeight=playerHeight/2;
			death=true;
		}
		else if ((Math.round(ex/speed)*speed)==Math.round(x/speed)*speed&&(Math.round(ey/speed)*speed)+speed==Math.round(y/speed)*speed) 
		{
			  temp=score;
			if(temp==score||temp<score) {
				score--;
				System.out.println(score);
			}
			System.out.println(temp+";"+score);
			playerWidth=playerWidth/2;
			playerHeight=playerHeight/2;
			death=true;
		}
		else if ((Math.round(ex/speed)*speed)==Math.round(x/speed)*speed&&(Math.round(ey/speed)*speed)-speed==Math.round(y/speed)*speed) 
		{
			  temp=score;
			if(temp==score||temp<score) {
				score--;
				System.out.println(score);
			}
			System.out.println(temp+";"+score);
			playerWidth=playerWidth/2;
			playerHeight=playerHeight/2;
			death=true;
		}
		else if ((Math.round(ex/speed)*speed)+speed==Math.round(x/speed)*speed&&(Math.round(ey/speed)*speed)+speed==Math.round(y/speed)*speed) 
		{
			  temp=score;
			if(temp==score||temp<score) {
				score--;
				System.out.println(score);
			}
			System.out.println(temp+";"+score);
			playerWidth=playerWidth/2;
			playerHeight=playerHeight/2;
			death=true;
		}
		else if ((Math.round(ex/speed)*speed)-speed==Math.round(x/speed)*speed&&(Math.round(ey/speed)*speed)+speed==Math.round(y/speed)*speed) 
		{
			  temp=score;
			if(temp==score||temp<score) {
				score--;
				System.out.println(score);
			}
			System.out.println(temp+";"+score);
			playerWidth=playerWidth/2;
			playerHeight=playerHeight/2;
			death=true;
		}
		else if ((Math.round(ex/speed)*speed)+speed==Math.round(x/speed)*speed&&(Math.round(ey/speed)*speed)-speed==Math.round(y/speed)*speed) 
		{
			  temp=score;
			if(temp==score||temp<score) {
				score--;
				System.out.println(score);
			}
			System.out.println(temp+";"+score);
			playerWidth=playerWidth/2;
			playerHeight=playerHeight/2;
			death=true;
		}
		else if ((Math.round(ex/speed)*speed)-speed==Math.round(x/speed)*speed&&(Math.round(ey/speed)*speed)-speed==Math.round(y/speed)*speed) 
		{
			  temp=score;
			if(temp==score||temp<score) {
				score--;
				System.out.println(score);
			}
			System.out.println(temp+";"+score);
			playerWidth=playerWidth/2;
			playerHeight=playerHeight/2;
			death=true;
		}
		if(playerWidth==1&&playerHeight==1)
		{
			TIMERI.TIME=0;
			start=true;
		}
		//System.out.println(playerWidth+","+playerHeight);
		//System.out.println(Math.round(ex/5)*5+","+Math.round(x/5)*5);
		//System.out.println(Math.round(ey/5)*5+","+Math.round(y/5)*5);
		mouse = (MouseInfo.getPointerInfo().getLocation())/*-(this.getLocationOnScreen())*/;
		if (super.isShowing() == true)
			if ((mouse.x - (super.getLocationOnScreen().x)) > 0
					&& (mouse.x - (super.getLocationOnScreen().x)) < super.getWidth()
					&& (mouse.y - (super.getLocationOnScreen().y)) > 0
					&& (mouse.y - (super.getLocationOnScreen().y)) < super.getHeight()) {
				mx = (mouse.x - (super.getLocationOnScreen().x));
				my = (mouse.y - (super.getLocationOnScreen().y));
			}
		if (K.shot == true) {
			COLLISION();
			if (mx > 0 && mx < super.getWidth() && my > 0 && my < super.getHeight()) {
				if (bx < Math.round(mx / bsx) * bsx)
					bx += bsx;
				if (bx > Math.round(mx / bsx) * bsx)
					bx -= bsx;
				if (by > Math.round(my / bsy) * bsy)
					by -= bsy;
				if (by < Math.round(my / bsy) * bsy)
					by += bsy;
			}
			else
				K.shot=false;
		}
		if(TIMERI.j==true)
			t.stop();
		
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(start) {
			if (K.shot == false) {
				bx = x+(playerWidth/2);
				by = y;
				bangle = getangle();
				K.shot = true;
			}
		}
		else if(!start)
		{
			score=0;
			start=true;
			tttt=new TIMERI();
			TIMERI.TIME=TIMEE;
			PLAYERDIES=false;
			playerWidth=player.getImage().getWidth(null);
			playerHeight=player.getImage().getHeight(null);
			t.start();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}

@SuppressWarnings("serial")
class K extends WINDOW {
	public static boolean shot;
	public K obj = new K();

	K() {
		K.shot = obj.getShot();
		obj.setShot(K.shot);
	}	
}