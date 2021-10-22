package gameEntities;

import gameComponents.Alliance;
import gameComponents.Tile;

public class Bishop extends Piece {
    	public Bishop(Alliance pieceAlliance) {
        	super(pieceAlliance);
        	this.setPieceType(PieceType.BISHOP);
        	this.setPieceImg("Images/wBishop.png",  "Images/bBishop.png");
    	}

	@Override
    	public boolean isValidMove(Tile startTile, Tile endTile) {
        	//calculates change in x and y values
        	int changeY = Math.abs(endTile.getRow() - startTile.getRow());
        	int changeX = Math.abs(endTile.getColumn() - startTile.getColumn());

        	//checks if same coloured piece occupies destination tile
        	if (isAllianceOverlap(startTile, endTile))
            		return false;
        	else
            		return changeX == changeY; 						//change in x and y values is the same for diagonal motion
    	}

    	@Override
    	public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) {
        	int yChange = endTile.getRow() - startTile.getRow();
        	int xChange = endTile.getColumn() - startTile.getColumn();

        	int xDir = Integer.signum(xChange);
        	int yDir = Integer.signum(yChange);

        	//if tile is between start and end is occupied than path is not valid
        	for (int i = 1; i < Math.abs(xChange); i++) {
            		if (tileMap[startTile.getRow() + i * yDir][startTile.getColumn() + i * xDir].isOccupied()) {
                		return false;
            		}
        	}
        	return true;
    	}
}