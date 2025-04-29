package model;

import controller.GameController;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer extends Player{


    private Node executedNode;

    /**
     * Constructor
     * @param tokenColor the token color of the human player
     */
    public ComputerPlayer(TokenColor tokenColor) {
        super(tokenColor);
    }

    @Override
    public boolean set() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve node triplets from board
        Board board = gameController.getBoard();
        NodeTriplet[] nodeTriplets = board.getNodeTriplets();

        // retrieve empty node
        ArrayList <Node> emptyNodes = new ArrayList<>();
        for(NodeTriplet nodeTriplet : board.getNodeTriplets()){
            for (Node node : nodeTriplet.getNodes()) {
                if (node.isEmpty()){
                    emptyNodes.add(node);
                }
            }
        }
        // get a random empty node from the empty nodes
        Random rand = new Random();
        Node randomEmptyNode = emptyNodes.get(rand.nextInt(emptyNodes.size()));

        // get set index
        int setIndex = randomEmptyNode.getIndexOnBoard();

        // search for node with targeted setIndex
        for (NodeTriplet nodeTriplet : nodeTriplets) {
            for (Node node : nodeTriplet.getNodes()) {
                int boardIndex = node.getIndexOnBoard();
                boolean indexEmpty = node.isEmpty();
                if (boardIndex==setIndex && indexEmpty) {
                    TokenColor playerColor = getTokenColor();
                    node.setTokenColor(playerColor);
                    updateTokensCountOnSet();
                    board.notifyObservers();
                    executedNode = node;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean move() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve node triplets from board
        Board board = gameController.getBoard();

        // retrieve occupied node
        ArrayList <Node> occupiedNodes = new ArrayList<>();
        for(NodeTriplet nodeTriplet : board.getNodeTriplets()){
            for (Node node : nodeTriplet.getNodes()) {
                // if the token color of the node is equal to the current player
                if(node.getTokenColor() == gameController.getCurrentPlayer().getTokenColor()){
                    // check if any of the neighbor is empty
                    for (Node neighbour : node.getNeighbors()){
                        // if one of them is empty, it means there is at least one empty neighbor
                        if (neighbour.isEmpty()){
                            occupiedNodes.add(node);
                        }
                    }
                }
            }
        }
        // get a random empty node from the empty nodes
        // retrieve src node
        Random rand = new Random();
        Node src = occupiedNodes.get(rand.nextInt(occupiedNodes.size()));

        // retrieve all empty neighbor nodes
        ArrayList <Node> emptyNeighborNodes = new ArrayList<>();
        for(Node neighbor : src.getNeighbors()){
            if (neighbor.isEmpty()){
                emptyNeighborNodes.add(neighbor);
            }
        }

        // get a random empty neighbor from the empty neighbors
        // retrieve dest node
        Node dest = emptyNeighborNodes.get(rand.nextInt(emptyNeighborNodes.size()));

        boolean destIsEmpty = dest.isEmpty();
        ArrayList<Node> srcNeighbors = src.getNeighbors();
        TokenColor srcTokenColor = src.getTokenColor();
        Player currentPlayer = gameController.getCurrentPlayer();
        TokenColor currentPlayerTokenColor = currentPlayer.getTokenColor();

        // move token from src to dest
        if (destIsEmpty && srcNeighbors.contains(dest) && srcTokenColor == currentPlayerTokenColor) {
            dest.setTokenColor(srcTokenColor);
            src.setTokenColor(null);
            board.setSelectedNode(null);
            board.notifyObservers();
            executedNode = dest;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove() {

        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve board
        Board board = gameController.getBoard();

        ArrayList <Node> removableTokens = new ArrayList<>();
        for(NodeTriplet nodeTriplet : board.getNodeTriplets()){
            for (Node node : nodeTriplet.getNodes()) {
                // if the token color of the node is equal to the current player
                if(node.isOccupied() &&  !(node.getTokenColor() == gameController.getCurrentPlayer().getTokenColor())){
                    if (!board.isMillExists(node, board.getPlayerFromTokenColor(node.getTokenColor()))){
                        removableTokens.add(node);
                    }
                }
            }
        }

        // Print all elements in the ArrayList
//        for (Node element : removableTokens) {
//            System.out.println("removeeeeeeeeeeeeeeeee" + element);
//        }


        // get a random empty node from the empty nodes
        // retrieve removeToken node
        Random rand = new Random();
        Node removeToken = removableTokens.get(rand.nextInt(removableTokens.size()));

        if (removeToken != null) {
            // get remove index
            int removeIndex = removeToken.getIndexOnBoard();
            return board.removeTokenFromIndex(removeIndex);
        }

        return false;
    }

    @Override
    public boolean jump() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve node triplets from board
        Board board = gameController.getBoard();

        // retrieve occupied node
        ArrayList <Node> occupiedNodes = new ArrayList<>();
        for(NodeTriplet nodeTriplet : board.getNodeTriplets()){
            for (Node node : nodeTriplet.getNodes()) {
                // if the token color of the node is equal to the current player
                if(node.getTokenColor() == gameController.getCurrentPlayer().getTokenColor()){
                    // check if any of the neighbor is empty
                    occupiedNodes.add(node);
                }
            }
        }

        // get a random occupied node from the occupied nodes
        // retrieve src node
        Random rand = new Random();
        Node src = occupiedNodes.get(rand.nextInt(occupiedNodes.size()));

        // retrieve all empty neighbor nodes
        ArrayList <Node> emptyNodes = new ArrayList<>();
        for(NodeTriplet nodeTriplet : board.getNodeTriplets()){
            for (Node node : nodeTriplet.getNodes()) {
                if (node.isEmpty()){
                    // check if the node is empty, then add to the array list
                    emptyNodes.add(node);
                }
            }
        }

        // get a random empty node from the empty nodes
        // retrieve src node
        Node dest = emptyNodes.get(rand.nextInt(emptyNodes.size()));

        boolean destIsEmpty = dest.isEmpty();
        TokenColor srcTokenColor = src.getTokenColor();
        Player currentPlayer = gameController.getCurrentPlayer();
        TokenColor currentPlayerTokenColor = currentPlayer.getTokenColor();

        // move token from src to dest
        if (destIsEmpty && src.getTokenColor() == currentPlayerTokenColor) {
            dest.setTokenColor(srcTokenColor);
            src.setTokenColor(null);
            gameController.getBoard().setSelectedNode(null);
            gameController.getBoard().notifyObservers();
            executedNode = dest;
            return true;
        }
        return false;
    }

    @Override
    public boolean isComputer() {
        return true;
    }

    public void setExecutedNode(Node newExecutedNode){
        executedNode = newExecutedNode;
    }

    public Node getExecutedNode(){
        return executedNode;
    }

}
