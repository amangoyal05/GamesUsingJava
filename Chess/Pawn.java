package gameEntities;

import gameComponents.Alliance;
import gameComponents.Tile;

public class Pawn extends Piece {

    	private boolean firstMove;

    	public Pawn (Alliance pieceAlliance) {
        	super(pieceAlliance);
        	this.setPieceType(PieceType.PAWN);
        	this.setPieceImg("Images/wPawn.png", "Images/bPawn.png");
        	firstMove = true;
    	}
    
	public void setFirstMove(boolean newValue) {
        	this.firstMove = newValue;
    	}

	@Override
    	public boolean isValidMove(Tile startTile, Tile endTile) {
        	int moveSpaces;

        	//finds change in position for rows (Y) and columns (X)
        	//since the pawn can only move forward row difference (change in x) is not an absolute value
        	int changeY = endTile.getRow() - startTile.getRow();
        	int changeX = Math.abs(endTile.getColumn() - startTile.getColumn());

        	//pawn can move 2 spaces on first move and 1 on every move thereafter
        	if(firstMove)
            		moveSpaces = 2;
        	else
            		moveSpaces = 1;

        	//to insure pawns can only move forward white pieces have their moveSpace
        	//value turned into a negative integer
        	if(getPieceAlliance() == Alliance.WHITE)
            		moveSpaces *= -1;

        	//checks if alliance piece is already on destination square
        	if(isAllianceOverlap(startTile, endTile))
            		return false;
        	//if piece is white then change in Y must range from 0 to negative moveSpaces. The piece can only move diagonally by one row (change y) amd not 2 (2 % 2 = 0) and (1 % 2 = 1)
        	else if(getPieceAlliance() == Alliance.WHITE && (changeY >= moveSpaces && changeY < 0) && changeX <= Math.abs(changeY % 2))
            		return true;
        	//if the piece is black change in Y must range from 0 to positive moveSpaces
        	else
            		return getPieceAlliance() == Alliance.BLACK && (changeY <= moveSpaces && changeY > 0) && changeX <= Math.abs(changeY % 2);
    	}

    	@Override
    	public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) {
        	int yChange = endTile.getRow() - startTile.getRow();
        	int xChange = endTile.getColumn() - startTile.getColumn();

        	int yDir = Integer.signum(yChange);

        	if(Math.abs(yChange) == 2 && xChange == 0) {
            		//stops pawn moving forward if it has to jump over a piece
            		if(tileMap[startTile.getRow() + yDir][startTile.getColumn()].isOccupied()) {
                		return false;
            	}
            	//stops pawn from attacking enemy piece in front of it (2 steps in front)
            	else
                	return !endTile.isOccupied() || endTile.getPiece().getPieceAlliance() == startTile.getPiece().getPieceAlliance();
        	}
		else if(Math.abs(yChange) == 1 && xChange == 0) {
			//stops pawn from attacking enemy piece in front of it (1 step in front)
            		return !endTile.isOccupied() || endTile.getPiece().getPieceAlliance() == startTile.getPiece().getPieceAlliance();
        	}
        	else if(Math.abs(yChange) == 1 && Math.abs(xChange) == 1) {
            		return endTile.isOccupied() && endTile.getPiece().getPieceAlliance() != startTile.getPiece().getPieceAlliance();
        	}
        	return true;
    	}
}
