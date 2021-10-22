package gameEntities;

import gameComponents.Alliance;
import gameComponents.Tile;

public class Queen extends Piece {
    	public Queen(Alliance pieceAlliance) {
        	super(pieceAlliance);
        	this.setPieceType(PieceType.QUEEN);
        	this.setPieceImg("Images/wQueen.png", "Images/bQueen.png");
    	}
    
	@Override
    	public boolean isValidMove(Tile startTile, Tile endTile) {
        	int changeY = Math.abs(endTile.getRow() - startTile.getRow());
        	int changeX = Math.abs(endTile.getColumn() - startTile.getColumn());

        	if(isAllianceOverlap(startTile, endTile))
            		return false;
        	else if((changeX > 0 && changeY == 0) || (changeX == 0 && changeY > 0))   	//tests for diagonal and linear motion
            		return true;
        	else
            		return changeX == changeY;
    	}

    	@Override
    	public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) {
        	int yChange = endTile.getRow() - startTile.getRow();
        	int xChange = endTile.getColumn() - startTile.getColumn();

        	int xDir = Integer.signum(xChange);
        	int yDir = Integer.signum(yChange);

        	if(yDir == 0) {   								//horizontal path obstruction
            		for(int i = 1; i < Math.abs(xChange); i++) {
                		if(tileMap[startTile.getRow()][startTile.getColumn() + i * xDir].isOccupied()) {
                    			return false;
                		}
            		}
        	}
        	else if(xDir == 0){     							//vertical path obstruction
           		for(int i = 1; i < Math.abs(yChange); i++) {
                		if(tileMap[startTile.getRow() + i * yDir][startTile.getColumn()].isOccupied()) {
                    			return false;
                		}
            		}
        	}
        	else {   									//diagonal path obstruction
            		for(int i = 1; i < Math.abs(xChange); i++) {
                		if(tileMap[startTile.getRow() + i * yDir][startTile.getColumn() + i * xDir].isOccupied()) {
                    			return false;
                		}
            		}
        	}
        	return true;
    	}
}