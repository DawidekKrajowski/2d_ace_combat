package ninjaTurtle.ninjaTurtle;

import javafx.application.Application;
import java.util.HashSet;
import javafx.application.Platform;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

//import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class aceCombat extends Application {// Window size constants
	public final static int GAME_WIDTH = 1000;
	public final static int GAME_HEIGHT = 800;

	// Game states
	public static final int TITLE_SCREEN = 0;
	public static final int PLAYING = 1;
	public static final int GAME_OVER = 2;

	private int gameState = TITLE_SCREEN;

	// used to track keys as they are pressed/released.
	private final HashSet keyboard = new HashSet();

	private GameTimer gameTimer = new GameTimer(time -> updateGame(time));

	private Sprite[] background = { new Sprite(new Image("file:Resources/background.png", GAME_WIDTH, 0, true, true)),
			new Sprite(new Image("file:Resources/background.png", GAME_WIDTH, 0, true, true)),

	};
	private Group backgroundDisplay = new Group(background[0], background[1]);

	private pilot player = new pilot(new Image("file:Resources/plane.png"));
	private ImageView Bomb = new ImageView("file:Resources/nuke.png");

	private Group otherPlanes = new Group();

	private double gunCoolDown = -1;

	private double newPlanesTimer = 1;

	private int Score = 0;
	private int amo = 0;
	private int highScore = 0;
//	private AudioClip bomb = new AudioClip("file:sound/Explosion.mp3");

	// interface for the game over screen
	private Text endTitle = new Text("Game\nOver");
	private Text endSubtitle = new Text("press space");
	private Text pause = new Text("press P to pause");
	// Interface elements for game screens
	private Text title = new Text("Ace Combat");
	private Text intro = new Text(
			"you are sent on a impossible mission to shoot down all the ennemy planes and survive for as long as possible");
	private Text keys = new Text(" soldier use the arrow keys to move around and hold the space bar to shoot");
	private Text subtitle = new Text("Press Space to Begin");
	private Group bullets = new Group();
	// Game Screen Groups
	private Text BombHint = new Text("press N:" + amo);
	private Text score = new Text("Score:" + Score);
	private Text highScorecount = new Text("Highscore: " + highScore);
	private Group[] gameScreens = { new Group(title, subtitle, intro, keys, highScorecount, pause),
			new Group(backgroundDisplay, player, otherPlanes, bullets, score, Bomb, BombHint, highScorecount), // amo
			new Group(endTitle, endSubtitle) };

	public aceCombat() {
		newGame();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Scene gameScene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.DARKGRAY);
		primaryStage.setScene(gameScene);
		// primaryStage.setResizable(false);
		primaryStage.setTitle("Ace Combat");
		primaryStage.show();

		gameScene.setOnKeyPressed(key -> keyPressed(key));
		gameScene.setOnKeyReleased(key -> keyReleased(key));
		root.getChildren().addAll(gameScreens[PLAYING], gameScreens[TITLE_SCREEN], gameScreens[GAME_OVER]);
		gameScreens[TITLE_SCREEN].setVisible(true);
		gameScreens[PLAYING].setVisible(true);
		gameScreens[GAME_OVER].setVisible(false);

		// Styling for the Text on Game Screens
		title.setFont(Font.font("Harlow Solid Italic", 80));
		title.setFill(Color.RED);
		title.setEffect(new DropShadow());
		title.setStroke(Color.WHITE);
		title.setStrokeWidth(2);
		title.setLayoutX(GAME_WIDTH / 2 - title.getLayoutBounds().getWidth() / 2);
		title.setLayoutY(100);

		intro.setFont(Font.font("Harlow Solid Italic", 24));
		intro.setFill(Color.RED);
		intro.setEffect(new DropShadow());
		intro.setStroke(Color.WHITE);
		intro.setStrokeWidth(1);
		intro.setLayoutX(10);
		intro.setLayoutY(200);

		pause.setFont(Font.font("Harlow Solid Italic", 25));
		pause.setFill(Color.RED);
		pause.setEffect(new DropShadow());
		pause.setStroke(Color.WHITE);
		pause.setStrokeWidth(1);
		pause.setLayoutX(420);
		pause.setLayoutY(500);

		keys.setFont(Font.font("Harlow Solid Italic", 25));
		keys.setFill(Color.RED);
		keys.setEffect(new DropShadow());
		keys.setStroke(Color.WHITE);
		keys.setStrokeWidth(1);
		keys.setLayoutX(150);
		keys.setLayoutY(300);

		subtitle.setFont(Font.font("Harlow Solid Italic", 35));
		subtitle.setFill(Color.RED);
		subtitle.setEffect(new DropShadow());
		subtitle.setStroke(Color.WHITE);
		subtitle.setStrokeWidth(1);
		subtitle.setLayoutX(GAME_WIDTH / 2 - subtitle.getLayoutBounds().getWidth() / 2);
		subtitle.setLayoutY(GAME_HEIGHT / 2 + 200);

		endTitle.setFont(Font.font("Harlow Solid Italic", 85));
		endTitle.setFill(Color.RED);
		endTitle.setEffect(new DropShadow());
		endTitle.setStroke(Color.WHITE);
		endTitle.setStrokeWidth(2);
		endTitle.setLayoutX(GAME_WIDTH / 2 - endSubtitle.getLayoutBounds().getWidth() / 2);
		endTitle.setLayoutY(GAME_HEIGHT / 2 - endTitle.getLayoutBounds().getWidth() / 2);

		endSubtitle.setFont(Font.font("Harlow Solid Italic", 45));
		endSubtitle.setFill(Color.RED);
		endSubtitle.setEffect(new DropShadow());
		endSubtitle.setStroke(Color.WHITE);
		endSubtitle.setStrokeWidth(1);
		endSubtitle.setLayoutX(GAME_WIDTH / 2 - endSubtitle.getLayoutBounds().getWidth() / 2);
		endSubtitle.setLayoutY(GAME_HEIGHT / 2 + 200);

		score.setFont(Font.font("Harlow Solid Italic", 25));
		score.setFill(Color.RED);
		score.setEffect(new DropShadow());
		score.setStroke(Color.WHITE);
		score.setStrokeWidth(1);
		score.setLayoutX(50);
		score.setLayoutY(50);

		highScorecount.setFont(Font.font("Harlow Solid Italic", 25));
		highScorecount.setFill(Color.RED);
		highScorecount.setEffect(new DropShadow());
		highScorecount.setStroke(Color.WHITE);
		highScorecount.setStrokeWidth(1);
		highScorecount.setLayoutX(820);
		highScorecount.setLayoutY(50);

		Bomb.relocate(50, 70);
		Bomb.setFitHeight(50);
		Bomb.setFitWidth(30);
		Bomb.setOpacity(0);

		BombHint.setFont(Font.font("Harlow Solid Italic", 10));
		BombHint.setFill(Color.WHITE);
	//	BombHint.setEffect(new DropShadow());
		BombHint.setStroke(Color.WHITE);
		//BombHint.setStrokeWidth(1);
		BombHint.relocate(50, 70);
		BombHint.setOpacity(0);

//		amo.setFont(Font.font("Harlow Solid Italic", 20));
//		amo.setFill(Color.RED);
//		amo.setEffect(new DropShadow());
//		amo.setStroke(Color.WHITE);
//		amo.setStrokeWidth(2);
//		amo.setLayoutX(50);
//		amo.setLayoutY(50);

	}

	/**
	 * Setup all sprites for a new game
	 */
	public void newGame() {
		// set the backgrounds up for the beggining of the game
		background[0].setPositionY(0);
		background[1].setPositionY(-background[1].getHeight());
		background[0].setVelocityY(100);
		background[1].setVelocityY(100);

		player.setPosition(GAME_WIDTH / 2 - player.getWidth() / 2, GAME_HEIGHT - 2 * player.getHeight());
		player.setVelocity(0, 0);
		player.revive();

		otherPlanes.getChildren().clear();
		newPlanesTimer = 1;

		bullets.getChildren().clear();
		gunCoolDown = 0.25;
		Score = 0;
	}

	/**
	 * Game updates happen as often as the timer can cause an event
	 */
	public void updateGame(double elapsedTime) {
		updateBackground(elapsedTime);
		if (gameState == PLAYING)
			updatePlayer(elapsedTime);
		updateOtherPlanes(elapsedTime);
		updateBullets(elapsedTime);
		updateBackground(elapsedTime);
		if (gameState == PLAYING)
			checkBulletCollision();
		if (gameState == PLAYING)
			checkPlayerCollision();
		cleanup();

		if (gameState == GAME_OVER) {
			if (otherPlanes.getChildren().size() == 0) {
				backToTitleScreen();
			}
		}
	}

	public void checkPlayerCollision() {
		for (int j = 0; j < otherPlanes.getChildren().size(); j++) {
			otherPlanes planes = (otherPlanes) otherPlanes.getChildren().get(j);
			if (planes.intersect(player)) {
				planes.kill();
				planes.setVelocityY(0);
				player.kill();
				gameOver();
			}
		}
	}

	public void checkBulletCollision() {
		for (int i = 0; i < bullets.getChildren().size(); i++) {
			Bullet b = (Bullet) bullets.getChildren().get(i);

			for (int j = 0; j < otherPlanes.getChildren().size(); j++) {
				otherPlanes plane = (otherPlanes) otherPlanes.getChildren().get(j);

				if (plane.intersect(b)) {
					if(plane.isDead()) {
						continue;
					}
					b.kill();
					plane.hit();
					scoreCounter();
					System.out.println("checkBulletCollision()");

				}
			}
		}
	}

	public void cleanup() {

		// clean up cars that are ready to be deleted
		for (int i = 0; i < otherPlanes.getChildren().size(); i++) {
			otherPlanes plane = (otherPlanes) otherPlanes.getChildren().get(i);
			if (plane.isReadyForCleanUp()) {
				otherPlanes.getChildren().remove(plane);
				i--;
			}
			// clean up bullets that are ready to be deleted
		}
		for (int i = 0; i < bullets.getChildren().size(); i++) {
			Bullet b = (Bullet) bullets.getChildren().get(i);
			if (b.isReadyForCleanUp()) {
				bullets.getChildren().remove(b);
				i--;
			}
		}
	}

	public void updateBullets(double elapsedTime) {

		for (int i = 0; i < bullets.getChildren().size(); i++) {
			Bullet b = (Bullet) bullets.getChildren().get(i);
			b.update(elapsedTime);

			
			if (b.getPositionY() < -b.getHeight()) {
				b.kill();
			}
		}
	}

	public void updateOtherPlanes(double elapsedTime) {
		// add new plane
		if (gameState == PLAYING && newPlanesTimer < 0) {
			otherPlanes newPlane = new otherPlanes();
			newPlane.setPosition((GAME_WIDTH - newPlane.getWidth()) * Math.random(), -newPlane.getHeight());
			newPlane.setVelocityY(200 * Math.random() + 100);
			newPlane.setVelocityX(-200 * Math.random() + 100);
			otherPlanes.getChildren().add(newPlane);
			newPlanesTimer = Math.random() - .2;
		} else {
			newPlanesTimer -= elapsedTime;
		}
		for (int i = 0; i < otherPlanes.getChildren().size(); i++) {
			otherPlanes plane = (otherPlanes) otherPlanes.getChildren().get(i);
			plane.update(elapsedTime);

			if (plane.getPositionY() > GAME_HEIGHT) {
				plane.kill();
			}
			if (plane.getPositionX() > 1200) {
				plane.kill();
			}
		}

	}

	/**
	 * Move the player left/right depending on keyboard input
	 * 
	 * @param elapsedTime
	 */
	public void updatePlayer(double elapsedTime) {
		// gives the user the ability to move in all 4 directions ( technically 8 )
		if (keyboard.contains(KeyCode.LEFT)) {
			player.flyLeft(elapsedTime);
		}
		if (keyboard.contains(KeyCode.RIGHT)) {
			player.flyRight(elapsedTime);
		}
		if (keyboard.contains(KeyCode.UP)) {
			player.flyUp(elapsedTime);
		}
		if (keyboard.contains(KeyCode.DOWN)) {
			player.flyDown(elapsedTime);
		}

		if (keyboard.contains(KeyCode.A)) {
			player.flyLeft(elapsedTime);
		}
		if (keyboard.contains(KeyCode.D)) {
			player.flyRight(elapsedTime);
		}
		if (keyboard.contains(KeyCode.W)) {
			player.flyUp(elapsedTime);
		}
		if (keyboard.contains(KeyCode.S)) {
			player.flyDown(elapsedTime);
		}
	
		if (gunCoolDown < 0) {
			if (keyboard.contains(KeyCode.SPACE)) {
				bullets.getChildren().add(player.shoot());
			}
			gunCoolDown = 0.25;

		} else {
			gunCoolDown -= elapsedTime;
		}
		ActivateCrazyMode(elapsedTime);
		// gun timer and cooldowntimer for player

	}

	/**
	 * Updates the background to create road animation. Moves both images down the
	 * window. When an image passes below the bottom of the window, resets it to the
	 * top
	 * 
	 * @param elapsedTime amount of time passed since last update.
	 */
	public void updateBackground(double elapsedTime) {
		background[0].update(elapsedTime);
		background[1].update(elapsedTime);

		if (background[0].getPositionY() > GAME_HEIGHT) {
			background[0].setPositionY(background[1].getPositionY() - background[0].getHeight());
		}
		if (background[1].getPositionY() > GAME_HEIGHT) {
			background[1].setPositionY(background[0].getPositionY() - background[1].getHeight());
		}
	}

	/**
	 * Respond to key press events by checking for the pause key (P), and starting
	 * or stopping the game timer appropriately
	 * 
	 * Other key presses are stored in the "Keyboard" HashSet for polling during the
	 * main game update.
	 * 
	 * @param key KeyEvent program is responding to
	 */
	public void keyPressed(KeyEvent key) {
		if (gameState == PLAYING) {
			if (!keyboard.contains(KeyCode.P)) {
				if (key.getCode() == KeyCode.P) {
					pause();
				}
			}
		}

		if (gameState == TITLE_SCREEN) {
			if (!keyboard.contains(KeyCode.SPACE)) {
				if (key.getCode() == KeyCode.SPACE) {
					startGame();
				}
			}
		}
		if (gameState == GAME_OVER) {
			if (!keyboard.contains(KeyCode.SPACE)) {
				if (key.getCode() == KeyCode.SPACE) {
					backToTitleScreen();
				}
			}
		}

		if (!keyboard.contains(KeyCode.ESCAPE)) {
			if (key.getCode() == KeyCode.ESCAPE) {
				Platform.exit();
				;
			}
			if (!keyboard.contains(KeyCode.N)) {
				if (key.getCode() == KeyCode.N && Score >= 500) {
				//	 bomb.play();
					Nuke();
					// switched the order sound and nuke commands because the audio has a delay
				}

			}
			if (!keyboard.contains(KeyCode.B)) {
				if (key.getCode() == KeyCode.B && Score >= 1000) {
					// bomb.play();
					
					// switched the order sound and nuke commands because the audio has a delay
				}

			}
			if (!keyboard.contains(KeyCode.SHIFT)) {
				if (key.getCode() == KeyCode.SHIFT && Score >= 5000) {
				//	bomb.play();
					Nuke();
					// switched the order sound and nuke commands because the audio has a delay
				}

			}

		}

		// record this particular key has been pressed:
		keyboard.add(key.getCode());
	}

	/**
	 * Removes a key from the "keyboard" HashSet when it is released
	 * 
	 * @param key KeyEvent that triggered this method call
	 */
	public void keyReleased(KeyEvent key) {
		// remove the record of the key being pressed:
		keyboard.remove(key.getCode());
	}

	/**
	 * Starts the game play from the title screen
	 */
	public void startGame() {
		gameScreens[TITLE_SCREEN].setVisible(false);
		gameScreens[GAME_OVER].setVisible(false);
		gameState = PLAYING;
		gameTimer.start();

	}

	/**
	 * Switch the program to a new state when the player loses (by hitting another
	 * car).
	 */
	public void gameOver() {

		gameScreens[TITLE_SCREEN].setVisible(false);
		gameScreens[GAME_OVER].setVisible(false);
		gameState = GAME_OVER;
		Score = highScore;
		if (highScore <= Score) {
			highScorecount.setText("highScore: " + highScore);
		}
		Score = 0;

	}

	public void backToTitleScreen() {
		gameScreens[TITLE_SCREEN].setVisible(true);
		gameScreens[GAME_OVER].setVisible(false);
		gameState = TITLE_SCREEN;
		gameTimer.stop();
		newGame();

	}

	/**
	 * Pause or unpause the game
	 */
	public void pause() {
		if (gameTimer.isPaused()) {
			gameTimer.start();
		} else {
			gameTimer.stop();
		}
	}

	public void scoreCounter() {
		Score += 100;
		score.setText("Score: " + Score);

		if (Score > highScore) {
			highScore = Score;

			highScorecount.setText("High Score: " + highScore);
		}

		if (Score >= 500) {
			Bomb.setOpacity(1);
			BombHint.setOpacity(1);
		} else {
			Bomb.setOpacity(0);
			BombHint.setOpacity(0);
// shows an image with text to tell the player to press n and use the nuke.
		}
		if (Score % 5000 == 0) {
			BombHint.setText("Press N:" + amo);
		}
	}

	public void Nuke() {
		for (int j = 0; j < otherPlanes.getChildren().size(); j++) {
			otherPlanes Plane = (otherPlanes) otherPlanes.getChildren().get(j);

			Plane.kill();
			Plane.setVelocityY(-50);
			Score -= 300;
			scoreCounter();
			// deletes all planes on screen when the N key is pressed
			// takes away 1000 from total
			// for some reason it takes away more points or less depending on how many
			// planes are on screen , try to fix it !
		}
	}

	public void ActivateCrazyMode(double elapsedTime) {
		if (Score > 1000) {

			if (keyboard.contains(KeyCode.B)) {
				bullets.getChildren().add(player.shoot());
			}
		}
	}

	public static void main(String[] args) {
		launch();
	}
}
