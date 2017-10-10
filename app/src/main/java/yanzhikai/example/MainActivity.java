package yanzhikai.example;

import android.app.Activity;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yanzhikai.guiderview.YGuider;

public class MainActivity extends Activity implements View.OnClickListener{
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
        mYGuider.addNextHighlight(new RectF(100,100,200,200),"大家好，我是Hello，World",0,-0);
        mYGuider.addNextHighlight(btn1,"asdfaSSSSSSSSSSSSSSSSSSweqesasdasdasdasdasdasdas",0,-200);
        mYGuider.addNextHighlight(btn2,"testsadasdasd2aadaafasdfsdfsq",0,130);
        mYGuider.prepareTarget();
    }

    @Override
    public void onClick(View view) {
        mYGuider.startGuide();
    }
}
