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
    int iteration = 2;
    Set<Position> visited = new HashSet<>();
    Set<Position> positionsToVisit = new HashSet<>();

    public Game(State state) {
        this.state = state;
        A = state.getA();
        B = state.getB();
        screenSize = A * B;
        visited.add(state.getSnake());
    }

    public String play() {

        //check if inputs are feasible
        String x = validateState();
        if (x != null) return x;


        if(state.getSnake().equals(state.getApple())){
            gameOver = true;
        };

        Position nextPosition;
        while(!gameOver && isNumberOfSteepsValid()){
            boolean nextPositionReached = false;
            nextPosition = findNextPosition();
            while(!gameOver && !nextPositionReached && isNumberOfSteepsValid()){
                Command nextCommand = findNextStep(nextPosition, state.getSnake());
//                    System.out.println(nextCommand);
                gameOver = sendSignal(nextCommand);
                nextPositionReached = state.getSnake().equals(nextPosition);
            }
        }

        if(gameOver){
            System.out.println("Number of steps: " + numberOfSteps);
            System.out.println("Visited positions: " + visited.size());
            double temp = (double) numberOfSteps;
            double size = (double) screenSize;
            double screen = Math.sqrt(size) * size;
            temp = temp/(screen);
//            temp = temp/(A*B*35);
            System.out.println(temp);
            //drawChart();
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

    public boolean sendSignal(Command command){
        switch(command){
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
        positionsToVisit.remove(state.getSnake());
        numberOfSteps++;
        visited.add(state.getSnake());
        return state.getApple().equals(state.getCursor());
    }

    public Position findNextPosition(){
        int sum = 0;
        for(int i = 1; i <= iteration; i++){
            sum += i;
        }
        if(fromLeftToRight){
            fromLeftToRight = !fromLeftToRight;
            return new Position(2*sum, 1);
        }else{
            fromLeftToRight = !fromLeftToRight;
            iteration++;
            return new Position(1, 2*sum);
        }
    }

    public Command findNextStep(Position nextPosition, Position snake){
        //already in the same column
        if(nextPosition.getX() == snake.getX()){
            if(nextPosition.getY() > snake.getY()){
                return Command.UP;
            }else {
                return Command.DOWN;
            }
        }
        //already in the same row
        if(nextPosition.getY() == snake.getY()){
            if(nextPosition.getX() > snake.getX()){
                return Command.RIGHT;
            }else {
                return Command.LEFT;
            }
        }

        if(nextPosition.getX() < snake.getX()){
            if(visited.contains(getLeft(snake))){
                return Command.UP;
            }else{
                return Command.LEFT;
            }
        }

        if(nextPosition.getX() > snake.getX()){
            if(visited.contains(getDown(snake))){
                return Command.RIGHT;
            }else{
                return Command.DOWN;
            }
        }
        return null;
    }

    private Position getLeft(Position position){
        return new Position(position.getX() - 1, position.getY());
    }

    private Position getRight(Position position){
        return new Position(position.getX() + 1, position.getY());
    }

    private Position getDown(Position position){
        return new Position(position.getX(), position.getY() - 1);
    }

    private Position getUp(Position position){
        return new Position(position.getX(), position.getY() + 1);
    }

    private String validateState() {
        if(state.getA() * state.getB() > MAX_NOKIA_SIZE){
            return "too large Nokia screen";
        }
        if(state.getSnake().getX() > state.getA() || state.getSnake().getY() > state.getB()){
            return "snake out of bounds exception";
        }
        if(state.getApple().getX() > state.getA() || state.getApple().getY() > state.getB()){
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

        int[][]drawMatrix = new int[maxX][maxY];

        for (Position position : visited) {
            drawMatrix[position.getX()-1][position.getY()-1] = 1;
        }

        try {
            FileWriter fileWriter = new FileWriter("chart.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (int j = maxY-1; j >=0; j--) {
                for (int i = 0; i < maxX; i++) {
                    if (drawMatrix[i][j] == 1) {
//                    System.out.print("*  ");
                        printWriter.print("*");
                    } else {
//                        System.out.print(" ");
                        printWriter.print(" ");
                    }
                }
//                System.out.println();
                printWriter.println();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
