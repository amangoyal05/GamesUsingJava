package gameComponents;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gameEntities.*;

public class Board extends JPanel implements ActionListener {
    
    	private static Tile[][] tileMap;          								//array to hold the tiles that make up the game board
    	private boolean firstClick;
    	private Piece tempPiece;
    	private Tile startTile;
    	private Tile endTile;
    	private final Color SQUARE_COLOUR_ONE = new Color(186, 202, 68);   					//Dark green colour
    	private final Color SQUARE_COLOUR_TWO = new Color(238, 238, 210);  					//light green colour

    	public Board() {
        	super();                    									//sets default values for the global variables
        	startTile = null;
        	endTile = null;
        	tempPiece = null;
        	firstClick = true;
        	//creates and 8 by 8 grid and a 2 dimensional array (8 by 8) of tiles
        	this.setLayout(new GridLayout(8, 8));
        	tileMap = new Tile[8][8];

        	//places the tiles in an alternating pattern
        	for(int i = 0; i < tileMap.length; i++) {                        				//row # (0, 7)
            		for(int j = 0; j < tileMap.length; j++) {                    				//Column # (0, 7)
                		tileMap[i][j] = new Tile(i, j, null);

                		//creates the checkered colour pattern
                		if((i + j) % 2 == 0)
                    			tileMap[i][j].setBackground(SQUARE_COLOUR_ONE);
                		else
                    			tileMap[i][j].setBackground(SQUARE_COLOUR_TWO);

                		//adds the tiles and listener
                		this.add(tileMap[i][j]);
                		tileMap[i][j].addActionListener(this);
            		}
        	}

        	resetBoard();    										//places chess pieces

        	this.setSize(500, 500);      									//sets panel size and visibility
        	this.setVisible(true);

    	}

    	public void refreshBoard() {
        	//loops through array and displays the pieces found on the tile
        	for(Tile[] tiles : tileMap) {
            		for(int j = 0; j < tileMap.length; j++) {
                		tiles[j].displayPiece();
                		resetColors();
            		}
        	}
    	}

    	public void resetColors() {
        	//loops through the array and sets colours in an alternating pattern
        	for(int i = 0; i < tileMap.length; i++) {
            		for(int j = 0; j < tileMap.length; j++) {
                		if((i + j) % 2 == 0)
                    			tileMap[i][j].setBackground(SQUARE_COLOUR_ONE);
                		else
                    			tileMap[i][j].setBackground(SQUARE_COLOUR_TWO);
            		}
        	}
    	}

	public void resetBoard() {
        	for(int i = 0; i < tileMap.length; i++) { //pawns
            		tileMap[1][i].setPiece(new Pawn(Alliance.BLACK));
            		tileMap[6][i].setPiece(new Pawn(Alliance.WHITE));

            		if(i == 0 || i == 7) { //rooks
                		tileMap[0][i].setPiece(new Rook(Alliance.BLACK));
                		tileMap[7][i].setPiece(new Rook(Alliance.WHITE));
            		}
            		else if(i == 1 || i == 6) { //knights
                		tileMap[0][i].setPiece(new Knight(Alliance.BLACK));
                		tileMap[7][i].setPiece(new Knight(Alliance.WHITE));
            		}
            		else if(i == 2 || i == 5) { //bishops
                		tileMap[0][i].setPiece(new Bishop(Alliance.BLACK));
                		tileMap[7][i].setPiece(new Bishop(Alliance.WHITE));
            		}
        	}

        	//sets the queens and kings
        	tileMap[0][3].setPiece(new Queen(Alliance.BLACK));
        	tileMap[7][3].setPiece(new Queen(Alliance.WHITE));
        	tileMap[0][4].setPiece(new King(Alliance.BLACK));
        	tileMap[7][4].setPiece(new King(Alliance.WHITE));

        	refreshBoard();  										//displays the different pieces
    	}

    	@Override
        public void actionPerformed(ActionEvent evt) {
        	//loops through tile objects checking for button clicks
        	for(int i = 0; i < tileMap.length; i++) {              						//loops through rows
            		for(int j = 0; j < tileMap.length; j++) {          					//loops through columns
                		if(tileMap[i][j] == evt.getSource()) {
                    			if(firstClick) {   							//sets start tile if user has not already selected a tile
                        			firstClick = false;
                        			startTile = tileMap[i][j];
                        			startTile.setBackground(new Color(255, 131, 117)); 		//sets colour to indicate starting tile
                        			moveOptions();
                    			}
                    			else { 									//sets end tile if user has selected a starting tile
                        			if(!(startTile.getRow() == i && startTile.getColumn() == j)) {
                            				firstClick = true;
                            				resetColors();   					//resets board colours removing red marker
                            				endTile = tileMap[i][j];
                        			}
                        			else {  							//if user selects the same tile twice than clear start and end tile selection
                            				clearSelection();
                        			}
                    			}
                		}
            		}
        	}
	}

	public void clearSelection () {
	        startTile = null;
        	endTile = null;
        	resetColors();
        	firstClick = true;
    	}

    	public void moveOptions() {
        	for(Tile[] tiles : tileMap) {
            		for(int j = 0; j < tileMap.length; j++) {
                		if(startTile.getPiece() != null && startTile.getPiece().isValidMove(startTile, tiles[j])) {
                    			if(startTile.getPiece().isValidPath(startTile, tiles[j], tileMap)) {
                        			//changes to yellow colour for all squares where piece is allowed to move
                        			tiles[j].setBackground(new Color(255, 251, 133, 207));
                    			}
                		}
            		}
        	}
    	}

    	public void move() {
        	tempPiece = endTile.getPiece();
        	setTilePiece(endTile, startTile.getPiece());
        	setTilePiece(startTile, null);
        	refreshBoard();
    	}

    	public void undoMove() {
        	setTilePiece(startTile, endTile.getPiece());
        	setTilePiece(endTile, tempPiece);
        	refreshBoard();
    	}

    	public Tile getTile(int row, int column) {
        	return tileMap[row][column];
    	}
    
	public void setTilePiece(Tile tile, Piece newPiece) {
        	tileMap[tile.getRow()][tile.getColumn()].setPiece(newPiece);
    	}

    	public boolean getFirstClick() {
        	return firstClick;
    	}

    	public Tile getStartTile() {
	        return startTile;
    	}

    	public Tile getEndTile() {
        	return endTile;
    	}

    	public Tile[][] getTileMap() {
        	return tileMap;
    	}
    
	public static void main(String[] args) {
        	JFrame testFrame = new JFrame("TESTING FRAME");

        	Board board = new Board();
        	testFrame.add(board);

        	testFrame.setSize(500, 500);
        	testFrame.setVisible(true);
        	testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	}
}