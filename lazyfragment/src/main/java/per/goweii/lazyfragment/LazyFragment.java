package per.goweii.lazyfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

public abstract class LazyFragment extends CacheFragment {

    private boolean mIsFirstVisible = true;
    private boolean mUserVisible = false;

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
        if (visible && !isParentVisible()) {
            return;
        }
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

    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        if (fragment == null) {
            return true;
        }
        if (fragment instanceof LazyFragment) {
            LazyFragment lazyFragment = (LazyFragment) fragment;
            return lazyFragment.isSupportUserVisible();
        }
        return fragment.isVisible();
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

    protected void onVisible(boolean isFirstVisible) {
    }

    protected void onInvisible() {
    }
}
