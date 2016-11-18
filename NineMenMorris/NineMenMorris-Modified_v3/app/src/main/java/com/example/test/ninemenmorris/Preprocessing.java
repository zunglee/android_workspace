package com.example.test.ninemenmorris;

/**
 * Created by apple on 11/10/16.
 */
public class Preprocessing {

  //marking the valid position for given position;

  private int validMove[][] = new int[Constants.BOARD_LENGTH][Constants.BOARD_WIDTH];

  public Preprocessing(){
    //
    for(int i=0 ;i< Constants.BOARD_LENGTH ;i++){
      for(int j=0 ; j<Constants.BOARD_WIDTH; j++){
        this.validMove[i][j] =0;
      }
    }
//1
    this.validMove[0][9] =1;
    this.validMove[0][1] =1;
//2
    this.validMove[1][0] =1;
    this.validMove[1][2] =1;
    this.validMove[1][4] =1;
    //3
    this.validMove[2][1] =1;
    this.validMove[2][14] =1;
//4
    this.validMove[3][4] =1;
    this.validMove[3][10] =1;
//5
    this.validMove[4][1] =1;
    this.validMove[4][3] =1;
    this.validMove[4][7] =1;
    this.validMove[4][4] =1;
//6
    this.validMove[5][4] =1;
    this.validMove[5][13] =1;

// 7
    this.validMove[6][7] =1;
    this.validMove[6][11] =1;

// 8
    this.validMove[7][4] =1;
    this.validMove[7][6] =1;
    this.validMove[7][8] =1;
// 9
    this.validMove[8][12] =1;
    this.validMove[8][7] =1;
//
// 10
    this.validMove[9][10] =1;
    this.validMove[9][0] =1;
    this.validMove[9][12] =1;
// 11
    this.validMove[10][9] =1;
    this.validMove[10][3] =1;
    this.validMove[10][11] =1;
    this.validMove[10][18] =1;
//12
    this.validMove[11][6] =1;
    this.validMove[11][10] =1;
    this.validMove[11][15] =1;
//13
    this.validMove[12][8] =1;
    this.validMove[12][13] =1;
    this.validMove[12][17] =1;
//14
    this.validMove[13][5] =1;
    this.validMove[13][12] =1;
    this.validMove[13][14] =1;
    this.validMove[13][20] =1;
//15
    this.validMove[14][2] =1;
    this.validMove[14][13] =1;
    this.validMove[14][23] =1;
//16
    this.validMove[15][11] =1;
    this.validMove[15][16] =1;
// 17
    this.validMove[16][15] =1;
    this.validMove[16][19] =1;
    this.validMove[16][17] =1;
// 18
    this.validMove[17][12] =1;
    this.validMove[17][16] =1;
// 19
    this.validMove[18][10] =1;
    this.validMove[18][19] =1;
//20
    this.validMove[19][16] =1;
    this.validMove[19][18] =1;
    this.validMove[19][20] =1;
    this.validMove[19][22] =1;
//21
    this.validMove[20][13] =1;
    this.validMove[20][19] =1;

// 22
    this.validMove[21][9] =1;
    this.validMove[21][22] =1;
// 23
    this.validMove[22][21] =1;
    this.validMove[22][19] =1;
    this.validMove[16][23] =1;
//24
    this.validMove[23][14] =1;
    this.validMove[23][22] =1;
  }

  public int getValidLocation(int desti , int src){
    return this.validMove[desti][src];
  }

  public boolean getAdvantage(int index , int arr[]){
    if(arr[index] == 0) {
      return false;
    }

    if((index == 1 || index == 2 || index == 3) && (arr[1] == arr[2] && arr[2] == arr[3])) {
      return true;
    }
    if((index == 4 || index == 5 || index == 6) && (arr[4] == arr[5] && arr[5] == arr[6])) {
      return true;
    }
    if((index == 7 || index == 8 || index == 9) && (arr[7] == arr[8] && arr[8] == arr[9])) {
      return true;
    }
    if((index == 10 || index == 11 || index == 12) && (arr[10] == arr[11] && arr[11] == arr[12])) {
      return true;
    }
    if((index == 13 || index == 14 || index == 15) && (arr[13] == arr[14] && arr[14] == arr[15])) {
      return true;
    }
    if((index == 16 || index == 17 || index == 18) && (arr[16] == arr[17] && arr[17] == arr[18])) {
      return true;
    }
    if((index == 19 || index == 20 || index == 21) && (arr[19] == arr[20] && arr[20] == arr[21])) {
      return true;
    }
    if((index == 22 || index == 23 || index == 24) && (arr[22] == arr[23] && arr[23] == arr[24])) {
      return true;
    }
    if((index == 1 || index == 10 || index == 22) && (arr[1] == arr[10] && arr[10] == arr[22])) {
      return true;
    }
    if((index == 4 || index == 11 || index == 19) && (arr[4] == arr[11] && arr[11] == arr[19])) {
      return true;
    }
    if((index == 7 || index == 12 || index == 16) && (arr[7] == arr[12] && arr[12] == arr[16])) {
      return true;
    }
    if((index == 2 || index == 5 || index == 8) && (arr[2] == arr[5] && arr[5] == arr[8])) {
      return true;
    }
    if((index == 17 || index == 20 || index == 23) && (arr[17] == arr[20] && arr[20] == arr[23])) {
      return true;
    }
    if((index == 9 || index == 13 || index == 18) && (arr[9] == arr[13] && arr[13] == arr[18])) {
      return true;
    }
    if((index == 6 || index == 14 || index == 21) && (arr[6] == arr[14] && arr[14] == arr[21])) {
      return true;
    }
    if((index == 3 || index == 15 || index == 24) && (arr[3] == arr[15] && arr[15] == arr[24])) {
      return true;
    }
    return false;
  }

}
