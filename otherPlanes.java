package ninjaTurtle.ninjaTurtle;


import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class otherPlanes extends Sprite {
	
	public static final int redPlane = 0;
	public static final int blackPlane = 1;
	public static final int greenPlane = 2;
	public static final int darkPlane = 3;
	
	private int lives;
	
	
	public static final Image[] OTHER_PLANES = {
			new Image("File:Resources/enemy0.png"),
			new Image("file:Resources/enemy1.png"),
			new Image("file:Resources/enemy2.png"),
			new Image("file:Resources/enemy3.png")
	};
	
	private static final Image EXPLOSION_IMAGE = new Image("file:Resources/explosion0.png");
	private ImageView explosion = new ImageView(EXPLOSION_IMAGE);
	
	private double deathDelay = 1;
	
	public static final Random RNG = new Random(); 
	
	private int type = 0;
	
	private boolean isDead = false;
	
	public otherPlanes() {
		super();
		
		type = RNG.nextInt(4);
		
		if (type ==0 ) {
			lives =1;
		} else if ( type ==1) {
			lives =1;
		}
		else if( type ==2) {
			lives=3;
		}
		else if(type ==3) {
				lives=5;
		}
			
		super.getChildren().add(explosion);
		explosion.setVisible(false);
		explosion.setLayoutX(10);
		explosion.setLayoutY(0);

		setImage(OTHER_PLANES[type]);
		
	}

	
	public void hit() {
		lives--;
		System.out.println("hit()");
		if (lives == 0) kill();
		
	}
	
	public void kill() {
		isDead = true;
		explosion.setVisible(true);
		setVelocity(0, 0);	
		System.out.println("kill()");
	}

	public void playNuke() {
	
	}
	
	public boolean isDead() {
		return isDead;
	}
	public boolean isReadyForCleanUp() {
		return deathDelay < 0;
	}
	
	@Override             
	public void update(double time) {
		super.update(time);
		
		if(isDead) {
			deathDelay -= time;
			
		}
	}
}
