package Models;

public class Enemy extends Character {
    private int action;
    private int[] position;

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int getAction() {
        return action;
    }
    public void setAction() {
        --this.action;
    }

    public Enemy(int lives, int x, int y){
        super(lives);
        position = new int[]{x, y};
        if(lives == 1) {
            id = 1;
            this.lives = 1;
            this.action = 1;
        }
        else{
            id = 2;
            this.action = 2;
            this.lives = 2;
        }
    }
    public void resetEnemyActions(){
        if(this.id == 2) action = 2;
        else if(this.id == 1) action = 1;
        else action = 0;
    }
}
