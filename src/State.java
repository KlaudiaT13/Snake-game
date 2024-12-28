public class State {
    private int A;
    private int B;
    private Position snake;
    private final Position apple;

    public State(int a, int b, Position snake, Position apple) {
        A = a;
        B = b;
        this.snake = snake;
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

    public Position getApple() {
        return apple;
    }

    public void snakeUP(){
        snake = new Position(snake.getX(), snake.getY() + 1);
    }

    public void snakeDown(){
        snake = new Position(snake.getX(), snake.getY() - 1);
    }

    public void snakeLeft(){
        snake = new Position(snake.getX() - 1, snake.getY());
    }
    public void snakeRight(){
        snake = new Position(snake.getX() + 1, snake.getY());
    }
}
