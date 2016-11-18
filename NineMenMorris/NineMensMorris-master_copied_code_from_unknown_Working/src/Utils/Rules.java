package Utils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/*
 * The game board positions
 *
 * 01           02           03
 *     04       05       06
 *         07   08   09
 * 10  11  12        13  14  15
 *         16   17   18
 *     19       20       21
 * 22           23           24
 * 
 */

public class Rules {
	private final String TAG = "Rules";
	private final String PLAYINGFIELD = "PLAYINGFIELD";
	private final String TURN = "TURN";
	private final String WHITE_MARKERS = "WHITE_MARKERS";
	private final String BLACK_MARKERS = "BLACK_MARKERS";
	
	private int[] playingfield;
	private int turn;
	//Markers not on the playing field
	private int blackMarkers;
	private int whiteMarkers;

	private final int EMPTY_FIELD = 0;

	public Rules() {
		playingfield = new int[25];
		blackMarkers = 9;
		whiteMarkers = 9;
		turn = Constants.WHITE; //Random who will begin?
	}

	/**
	 * Try to move the checker.
	 * @param from The position to move from.
	 * @param to The position to move to.
	 * @return True if the move was successful, else false is returned.
	 */
	public boolean validMove(int from, int to) {
		Log.i(TAG, "Trying to move : " + from + " - " + to);
		Log.i(TAG, "White markers in sideboard: " + whiteMarkers);
		Log.i(TAG, "Black markers in sideboard: " + blackMarkers);

		// Put a marker from "hand" to the board
		if(blackMarkers > 0 && turn == Constants.BLACK && playingfield[to] == EMPTY_FIELD) {
			playingfield[to] = Constants.BLACK;
			blackMarkers--;
			turn = Constants.WHITE;
			return true;
		}
		if(whiteMarkers > 0 && turn == Constants.WHITE  && playingfield[to] == EMPTY_FIELD) {
			playingfield[to] = Constants.WHITE;
			whiteMarkers--;
			turn = Constants.BLACK;
			return true;
		}

		//Not the right players turn
		if(playingfield[from] != turn) {
			return false;
		}

		//Not a valid move
		if(!isValidMove(from, to)) {
			return false;
		}

		// Move the marker to it's new position
		playingfield[to] = playingfield[from];
		playingfield[from] = EMPTY_FIELD;

		// Change turn
		if(turn == Constants.WHITE) {
			turn = Constants.BLACK;
		} else {
			turn = Constants.WHITE;
		}

		return true;
	}

	/**
	 * Is it a valid move?
	 * @param from The area the checker is at.
	 * @param to The area the checker wants to go.
	 * @return True if it's a valid move, else false is returned.
	 */
	public boolean isValidMove(int from, int to) {
		//The "to"-field needs to be empty
		if(playingfield[to] != EMPTY_FIELD)  {
			return false;
		}

		//If it is from the side board, all moves are valid.
		if(from == 0) {
			return true;
		}

		//If it is flying phase, all moves are valid.
		if(isItFlyingPhase(playingfield[from])) {
			return true;
		}

		//Can only move to it's neighbors.
		switch (to) {
		case 1:
			return (from == 10 || from == 2);
		case 2:
			return (from == 1 || from == 3 || from == 5 );
		case 3:
			return (from == 2 || from == 15);
		case 4:
			return (from == 5 || from == 11);
		case 5:
			return (from == 2 || from == 4 || from == 8 || from == 6);
		case 6:
			return (from == 5 || from == 14);
		case 7:
			return (from == 8 || from == 12);
		case 8:
			return (from == 5 || from == 7 || from == 9);
		case 9:
			return (from == 8 || from == 13);
		case 10:
			return (from == 1 || from == 11 || from == 22);
		case 11:
			return (from == 4 || from == 10 || from == 12 || from == 19);
		case 12:
			return (from == 7 || from == 11 || from == 16);
		case 13:
			return (from == 9 || from == 14 || from == 18);
		case 14:
			return (from == 6 || from == 13 || from == 15 || from == 21);
		case 15:
			return (from == 3 || from == 14 || from == 24);
		case 16:
			return (from == 12 || from == 17);
		case 17:
			return (from == 16 || from == 18 || from == 20);
		case 18:
			return (from == 13 || from == 17);
		case 19:
			return (from == 11 || from == 20);
		case 20:
			return (from == 17 || from == 19 || from == 21 || from == 23);
		case 21:
			return (from == 14 || from == 20);
		case 22:
			return (from == 10 || from == 23);
		case 23:
			return (from == 20 || from == 22 || from == 24);
		case 24:
			return (from == 15 || from == 23);
		}
		return false;
	}

	/**
	 * Check if the player is allowed to remove a checker from the other player. 
	 * @param partOfLine The position of the checker.
	 * @return True if the checker is part of a line, else return false.
	 */
	public boolean canRemove(int partOfLine) {
		// Check if the argument is part of a line on the board
		if(playingfield[partOfLine] == EMPTY_FIELD) {
			return false;
		}

		//All possible lines.
		if((partOfLine == 1 || partOfLine == 2 || partOfLine == 3) && (playingfield[1] == playingfield[2] && playingfield[2] == playingfield[3])) {
			return true;
		}
		if((partOfLine == 4 || partOfLine == 5 || partOfLine == 6) && (playingfield[4] == playingfield[5] && playingfield[5] == playingfield[6])) {
			return true;
		}
		if((partOfLine == 7 || partOfLine == 8 || partOfLine == 9) && (playingfield[7] == playingfield[8] && playingfield[8] == playingfield[9])) {
			return true;
		}
		if((partOfLine == 10 || partOfLine == 11 || partOfLine == 12) && (playingfield[10] == playingfield[11] && playingfield[11] == playingfield[12])) {
			return true;
		}
		if((partOfLine == 13 || partOfLine == 14 || partOfLine == 15) && (playingfield[13] == playingfield[14] && playingfield[14] == playingfield[15])) {
			return true;
		}
		if((partOfLine == 16 || partOfLine == 17 || partOfLine == 18) && (playingfield[16] == playingfield[17] && playingfield[17] == playingfield[18])) {
			return true;
		}
		if((partOfLine == 19 || partOfLine == 20 || partOfLine == 21) && (playingfield[19] == playingfield[20] && playingfield[20] == playingfield[21])) {
			return true;
		}
		if((partOfLine == 22 || partOfLine == 23 || partOfLine == 24) && (playingfield[22] == playingfield[23] && playingfield[23] == playingfield[24])) {
			return true;
		}
		if((partOfLine == 1 || partOfLine == 10 || partOfLine == 22) && (playingfield[1] == playingfield[10] && playingfield[10] == playingfield[22])) {
			return true;
		}
		if((partOfLine == 4 || partOfLine == 11 || partOfLine == 19) && (playingfield[4] == playingfield[11] && playingfield[11] == playingfield[19])) {
			return true;
		}
		if((partOfLine == 7 || partOfLine == 12 || partOfLine == 16) && (playingfield[7] == playingfield[12] && playingfield[12] == playingfield[16])) {
			return true;
		}
		if((partOfLine == 2 || partOfLine == 5 || partOfLine == 8) && (playingfield[2] == playingfield[5] && playingfield[5] == playingfield[8])) {
			return true;
		}
		if((partOfLine == 17 || partOfLine == 20 || partOfLine == 23) && (playingfield[17] == playingfield[20] && playingfield[20] == playingfield[23])) {
			return true;
		}
		if((partOfLine == 9 || partOfLine == 13 || partOfLine == 18) && (playingfield[9] == playingfield[13] && playingfield[13] == playingfield[18])) {
			return true;
		}
		if((partOfLine == 6 || partOfLine == 14 || partOfLine == 21) && (playingfield[6] == playingfield[14] && playingfield[14] == playingfield[21])) {
			return true;
		}
		if((partOfLine == 3 || partOfLine == 15 || partOfLine == 24) && (playingfield[3] == playingfield[15] && playingfield[15] == playingfield[24])) {
			return true;
		}
		return false;
	}

	/**
	 * Remove a marker from the position if it matches the color
	 * @param from The checker to be removed.
	 * @param color The color the checker should be if the remove is valid. 
	 * @return True if the removal was successful, else false is returned.
	 */
	public boolean remove(int from, int color) {
		if (playingfield[from] == color) {
			playingfield[from] = EMPTY_FIELD;
			return true;
		} else
			return false;
	}

	/**
	 * Check if color 'color' has lost
	 * @param color The color which may have lost.
	 * @return True if color has lost, else false is returned.
	 */
	public boolean isItAWin(int color) {
		//A player can't win if it is checker left on the sideboard.
		if(whiteMarkers > 0 || blackMarkers > 0) {
			return false;
		}

		//color lost if there is no valid moves
		if(!hasValidMoves(color)) {
			return true;
		}

		//Does the color have less then 3 checkers left?
		int count = 0;
		for(int i : playingfield) {
			if(i == color) {
				count++;
			}
		}
		return (count < 3);
	}

	private boolean hasValidMoves(int color) {
		for(int i = 0; i < 24; i++) {
			if(playingfield[i+1] == color) {
				Log.i(TAG, "found color: " + color);
				for(int j = 0; j < 24; j++) {
					if(isValidMove(i+1, j+1)) {
						Log.i(TAG, "Has valid moves");
						return true;
					}
				}
			}
		}
		Log.i(TAG, "Doesn't have valid moves");
		return false;
	}

	/**
	 * 
	 * @param color The color which may be in the flying phase.
	 * @return True if it has exactly 3 checkers left, else return false.
	 */
	private boolean isItFlyingPhase(int color) {
		int count = 0;
		for(int i : playingfield) {
			if(i == color) {
				count++;
			}
		}
		return (count == 3);
	}

	/**
	 * 
	 * @param field The field to be checked.
	 * @return The color the checker on a field is.
	 */
	public int fieldColor(int field) {
		return playingfield[field];
	}

	/**
	 * 
	 * @return The player whos turn it is.
	 */
	public int getTurn() {
		return turn;
	}

	public void savePref(SharedPreferences.Editor instance) {
		for(int i = 0; i < playingfield.length; i++) {
			instance.putInt(PLAYINGFIELD+i, playingfield[i]);
		}
		
		instance.putInt(TURN, turn);
		instance.putInt(WHITE_MARKERS, whiteMarkers);
		instance.putInt(BLACK_MARKERS, blackMarkers);
		instance.commit();
	}
	
	public void restorePref(SharedPreferences instance) {
		for(int i = 0; i < playingfield.length; i++) {
			playingfield[i] = instance.getInt(PLAYINGFIELD+i, EMPTY_FIELD);
			Log.i(TAG, "Field " + i + " " + playingfield[i]);
		}
		

		turn = instance.getInt(TURN, Constants.WHITE);
		whiteMarkers = instance.getInt(WHITE_MARKERS, 9);
		blackMarkers = instance.getInt(BLACK_MARKERS, 9);
		Log.i(TAG, whiteMarkers + "");
		Log.i(TAG, blackMarkers + "");
	}
}