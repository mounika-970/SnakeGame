package play;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakePanel extends JPanel implements ActionListener,Game{
static final int panelheight=500;
static final int panelwidth=500;
static final int unit_size=20;
static final int num_of_units=(panelheight*panelwidth)/(unit_size*unit_size);
final int x[]=new int[num_of_units];
final int y[]=new int[num_of_units];
int snakelength=5;
int foodswallowed;
private char direction='D';
int foodX;
int foodY;
Random random;
Timer timer;
boolean running=false;

public char getDirection() {
	return direction;
}
public void setDirection(char direction) {
	this.direction = direction;
}
public SnakePanel()
{
	random=new Random();
	this.setSize(panelwidth,panelheight);
	this.setBackground(Color.black);
	this.setFocusable(true);
	playGame();
}
	public void move() {
		for(int i=snakelength;i>0;i--)
		{
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		if(direction=='L')
		{
			x[0]=x[0]-unit_size;
		}
		else if(direction=='R')
		{
			x[0]=x[0]+unit_size;
			
		}
		else if(direction=='U')
		{
			y[0]=y[0]-unit_size;
		}
		else
		{
			y[0]=y[0]+unit_size;
		}
		
	}

	public void checkHit() {
		for(int i=snakelength;i>0;i--)
		{
			if(x[0]==x[i]&&y[0]==y[i])
			{
				running=false;
			}
			if(x[0]<0 ||x[0]>panelwidth||y[0]<0||y[0]>panelheight)
			{
				running=false;
			
			}
			if(!running)
			{
				timer.stop();
			}
		}
		
	}

	public void addFood() {
		foodX=random.nextInt((int)(panelwidth/unit_size))*unit_size;
		foodY=random.nextInt((int)(panelwidth/unit_size))*unit_size;
	}

	public void checkFood() {
		if(x[0]==foodX && y[0]==foodY)
		{
			snakelength++;
			foodswallowed++;
			addFood();
			
		}
		
	}

	public void playGame() {
	running=true;
	addFood();
	timer=new Timer(130,this);
	timer.start();
		
	}

	public void draw(Graphics graphics) {
		if(running)
		{
			graphics.setColor(new Color(214,00,00));
			graphics.fillOval(foodX, foodY,unit_size,unit_size);
		graphics.setColor(Color.white);
		graphics.fillRect(x[0],y[0], unit_size,unit_size);
		
		for(int i=1;i<snakelength;i++)
		{
			graphics.setColor(new Color(212,100,215));
			graphics.setFont(new Font("Sans serif",Font.ROMAN_BASELINE,25));
		}
		FontMetrics metrics=getFontMetrics(graphics.getFont());
		graphics.drawString("Score:"+foodswallowed,(panelwidth-metrics.stringWidth("score:"+foodswallowed)), foodY);
		}
		else
		{
			gameOver(graphics);
		}
		}
		

	public void gameOver(Graphics graphics) {
		graphics.setColor(Color.white);
		graphics.setFont(new Font("Sans serif",Font.ROMAN_BASELINE,25));
		FontMetrics metrics=getFontMetrics(graphics.getFont());
		graphics.drawString("Game Over",(panelwidth-metrics.stringWidth("Game Over"))/2,panelheight/2);
	
		graphics.setColor(Color.red);
		graphics.setFont(new Font("Sans serif",Font.ROMAN_BASELINE,25));
		 metrics=getFontMetrics(graphics.getFont());
		
		 
		 graphics.drawString("Score:"+foodswallowed,(panelwidth-metrics.stringWidth("score:"+foodswallowed))/2,graphics.getFont().getSize());
	
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running)
		{
			move();
			checkFood();
			checkHit();
			
		}
		repaint();
		
	}
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		draw(graphics);
	}
}


