import java.util.HashSet;
import java.util.Set;

public class Game {

    public static final int MAX_NOKIA_SIZE = 1000000;

    private boolean gameOver = false;
    private State state;

    public Game(State state) {
        this.state = state;
    }

    public String play() {

        int A = state.getA();
        int B = state.getB();

        Set<Position> visited = new HashSet<>();
        Set<Position> positionsToVisit = new HashSet<>();

        //check if inputs are feasible
        String x = validateState();
        if (x != null) return x;

        int screenSize = A * B;
        int numberOfSteps = 0;
        int alpha = 1; //y=alpha * (1/x)
        boolean fromLeftToRight = true;


        if(state.getSnake().equals(state.getApple())){
            gameOver = true;
        };

        while(!gameOver && numberOfSteps <= 35 * screenSize){
            positionsToVisit = getToVisit(alpha, visited);
            if(state.getSnake().getX() == 1){
                fromLeftToRight = false;
            }else{
                fromLeftToRight = true;
            }
            Position nextPosition;
            boolean nextPositionFound = false;
            while(!positionsToVisit.isEmpty()){
                nextPosition = findNextPosition(positionsToVisit, fromLeftToRight, state.getSnake());
                nextPositionFound = false;
                while(!nextPositionFound){
                    if(nextPosition.getX() > state.getSnake().getX()){

                    }else if(nextPosition.getX() < state.getSnake().getX()){

                    } else{
                        if(nextPosition.getY() > state.getSnake().getY()){
                            sendSignal(Command.UP);
                        } else if(nextPosition.getY() < state.getSnake().getY()){
                            sendSignal(Command.DOWN);
                        } else{
                            nextPositionFound = true;
                        }
                    }
                }


                fromLeftToRight = !fromLeftToRight;
            }
            alpha++;

        }

        if(gameOver){
            return "You win";
        }else{
            return "You lose - too many steps";
        }


    }

    public boolean sendSignal(Command command){

        return false;
    }

    public Set<Position> getToVisit(int constant, Set<Position> visited){
        // gets all positions under f(x) = constant * 1/x^2
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

    public Position findNextPosition(Set<Position> positionsToVisit, boolean fromLeftToRight, Position snake){
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

            next = maybe.iterator().next();
            for(Position position : maybe){
                if(position.getY() > next.getY()){
                    next = position;
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

            next = maybe.iterator().next();
            for(Position position : maybe){
                if(position.getX() > next.getX()){
                    next = position;
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
