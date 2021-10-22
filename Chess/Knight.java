package gameEntities;

import gameComponents.Alliance;
import gameComponents.Tile;

public class Knight extends Piece {

    	public Knight(Alliance pieceAlliance) {
        	super(pieceAlliance);
        	this.setPieceType(PieceType.KNIGHT);
        	this.setPieceImg("Images/wKnight.png", "Images/bKnight.png");
    	}

	@Override
    	public boolean isValidMove(Tile startTile, Tile endTile) {
        	int changeY = Math.abs(endTile.getRow() - startTile.getRow());
        	int changeX = Math.abs(endTile.getColumn() - startTile.getColumn());

        	//checks for overlap
        	if(isAllianceOverlap(startTile, endTile))
            		return false;
        	else
            		return (changeX == 2 && changeY == 1) || (changeX == 1 && changeY == 2);  			//motion for knight
    	}

    	@Override
    	//always true since the knight can jump over pieces
    	public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) {
        	return true;
    	}
}
