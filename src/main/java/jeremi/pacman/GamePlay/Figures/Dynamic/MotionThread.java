package jeremi.pacman.GamePlay.Figures.Dynamic;

import jeremi.pacman.GamePlay.Tracker;

public class MotionThread extends Thread {

    private Ghost ghost;
    private Tracker tracker;

    // Time between consecutive hunts
    private int regeneration;

    public MotionThread(Ghost ghost, Tracker tracker, int regeneration){
        this.ghost = ghost;
        this.regeneration = regeneration;
        this.tracker = tracker;
    }

    public void move(){

        if (ghost.movementActive){

            if (!ghost.trail.isEmpty()){
                Tracker.Square square = ghost.trail.poll();
                System.out.println(square);
                int newX = square.x;
                int newY = square.y;

                ghost.board.moveGhost(ghost,newX,newY);
            }
        }

    }

    @Override
    public void run() {
        while(true) {
            try {

                // Compute next moves if clyde can move
                if (ghost.movementActive && ghost.trail.isEmpty()){
                    ghost.trail = Tracker.convertStackToQueue(this.tracker.sniff(ghost.getXPos(), ghost.getYPos(), ghost.board.getPacManX(), ghost.board.getPacManY()));
                    Thread.sleep(regeneration);
                }
                // If clyde can not move clear the trail
                else if (!ghost.movementActive) {
                    ghost.trail.clear();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
