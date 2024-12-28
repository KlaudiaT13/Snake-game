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


        int alpha = 1; //y=alpha * (1/x)


        if(state.getSnake().equals(state.getApple())){
            gameOver = true;
        };

        while(!gameOver && isNumberOfSteepsValid()){
            positionsToVisit = getToVisit(alpha, visited);
            //fromLeftToRight = !fromLeftToRight;
            Position nextPosition;
            boolean nextPositionReached = false;
            while(!gameOver && !positionsToVisit.isEmpty() && isNumberOfSteepsValid()){
                nextPosition = findNextPosition(positionsToVisit, state.getSnake());
                nextPositionReached = false;
                while(!gameOver && !nextPositionReached && isNumberOfSteepsValid()){
                    Command nextCommand = findNextStep(nextPosition, state.getSnake(), visited);
                    System.out.println(nextCommand);
                    gameOver = sendSignal(nextCommand);
                    nextPositionReached = state.getSnake().equals(nextPosition);
                }


                //fromLeftToRight = !fromLeftToRight;
            }
            alpha++;

        }

        if(gameOver){
            return "You win";
        }else{
            return "You lose - too many steps";
        }


    }

    private boolean isNumberOfSteepsValid() {
        return numberOfSteps <= 35 * screenSize;
    }

    public boolean sendSignal(Command command){
        switch(command){
            case UP:
                state.getSnake().setUP();
                break;
            case DOWN:
                state.getSnake().setDown();
                break;
            case LEFT:
                state.getSnake().setLeft();
                break;
            case RIGHT:
                state.getSnake().setRight();
        }
        positionsToVisit.remove(state.getSnake());
        numberOfSteps++;
        visited.add(state.getSnake());
        return state.getApple().equals(state.getSnake());
    }

    public Set<Position> getToVisit(int constant, Set<Position> visited){
        // gets all positions under f(x) = constant * 1/x
        int x = 1;
        Set<Position> positions = new HashSet<>();
        while(function(constant, x) >= 1){
            for(int i = 1; i <= function(constant, x); i++){
                Position position = new Position(x, i);
                if(!visited.contains(position)){
                    positions.add(position);
                }
            }
            x++;
        }
        return positions;
    }

    public double function(int A, int x){
        double y = (double) A;
        return y/(x);
    }

    public Position findNextPosition(Set<Position> positionsToVisit, Position snake){
        if(positionsToVisit.isEmpty()){
            return null;
        }
        Position next = null;
        Set<Position> maybe = new HashSet<>();
        //same x with highest y, or x+1 with highest y
        if(fromLeftToRight){
            int i=0;
            while(maybe.isEmpty() && i<10){
                for(Position position : positionsToVisit){
                    if(position.getX() == snake.getX() + i){
                        maybe.add(position);
                    }
                }
                i++;
            }
            if(maybe.isEmpty()){
                fromLeftToRight = !fromLeftToRight;
                next = findNextPosition(positionsToVisit, snake);
            } else{
                next = maybe.iterator().next();
                for(Position position : maybe){
                    if(position.getY() > next.getY()){
                        next = position;
                    }
                }
            }
        }else{
            int i=0;
            while(maybe.isEmpty() && i<10){
                for(Position position : positionsToVisit){
                    if(position.getY() == snake.getY() + i){
                        maybe.add(position);
                    }
                }
                i++;
            }
            if(maybe.isEmpty()){
                fromLeftToRight = !fromLeftToRight;
                next = findNextPosition(positionsToVisit, snake);
            } else{
                next = maybe.iterator().next();
                for(Position position : maybe){
                    if(position.getX() > next.getX()){
                        next = position;
                    }
                }
            }

        }
        return next;
    }

    public Command findNextStep(Position nextPosition, Position snake, Set<Position> visited){
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
            if(visited.contains(getRight(snake))){
                return Command.DOWN;
            }else{
                return Command.RIGHT;
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

}
