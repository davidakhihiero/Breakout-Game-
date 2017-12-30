package breakout;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {
	private int [][] blocks = {{60,100},{122,100},{254,100},{316,100},{378,100},{470,100},
			                   {60,122},{122,122},{254,122},{316,122},{378,122},{470,122},
			                   {60,144},{122,144},{254,144},{316,144},{378,144},{470,144},
			                   {60,166},{122,166},{254,166},{316,166},{378,166},{470,166},
			                   {60,188},{122,188},{254,188},{316,188},{378,188},{470,188}};
	private boolean [] blockColors = new boolean[30];
	private boolean [] blockExists = new boolean[30];
	private final int blockWidth = 60;
	private final int blockHeight = 20;
	private final int padWidth = 60;
	private final int padHeight = 24;
	private int padX;
	private int padY;
	private int ballDiameter = 8;
	private int ballX;
	private int ballY;
	private int ballXSpeed;
	private int ballYSpeed;
	private int padXSpeed;
	private final int clearance = 5;
	private int boardWidth = 550;
	private int boardHeight = 550;
	private final int DELAY = 20;
	private Timer timer;
	private boolean inGame;
	private boolean restMode = true;
	private int lives = 3;
	private ImageIcon red = new ImageIcon("C:\\Users\\DIACIOUS\\"
			+ "Documents\\Java\\Images\\redheart.png");
	private ImageIcon blue = new ImageIcon("C:\\Users\\DIACIOUS\\"
			+ "Documents\\Java\\Images\\blueheart.png");
	private Image redH = red.getImage();
	private Image blueH = blue.getImage();
	private boolean [] life = new boolean[3];
	private int [][] drawH = {{460,3},{490,3},{520,3}};
	

	public Board() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		initGame();
	}
	private void initGame(){
		inGame = true;
		for(int i=0;i<blockColors.length;i++){
			blockColors[i] = true;
			blockExists[i] = true;
		}
		for(int i=0;i<life.length;i++){
			life[i] = true;
		}
		padX = (boardWidth-padWidth)/2;
		padY = (boardHeight- padHeight-5);
		ballX = padX + (padWidth-ballDiameter)/2;
		ballY =  (padY - ballDiameter);
		ballXSpeed = -1;
		ballYSpeed = 0;
		padXSpeed = 0;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
	    g.fillRect(0, 0, boardWidth, 20);
		if(inGame){
			drawSketch(g);
		}
		else{
			drawGameOver(g);
		}
      
	}
	private void drawSketch(Graphics g){
		for(int i=0;i<blocks.length;i++){
			if(blockExists[i]){
			     if(blockColors[i]){
				                g.setColor(Color.GREEN);
				                g.fill3DRect(blocks[i][0], blocks[i][1], blockWidth, blockHeight,true);
			} 
			           else{
				            g.setColor(Color.PINK);
				            g.fill3DRect(blocks[i][0], blocks[i][1], blockWidth, blockHeight,true);
			}
			}
		}
		for(int i=0;i<drawH.length;i++){
			if(life[i]){
				g.drawImage(blueH,drawH[i][0],drawH[i][1],null);
			}
			else{
				g.drawImage(redH,drawH[i][0],drawH[i][1],null);
			}
		}
		g.setColor(Color.BLUE);
		g.fillRect(padX, padY, padWidth, padHeight);
		g.fillOval((padX-padHeight/2), padY, padHeight, padHeight);
		g.fillOval(((padX + padWidth)-padHeight/2), padY, padHeight, padHeight);
		g.setColor(Color.BLACK);
		g.fillOval(ballX, ballY, ballDiameter, ballDiameter);
	}
	private void drawGameOver(Graphics g){
		timer.stop();
		for(int i=0;i<drawH.length;i++){
			if(life[i]){
				g.drawImage(blueH,drawH[i][0],drawH[i][1],null);
			}
			else{
				g.drawImage(redH,drawH[i][0],drawH[i][1],null);
			}
		}
		boolean lose = false;
		
		for(boolean i:blockExists){
			if(i){
				lose = true;
			}
		}
		if(lose){
		g.setColor(Color.BLACK);
		String gO = "Game Over";
		Font font = new Font("Helvetica",Font.BOLD,50);
		FontMetrics fm = getFontMetrics(font);
		g.setFont(font);
		g.drawString(gO,(boardWidth-fm.stringWidth(gO))/2, (boardHeight-fm.getHeight())/2);
		}
		else{
			g.setColor(Color.BLACK);
			String gO = "You Won";
			Font font = new Font("Helvetica",Font.BOLD,50);
			g.setFont(font);
			FontMetrics fm = getFontMetrics(font);
			g.drawString(gO,(boardWidth-fm.stringWidth(gO))/2, (boardHeight-fm.getHeight())/2);
		}
	}
	public void actionPerformed(ActionEvent e){
        moveBall();
        movePad();
        checkCollision();
        checkIfWon();
        repaint();
	}
	private void moveBall(){
		if(restMode){
			ballX+=ballXSpeed;
			if(ballX<=padX-(ballDiameter/2)){
				ballX = padX-(ballDiameter/2);
				ballXSpeed = -ballXSpeed;
			}
			if(ballX>=(padX+padWidth)-(ballDiameter/2)){
				ballX = (padX+padWidth)-(ballDiameter/2);
				ballXSpeed = -ballXSpeed;
			}
		}
		else{
		ballX+=ballXSpeed;
		ballY+=ballYSpeed;
		}
	}
	private void movePad(){
		padX+=padXSpeed;
		if(padX-padHeight/2<=0){
			padX=padHeight/2;
		}
		if(padX>=boardWidth-(padWidth+padHeight/2)){
			padX=boardWidth-(padWidth+padHeight/2);
		}
		
	}
	
	private class TAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			if(key==KeyEvent.VK_LEFT){
				padXSpeed = -4;
			}
            if(key==KeyEvent.VK_RIGHT){
            	padXSpeed = 4;
			}
            if(key==KeyEvent.VK_SPACE){
            	restMode = false;
			}
				
		}
		public void keyReleased(KeyEvent e){
			int key = e.getKeyCode();
			if(key==KeyEvent.VK_LEFT){
				padXSpeed = 0;
			}
            if(key==KeyEvent.VK_RIGHT){
            	padXSpeed = 0;
			}
		}
	}
	private void checkCollision(){
		boolean alreadyCollided = false;
		Point right = new Point(ballX+ballDiameter+clearance,ballY);
		Point right2 = new Point(ballX+ballDiameter+clearance,ballY+ballDiameter);
		Point left = new Point(ballX-clearance,ballY);
		Point left2 = new Point(ballX-clearance,ballY+ballDiameter);
		Point top = new Point(ballX,ballY-clearance);
		Point top2 = new Point(ballX+ballDiameter,ballY-clearance);
		Point bottom = new Point(ballX,ballY+ballDiameter+clearance);
		Point bottom2 = new Point(ballX+ballDiameter,ballY+ballDiameter+clearance);
		Point bottomMid = new Point(ballX+(ballDiameter)/2,ballY+ballDiameter+clearance);
		
		for(int i=0;i<blocks.length;i++){
   Rectangle blocksR = new Rectangle(blocks[i][0],(blocks[i][1]),blockWidth,blockHeight);
   if((blocksR.contains(right)||blocksR.contains(left)||blocksR.contains(right2)
		   ||blocksR.contains(left2))&&blockExists[i]){
	   if(blockColors[i]){
		   blockColors[i] = false;
		   ballXSpeed = - ballXSpeed;
		   alreadyCollided = true;
	   }
	   else{
		   ballXSpeed = - ballXSpeed;
		   blockExists[i] = false;
	   }
   }
		}
		if(ballX<=0||ballX>=boardWidth-ballDiameter){
			ballXSpeed = -ballXSpeed;
		}
		if(alreadyCollided){
			
		}
		else if(!alreadyCollided){
		for(int i=0;i<blocks.length;i++){
	Rectangle blocksR = new Rectangle(blocks[i][0],(blocks[i][1]),blockWidth,blockHeight);
	if((blocksR.contains(top)||blocksR.contains(bottom)
			||blocksR.contains(top2)||blocksR.contains(bottom2))&&blockExists[i]){
				   if(blockColors[i]){
					   blockColors[i] = false;
					   ballYSpeed = - ballYSpeed;
				   }
				   else{
					   ballYSpeed = - ballYSpeed;
					   blockExists[i] = false;
				   }
			   }
					}
		}
		
		if(ballY<=20){
			ballYSpeed = -ballYSpeed;
		}
		if((ballY>=boardHeight-ballDiameter)&&lives<=1){
			lives--;
			life[lives] = false;
			inGame = false;
		}
		else if((ballY>=boardHeight-ballDiameter)&&lives>1){
			lives--;
			life[lives] = false;
			restMode = true;
			padX = (boardWidth-padWidth)/2;
			padY = (boardHeight- padHeight-5);
			ballX = padX + (padWidth-ballDiameter)/2;
			ballY =  (padY - ballDiameter);
		}
		Rectangle padR = new Rectangle(padX-(padHeight/2),padY,padWidth+padHeight,padHeight);
		if(padR.contains(bottomMid)&&!restMode){
			int first = padX - padHeight/2;
			int second = first+18;
			int third = second+18;
			int forth = third+18;
			int fifth = forth+18;
			int sixth = fifth+18;
			int seventh = sixth+18;
	
			if(bottomMid.getX()>=first-1&&bottomMid.getX()<second){
				ballXSpeed = -3;
				ballYSpeed = -3;
			}
			else if(bottomMid.getX()>=second&&bottomMid.getX()<third){
				ballXSpeed = -2;
				ballYSpeed = -3;
			}
			else if(bottomMid.getX()>=third&&bottomMid.getX()<forth){
				ballXSpeed = -1;
				ballYSpeed = -3;
			}
			else if(bottomMid.getX()==forth){
				ballXSpeed = 0;
				ballYSpeed = -3;
			}
			else if(bottomMid.getX()>=forth&&bottomMid.getX()<fifth){
				ballXSpeed = 1;
				ballYSpeed = -3;
			}
			else if(bottomMid.getX()>=fifth&&bottomMid.getX()<sixth){
				ballXSpeed = 2;
				ballYSpeed = -3;
			}
			else if(bottomMid.getX()>=sixth&&bottomMid.getX()<seventh+1){
				ballXSpeed = 3;
				ballYSpeed = -3;
			}
		
		}
		
	}
	private void checkIfWon(){
		boolean won = true;
		for(boolean i:blockExists){
			if(i){
				won = false;
			}
		}
		if(won){
			inGame = false;
		}
	}
	

}
