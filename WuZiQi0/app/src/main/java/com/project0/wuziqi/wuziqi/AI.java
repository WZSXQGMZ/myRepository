package com.project0.wuziqi.wuziqi;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class AI {
    private static int[][] board = new int[11][11];
    private static AI ai;
    private static int arrayX=0,arrayY=0;
    private final static int BLACK_POINT=1,WHITE_POINT=2,BLANK=0;

    private AI(){}
    public static AI getSingleton(){
        if(ai==null) {
            ai=new AI();
            return ai;
        }
        else
            return ai;
    }

    public int[] aiWork(int[][] board){
        this.board = board;
        int[] setPosition = new int[2];

        if(white4()){
            setPosition[0]=arrayX;
            setPosition[1]=arrayY;
            return setPosition;
        }
        else if(black4()){
            setPosition[0]=arrayX;
            setPosition[1]=arrayY;
            return setPosition;
        }
        else if(white3()){
            setPosition[0]=arrayX;
            setPosition[1]=arrayY;
            return setPosition;
        }
        else if(black3()){
            setPosition[0]=arrayX;
            setPosition[1]=arrayY;
            return setPosition;
        }
        else if(white3x3()){
            setPosition[0]=arrayX;
            setPosition[1]=arrayY;
            return setPosition;
        }
        else if(black3x3()){
            setPosition[0]=arrayX;
            setPosition[1]=arrayY;
            return setPosition;
        }
        else if(white2()){
            setPosition[0]=arrayX;
            setPosition[1]=arrayY;
            return setPosition;
        }
        else if(black2()){
            setPosition[0]=arrayX;
            setPosition[1]=arrayY;
            return setPosition;
        }
        else if(white1()){
            setPosition[0]=arrayX;
            setPosition[1]=arrayY;
            return setPosition;
        }
        else{
            black1();
            setPosition[0]=arrayX;
            setPosition[1]=arrayY;
            return setPosition;
        }

    }

    public boolean black4(){
        int i=0,j=0;
        int x=0,y=0;

        //判断黑方4子
        for(x=0;x<10;++x) {
            for (y = 0; y < 10; ++y) {
                if (board[x][y] == BLANK) {
                    int endpoint = 0;
                    int count = 0;
                    //判断竖直方向
                    for (i = x, j = y; j > 0; --j) {
                        if (board[i][j - 1] != BLACK_POINT) {
                            if (board[i][j - 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if(j==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; j < 10; ++j) {
                        if (board[i][j + 1] != BLACK_POINT) {
                            if (board[i][j + 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (j == 10) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 4 && endpoint<2) {
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断水平方向
                    for (i = x, j = y; i > 0; --i) {
                        if (board[i - 1][j] != BLACK_POINT) {
                            if (board[i - 1][j] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (i == 1) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i < 10; ++i) {
                        if (board[i + 1][j] != BLACK_POINT) {
                            if (board[i + 1][j] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (i == 10) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 4 && endpoint<2){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断左上-右下方向
                    for (i = x, j = y; i > 0 && j > 0; --i, --j) {
                        if (board[i - 1][j - 1] != BLACK_POINT) {
                            if (board[i - 1][j - 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==1 || i==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i < 10 && j < 10; ++i, ++j) {
                        if (board[i + 1][j + 1] != BLACK_POINT) {
                            if (board[i + 1][j + 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==10 || i==10){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 4 && endpoint<2){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断右上-左下方向
                    for (i = x, j = y; i < 10 && j > 0; ++i, --j) {
                        if (board[i + 1][j - 1] != BLACK_POINT) {
                            if (board[i + 1][j - 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==1 || i==10){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i > 0 && j < 10; --i, ++j) {
                        if (board[i - 1][j + 1] != BLACK_POINT) {
                            if (board[i - 1][j + 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==10 || i==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 4 && endpoint<2){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;
                }
            }
        }

        return false;
    }
    public boolean white4(){
        int i=0,j=0;
        int x=0,y=0;

        //判断白方4子
        for(x=0;x<10;++x) {
            for (y = 0; y < 10; ++y) {
                if (board[x][y] == BLANK) {
                    int endpoint = 0;
                    int count = 0;
                    //判断竖直方向
                    for (i = x, j = y; j > 0; --j) {
                        if (board[i][j - 1] != WHITE_POINT) {
                            if (board[i][j - 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if(j==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; j < 10; ++j) {
                        if (board[i][j + 1] != WHITE_POINT) {
                            if (board[i][j + 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (j == 10) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 4 && endpoint<2){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断水平方向
                    for (i = x, j = y; i > 0; --i) {
                        if (board[i - 1][j] != WHITE_POINT) {
                            if (board[i - 1][j] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (i == 1) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i < 10; ++i) {
                        if (board[i + 1][j] != WHITE_POINT) {
                            if (board[i + 1][j] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (i == 10) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 4 && endpoint<2){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断左上-右下方向
                    for (i = x, j = y; i > 0 && j > 0; --i, --j) {
                        if (board[i - 1][j - 1] != WHITE_POINT) {
                            if (board[i - 1][j - 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==1 || i==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i < 10 && j < 10; ++i, ++j) {
                        if (board[i + 1][j + 1] != WHITE_POINT) {
                            if (board[i + 1][j + 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==10 || i==10){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 4 && endpoint<2){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断右上-左下方向
                    for (i = x, j = y; i < 10 && j > 0; ++i, --j) {
                        if (board[i + 1][j - 1] != WHITE_POINT) {
                            if (board[i + 1][j - 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==1 || i==10){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i > 0 && j < 10; --i, ++j) {
                        if (board[i - 1][j + 1] != WHITE_POINT) {
                            if (board[i - 1][j + 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==10 || i==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 4 && endpoint<2){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;
                }
            }
        }

        return false;
    }
    public boolean black3(){
        int i=0,j=0;
        int x=0,y=0;

        //判断黑方3子
        for(x=0;x<10;++x) {
            for (y = 0; y < 10; ++y) {
                if (board[x][y] == BLANK) {
                    int endpoint = 0;
                    int count = 0;
                    //判断竖直方向
                    for (i = x, j = y; j > 0; --j) {
                        if (board[i][j - 1] != BLACK_POINT) {
                            if (board[i][j - 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if(j==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; j < 10; ++j) {
                        if (board[i][j + 1] != BLACK_POINT) {
                            if (board[i][j + 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (j == 10) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 3 && endpoint<1) {
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断水平方向
                    for (i = x, j = y; i > 0; --i) {
                        if (board[i - 1][j] != BLACK_POINT) {
                            if (board[i - 1][j] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (i == 1) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i < 10; ++i) {
                        if (board[i + 1][j] != BLACK_POINT) {
                            if (board[i + 1][j] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (i == 10) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 3 && endpoint<1){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断左上-右下方向
                    for (i = x, j = y; i > 0 && j > 0; --i, --j) {
                        if (board[i - 1][j - 1] != BLACK_POINT) {
                            if (board[i - 1][j - 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==1 || i==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i < 10 && j < 10; ++i, ++j) {
                        if (board[i + 1][j + 1] != BLACK_POINT) {
                            if (board[i + 1][j + 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==10 || i==10){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 3 && endpoint<1){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断右上-左下方向
                    for (i = x, j = y; i < 10 && j > 0; ++i, --j) {
                        if (board[i + 1][j - 1] != BLACK_POINT) {
                            if (board[i + 1][j - 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==1 || i==10){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i > 0 && j < 10; --i, ++j) {
                        if (board[i - 1][j + 1] != BLACK_POINT) {
                            if (board[i - 1][j + 1] == WHITE_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==10 || i==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 3 && endpoint<1){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;
                }
            }
        }
        return false;
    }
    public boolean white3(){
        int i=0,j=0;
        int x=0,y=0;

        //判断白方3子
        for(x=0;x<10;++x) {
            for (y = 0; y < 10; ++y) {
                if (board[x][y] == BLANK) {
                    int endpoint = 0;
                    int count = 0;
                    //判断竖直方向
                    for (i = x, j = y; j > 0; --j) {
                        if (board[i][j - 1] != WHITE_POINT) {
                            if (board[i][j - 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if(j==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; j < 10; ++j) {
                        if (board[i][j + 1] != WHITE_POINT) {
                            if (board[i][j + 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (j == 10) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 3 && endpoint<2){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断水平方向
                    for (i = x, j = y; i > 0; --i) {
                        if (board[i - 1][j] != WHITE_POINT) {
                            if (board[i - 1][j] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (i == 1) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i < 10; ++i) {
                        if (board[i + 1][j] != WHITE_POINT) {
                            if (board[i + 1][j] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else {
                            if (i == 10) {
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 3 && endpoint<2){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断左上-右下方向
                    for (i = x, j = y; i > 0 && j > 0; --i, --j) {
                        if (board[i - 1][j - 1] != WHITE_POINT) {
                            if (board[i - 1][j - 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==1 || i==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i < 10 && j < 10; ++i, ++j) {
                        if (board[i + 1][j + 1] != WHITE_POINT) {
                            if (board[i + 1][j + 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==10 || i==10){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 3 && endpoint<2){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;

                    //判断右上-左下方向
                    for (i = x, j = y; i < 10 && j > 0; ++i, --j) {
                        if (board[i + 1][j - 1] != WHITE_POINT) {
                            if (board[i + 1][j - 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==1 || i==10){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    for (i = x, j = y; i > 0 && j < 10; --i, ++j) {
                        if (board[i - 1][j + 1] != WHITE_POINT) {
                            if (board[i - 1][j + 1] == BLACK_POINT)
                                ++endpoint;
                            break;
                        } else{
                            if(j==10 || i==1){
                                ++endpoint;
                            }
                            ++count;
                        }
                    }
                    if (count == 3 && endpoint<2){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    count = 0;
                }
            }
        }

        return false;
    }
    public boolean black3x3(){
        int i=0,j=0;
        for(i=0;i<11;++i){
            for(j=0;j<11;++j){
                if(board[i][j]==BLANK
                        && couldBe5(i,j,BLACK_POINT)
                        && making33(i,j,BLACK_POINT)){
                    arrayX=i;
                    arrayY=j;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean white3x3(){
        int i=0,j=0;
        for(i=0;i<11;++i){
            for(j=0;j<11;++j){
                if(board[i][j]==BLANK
                        && couldBe5(i,j,WHITE_POINT)
                        && making33(i,j,WHITE_POINT)){
                    arrayX=i;
                    arrayY=j;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean white2(){
        int x=0,y=0;
        for(x=2;x<9;++x){
            for(y=2;y<9;++y){
                if(board[x][y]==BLANK){
                    if(board[x-1][y+1]==WHITE_POINT && board[x-2][y+2]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x-1][y-1]==WHITE_POINT && board[x-2][y-2]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y+1]==WHITE_POINT && board[x+2][y+2]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y-1]==WHITE_POINT && board[x+2][y-2]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y]==WHITE_POINT && board[x+2][y]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x][y+1]==WHITE_POINT && board[x][y+2]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x][y-1]==WHITE_POINT && board[x][y-2]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x-1][y]==WHITE_POINT && board[x-2][y]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean black2(){
        int x=0,y=0;
        for(x=2;x<9;++x){
            for(y=2;y<9;++y){
                if(board[x][y]==BLANK){
                    if(board[x-1][y+1]==BLACK_POINT && board[x-2][y+2]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x-1][y-1]==BLACK_POINT && board[x-2][y-2]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y+1]==BLACK_POINT && board[x+2][y+2]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y-1]==BLACK_POINT && board[x+2][y-2]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y]==BLACK_POINT && board[x+2][y]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x][y+1]==BLACK_POINT && board[x][y+2]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x][y-1]==BLACK_POINT && board[x][y-2]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x-1][y]==BLACK_POINT && board[x-2][y]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean white1(){
        int x=0;
        int y=0;
        for(x=1;x<10;++x){
            for(y=1;y<10;++y){
                if(board[x][y]==BLANK){
                    if(board[x-1][y+1]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x-1][y-1]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y+1]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y-1]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x][y+1]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x][y-1]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x-1][y]==WHITE_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean black1(){
        int x=0;
        int y=0;
        for(x=1;x<10;++x){
            for(y=1;y<10;++y){
                if(board[x][y]==BLANK){
                    if(board[x-1][y+1]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x-1][y-1]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y+1]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y-1]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x+1][y]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x][y+1]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x][y-1]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                    if(board[x-1][y]==BLACK_POINT){
                        arrayX=x;
                        arrayY=y;
                        return true;
                    }
                }
            }
        }
        return false;
    }


    //是否有机会连成5子
    public boolean couldBe5(final int x,final int y,final int color){
        int i=0,j=0;
        int count=1;

        //左右
        for(i=x,j=y;i>=0 && count<5;--i){
            if(board[i][j]==color || board[i][j]==BLANK){
                ++count;
            }
        }
        for(i=x,j=y;i<=10 && count<5;++i){
            if(board[i][j]==color || board[i][j]==BLANK){
                ++count;
            }
        }
        if(count==5){
            return true;
        }
        count=1;

        //上下
        for(i=x,j=y;j>=0 && count<5;--j){
            if(board[i][j]==color || board[i][j]==BLANK){
                ++count;
            }
        }
        for(i=x,j=y;j<=10 && count<5;++j){
            if(board[i][j]==color || board[i][j]==BLANK){
                ++count;
            }
        }
        if(count==5){
            return true;
        }
        count=1;

        //左下-右上
        for(i=x,j=y;i>=0 && j<=10 && count<5;--i,++j){
            if(board[i][j]==color || board[i][j]==BLANK){
                ++count;
            }
        }
        for(i=x,j=y;i<=10 && j>=0 && count<5;++i,--j){
            if(board[i][j]==color || board[i][j]==BLANK){
                ++count;
            }
        }
        if(count==5){
            return true;
        }
        count=1;

        //左上-右下
        for(i=x,j=y;i>=0 && j>=0 && count<5;--i,--j){
            if(board[i][j]==color || board[i][j]==BLANK){
                ++count;
            }
        }
        for(i=x,j=y;i<=10 && j<=10 && count<5;++i,++j){
            if(board[i][j]==color || board[i][j]==BLANK){
                ++count;
            }
        }
        if(count==5){
            return true;
        }
        count=1;

        return false;
    }
    public boolean making33(final int x,final int y,final int color){
        int lineCount=0;
        int count=0;
        int index1=0,index2=0,index3=0,index4=0;

        //左右
        for(int i=1;i<=3;++i){
            if(x<=1 || y<=1 || x>=9 || y>=9){
                i=0;
                break;
            }
            else if(x==2 || y==2 || y==8 || x==8){
                if(i==3)
                    break;
            }
            if(index1==0 && board[x-i][y]==color){
                index1=-i;
            }
            else if(index1!=0 && index1!=-i && index3==0 && board[x-i][y]==color){
                index3=-i;
            }
            if(index2==0 && board[x+i][y]==color){
                index2=i;
            }
            else if(index2!=0 && index2!=i && index4==0 && board[x+i][y]==color){
                index4=i;
            }
        }
        if( ((index2-index1)<=3 && index1*index2!=0)
                ||((index1-index3)<=3 && index1*index3!=0)
                ||((index4-index2)<=3 && index2*index4!=0)){
            ++lineCount;
        }
        index1=0;
        index2=0;
        index3=0;
        index4=0;

        //上下
        for(int i=1;i<=3;++i){
            if(x<=1 || y<=1 || x>=9 || y>=9){
                i=0;
                break;
            }
            else if(x==2 || y==2 || y==8 || x==8){
                if(i==3)
                    break;
            }
            if(index1==0 && board[x][y-i]==color){
                index1=-i;
            }
            else if(index1!=0 && index1!=-i && index3==0 && board[x][y-i]==color){
                index3=-i;
            }
            if(index2==0 && board[x][y+i]==color){
                index2=i;
            }
            else if(index2!=0 && index2!=i && index4==0 && board[x][y+i]==color){
                index4=i;
            }
        }
        if( ((index2-index1)<=3 && index1*index2!=0)
                ||((index1-index3)<=3 && index1*index3!=0)
                ||((index4-index2)<=3 && index2*index4!=0)){
            ++lineCount;
        }
        index1=0;
        index2=0;
        index3=0;
        index4=0;

        //左上-右下
        for(int i=1;i<=3;++i){
            if(x<=1 || y<=1 || x>=9 || y>=9){
                i=0;
                break;
            }
            else if(x==2 || y==2 || y==8 || x==8){
                if(i==3)
                    break;
            }
            if(index1==0 && board[x-i][y-i]==color){
                index1=-i;
            }
            else if(index1!=0 && index1!=-i && index3==0 && board[x-i][y-i]==color){
                index3=-i;
            }
            if(index2==0 && board[x+i][y+i]==color){
                index2=i;
            }
            else if(index2!=0 && index2!=i && index4==0 && board[x+i][y+i]==color){
                index4=i;
            }
        }
        if( ((index2-index1)<=3 && index1*index2!=0)
                ||((index1-index3)<=3 && index1*index3!=0)
                ||((index4-index2)<=3 && index2*index4!=0)){
            ++lineCount;
        }
        index1=0;
        index2=0;
        index3=0;
        index4=0;

        //左下-右上
        for(int i=1;i<=3;++i){
            if(x<=1 || y<=1 || x>=9 || y>=9){
                i=0;
                break;
            }
            else if(x==2 || y==2 || y==8 || x==8){
                if(i==3)
                    break;
            }
            if(index1==0 && board[x-i][y+i]==color){
                index1=-i;
            }
            else if(index1!=0 && index1!=-i && index3==0 && board[x-i][y+i]==color){
                index3=-i;
            }
            if(index2==0 && board[x+i][y-i]==color){
                index2=i;
            }
            else if(index2!=0 && index2!=i && index4==0 && board[x+i][y-i]==color){
                index4=i;
            }
        }
        if( ((index2-index1)<=3 && index1*index2!=0)
                ||((index1-index3)<=3 && index1*index3!=0)
                ||((index4-index2)<=3 && index2*index4!=0)){
            ++lineCount;
        }
        index1=0;
        index2=0;
        index3=0;
        index4=0;

        if(lineCount>1){
            return true;
        }
        else{
            return false;
        }

    }



}
