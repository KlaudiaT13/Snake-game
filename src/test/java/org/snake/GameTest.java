package org.snake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Position snake = new Position(1, 1);

    @Test
    void play10000x100() {
        Position apple = new Position(5000, 65);
        State state = new State(10000,100, snake, apple);
        Game game  = new Game(state);

        String play = game.play();

        assertEquals("You win", play);
    }

    @Test
    void play1x1000000() {
        Position apple = new Position(1, 500000);
        State state = new State(1,1000000, snake, apple);
        Game game  = new Game(state);

        String play = game.play();

        assertEquals("You win", play);
    }

    @Test
    void play1000000x1() {
        Position apple = new Position(500000, 1);
        State state = new State(1000000,1, snake, apple);
        Game game  = new Game(state);

        String play = game.play();

        assertEquals("You win", play);
    }

    @Test
    void play1000x1000() {
        Position apple = new Position(500, 500);
        State state = new State(1000,1000, snake, apple);
        Game game  = new Game(state);

        String play = game.play();

        assertEquals("You win", play);
    }

    @Test
    void play100x20() {
        Position apple = new Position(87, 17);
        State state = new State(100,20, snake, apple);
        Game game  = new Game(state);

        String play = game.play();

        assertEquals("You win", play);
    }
}
