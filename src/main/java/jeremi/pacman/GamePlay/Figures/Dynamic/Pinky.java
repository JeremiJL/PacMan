package jeremi.pacman.GamePlay.Figures.Dynamic;

import jeremi.pacman.GamePlay.Board;
import jeremi.pacman.GamePlay.Figures.BoardFigure;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Pinky extends Ghost {

    //Inky default Image
    private static ImageIcon DEFAULT_IMAGE = new ImageIcon("Graphics/Ghosts/Pinky/PinkyDefault1.png");

    //Animator class handles animation it's a Thread
    public PinkyAnimator pinkyAnimator;
    private Thread animationThread;

    //Enables / disables ghosts movement
    private boolean movementActive;

    //Movement

    //Default movement speed
    private int defaultMovementSpeed = 800;

    public Pinky(Board board, int xPos, int yPos) {
        super(board, xPos, yPos,DEFAULT_IMAGE);

        //As default
        this.movementActive = true;

        //Movement speed of clyde
        setMTI(defaultMovementSpeed);

        //Assignment
        this.pinkyAnimator = new PinkyAnimator();

        //Starting Animation Thread
        this.animationThread = new Thread(this.pinkyAnimator);
        this.animationThread.start();

        //Movement logic
        computeAdjecencyList();
    }

    public void setMovementActive(boolean movementActive) {
        this.movementActive = movementActive;
    }

    @Override
    public String toString() {
        return "Pinky";
    }

    //Moving in random directions
    protected void move(){

        if(movementActive){

            int newX = getXPos();
            int newY = getYPos();

            board.moveGhost(this,newX,newY);

        }

    }

    protected void computeAdjecencyList(){

        List<Square> squaresList = new ArrayList<>();

        int size = this.board.getBoardSize();

        //Add squares to list
        for (int row = 0; row < size; row++){
            for (int el = 0; el < size; el++){
                squaresList.add(new Square(el,row));
            }
        }

        //Set neighbours
        for (int row = 0; row < size; row++){
            for (int el = 0; el < size; el++){

                //Select square for neighbour update
                Square squareForUpdate = squaresList.get(row * size + el);
                //Add neighbours only if this square corresponds to empty space
                if (this.board.getStaticFigData()[row][el] == null) {
                    //Add neighbour from east
                    if (el + 1 < size && this.board.getStaticFigData()[row][el + 1] == null){
                        Square easternNeighbour = squaresList.get(row + el + 1);
                        squareForUpdate.addNeighbour(easternNeighbour);
                    }
                }
            }
        }

        testAdjacency(squaresList);

        for (BoardFigure[] row : this.board.getStaticFigData()){
            for (BoardFigure fig : row){
                if (fig == null)
                    System.out.print("null ");
                else
                    System.out.print("elem ");
            }
            System.out.print("\n");
        }
    }




    class Square {

        public final int x;
        public final int y;
        private List<Square> neighbours;

        public Square(int x, int y) {
            this.x = x;
            this.y = y;
            neighbours = new ArrayList<>();
        }

        public void addNeighbour(Square sqr){
            neighbours.add(sqr);
        }

        @Override
        public String toString() {
            return "x:" + this.x + " y:" + this.y;
        }
    }

    private void testAdjacency(List<Square> list){
        for (Square square : list){
            System.out.print(square.toString() + " neighbours: ");
            square.neighbours.forEach(System.out::print);
            System.out.print("\n");
        }
    }



    public class PinkyAnimator implements Runnable {

        //Table contains default animation frames, in case of Ghost it's just looking around and simple animation of 'hovering'
        protected static ImageIcon[] DEFAULT_FRAMES = new ImageIcon[]{
                new ImageIcon("Graphics/Ghosts/Pinky/PinkyDefault1.png"),
                new ImageIcon("Graphics/Ghosts/Pinky/PinkyDefault2.png"),
                new ImageIcon("Graphics/Ghosts/Pinky/PinkyDefault3.png"),
                new ImageIcon("Graphics/Ghosts/Pinky/PinkyDefault4.png")
        };

        private static ImageIcon[] ACTION_FRAMES = null;

        private static ImageIcon[] DEAD_FRAMES = null;

        //Role of this enum is to make code more clear
        //State corresponds with ImageIcon tables
        public enum State {

            DEFAULT(DEFAULT_FRAMES);

            public ImageIcon[] framesTab;

            State(ImageIcon[] frames) {
                this.framesTab = frames;
            }

        }

        //Actual animation-state of character
        private State myState;

        //Period that every frame of animation will take, in milliseconds
        private static int ANIMATION_TIME_INTERVAL = 200; //ms

        public PinkyAnimator() {
            //Starter animation-state
            setState(State.DEFAULT);
        }

        public void setState(State state) {
            this.myState = state;
        }

        public void animate(ImageIcon[] frames) {

            //Iterates over the table of Images and displays them in loop
            for (ImageIcon frame : frames) {

                setImage(frame);

                //Sleeps for the period that frame should take
                try {
                    Thread.sleep(ANIMATION_TIME_INTERVAL);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }

        @Override
        public void run() {

            //Animation in the loop
            while (true) {
                animate(myState.framesTab);
            }
        }
    }
}
