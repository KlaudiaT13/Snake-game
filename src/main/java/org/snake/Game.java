package org.snake;

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
    boolean fromLeftToRight = false;
    int iteration = 2;
    int howLong = 8;
    Set<Position> visited = new HashSet<>();
    Set<Position> positionsToVisit = new HashSet<>();
    Position continueLeft = new Position(-3, 2);
    Position continueRight = new Position(3, -2);

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

        if(start() != null && !gameOver){
            return start();
        }

        while(!gameOver && isNumberOfSteepsValid()){
            while(!gameOver && isNumberOfSteepsValid()){
                goToNextExtension(continueLeft);
                followExtension(howLong);
                goToNextExtension(continueRight);
                followExtension(howLong);
                howLong += iteration;
                iteration +=100;
            }
        }

        return gameOverMessage();
    }

    private void goToNextExtension(Position nextPosition){
        if(!fromLeftToRight){
            while (!gameOver && isNumberOfSteepsValid() && !state.getCursor().equals(nextPosition)){
                if(visited.contains(getUp(state.getCursor()))){
                    gameOver = sendSignal(Command.LEFT);
                } else{
                    gameOver = sendSignal(Command.UP);
                }
            }
        } else{
            while (!gameOver && isNumberOfSteepsValid() && !state.getCursor().equals(nextPosition)){
                if(visited.contains(getDown(state.getCursor()))){
                    gameOver = sendSignal(Command.RIGHT);
                } else {
                    gameOver = sendSignal(Command.DOWN);
                }
            }
        }
    }

    private void followExtension(int howLong){
        int i = 0;
        boolean vertical = true;
        if(fromLeftToRight){
            while(i<howLong && isNumberOfSteepsValid() && !gameOver){
                if(vertical){
                    gameOver = sendSignal(Command.DOWN);
                    i++;
                } else{
                    gameOver = sendSignal(Command.RIGHT);
                }
                vertical = !vertical;
            }
            continueRight = getRight(state.getCursor());
        } else{
            while(i<howLong && isNumberOfSteepsValid() && !gameOver){
                if(vertical){
                    gameOver = sendSignal(Command.UP);
                    i++;
                } else {
                    gameOver = sendSignal(Command.LEFT);
                }
                vertical = !vertical;
            }
            continueLeft = getLeft(state.getCursor());
        }

        fromLeftToRight = !fromLeftToRight;
    }

    private String start(){
        gameOver = sendSignal(Command.DOWN);
        if(gameOver || !isNumberOfSteepsValid()) return gameOverMessage();
        gameOver = sendSignal(Command.RIGHT);
        if(gameOver || !isNumberOfSteepsValid()) return gameOverMessage();
        gameOver = sendSignal(Command.DOWN);
        if(gameOver || !isNumberOfSteepsValid()) return gameOverMessage();
        gameOver = sendSignal(Command.RIGHT);
        if(gameOver || !isNumberOfSteepsValid()) return gameOverMessage();

        gameOver = sendSignal(Command.RIGHT);
        if(gameOver || !isNumberOfSteepsValid()) return gameOverMessage();
        gameOver = sendSignal(Command.DOWN);
        if(gameOver || !isNumberOfSteepsValid()) return gameOverMessage();
        gameOver = sendSignal(Command.RIGHT);
        if(gameOver || !isNumberOfSteepsValid()) return gameOverMessage();
        gameOver = sendSignal(Command.DOWN);
        if(gameOver || !isNumberOfSteepsValid()) return gameOverMessage();

        return null;
    }

    private String gameOverMessage() {
        System.out.println("Number of steps used: " + numberOfSteps);
//        System.out.println("Visited positions: " + visited.size());
        double temp = (double) numberOfSteps;
        double size = (double) screenSize;
        double screen = Math.sqrt(size) * size;
        temp = temp/(screenSize*35);
        System.out.println("usedSteps/upperBound: " + temp);
        //drawChart();
        if(gameOver){
            return "You win";
        } else {
            return "You lose - too many steps";
        }

    }

    private boolean isNumberOfSteepsValid() {
        return numberOfSteps <= 200 * screenSize;
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
        numberOfSteps++;
        visited.add(state.getCursor());
        return state.getApple().equals(state.getSnake());
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
