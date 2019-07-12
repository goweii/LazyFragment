package per.goweii.adnroid.lazyfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import per.goweii.lazyfragment.LazyFragment;

public class Test1Fragment extends LazyFragment {

    public static final String TAG = Test1Fragment.class.getSimpleName();
    private String name;
    private ViewPager vp;

    public static Test1Fragment newInstance(String name){
        Test1Fragment fragment = new Test1Fragment();
        Bundle args = new Bundle(1);
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_test1;
    }

    @Override
    protected void initView() {
        name = getArguments().getString("name");
        TextView tv_name = findViewById(R.id.tv_name);
        tv_name.setText(name);

        List<Fragment> fragments = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            fragments.add(Test2Fragment.newInstance("Test2Fragment-" + i));
        }
        FixFragmentPagerAdapter adapter = new FixFragmentPagerAdapter(getChildFragmentManager(), fragments);
        vp = findViewById(R.id.vp);
        vp.setAdapter(adapter);

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

    @Override
    public void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
        MainActivity.log(name + " onVisible : isFirstVisible=" + isFirstVisible);
    }

    @Override
    public void onInvisible() {
        super.onInvisible();
        MainActivity.log(name + " onInvisible");
    }
}
