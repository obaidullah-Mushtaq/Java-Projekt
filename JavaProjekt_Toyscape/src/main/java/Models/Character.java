package Models;

public abstract class Character {
    protected int id;
    protected int lives;

    public Character(int lives) {
        this.lives = lives;
    }

    public int getId() {
        return id;
    }

    public int getLives() {
        return lives;
    }

    public boolean getAttacked() {
        lives--;
        boolean dead = lives == 0;
        if(dead) id = -1;
        return dead;
    }
}