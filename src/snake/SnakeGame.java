package snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame {

	private static final int MOVE_UP = 8;
	private static final int MOVE_DOWN = 2;
	private static final int MOVE_LEFT = 4;
	private static final int MOVE_RIGHT = 6;
	private static final int BORDER_END = 8;
	private static final int BORDER_FIRST_FRAME = 0;
	private static final int BORDER_LAST_FRAME = 7;
	private static final int INVALID_VALUE = -1;
	private static final int SNAKE_START_X_CORD = 4;
	private static final int SNAKE_START_Y_CORD = 4;
	private static final int APPLE_START_X_CORD = 1;
	private static final int APPLE_START_Y_CORD = 4;
	private static final int MOVE_STEP = 1;
	private static final int MAX_RANDOM_INTEGER = 7;

	private Snake snake;
	private Apple apple;
	private Random rand;
	private boolean isSpin = true;
	private boolean canGoUp = true;
	private boolean canGoDown = true;
	private boolean canGoRight = true;
	private boolean canGoLeft = true;

	public SnakeGame() {

		snake = new Snake(SNAKE_START_X_CORD, SNAKE_START_Y_CORD);
		apple = new Apple(APPLE_START_X_CORD, APPLE_START_Y_CORD);
		rand = new Random();

	}

	public void run() {
		System.out.println(" 8 - up/ 2 - down/ 4 - left/ 6 - right");
		onRender();

		while (isSpin) {
			Scanner sc = new Scanner(System.in);
			int position = sc.nextInt();
			makeMove(position);
			snakeHeadColisionSnakeBody();
			appleCollisionSnake();
			snakeColisionBoard();
			onRender();
		}

		System.out.println("Your points : " + (snake.getSnake().size() - 4));
	}

	public void makeMove(int position) {

		SnakeBody head = snake.getSnake().get(0);

		if (position == MOVE_UP) {

			setSnake();
			head.move(-MOVE_STEP, 0);

			canGoDown = false;
			canGoRight = true;
			canGoLeft = true;
		}

		if (position == MOVE_DOWN) {

			if (canGoDown) {

				setSnake();
				head.move(MOVE_STEP, 0);

				canGoUp = false;
				canGoRight = true;
				canGoLeft = true;
			}
		}

		if (position == MOVE_LEFT) {

			if (canGoLeft) {

				setSnake();
				head.move(0, -MOVE_STEP);

				canGoUp = true;
				canGoDown = true;
				canGoRight = false;
			}
		}

		if (position == MOVE_RIGHT) {

			if (canGoRight) {

				setSnake();
				head.move(0, MOVE_STEP);

				canGoUp = true;
				canGoDown = true;
				canGoLeft = false;
			}
		}
	}

	public void onRender() {
		for (int i = 0; i < BORDER_END; i++) {
			for (int j = 0; j < BORDER_END; j++) {

				if (isEqual(i, j)) {

					System.out.print(" +");

				} else if (renderApple(i, j)) {

					System.out.print(" *");

				} else {

					System.out.print(" .");
				}
			}
			System.out.println();
		}
	}

	public void snakeColisionBoard() {

		for (int i = 1; i < snake.getSnake().size(); i++) {
			SnakeBody body = snake.getSnake().get(i);
			if (body.getX() < BORDER_FIRST_FRAME || body.getX() > BORDER_LAST_FRAME || body.getY() < BORDER_FIRST_FRAME
					|| body.getY() > BORDER_LAST_FRAME) {
				isSpin = false;
			}
		}
	}

	public void snakeHeadColisionSnakeBody() {

		SnakeBody head = snake.getSnake().get(0);
		for (int i = 1; i < snake.getSnake().size(); i++) {
			SnakeBody body = snake.getSnake().get(i);
			if (body.getX() == head.getX() && head.getY() == body.getY()) {
				isSpin = false;
			}
		}
	}

	public void appleCollisionSnake() {

		SnakeBody head = snake.getSnake().get(0);
		if (head.getX() == apple.getX() && head.getY() == apple.getY()) {
			snake.getSnake().add(new SnakeBody(apple.getX(), apple.getY()));

			int appleX = generateAppleX();
			int appleY = generateAppleY();
			apple = new Apple(appleX, appleY);
		}

	}

	public int generateAppleY() {
		List<Integer> snakeY = new ArrayList<>();
		for (int i = 0; i < snake.getSnake().size(); i++) {
			SnakeBody body = snake.getSnake().get(i);
			snakeY.add(body.getY());
		}
		boolean spin = true;
		while (spin) {
			int appleY = rand.nextInt(MAX_RANDOM_INTEGER);
			if (!snakeY.contains(appleY)) {
				spin = false;
				return appleY;
			}
		}
		return INVALID_VALUE;
	}

	public int generateAppleX() {
		
		List<Integer> snakeX = new ArrayList<>();
		
		for (int i = 0; i < snake.getSnake().size(); i++) {
			SnakeBody body = snake.getSnake().get(i);
			snakeX.add(body.getX());
		}
		
		boolean spin = true;
		
		while (spin) {
			
			int appleX = rand.nextInt(MAX_RANDOM_INTEGER);
			if (!snakeX.contains(appleX)) {
				spin = false;
				return appleX;
			}
		}
		return INVALID_VALUE;
	}

	public void setSnake() {
		
		for (int k = snake.getSnake().size() - 1; k > 0; k--) {

			SnakeBody previousBody = snake.getSnake().get(k - 1);
			SnakeBody body = snake.getSnake().get(k);

			body.setX(previousBody.getX());
			body.setY(previousBody.getY());

		}
	}

	public boolean renderApple(int i, int j) {
		if (apple.getX() == i && apple.getY() == j) {
			return true;
		}
		return false;
	}

	public boolean isEqual(int i, int j) {
		for (int k = 0; k < snake.getSnake().size(); k++) {
			SnakeBody snakeBody = snake.getSnake().get(k);
			if (snakeBody.getX() == i && snakeBody.getY() == j) {
				return true;
			}
		}
		return false;
	}

}
