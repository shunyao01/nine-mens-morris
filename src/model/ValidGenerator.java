package model;

import controller.GameController;

import java.util.ArrayList;
import java.util.Random;

public class ValidGenerator {

    // retrieve valid nodes for set
    public static ArrayList<Node> getNodesForSet() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve board
        Board board = gameController.getBoard();

        // retrieve empty nodes
        ArrayList<Node> emptyNodes = new ArrayList<>();
        for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
            for (Node node : nodeTriplet.getNodes()) {
                if (node.isEmpty()) {
                    emptyNodes.add(node);
                }
            }
        }

        return emptyNodes;
    }

    // retrieve valid nodes for remove
    public static ArrayList<Node> getNodesForRemove() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve board
        Board board = gameController.getBoard();

        // retrieve removable nodes
        ArrayList<Node> removableNodes = new ArrayList<>();
        for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
            for (Node node : nodeTriplet.getNodes()) {
                if (node.isOccupied() &&
                    node.getTokenColor() != gameController.getCurrentPlayer().getTokenColor() &&
                    !board.isMillExists(node, board.getPlayerFromTokenColor(node.getTokenColor()))) {
                    removableNodes.add(node);
                }
            }
        }

        return removableNodes;
    }

    // retrieve valid nodes for move
    public static ArrayList<Node> getNodesForMove() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve board
        Board board = gameController.getBoard();

        // retrieve source node
        Node src = board.getSelectedNode();

        // retrieve movable or neighbor nodes depending on criteria below
        ArrayList<Node> nodes = new ArrayList<>();

        // either node has not been selected or select the wrong color node
        if (src == null || src.getTokenColor() != gameController.getCurrentPlayer().getTokenColor()) {
            for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
                for (Node node : nodeTriplet.getNodes()) {
                    // if the token color of the node is equal to the color of the current player
                    if (node.getTokenColor() == gameController.getCurrentPlayer().getTokenColor()) {
                        // check if any of the neighbor is empty
                        for (Node neighbor : node.getNeighbors()) {
                            // if one of them is empty, it means this node can be moved
                            if (neighbor.isEmpty()) {
                                nodes.add(node);
                            }
                        }
                    }
                }
            }
        } else if (src.getTokenColor() == gameController.getCurrentPlayer().getTokenColor()) {
            // retrieve neighbor nodes
            for (Node neighbor : src.getNeighbors()) {
                if (neighbor.isEmpty()) {
                    nodes.add(neighbor);
                }
            }
        }

        return nodes;
    }

    // retrieve valid nodes for jump
    public static ArrayList<Node> getNodesForJump() {
        // retrieve game controller
        GameController gameController = GameController.getInstance();

        // retrieve board
        Board board = gameController.getBoard();

        // retrieve source node
        Node src = board.getSelectedNode();

        // retrieve jump or empty nodes depending on criteria below
        ArrayList<Node> nodes = new ArrayList<>();

        // either node has not been selected or select the wrong color node
        if (src == null || src.getTokenColor() != gameController.getCurrentPlayer().getTokenColor()) {
            for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
                for (Node node : nodeTriplet.getNodes()) {
                    // if the token color of the node is equal to the color of the current player
                    if (node.getTokenColor() == gameController.getCurrentPlayer().getTokenColor()) {
                        nodes.add(node);
                    }
                }
            }
        } else if (src.getTokenColor() == gameController.getCurrentPlayer().getTokenColor()) {
            // retrieve empty nodes
            for (NodeTriplet nodeTriplet : board.getNodeTriplets()) {
                for (Node node : nodeTriplet.getNodes()) {
                    if (node.isEmpty()) {
                        nodes.add(node);
                    }
                }
            }
        }

        return nodes;
    }
}
