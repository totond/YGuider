package yanzhikai.example;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yanzhikai.guiderview.YGuider;
import com.yanzhikai.guiderview.interfaces.OnGuiderListener;

public class MainActivity extends Activity implements View.OnClickListener{
    private final String TAG = "MainActivity";
    private Button btn1, btn2;
    private YGuider mYGuider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        initGuider();
    }

    private void initGuider() {
        mYGuider = new YGuider(this);
//        mYGuider.setWindowBackground(R.drawable.dialog_shape1);
//        mYGuider.setWindowContent(R.layout.tips_window_layout_test);
//        mYGuider.setWindowTyperTextSize(10);
//        mYGuider.setWindowJumpAndNextTextSize(8);
//        mYGuider.setNextText("下一步");
//        mYGuider.setJumpText("跳过");
//        mYGuider.setMaskColor(Color.argb(99,200,100,99));
//        mYGuider.setMaskPaint(getPaint());
        mYGuider.addNextHighlight(new RectF(100,100,200,200),"点击这里可以立刻出现所有提示文字,大家好，我是YGuider！",90,0);
        mYGuider.addNextHighlight(btn1,"这里是根据传入View来确定的区域!",150,10);
        mYGuider.addNextHighlight(new RectF(50,500,150,650),"这里是根据传入矩形区域坐标来确定的！",100,10);
        mYGuider.addNextHighlight(
                btn2,"通过设置偏移量来控制PopupWindow出现的位置，还可以设置它的大小！"
                ,-350,-300
                ,430, ViewGroup.LayoutParams.WRAP_CONTENT);
        mYGuider.addNextHighlight(
                new RectF(500,200,600,270),"可以动态改变两个按钮的文字",
                -300,20
                ,300,ViewGroup.LayoutParams.WRAP_CONTENT
                ,"","Finish");
        mYGuider.prepareTarget();
        mYGuider.setOnGuiderListener(new MyGuiderListener());
    }

    private Paint getPaint() {
        BlurMaskFilter mf = new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL);

        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(10);
        p.setMaskFilter(mf);
        p.setColor(Color.BLUE);
        return p;
    }

    @Override
    public void onClick(View view) {
        mYGuider.startGuide();
    }

    class MyGuiderListener extends OnGuiderListener{
        @Override
        public void onMaskClick() {
            Log.d(TAG, "onMaskClick: ");
        }

        @Override
        public void onNextClick(int nextIndex) {
            Log.d(TAG, "onNextClick: " + nextIndex);
        }

        @Override
        public void onJumpClick() {
            Log.d(TAG, "onJumpClick: ");
        }

        @Override
        public void onGuiderStart() {
            Log.d(TAG, "onGuiderStart: ");
        }

        @Override
        public void onGuiderNext(int nextIndex) {
            Log.d(TAG, "onGuiderNext: " + nextIndex);
        }

        @Override
        public void onGuiderFinished() {
            Log.d(TAG, "onGuiderFinished: ");
        }

        @Override
        public void onTargetClick(int index) {
            Log.d(TAG, "onTargetClick: " + index);
        }
    }
}
