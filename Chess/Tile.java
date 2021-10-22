package gameComponents;

import gameEntities.Pawn;
import gameEntities.Piece;

import javax.swing.*;

public class Tile extends JButton {
    
    	private Piece piece;
    	private int row;
    	private int column;

    	public Tile(int row, int column, Piece piece) {
        	this.row = row;
        	this.column = column;
        	this.piece = piece;
        	this.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    	}

    	public boolean isOccupied() {
        	return piece != null;
    	}

	public void displayPiece() {
        	if(isOccupied())
            		this.setIcon(piece.getPieceImg());
        	else
            		this.setIcon(null);
    	}

    	public int getRow() {
        	return this.row;
    	}

    	public int getColumn() {
        	return this.column;
    	}

    	public Piece getPiece() {
        	return this.piece;
    	}

    	public Pawn getPawn() {
        	return (Pawn) this.piece;
    	}

    	public void setPiece(Piece newPiece) {
        	this.piece = newPiece;
    	}
}