package per.goweii.adnroid.lazyfragment;

import android.util.Log;

import per.goweii.lazyfragment.LazyFragment;

public class Test1Fragment extends LazyFragment {

    public static final String TAG = Test1Fragment.class.getSimpleName();

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
        Log.d(TAG, "onVisible : isFirstVisible=" + isFirstVisible);
    }

    @Override
    public void onVisibleLazy(boolean isFirstVisible) {
        super.onVisibleLazy(isFirstVisible);
        Log.d(TAG, "onVisibleLazy : isFirstVisible=" + isFirstVisible);
    }

    @Override
    public void onInvisible() {
        super.onInvisible();
        Log.d(TAG, "onInvisible");
    }
}
