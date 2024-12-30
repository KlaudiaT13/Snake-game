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
        this.cursor = snake;
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
        snake = new Position(snake.getX(), snake.getY() + 1);

        if (cursor.getY() == B) {
            cursor = new Position(cursor.getX(), 1);
        } else {
            cursor = new Position(cursor.getX(), cursor.getY() + 1);
        }
    }

    public void snakeDown(){
        snake = new Position(snake.getX(), snake.getY() - 1);

        if (cursor.getY() == 1) {
            cursor = new Position(cursor.getX(), B);
        } else {
            cursor = new Position(cursor.getX(), cursor.getY() - 1);
        }
    }

    public void snakeLeft(){
        snake = new Position(snake.getX() - 1, snake.getY());

        if (cursor.getX() == 1) {
            cursor = new Position(A, cursor.getY());
        } else {
            cursor = new Position(cursor.getX() - 1, cursor.getY());
        }
    }

    public void snakeRight(){
        snake = new Position(snake.getX() + 1, snake.getY());

        if (cursor.getX() == A) {
            cursor = new Position(1, cursor.getY());
        } else {
            cursor = new Position(cursor.getX() + 1, cursor.getY());
        }
    }
}
