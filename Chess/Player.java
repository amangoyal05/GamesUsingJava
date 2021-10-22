package gameComponents;

import gameEntities.Piece;

public class Player {
    
    	private Alliance playerAlliance;
    	private boolean isTurn;
    	private int score;

    	public Player (Alliance pAlliance, boolean isTurn) {
        	//sets default values for global variables
        	this.playerAlliance = pAlliance;
        	this.isTurn = isTurn;
        	this.score = 0;
    	}

	public void updateScore(Piece killedPiece) {
        	//assigns a score from 1,3,5,and 8 depending on the piece captured
        	switch(killedPiece.getPieceType()) {
            		case PAWN :
                		this.score += 1;
                		break;
            		case ROOK:
                		this.score += 5;
                		break;
            		case KNIGHT:
				this.score += 3;
				break;
            		case BISHOP:
                		this.score += 3;
                		break;
            		case QUEEN:
                		this.score += 8;
                		break;
            		default:
               			break;
        	}
    	}

    	public void fixScore(Piece revivedPiece) {
        	//subtracts the corresponding value (added previously) for the piece that is revived
        	if(revivedPiece != null) {
           		switch(revivedPiece.getPieceType()) {
                	case PAWN:
                    		this.score -= 1;
                    		break;
                	case ROOK:
                    		this.score -= 5;
                    		break;
                	case KNIGHT:
                    		this.score -= 3;
                    		break;
                	case BISHOP:
                    		this.score -= 3;
                    		break;
                	case QUEEN:
                    		this.score -= 8;
                    		break;
                	default:
                    		break;
            		}
        	}
    	}

    	public int getScore () {
        	return this.score;
    	}

    	public Alliance getPlayerAlliance () {
        	return this.playerAlliance;
    	}

    	public boolean isTurn() {
        	return isTurn;
    	}

    	public void setTurn (boolean newTurnValue) {
        	this.isTurn = newTurnValue;
    	}
}