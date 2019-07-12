package per.goweii.lazyfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class LazyFragment extends CacheFragment {

    private static final int WHAT_LAZY_VISIBLE = 1;
    private static final long LAZY_LOAD_DELAY = 100;

    private boolean mIsFirstVisible = true;
    private boolean mUserVisible = false;

    @SuppressLint("HandlerLeak")
    private Handler mLazyLoadHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_LAZY_VISIBLE) {
                final boolean isFirstVisible = msg.arg1 == 1;
                onVisibleLazy(isFirstVisible);
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mViewCreated) {
            if (isVisibleToUser && !mUserVisible) {
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && mUserVisible) {
                dispatchUserVisibleHint(false);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isHidden() && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsFirstVisible) {
            if (!isHidden() && !mUserVisible && getUserVisibleHint()) {
                dispatchUserVisibleHint(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mUserVisible && getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsFirstVisible = true;
    }

    private void dispatchUserVisibleHint(boolean visible) {
        if (visible && isParentInvisible()) return;
        if (mUserVisible == visible) {
            return;
        }
        mUserVisible = visible;
        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                onVisible(true);
            } else {
                onVisible(false);
            }
            dispatchChildVisibleState(true);
        } else {
            dispatchChildVisibleState(false);
            onInvisible();
        }
    }

    private boolean isParentInvisible() {
        LazyFragment fragment = (LazyFragment) getParentFragment();
        return fragment != null && !fragment.isSupportUserVisible();
    }

    private boolean isSupportUserVisible() {
        return mUserVisible;
    }

    private void dispatchChildVisibleState(boolean visible) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (!fragments.isEmpty()) {
            for (Fragment child : fragments) {
                if (child instanceof LazyFragment && !child.isHidden() && child.getUserVisibleHint()) {
                    ((LazyFragment) child).dispatchUserVisibleHint(visible);
                }
            }
        }
    }

    public void onVisibleLazy(boolean isFirstVisible) {
    }

    public void onVisible(boolean isFirstVisible) {
        Message msg = mLazyLoadHandler.obtainMessage();
        msg.what = WHAT_LAZY_VISIBLE;
        msg.arg1 = isFirstVisible ? 1 : 0;
        mLazyLoadHandler.sendMessageDelayed(msg, LAZY_LOAD_DELAY);
    }

    public void onInvisible() {
        mLazyLoadHandler.removeMessages(WHAT_LAZY_VISIBLE);
    }
}