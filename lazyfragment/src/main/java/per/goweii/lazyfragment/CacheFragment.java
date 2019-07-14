package per.goweii.lazyfragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract class CacheFragment extends Fragment {

    protected View mRootView = null;
    protected boolean mViewCreated = false;

    private SparseArray<View> mCacheViews = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            final int layoutId = getLayoutRes();
            if (layoutId > 0) {
                mRootView = inflater.inflate(getLayoutRes(), container, false);
            }
        }
        initView();
        mViewCreated = true;
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
        mCacheViews.clear();
        mCacheViews = null;
        mViewCreated = false;
    }

    @Nullable
    public View getRootView() {
        return mRootView;
    }

    public final <V extends View> V getView(@IdRes int id) {
        if (mCacheViews == null) {
            mCacheViews = new SparseArray<>();
        }
        View view = mCacheViews.get(id);
        if (view == null) {
            view = findViewById(id);
            if (view != null) {
                mCacheViews.put(id, view);
            }
        }
        return (V) view;
    }

    public final <V extends View> V findViewById(@IdRes int id) {
        if (mRootView == null) {
            return null;
        }
        return mRootView.findViewById(id);
    }

    /**
     * 返回布局 resId
     */
    protected abstract int getLayoutRes();

    /**
     * 初始化view
     */
    protected abstract void initView();
}
