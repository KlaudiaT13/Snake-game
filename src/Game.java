import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class Game {

    public static final int MAX_NOKIA_SIZE = 1000000;

    private boolean gameOver = false;
    private State state;
    int numberOfSteps = 0;
    int A;
    int B;
    int screenSize;
    boolean fromLeftToRight = true;
    int wuzel = 1;
    int maxIterations = 4;
    Command previousCommand = Command.RIGHT;
    Set<Position> visited = new HashSet<>();
//    Set<Position> positionsToVisit = new HashSet<>();

    public Game(State state) {
        this.state = state;
        A = state.getA();
        B = state.getB();
        screenSize = A * B;
//        visited.add(state.getSnake());
    }

    public String play() {

        //check if inputs are feasible
        String x = validateState();
        if (x != null) return x;


        if (state.getSnake().equals(state.getApple())) {
            gameOver = true;
        }

        while (!gameOver && isNumberOfSteepsValid()) {
            Command nextCommand = findNextStep(state.getCursor());
//                    System.out.println(nextCommand);
            gameOver = sendSignal(nextCommand);
        }

        if (gameOver) {
            System.out.println("Number of steps: " + numberOfSteps);
            System.out.println("Visited positions: " + visited.size());
            double temp = (double) numberOfSteps;
            double size = (double) screenSize;
            double screen = Math.sqrt(size) * size;
            temp = temp / (screen);
//            temp = temp/(A*B*35);
            System.out.println(temp);
            drawChart();
            return "You win";
        } else {
            return "You lose - too many steps";
        }


    }

    private boolean isNumberOfSteepsValid() {
//        return numberOfSteps <= 35 * screenSize;
        double size = (double) screenSize;
        return numberOfSteps <= Math.sqrt(size) * size;
    }

    public boolean sendSignal(Command command) {
        switch (command) {
            case UP:
                state.snakeUP();
                break;
            case DOWN:
                state.snakeDown();
                break;
            case LEFT:
                state.snakeLeft();
                break;
            case RIGHT:
                state.snakeRight();
        }
        previousCommand = command;
//        positionsToVisit.remove(state.getSnake());
        numberOfSteps++;
        visited.add(state.getCursor());
        return state.getApple().equals(state.getCursor());
    }

    public Command findNextStep(Position cursor) {
        if (fromLeftToRight) {
            if (cursor.getY() < maxIterations) {
                if (previousCommand == Command.RIGHT) {
                    return Command.UP;
                } else {
                    return Command.RIGHT;
                }
            } else {
                if (previousCommand == Command.RIGHT) {
                    fromLeftToRight = false;
                    return Command.RIGHT;
                }
                return Command.RIGHT;
            }
        } else {
            if (cursor.getY() > 0) {
                if (previousCommand != Command.DOWN) {
                    return Command.DOWN;
                } else {
                    return Command.LEFT;
                }
            } else {
                if (previousCommand == Command.RIGHT) {
                    fromLeftToRight = true;
                    wuzel++;
                    maxIterations = (wuzel * 4);
                    return Command.RIGHT;
                }
                return Command.RIGHT;
            }
        }
    }

    private String validateState() {
        if (state.getA() * state.getB() > MAX_NOKIA_SIZE) {
            return "too large Nokia screen";
        }
        if (state.getSnake().getX() > state.getA() || state.getSnake().getY() > state.getB()) {
            return "snake out of bounds exception";
        }
        if (state.getApple().getX() > state.getA() || state.getApple().getY() > state.getB()) {
            return "apple out of bounds exception";
        }
        return null;
    }

    private void drawChart() {
        int maxX = 0;
        int maxY = 0;

        for (Position position : visited) {
            maxX = Math.max(maxX, position.getX());
            maxY = Math.max(maxY, position.getY());
        }

        int[][] drawMatrix = new int[maxX][maxY];

        for (Position position : visited) {
            drawMatrix[position.getX() - 1][position.getY() - 1] = 1;
        }

        try {
            FileWriter fileWriter = new FileWriter("chart2.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (int j = maxY - 1; j >= 0; j--) {
                for (int i = 0; i < maxX; i++) {
                    if (drawMatrix[i][j] == 1) {
                    System.out.print("*  ");
                        printWriter.print("*   ");
                    } else {
                        System.out.print(" ");
                        printWriter.print(" ");
                    }
                }
                System.out.println();
                printWriter.println();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
