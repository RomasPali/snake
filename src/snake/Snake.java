package snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {

	private List<SnakeBody> snake;

	public Snake(int x, int y) {

		snake = new ArrayList<>();
		snake.add(new SnakeBody(x, y));
		snake.add(new SnakeBody(x, y + 1));
		snake.add(new SnakeBody(x, y + 2));
		snake.add(new SnakeBody(x, y + 3));
	}

	public List<SnakeBody> getSnake() {
		return snake;
	}

	public void setSnake(List<SnakeBody> snake) {
		this.snake = snake;
	}
}
