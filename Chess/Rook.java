package gameEntities;

import gameComponents.Alliance;
import gameComponents.Tile;

public class Rook extends Piece {

    	public Rook(Alliance pieceAlliance) {
        	super(pieceAlliance);
        	this.setPieceType(PieceType.ROOK);
        	this.setPieceImg("Images/wRook.png", "Images/bRook.png");
    	}

    	@Override
    	public boolean isValidMove(Tile startTile, Tile endTile) {
        	int changeY = Math.abs(endTile.getRow() - startTile.getRow());
        	int changeX = Math.abs(endTile.getColumn() - startTile.getColumn());

        	if(isAllianceOverlap(startTile, endTile))
            		return false;
        	else
            		return (changeX > 0 && changeY == 0) || (changeX == 0 && changeY > 0); 			//tests for linear movement
    	}

    	@Override
    	public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) {
            	int yChange = endTile.getRow() - startTile.getRow();
            	int xChange = endTile.getColumn() - startTile.getColumn();

            	int xDir = Integer.signum(xChange);
  	        int yDir = Integer.signum(yChange);

            	if(yDir == 0) { 										//horizontal obstruction
                	for (int i = 1; i < Math.abs(xChange); i++) {
                		if(tileMap[startTile.getRow()][startTile.getColumn() + i * xDir].isOccupied()) {
                        		return false;
                    		}
                	}
            	}
            	else {   											//vertical obstruction
                	for(int i = 1; i < Math.abs(yChange); i++) {
                    		if(tileMap[startTile.getRow() + i * yDir][startTile.getColumn()].isOccupied()) {
                        		return false;
                    		}
                	}
            	}
        	return true;
       	}
}