package jeremi.pacman.GamePlay;

import jeremi.pacman.GamePlay.Board;
import jeremi.pacman.GamePlay.Figures.Static.Box;

import java.util.*;

public class Tracker {

    private final Board board;
    private List<Square> adjecencyList;

    public Tracker(Board board) {
        this.board = board;
        adjecencyList = computeAdjecencyList();
    }

    protected List<Square> computeAdjecencyList() {

        List<Square> squaresList = new ArrayList<>();

        int size = this.board.getBoardSize();

        //Add squares to list
        for (int row = 0; row < size; row++) {
            for (int el = 0; el < size; el++) {
                squaresList.add(new Square(el, row));
            }
        }

        //Set neighbours
        for (int row = 0; row < size; row++) {
            for (int el = 0; el < size; el++) {

                //Select square for neighbour update
                Square squareForUpdate = squaresList.get(row * size + el);
                //Add neighbours only if this square corresponds to empty space
                if (!(this.board.getStaticFigData()[row][el] instanceof Box)) {
                    //Add neighbour from east
                    if (el + 1 < size && !(this.board.getStaticFigData()[row][el + 1] instanceof Box)) {
                        Square easternNeighbour = squaresList.get(row * size + el + 1);
                        squareForUpdate.addNeighbour(easternNeighbour);
                    }
                    //Add neighbour from west
                    if (el > 0 && !(this.board.getStaticFigData()[row][el - 1] instanceof Box)) {
                        Square westernNeighbour = squaresList.get(row * size + el - 1);
                        squareForUpdate.addNeighbour(westernNeighbour);
                    }
                    //Add neighbour from north
                    if (row > 0 && !(this.board.getStaticFigData()[row - 1][el] instanceof Box)) {
                        Square northernNeighbour = squaresList.get((row - 1) * size + el);
                        squareForUpdate.addNeighbour(northernNeighbour);
                    }
                    //Add neighbour from south
                    if (row + 1 < size && !(this.board.getStaticFigData()[row + 1][el] instanceof Box)) {
                        Square southernNeighbour = squaresList.get((row + 1) * size + el);
                        squareForUpdate.addNeighbour(southernNeighbour);
                    }
                }
            }
        }

        return squaresList;
    }


    public Stack<Square> sniff(int startX, int startY, int endX, int endY){

        Square start = findSquare(startX,startY);
        Square end = findSquare(endX,endY);

        List<Square> checked = new ArrayList<>();
        Stack<Square> trail = new Stack<>();

        //Recursive hunt
        hunt(start,end,checked,trail);

        //Display the result for test purposes
//        System.out.println("Start : " + start + "\tEnd : " + end);
//        System.out.println("Final trail : ");
//        trail.forEach(s -> System.out.print(s + ", "));

        //Return result
        return trail;
    }

    private boolean hunt(Square current, Square prey, List<Square> checked, Stack<Square> trail){

        // Add me to trail and mark me as checked
        trail.push(current);
        checked.add(current);

        // First possibility - this the last step before the pray
        for (Square neighbour : current.neighbours){
            if (neighbour == prey){
                trail.push(prey);
                return true;
            }
        }

        // Second possibility - there is unchecked path from this square
        for (Square neighbour : current.neighbours){
            if (!checked.contains(neighbour)){
                return hunt(neighbour,prey,checked,trail);
            }
        }

        // Third possibility - all my neighbours are already checked, I am a dead end
        // Then : mark me as checked and remove from trail
        checked.add(current);
        trail.pop();

        // Start from the previous square
        return hunt(trail.pop(), prey, checked, trail);
    }


    private Square findSquare(int x, int y){
        return adjecencyList.get(y * board.getBoardSize() + x);
    }


    private void testAdjacency() {
        for (Square square : adjecencyList) {
            System.out.print(square.toString() + " neighbours: ");
            square.neighbours.forEach(e -> System.out.print(e.toString() + ", "));
            System.out.print("\n");
        }
    }

    public static <T> Queue<T> convertStackToQueue(Stack<T> stack){

        // create new queue
        Queue<T> queue = new LinkedList<>();

        //  fill queue
        while(!stack.isEmpty())
            queue.offer(stack.pop());

        return queue;
    }


    public class Square {

        public final int x;
        public final int y;
        private List<Square> neighbours;

        public Square(int x, int y) {
            this.x = x;
            this.y = y;
            neighbours = new ArrayList<>();
        }

        public void addNeighbour(Square sqr) {
            neighbours.add(sqr);
        }

        @Override
        public String toString() {
            return "x:" + this.x + " y:" + this.y;
        }
    }

}
