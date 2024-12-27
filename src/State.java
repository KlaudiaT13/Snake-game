public class State {
    int A;
    int B;
    int snake_x;
    int snake_y;
    int apple_x;
    int apple_y;

    public State(int a, int b, int snake_x, int snake_y, int apple_x, int apple_y) {
        A = a;
        B = b;
        this.snake_x = snake_x;
        this.snake_y = snake_y;
        this.apple_x = apple_x;
        this.apple_y = apple_y;
    }

    public int getA() {
        return A;
    }

    public int getB() {
        return B;
    }

    public int getSnake_x() {
        return snake_x;
    }

    public int getSnake_y() {
        return snake_y;
    }

    public int getApple_x() {
        return apple_x;
    }

    public int getApple_y() {
        return apple_y;
    }
}
