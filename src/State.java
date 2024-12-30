public class State {
    private int A;
    private int B;
    private Position snake;
    private Position cursor;
    private final Position apple;

    public State(int a, int b, Position snake, Position apple) {
        A = a;
        B = b;
        this.snake = snake;
        this.cursor = new Position(-2, 2);
        this.apple = apple;
    }

    public int getA() {
        return A;
    }

    public int getB() {
        return B;
    }

    public Position getSnake() {
        return snake;
    }

    public void setSnake(Position snake) {
        this.snake = snake;
    }

    public Position getCursor() {
        return cursor;
    }

    public Position getApple() {
        return apple;
    }

    public void snakeUP(){
        cursor = new Position(cursor.getX(), cursor.getY() + 1);

        if (snake.getY() == B) {
            snake = new Position(snake.getX(), 1);
        } else {
            snake = new Position(snake.getX(), snake.getY() + 1);
        }
    }

    public void snakeDown(){
        cursor = new Position(cursor.getX(), cursor.getY() - 1);

        if (snake.getY() == 1) {
            snake = new Position(snake.getX(), B);
        } else {
            snake = new Position(snake.getX(), snake.getY() - 1);
        }
    }

    public void snakeLeft(){
        cursor = new Position(cursor.getX() - 1, cursor.getY());

        if (snake.getX() == 1) {
            snake = new Position(A, snake.getY());
        } else {
            snake = new Position(snake.getX() - 1, snake.getY());
        }
    }

    public void snakeRight(){
        cursor = new Position(cursor.getX() + 1, cursor.getY());

        if (snake.getX() == A) {
            snake = new Position(1, snake.getY());
        } else {
            snake = new Position(snake.getX() + 1, snake.getY());
        }
    }
}
