package com.project0.wuziqi.wuziqi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Xuhan on 2016/5/18 0018.
 */
public class Board extends View{

    protected final static int GAME_RUN = 0;
    protected final static int GAME_OVER=1;
    protected final static int GAME_PAUSE=2;
    protected final static int PVP = 0;
    protected final static int PVE =1;
    protected final static int BLACK_TURN = 1;
    protected final static int WHITE_TURN =2;

    protected static final int row = 11, col = 11;
    int i = 0, j = 0;
    private static int whoseTurn = 1;
    private static int arrayX=0,arrayY=0;
    private static int board[][] = new int[row][col];
    private static int pieceCount = 0;
    private static int judge = 0;
    private static int gameState = GAME_RUN;
    private static int gameMode = PVP;
    private static AI ai = AI.getSingleton();
    //boolean pieceSetAvailable = true;
    //int boardStartX=10,boardStartY=90;

    //设置画笔
    Canvas canvas1 = new Canvas();
    Paint linePaint = new Paint();
    Paint blackPointPaint = new Paint();
    Paint whitePointPaint = new Paint();

    public Board (Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public Board (Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }

    public void init(){
        //设置棋子画笔
        blackPointPaint.setStrokeWidth(6);
        blackPointPaint.setStyle(Paint.Style.FILL);
        blackPointPaint.setColor(Color.BLACK);
        blackPointPaint.setAntiAlias(true);
        whitePointPaint.setStrokeWidth(6);
        whitePointPaint.setStyle(Paint.Style.FILL);
        whitePointPaint.setColor(Color.WHITE);
        whitePointPaint.setAntiAlias(true);
    }
/*
    public boolean drawPiece(int x,int y,int color,Canvas canvas1){
        if(color==1 && board[x][y]==0){
            canvas1.drawCircle(x*90,y*90,30,blackPointPaint);
            return true;
        }
        else if(color == 2 && board[x][y]==0) {
            canvas1.drawCircle(x*90,y*90,30,whitePointPaint);
            return true;
        }
        else
            return false;
    }
*/
    public boolean onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();

        if(event.getAction()!=MotionEvent.ACTION_DOWN || gameState == GAME_OVER || gameState==GAME_PAUSE)
            return false;
        /*
        //测试落子位置
        int arrayX = (int)(x+45)/90;
        int arrayY = (int)(y+45)/90;
        AlertDialog.Builder dialog1 =new AlertDialog.Builder(this.getContext());
        dialog1.setTitle("");
        dialog1.setMessage(arrayX+","+arrayY+","+board[arrayX][arrayY]);
        dialog1.setPositiveButton("确定",null);
        dialog1.show();
        return true;
        */

        if(x>=15 && x<=975 && y>=15 && y<=975 && pieceCount<=121) {
            //将像素坐标转化为数组坐标
            arrayX = (int) x / 90;
            arrayY = (int) y / 90;
            if (board[arrayX][arrayY] != 0)
                return false;

            if(gameMode==PVP)
                manTurn();
            else if(gameMode==PVE){
                manTurn();
                comTurn();
            }
        }
        return true;

    }

    //用户回合
    public void manTurn(){
        board[arrayX][arrayY]=whoseTurn;
        ++pieceCount;
        this.invalidate();

        //判断获胜，并弹出窗口
        judge = judgeWin(arrayX,arrayY);
        if(judge==1 || judge==2)
            winState(judge,gameMode);

        changeTurn();


    }
    //程序回合
    public void comTurn(){
        //unfinished
        gameState=GAME_PAUSE;
        int[] result=ai.aiWork(board);
        arrayX=result[0];
        arrayY=result[1];
        board[arrayX][arrayY]=whoseTurn;
        ++pieceCount;
        this.invalidate();
        gameState=GAME_RUN;
        //判断获胜，并弹出窗口
        judge = judgeWin(arrayX,arrayY);
        if(judge==1 || judge==2)
            winState(judge,gameMode);

        changeTurn();


    }
    //该方法判断是否有一方获胜
    public int judgeWin(int x,int y){
        int count=1;
        //判断竖直方向
        for(i=x,j=y;j>0;--j){
            if(board[i][j-1]!=board[i][j])
                break;
            else
                ++count;
        }
        for(i=x,j=y;j<10;++j){
            if(board[i][j+1]!=board[i][j])
                break;
            else
                ++count;
        }
        if(count==5)
            return board[x][y];
        count=1;

        //判断水平方向
        for(i=x,j=y;i>0;--i){
            if(board[i-1][j]!=board[i][j])
                break;
            else
                ++count;
        }
        for(i=x,j=y;i<10;++i){
            if(board[i+1][j]!=board[i][j])
                break;
            else
                ++count;
        }
        if(count==5)
            return board[x][y];
        count=1;

        //判断左上-右下方向
        for(i=x,j=y;i>0 && j>0;--i,--j){
            if(board[i-1][j-1]!=board[i][j])
                break;
            else
                ++count;
        }
        for(i=x,j=y;i<10 && j<10;++i,++j){
            if(board[i+1][j+1]!=board[i][j])
                break;
            else
                ++count;
        }
        if(count==5)
            return board[x][y];
        count=1;

        //判断右上-左下方向
        for(i=x,j=y;i<10 && j>0;++i,--j){
            if(board[i+1][j-1]!=board[i][j])
                break;
            else
                ++count;
        }
        for(i=x,j=y;i>0 && j<10;--i,++j){
            if(board[i-1][j+1]!=board[i][j])
                break;
            else
                ++count;
        }
        if(count==5)
            return board[x][y];
        count=1;

        return 0;
    }
    //变更回合
    public void changeTurn(){
        //变更为对手回合
        if(whoseTurn == BLACK_TURN)
            whoseTurn=WHITE_TURN;
        else if(whoseTurn ==WHITE_TURN){
            whoseTurn=BLACK_TURN;
        }
    }
    public void winState(int judge,int gameMode){

        //设置游戏阶段为结束
        gameState=GAME_OVER;

        if(gameMode==PVP){
            if(judge==1) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext());
                dialog.setTitle("");
                dialog.setMessage("Black Win");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                });
                dialog.show();
            }
            else if(judge==2){
                AlertDialog.Builder dialog =new AlertDialog.Builder(this.getContext());
                dialog.setTitle("");
                dialog.setMessage("White Win");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                });
                dialog.show();
            }
            else if(pieceCount>=121){
                AlertDialog.Builder dialog =new AlertDialog.Builder(this.getContext());
                dialog.setTitle("");
                dialog.setMessage("Tie");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                });
                dialog.show();
            }
        }
        else if(gameMode==PVE){
            if(judge==1) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext());
                dialog.setTitle("");
                dialog.setMessage("You Win");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                });
                dialog.show();
            }
            else if(judge==2){
                AlertDialog.Builder dialog =new AlertDialog.Builder(this.getContext());
                dialog.setTitle("");
                dialog.setMessage("Computer Win");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                });
                dialog.show();
            }
            else if(pieceCount>=121){
                AlertDialog.Builder dialog =new AlertDialog.Builder(this.getContext());
                dialog.setTitle("");
                dialog.setMessage("Tie");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                });
                dialog.show();
            }
        }
    }
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //绘制棋盘
        canvas.drawARGB(255,169,121,57);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(3);
        for(int i=1;i<10;++i){
            canvas.drawLine(45,i*90+45,945,i*90+45,linePaint);
        }
        for(int j=1;j<10;++j){
            canvas.drawLine(j*90+45,45,j*90+45,945,linePaint);
        }
        linePaint.setStrokeWidth(9);
        canvas.drawLine(45,45,45,945,linePaint);
        canvas.drawLine(45,45,945,45,linePaint);
        canvas.drawLine(945,945,45,945,linePaint);
        canvas.drawLine(945,945,945,45,linePaint);
/*
        //测试onDraw()是否被调用
        AlertDialog.Builder dialog1 =new AlertDialog.Builder(this.getContext());
        dialog1.setTitle("");
        dialog1.setMessage(arrayX+","+arrayY+","+board[arrayX][arrayY]+","+pieceCount+whoseTurn);
        dialog1.setPositiveButton("确定",null);
        dialog1.show();
*/
        //绘制棋子
        init();
        for(int i=0;i<11;++i){
            for(int j=0;j<11;++j){
                //drawPiece(i,j,board[i][j],canvas);
                if(board[i][j]==1){
                    canvas.drawCircle(i*90+45,j*90+45,40,blackPointPaint);
                }
                else if(board[i][j]==2) {
                    canvas.drawCircle(i*90+45,j*90+45,40,whitePointPaint);
                }
            }
        }
        canvas.save();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //重置棋局
    public void reset(){
        whoseTurn=BLACK_TURN;
        pieceCount=0;
        board = new int[row][col];
        judge=0;
        gameState=GAME_RUN; //设置游戏阶段为开始
        postInvalidate();

        /*
        //重置计时器，有点错误
        Chronometer mChronometer = (Chronometer)findViewById(R.id.chronometer);
        mChronometer.stop();
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
        */
    }
    /*
    public void reset(){
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(3);
        pieceCount=1;
        board = new int[row][col];
        for(int i=0;i<11;++i){
            canvas1.drawLine(0,i*90,900,i*90,linePaint);
        }
        for(int j=0;j<11;++j){
            canvas1.drawLine(j*90,0,j*90,900,linePaint);
        }
    }
    */

    public void setGameMode(int mode){
        gameMode=mode;
    }
}
