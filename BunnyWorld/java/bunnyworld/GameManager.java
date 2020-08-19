package edu.stanford.cs108.bunnyworld;

public class GameManager {
    static Game cur_game= new Game("test_game");


    // TODO
    static Game load_game(String name) {
        cur_game = new Game(name);
        return cur_game;
    }

    static void save_game(Game game) {
        ;
    }
}
