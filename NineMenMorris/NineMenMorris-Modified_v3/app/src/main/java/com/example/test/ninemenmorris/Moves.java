package com.example.test.ninemenmorris;

/**
 * Created by apple on 11/07/16.
 */

import android.content.SharedPreferences;


public class Moves {
    private final String TAG = "Moves";
    private final String PLAYINGFIELD = "PLAYINGFIELD";
    private final String TURN = "TURN";
    private final String WHITE_MARKERS = "WHITE_MARKERS";
    private final String BLACK_MARKERS = "BLACK_MARKERS";

    private int[] playingfield;
    private int turn;
    private int blackMarkers;
    private int whiteMarkers;

    public Preprocessing validLocation = new Preprocessing();

    private final int EMPTY_FIELD = 0;

    public Moves() {
        playingfield = new int[25];
        blackMarkers = 9;
        whiteMarkers = 9;
        turn = Constants.WHITE;
    }



    public boolean canMove(int from, int to) {
        if(playingfield[to] != EMPTY_FIELD)  {
            return false;
        }

        if(from == 0) {
            return true;
        }

        if(jumpMode(playingfield[from])) {
            return true;
        }
        if (validLocation.getValidLocation(to-1, from-1) == 1) {
            return true;
        }
        return false;
    }

    public boolean canRemove(int partOfLine) {
        return validLocation.getAdvantage(partOfLine ,this.playingfield);
    }

    public boolean remove(int from, int color) {
        if (playingfield[from] == color) {
            playingfield[from] = EMPTY_FIELD;
            return true;
        } else
            return false;
    }

    public boolean isItAWin(int color) {
        if(whiteMarkers > 0 || blackMarkers > 0) {
            return false;
        }

        if(!canFormTriplet(color)) {
            return true;
        }

        int count = 0;
        for(int i : playingfield) {
            if(i == color) {
                count++;
            }
        }
        return (count < 3);
    }

    private boolean canFormTriplet(int color) {
        for(int i = 0; i < 24; i++) {
            if(playingfield[i+1] == color) {
                for(int j = 0; j < 24; j++) {
                    if(canMove(i+1, j+1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean jumpMode(int color) {
        int count = 0;
        for(int i : playingfield) {
            if(i == color) {
                count++;
            }
        }
        return (count == 3);
    }

    public int fieldColor(int field) {
        return playingfield[field];
    }

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
        }


        turn = instance.getInt(TURN, Constants.WHITE);
        whiteMarkers = instance.getInt(WHITE_MARKERS, 9);
        blackMarkers = instance.getInt(BLACK_MARKERS, 9);
    }

    public boolean checkIfMoveIsValid(int from, int to) {

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

        if(playingfield[from] != turn) {
            return false;
        }

        if(!canMove(from, to)) {
            return false;
        }

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
}