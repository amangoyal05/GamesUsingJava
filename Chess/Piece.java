package gameEntities;

import gameComponents.Alliance;
import gameComponents.Tile;

import javax.swing.*;

public abstract class Piece {
    	private Alliance pieceAlliance;
    	private PieceType pieceType;
    	private ImageIcon pieceImg;

    	public Piece(Alliance pieceAlliance) {
        	this.pieceAlliance = pieceAlliance;
        	this.pieceImg = null;
    	}

    	public abstract boolean isValidMove(Tile startTile, Tile endTile);

    	public abstract boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap);

    	public ImageIcon getPieceImg() {
        	return pieceImg;
    	}

	public void setPieceType(PieceType pieceType) {
        	this.pieceType = pieceType;
    	}

    	public PieceType getPieceType() {
        	return this.pieceType;
    	}
    
	public void setPieceImg(String imgWFileName, String imgBFileName) {
        	if (pieceAlliance == Alliance.WHITE)
            		this.pieceImg = new ImageIcon(imgWFileName);
        	else
            		this.pieceImg = new ImageIcon(imgBFileName);
    	}

    	public boolean isAllianceOverlap(Tile startTile, Tile endTile) {
        	return (endTile.isOccupied() && startTile.isOccupied() && endTile.getPiece().getPieceAlliance() == startTile.getPiece().getPieceAlliance());
    	}

    	public Alliance getPieceAlliance() {
        	return pieceAlliance;
    	}
}
