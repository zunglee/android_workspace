package com.example.test.ninemenmorris;

/**
 * Created by apple on 11/05/16.
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends Activity {
    private final String TAG = "Main activity";

    private final String WHITE_INDEXES = "WHITE_INDEXES";
    private final String BLACK_INDEXES = "BLACK_INDEXES";
    private final String WHITE_INDEXES_SIZE = "WHITE_INDEXES_SIZE";
    private final String BLACK_INDEXES_SIZE = "BLACK_INDEXES_SIZE";
    private final String REMOVE_CHECKER = "REMOVE_CHECKER";
    private final String IS_WIN = "IS_WIN";
    private final String NEW_GAME = "NEW_GAME";

    Moves moves = new Moves();

    private TextView playerTurn;
    private ArrayList<ImageView> whiteCheckers;
    private ArrayList<ImageView> blackCheckers;
    private ArrayList<FrameLayout> higBoxAreas;
    private ImageView selectedChecker;
    private FrameLayout areaToMoveTo;
    private HashMap<ImageView, Integer> checkerPositions;

    private boolean hasSelectedChecker = false;
    private boolean removeNextChecker = false;
    private boolean isWin = false;
    private boolean newGame = true;

    private SharedPreferences pref;
    private SharedPreferences.Editor edit;

    private ArrayList<String> whiteIndexes = new ArrayList<String>();
    private ArrayList<String> blackIndexes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = this.getSharedPreferences("com.ninemensmorris", Context.MODE_PRIVATE);
        edit = pref.edit();

        selectedChecker = null;
        areaToMoveTo = null;
        checkerPositions = new HashMap<ImageView, Integer>();
        playerTurn = (TextView) findViewById(R.id.TurnText);

        whiteCheckers = new ArrayList<ImageView>();
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker1));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker2));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker3));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker4));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker5));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker6));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker7));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker8));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker9));

        blackCheckers = new ArrayList<ImageView>();
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker1));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker2));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker3));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker4));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker5));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker6));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker7));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker8));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker9));

        higBoxAreas = new ArrayList<FrameLayout>();
        higBoxAreas.add((FrameLayout) findViewById(R.id.area1));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area2));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area3));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area4));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area5));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area6));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area7));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area8));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area9));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area10));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area11));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area12));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area13));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area14));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area15));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area16));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area17));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area18));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area19));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area20));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area21));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area22));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area23));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area24));

        for (ImageView v : whiteCheckers) {
            checkerPositions.put(v, 0);
            v.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (moves.getTurn() == Constants.WHITE && !isWin) {
                        selectChecker(v);
                    }
                }
            });
        }

        for (ImageView v : blackCheckers) {
            checkerPositions.put(v, 0);
            v.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (moves.getTurn() == Constants.BLACK && !isWin) {
                        selectChecker(v);
                    }
                }
            });
        }

        for (FrameLayout v : higBoxAreas) {
            v.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (hasSelectedChecker) {
                        int currentTurn = moves.getTurn();
                        areaToMoveTo = (FrameLayout) v;

                        int to = Integer.parseInt((String) areaToMoveTo.getContentDescription());
                        int from = checkerPositions.get(selectedChecker);
                        if (moves.checkIfMoveIsValid(from, to)) {
                            moveChecker(currentTurn);

                            checkerPositions.put((ImageView) selectedChecker, Integer.parseInt((String) areaToMoveTo.getContentDescription()));

                            removeNextChecker = moves.canRemove(to);

                            selectedChecker.setAlpha(1.0f);

                            hasSelectedChecker = false;
                            selectedChecker = null;
                            checkerPositions.put((ImageView) selectedChecker, Integer.parseInt((String) areaToMoveTo.getContentDescription()));

                            removeNextChecker = moves.canRemove(to);

                            if (removeNextChecker) {
                                if (currentTurn == Constants.BLACK) {
                                    playerTurn.setText("Remove White");
                                } else {
                                    playerTurn.setText("Remove Black");
                                }
                            } else {
                                if (currentTurn == Constants.BLACK) {
                                    playerTurn.setText("White turn");
                                } else {
                                    playerTurn.setText("Black turn");
                                }
                            }

                            isWin = moves.isItAWin(moves.getTurn());
                            if(isWin) {
                                if (moves.getTurn() == Constants.BLACK) {
                                    playerTurn.setText("White wins!");
                                } else {
                                    playerTurn.setText("Black wins!");
                                }

                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        moves.savePref(edit);
        edit.putInt(WHITE_INDEXES_SIZE, whiteIndexes.size());
        edit.putInt(BLACK_INDEXES_SIZE, blackIndexes.size());

        for(int i = 0; i < whiteIndexes.size(); i++) {
            edit.putString(WHITE_INDEXES+i, whiteIndexes.get(i));
        }
        for(int i = 0; i < blackIndexes.size(); i++) {
            edit.putString(BLACK_INDEXES+i, blackIndexes.get(i));
        }
        edit.putBoolean(IS_WIN, isWin);
        edit.putBoolean(REMOVE_CHECKER, removeNextChecker);
        edit.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        newGame = pref.getBoolean(NEW_GAME, false);
        edit.putBoolean(NEW_GAME, false);
        if(!newGame) {
            moves.restorePref(pref);
            int whiteSize = pref.getInt(WHITE_INDEXES_SIZE, 0);
            int blackSize = pref.getInt(BLACK_INDEXES_SIZE, 0);
            for(int i = 0; i < whiteSize; i++) {
                whiteIndexes.add(pref.getString(WHITE_INDEXES+i, ""));
            }
            for(int i = 0; i < blackSize; i++) {
                blackIndexes.add(pref.getString(BLACK_INDEXES+i, ""));
            }

            isWin = pref.getBoolean(IS_WIN, false);
            removeNextChecker = pref.getBoolean(REMOVE_CHECKER, false);
            restore();
        }
    }

    private void restore() {
        int white = 0;
        int black = 0;
        for(int i = 1; i < 25; i ++) {
            int color = moves.fieldColor(i);
            int index = 0;
            ViewGroup parent = null;
            if(color == Constants.WHITE) {
                index = Integer.parseInt(whiteIndexes.get(white));
                white++;
                parent = ((ViewGroup)findViewById(R.id.whiteCheckerArea));
            } else if(color == Constants.BLACK) {
                index = Integer.parseInt(blackIndexes.get(black));
                black++;
                parent = ((ViewGroup)findViewById(R.id.blackCheckerArea));
            }
            if(parent != null) {
                ImageView checker = setPlaceHolder(index, parent);
                ((ViewGroup) findViewById(R.id.board)).addView(checker);
                int areaId = getResources().getIdentifier("area" + i, "id", this.getPackageName());
                checker.setLayoutParams(findViewById(areaId).getLayoutParams());
                checkerPositions.put(checker, i);
            }
            if (removeNextChecker) {
                if (moves.getTurn() == Constants.WHITE) {
                    playerTurn.setText("Remove White");
                } else {
                    playerTurn.setText("Remove Black");
                }
            } else {
                if (moves.getTurn() == Constants.WHITE) {
                    playerTurn.setText("White turn");
                } else {
                    playerTurn.setText("Black turn");
                }
            }
            if(isWin) {
                if (moves.getTurn() == Constants.BLACK) {
                    playerTurn.setText("White wins!");
                } else {
                    playerTurn.setText("Black wins!");
                }
            }
        }
        while(white < whiteIndexes.size()) {
            setPlaceHolder(Integer.parseInt(whiteIndexes.get(white)), ((ViewGroup)findViewById(R.id.whiteCheckerArea)));
            white++;
        }
        while(black < blackIndexes.size()) {
            setPlaceHolder(Integer.parseInt(blackIndexes.get(black)), ((ViewGroup)findViewById(R.id.blackCheckerArea)));
            black++;
        }
    }

    private ImageView setPlaceHolder(int index, ViewGroup parent) {
        ImageView checker = (ImageView)parent.getChildAt(index);
        parent.removeViewAt(index);
        FrameLayout placeholder = (FrameLayout) getLayoutInflater().inflate(R.layout.layout_placeholder, parent, false);
        parent.addView(placeholder, index);
        return checker;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                finish();
                startActivity(getIntent());
                edit.putBoolean(NEW_GAME, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void moveChecker(int turn) {

        ImageView animChecker = null;
        final int[] locationChecker = {0, 0};
        final int[] locationArea = {0, 0};
        selectedChecker.getLocationOnScreen(locationChecker);
        areaToMoveTo.getLocationOnScreen(locationArea);

        ViewGroup parent = ((ViewGroup)selectedChecker.getParent());
        final int index = parent.indexOfChild(selectedChecker);

        if(turn == Constants.WHITE) {
            whiteIndexes.add(index + "");
            animChecker = (ImageView) getLayoutInflater().inflate(R.layout.anim_white_checker, parent, false);
        } else {
            blackIndexes.add(index + "");
            animChecker = (ImageView) getLayoutInflater().inflate(R.layout.anim_black_checker, parent, false);
        }

        if (parent != findViewById(R.id.board)) {
            parent.removeView(selectedChecker);
            parent.addView(animChecker, index);

            ((ViewGroup) findViewById(R.id.board)).addView(selectedChecker);

        } else {

            parent.addView(animChecker);
            animChecker.setLayoutParams(selectedChecker.getLayoutParams());
        }

        selectedChecker.setLayoutParams(areaToMoveTo.getLayoutParams());
        selectedChecker.setVisibility(View.INVISIBLE);

        if (parent != findViewById(R.id.board)) {
            FrameLayout placeholder = (FrameLayout) getLayoutInflater().inflate(R.layout.layout_placeholder, parent, false);
            parent.addView(placeholder, index);
        }

        parent.removeView(animChecker);
        selectedChecker.setVisibility(View.VISIBLE);
    }

    private void selectChecker(View v) {
        if (removeNextChecker) {
            if(moves.getTurn() == Constants.BLACK && moves.remove(checkerPositions.get(v), Constants.BLACK)) {

                blackCheckers.remove(v);
                removeNextChecker = false;
                ViewGroup parent = ((ViewGroup)v.getParent());
                parent.removeView(v);
                playerTurn.setText("Black turn");

                isWin = moves.isItAWin(Constants.BLACK);
                if (isWin) {
                    playerTurn.setText("White wins!");
                }
            }
            else if(moves.getTurn() == Constants.WHITE && moves.remove(checkerPositions.get(v), Constants.WHITE)) {
                whiteCheckers.remove(v);
                removeNextChecker = false;
                ViewGroup parent = ((ViewGroup)v.getParent());
                parent.removeView(v);
                playerTurn.setText("White turn");

                isWin = moves.isItAWin(Constants.WHITE);
                if (isWin) {
                    playerTurn.setText("Black wins!");
                }
            }

        }
        else if (!(checkerPositions.get(v) != 0 && checkerPositions.containsValue(0)) || (checkerPositions.get(v) == 0)) {
            if (selectedChecker != null) {
                selectedChecker.setAlpha(1.0f);
            }

            if(selectedChecker == v) {
                hasSelectedChecker = false;
                selectedChecker = null;
                return;
            }
            hasSelectedChecker = true;
            selectedChecker = (ImageView) v;
            selectedChecker.setAlpha(0.5f);
        }
    }
}
