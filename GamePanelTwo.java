package SnakeGameSetUp;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;


public class GamePanelTwo extends JPanel implements ActionListener {

	//Variables and Constant
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;  // the unit size of the objects we want ex- snake will increase by this unit
	static final int GAME_UNIT =(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;  //number of objects that can fit in
	static final int DELAY = 100;
	//Snake Body Array
	final int x[] = new int[GAME_UNIT]; // as the coordinates can't bypass the number of objects that can fit in game
	final int y[] = new int[GAME_UNIT];
	
	int bodyParts =6; //initial Body Parts
	
	//Apple elements
	int applesEaten=180;  //no of apples eaten
	int appleX, appleY;  //x and y coordinates of apple that generate randomly
	char direction ='R';   // direction of the snake is given R= right, L= left, U =up, D = down
	boolean running =false;
	Timer timer;
	Random random;
	JLabel back;
    ImageIcon background ;
    
    JButton restart , exit;
	
    GamePanelTwo(){
		random = new Random();
		// since in the GameFrame we already decided how the frame is going to open at
		//center therefore
	    // here we are setting it directly by dimensions.
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
	    this.setBackground(Color.WHITE);
	    this.setFocusable(true);
	    this.addKeyListener(new MyKeyAdapter());
	    startGame();
	      
	    
	}
	public void startGame() {
		newApple();
		running = true;
		 // to delay the time by 75 milliseconds
		// A Swing timer (an instance of javax. swing. Timer ) fires one or more action
		// events after a specified delay. Do not confuse Swing timers with
		// the general-purpose timer facility in the java.
		// DELAY decides the time delay to be made and the 2nd argument is a
		// actionListener
		// so after every 75 milliseconds the actionListener actions will be performed
		// here we are passing this

		timer = new Timer(DELAY,this);
		timer.start();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g) {
		
		if(running) {
		// Creating a matrix to understand the UNIT_SIZE = 25 and making easy for us to
		// locate co-ordinaes
		
		/*
		 * for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
		 * g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT );
		 * g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE ); }
		 * 
		 */
	
		// Drawing the APPLE
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
		if(applesEaten == 90 || applesEaten==190) {
			g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		}
	
		//Snake head and Body
		for(int i=0;i<bodyParts;i++) {
			if(i==0) {
				g.setColor(Color.green);
				g.fill3DRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE,true);
			}
			else {
				g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
				g.fill3DRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE,true);
			}
			
			//Obstacles
			g.setColor(Color.decode("#1e4263"));
			g.fill3DRect(0,UNIT_SIZE*7,UNIT_SIZE*6,UNIT_SIZE,true); //(0,175,150,25)
			
			g.setColor(Color.decode("#1e4263"));
			g.fill3DRect(300,100,UNIT_SIZE,UNIT_SIZE*10,true);//(300,175,25,25)
			
			g.setColor(Color.decode("#1e4263"));
			g.fill3DRect(350,500,UNIT_SIZE*6,UNIT_SIZE,true);//(350,500,150,25)
			
			//Score
			g.setColor(Color.BLACK);
			g.setFont(new Font("Ink Free", Font.BOLD,25));
			//Putting the text for SCORE
			FontMetrics metrics =  getFontMetrics(g.getFont());
			g.drawString("SCORE "+applesEaten,(SCREEN_WIDTH -metrics.stringWidth("SCORE "+applesEaten))/2,20);
		}
	}else {
		gameOver(g);
	}
		 
	}

	public void newApple() {
		// Logic -->
		// SCREEN_WIDTH/UNIT_SIZE is used to locate the x axis and we multiplied it by
		// UNIT_SIZE
		// because we want to fit the object perfectly into the matrix grid created of
		// UNITE_SIZE =25
		
		appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

	}
	public void move() {
		for(int i= bodyParts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0]=y[0]-UNIT_SIZE;
			break;
		case 'D':
			y[0]=y[0]+UNIT_SIZE;
			break;
		case 'L':
			x[0]=x[0]-UNIT_SIZE;
			break;
		case 'R':
			x[0]=x[0]+UNIT_SIZE;
			break;
		}
	}
	public void checkApple() {
		if((x[0]==appleX) &&(y[0]==appleY)) {
			bodyParts++; //increase the size of the snake
			applesEaten+=10; // maintain to update score
			newApple();  // generate new apple
		}
	}

	public void checkCollisions() {
     
		// if head collides with body
		for(int i= bodyParts;i>0;i--) {
			if(x[0]==x[i] && (y[0]==y[i])) {
				running =false;
			}
		}
		

		// head touches the left boundary
				if(x[0]<0) {
					running = false;
				}
				// head touches the right boundary
				if(x[0]>SCREEN_WIDTH) {
					running = false;
				}
				// head touches the top boundary
				if(y[0]<0) {
					running = false;
				}
				// head touches the bottom boundary
				if(y[0]>SCREEN_HEIGHT) {
					running = false;
				}
				//handling block one
				
				if(((x[0]==0 ) ||(x[0]==25 )||(x[0]==50 )||(x[0]==75 )||(x[0]==100 )||(x[0]==125 ))&& (y[0]==175)){
				//(0,175),(25,175),(50,175),(75,175),(100,175),
					running = false;
				}
				if(((y[0]==100 ) ||(y[0]==125 )||(y[0]==150 )||(y[0]==175 )||(y[0]==200 )||(y[0]==225 )||(y[0]==250 )||(y[0]==275 )||(y[0]==300 )||(y[0]==325 ))&& (x[0]==300)){
				//(300,100),(300,150),(300,175),(300,200),(300,225),(300,250),(300,275),(300,300),(300,325),(300,350)
					running = false;
				}if(((x[0]==350 ) ||(x[0]==375 )||(x[0]==400 )||(x[0]==425 )||(x[0]==450 )||(x[0]==475 ))&& (y[0]==500)){
				//	(350,500),(350,500),(375,500),(400,500),(425,500),(450,500),
					running = false;
				}
				if(!running) {
					timer.stop();
				}
	}
	public void gameOver(Graphics g) {
		//Game Over Text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD,40));
		//Putting the text GAME OVER in center
		FontMetrics metrics =  getFontMetrics(g.getFont());
		g.drawString("GAME OVER",(SCREEN_WIDTH -metrics.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Ink Free", Font.BOLD,30));
		//Putting the text for SCORE
		FontMetrics metrics1 =  getFontMetrics(g.getFont());
		g.drawString("SCORE "+applesEaten,(SCREEN_WIDTH -metrics1.stringWidth("SCORE "+applesEaten))/2,400);
		
		/*
		 * restart = new JButton("RESTART"); restart.setFont(new
		 * Font("Raleway",Font.BOLD,20)); restart.setBackground(Color.WHITE);
		 * restart.setForeground(Color.BLACK); restart.setBounds(150,450,150,30);
		 * restart.addActionListener(this); add(restart);
		 * 
		 * exit = new JButton("EXIT"); exit.setFont(new Font("Raleway",Font.BOLD,20));
		 * exit.setBackground(Color.WHITE); exit.setForeground(Color.BLACK);
		 * exit.setBounds(350,450,150,30); exit.addActionListener(this); add(exit);
		 */
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
		if(applesEaten==200) {
            JOptionPane.showMessageDialog(null,"Congratulations You WIN");
			this.setVisible(false);
			applesEaten=0;
		}
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
		// we don't want to make the head do 180 degree turn therefore 
		//checking if it's not going in opposite directio
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!='R') {
					direction ='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction!='L') {
					direction ='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction!='D') {
					direction ='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction!='U') {
					direction ='D';
				}
				break;
			}
		}
	}
	
	
	
	}
