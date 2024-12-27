import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Main {
    //1x1 is origin point
    //so viable x-coordinates are from 1 to A
    public static void main(String[] args) {

    }


    public String play(State state) {
        int A = state.getA();
        int B = state.getB();
        int snake_x = state.getSnake_x();
        int snake_y = state.getSnake_y();
        int apple_x = state.getApple_x();
        int apple_y = state.getApple_y();
        Set<Position> visited = new HashSet<>();
        Set<Position> queue = new HashSet<>();
        //check if inputs are feasible
        if(A*B > 1000000){
            return "too large Nokia screen";
        }
        if(snake_x > A || snake_y > B){
            return "snake out of bounds exception";
        }
        if(apple_x > A || apple_y > B){
            return "apple out of bounds exception";
        }
        int S = A * B;
        int numberOfSteps = 0;
        int constant = 1;
        boolean fromLeftToRight = true;

        boolean gameOver = false;
        if(snake_x == apple_x && snake_y == apple_y){
            gameOver = true;
        };
        while(!gameOver && numberOfSteps <= 35 * S){
            queue = queue(constant, visited);
            while(!queue.isEmpty()){
                if(snake_x == 1){

                }else{

                }
            }
            constant++;

        }

        if(gameOver){return "You win";}else{return "You lose - too many steps";}


    }

    public boolean sendSignal(Command command, int A, int B, int snake_x, int snake_y, int apple_x, int apple_y){
        switch (command) {
            case UP:
                return snake_x == apple_x && ((snake_y + 1) % B) == apple_y;
            case DOWN:
                return snake_x == apple_x && ((snake_y - 1) % B) == apple_y;
        }
        return false;
    }

    public Set<Position> queue(int constant, Set<Position> visited){
        // gets all positions under f(x) = constant * 1/x^2
        int x = 1;
        Set<Position> positions = new HashSet<>();
        while(function(constant, x) >= 1){
            for(int i = 1; i <= function(constant, x); i++){
                positions.add(new Position(x, i));
            }
            x++;
        }

        for(Position position : visited){
            positions.remove(position);
        }
        return positions;
    }

    public double function(int A, int x){
        double y = (double) A;
        return y/(x^2);
    }

    public Position findNextPosition(Set<Position> positions, boolean fromLeftToRight, int current_x, int current_y){
        if(positions.isEmpty()){
            return null;
        }
        Position next = null;
        Set<Position> maybe = new HashSet<>();
        //same x with highest y, or x+1 with highest y
        if(fromLeftToRight){
            for(Position position : positions){
                if(position.getX_coordinate() == current_x){
                    maybe.add(position);
                }
            }
            if(maybe.isEmpty()){
                for(Position position : positions){
                    if(position.getX_coordinate() == current_x + 1){
                        maybe.add(position);
                    }
                }
            }
            int next_y = 0;
            for(Position position : maybe){
                if(position.getY_coordinate() > next_y){
                    next = position;
                    next_y = position.getY_coordinate();
                }
            }
        }else{
            for(Position position : positions){
                if(position.getY_coordinate() == current_y){
                    maybe.add(position);
                }
            }
            if(maybe.isEmpty()){
                for(Position position : positions){
                    if(position.getY_coordinate() == current_y + 1){
                        maybe.add(position);
                    }
                }
            }
            int next_x = 0;
            for(Position position : maybe){
                if(position.getX_coordinate() > next_x){
                    next = position;
                    next_x = position.getX_coordinate();
                }
            }
        }
        return next;
    }

}