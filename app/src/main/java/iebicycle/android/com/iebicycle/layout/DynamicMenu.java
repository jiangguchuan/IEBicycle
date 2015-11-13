package iebicycle.android.com.iebicycle.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by Guchuan on 2015/11/3.
 */
public class DynamicMenu extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Context mContext;
    private int mScreenWidth;		//��Ļ���
    private int mScreenHeight;		//��Ļ�߶�
    private Paint mPaint; 		//���廭��
    private float cx = 50;		//Բ��Ĭ��X����
    private float cy = 50;		//Բ��Ĭ��Y����
    private int radius = 20;
    //������ɫ����
    private Canvas canvas = null; //���廭��
    private Thread th = null;     //�����߳�
    private SurfaceHolder sfh = null;

    public DynamicMenu(Context context){
        super(context);
        mContext = context;
        initPaint();
        sfh = getHolder();
        sfh.addCallback(this);
        th = new Thread(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mScreenWidth = getWidth();
        mScreenHeight = getHeight();
        th.start();
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
    }

    @Override
    public void run() {
        while(true){
            try{
                myDraw();
                Thread.sleep(10);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void myDraw() {
        canvas = sfh.lockCanvas();
        Random random = new Random();
        float y = random.nextFloat() * mScreenHeight;
        float x = random.nextFloat() * mScreenWidth;
//        canvas.drawLine(cx, cy, x, y, mPaint);
        canvas.drawPoint(x, y, mPaint);
        cx = x;
        cy = y;
        //�����õĻ����ύ
        sfh.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
