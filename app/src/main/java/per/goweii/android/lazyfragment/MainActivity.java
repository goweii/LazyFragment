package per.goweii.android.lazyfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static TextView tv_log;
    private static ScrollView sv_log;
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_log = findViewById(R.id.tv_log);
        sv_log = findViewById(R.id.sv_log);

        List<Fragment> fragments = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            fragments.add(Test1Fragment.newInstance("Test1Fragment-" + i));
        }
        FixFragmentPagerAdapter adapter = new FixFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        vp = findViewById(R.id.vp);
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(4);

        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(0);
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(1);
            }
        });
        findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(2);
            }
        });
        findViewById(R.id.tv4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(3);
            }
        });
        findViewById(R.id.tv5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(4);
            }
        });
    }

    public static synchronized void log(String msg){
        if (tv_log == null) {
            return;
        }
        String last = tv_log.getText().toString();
        String curr = last + "\n" + msg;
        tv_log.setText(curr);
        tv_log.post(new Runnable() {
            @Override
            public void run() {
                if (sv_log != null) {
                    sv_log.fullScroll(ScrollView.FOCUS_DOWN);
                }
            }
        });
    }
}
