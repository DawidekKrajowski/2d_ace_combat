package ninjaTurtle.ninjaTurtle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class pilot extends Sprite {

	private static final Image EXPLOSION_IMAGE = new Image("file:Resources/fireball2.png");
	private ImageView explosion = new ImageView(EXPLOSION_IMAGE);
	private boolean isDead = false;
	

	public pilot(Image i) {
		super(i);

		super.getChildren().add(explosion);
		explosion.setVisible(false);
		explosion.setLayoutX(10);
		explosion.setLayoutY(5);
	}

	public void flyLeft(double time) {
		super.setVelocityX(-350);
		super.update(time);
		super.setVelocityX(0);

		if (getPositionX() < 20) {
			setPositionX(20);
		}
	}

	public void flyRight(double time) {
		super.setVelocityX(350);
		super.update(time);
		super.setVelocityX(0);

		if (getPositionX() + getWidth() > 980) {
			setPositionX(980 - getWidth());
		}
	}
	public void flyUp(double time) {
		super.setVelocityY(-350);
		super.update(time);
		super.setVelocityY(0);

		if (getPositionY() + getHeight() > 800) {
			setPositionY(800 - getHeight());
		}
	}
	public void flyDown(Double time)  {
		super.setVelocityY(350);
		super.update(time);
		super.setVelocityY(0);

		if (getPositionY() + getHeight() > 800) {
			setPositionY(800 - getHeight());
		}
	}

	public Bullet shoot() {
		Bullet b = new Bullet();
		b.setPosition(getPositionX() + getWidth() / 3, getPositionY());
		b.setVelocityY(-500);

		return b;
	}

	public void kill() {
		isDead = true;
		explosion.setVisible(true);
	}
	public boolean isDead() {
		return isDead;
	}
	public void revive() {
		isDead = false;
		explosion.setVisible(false);
	}
	
}
