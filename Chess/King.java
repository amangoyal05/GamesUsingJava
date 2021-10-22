package gameEntities;

import gameComponents.Alliance;
import gameComponents.Tile;

import java.util.ArrayList;

public class King extends Piece {
    
    	private Tile checkedBy;
    	private ArrayList<Tile> checkedPath = new ArrayList<>();

    	public King (Alliance pieceAlliance) {
        	super (pieceAlliance);
        	this.setPieceType(PieceType.KING);
        	this.checkedBy = null;
        	this.setPieceImg("Images/wKing.png", "Images/bKing.png");
    	}

    	@Override
    	public boolean isValidMove(Tile startTile, Tile endTile) {
        	int changeY = Math.abs(endTile.getRow() - startTile.getRow());
        	int changeX = Math.abs(endTile.getColumn() - startTile.getColumn());

        	if(isAllianceOverlap(startTile, endTile))
            		return false;
        	else
            		return changeX <= 1 && changeY <= 1; //can only move one tile
    	}

    	public Tile findKing(Tile[][] tileMap) {
        	//loops until king is found
        	for(Tile[] tiles : tileMap) {
            		for(int j = 0; j < tileMap.length; j++) {
                		if(tiles[j].isOccupied() && tiles[j].getPiece().getPieceType() == PieceType.KING && tiles[j].getPiece().getPieceAlliance() == this.getPieceAlliance()) {
                    			return tiles[j];
                		}
            		}
        	}
        	return null;
    	}

    	@Override
    	public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) {
        	Piece temp = endTile.getPiece();
        	endTile.setPiece(startTile.getPiece());
        	if(!this.inCheck(tileMap, endTile)) {
            		endTile.setPiece(temp);
            		return true;
        	}
        	endTile.setPiece(temp);
        	return false;
    	}

    	public boolean inCheck(Tile[][] tileMap, Tile kingTile) {
        	for(Tile[] tiles : tileMap) {
            		for(int j = 0; j < tileMap.length; j++) {
                		if(tiles[j].isOccupied() && tiles[j].getPiece().getPieceAlliance() != this.getPieceAlliance()) {
                    			if(tiles[j].getPiece() instanceof Pawn) { 				//tests for diagonal pawn movement (pawns only kill diagonally)
                        			if(tiles[j].getPiece().isValidMove(tiles[j], kingTile))  {
                            				if(Math.abs(kingTile.getColumn() - tiles[j].getColumn()) > 0) {
                                				this.checkedBy = tiles[j];
                                				return true;
                            				}
                        			}
                    			}
                    			//if king is in path of another piece than it is in check
                    			else if(tiles[j].getPiece().isValidMove(tiles[j], kingTile) && tiles[j].getPiece().isValidPath(tiles[j], kingTile, tileMap)) {
                        			this.checkedBy = tiles[j];
                        			return true;
                    			}
                		}
            		}
        	}
        	return false;
    	}

    	public boolean checkMate (Tile[][] tileMap) {
        	Tile kingTile = findKing(tileMap);
        	int rowDiff = kingTile.getRow() - checkedBy.getRow();
       		int colDiff = kingTile.getColumn() - checkedBy.getColumn();
        	int tileAmt = Math.max(Math.abs(rowDiff), Math.abs(colDiff));

        	for(Tile[] tiles: tileMap) { 									//checks if the king can move
            		for(int j = 0; j < tileMap.length; j++) {
                		if(kingTile.getPiece() != null && kingTile.getPiece().isValidMove(kingTile, tiles[j]) && kingTile.getPiece().isValidPath(kingTile, tiles[j], tileMap)) {
                    			return false;
                		}
            		}
        	}

        	try {												//adds path between king and the piece that is the cause for check
            		for(int i = 0; i < tileAmt; i++) {
                		checkedPath.add(tileMap[checkedBy.getRow() + i * Integer.signum(rowDiff)][checkedBy.getColumn() + i * Integer.signum(colDiff)]);
            		}
        	} catch (Exception e) {
            		return true;
        	}

        	//loops through and checks if any piece can block the check
        	for(Tile[] tiles : tileMap) {
            		for(int j = 0; j < tileMap.length; j++) {
                		for(Tile[] value : tileMap) {
                    			for(int c = 0; c < tileMap.length; c++) {
                        			if(tiles[j].getPiece() != null && tiles[j].getPiece().getPieceAlliance() == this.getPieceAlliance() && tiles[j].getPiece().isValidMove(tiles[j], value[c]) && tiles[j].getPiece().isValidPath(tiles[j], value[c], tileMap)) {
                            				for(Tile pathTile : checkedPath) {
                                				if(value[c] == pathTile) { 			//tests if piece is able to move into path
                                    					checkedPath.clear();
                                    					return false;
                                				}
                            				}
                        			}
                    			}
                		}
            		}
        	}
        	checkedPath.clear();
        	return true;
    	}
}