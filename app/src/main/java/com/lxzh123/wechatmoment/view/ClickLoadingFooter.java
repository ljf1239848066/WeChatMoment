package com.lxzh123.wechatmoment.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.lxzh123.lrecyclerview.interfaces.ILoadMoreFooter;
import com.lxzh123.lrecyclerview.interfaces.OnLoadMoreListener;
import com.lxzh123.lrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.lxzh123.lrecyclerview.recyclerview.ProgressStyle;
import com.lxzh123.lrecyclerview.view.SimpleViewSwitcher;
import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.utils.LogUtils;

/**
 * description: 手动点击加载更多
 * author:      Created by lxzh on 2021/4/19.
 */
public class ClickLoadingFooter extends RelativeLayout implements ILoadMoreFooter {

    protected State mState = State.Normal;
    private View mLoadingView;
    private View mTheEndView;
    private View mManualView;
    private SimpleViewSwitcher mProgressView;
    private TextView mLoadingText;
    private TextView mNoMoreText;
    private String loadingHint;
    private String noMoreHint;
    private int style;
    private int indicatorColor;
    private int hintColor = R.color.colorAccent;

    public ClickLoadingFooter(Context context) {
        super(context);
        init();
    }

    public ClickLoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClickLoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

        inflate(getContext(), R.layout.view_manual_recyclerview_footer, this);
        setOnClickListener(null);

        onReset();//初始为隐藏状态

        indicatorColor = ContextCompat.getColor(getContext(), R.color.colorAccent);
        style = ProgressStyle.SysProgress;
    }

    public void setLoadingHint(String hint) {
        this.loadingHint = hint;
    }

    public void setNoMoreHint(String hint) {
        this.noMoreHint = hint;
    }

    public void setIndicatorColor(int color) {
        this.indicatorColor = color;
    }

    public void setHintTextColor(int color) {
        this.hintColor = color;
    }

    public void setViewBackgroundColor(int color) {
        this.setBackgroundColor(ContextCompat.getColor(getContext(), color));
    }

    public void setProgressStyle(int style) {
        this.style = style;
    }

    public State getState() {
        return mState;
    }

    public void setState(State status) {
        setState(status, true);
    }

    private View initIndicatorView(int style) {
        if (style == ProgressStyle.SysProgress) {
            return new ProgressBar(getContext(), null, android.R.attr.progressBarStyle);
        } else {
            AVLoadingIndicatorView progressView = (AVLoadingIndicatorView) LayoutInflater.from(getContext()).inflate(R.layout.layout_indicator_view, null);
            progressView.setIndicatorId(style);
            progressView.setIndicatorColor(indicatorColor);
            return progressView;
        }

    }

    @Override
    public void onReset() {
        onComplete();
    }

    @Override
    public void onLoading() {
        setState(State.Loading);
    }

    @Override
    public void onComplete() {
        LogUtils.d("onComplete  111");
        setState(State.ManualLoadMore);
    }

    @Override
    public void onNoMore() {
        //setState(State.NoMore);
        setState(State.Normal);
        setVisibility(GONE);
    }

    @Override
    public View getFootView() {
        return this;
    }

    @Override
    public void setOnClickLoadMoreListener(final OnLoadMoreListener listener) {
        setVisibility(VISIBLE);
        setState(State.ManualLoadMore);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(State.Loading);
                listener.onLoadMore();
            }
        });
    }

    /**
     * 设置状态
     *
     * @param status
     * @param showView 是否展示当前View
     */
    public void setState(State status, boolean showView) {
        if (mState == status) {
            return;
        }
        mState = status;

        switch (status) {
            case Normal:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mManualView != null) {
                    mManualView.setVisibility(GONE);
                }

                break;
            case Loading:
                setOnClickListener(null);
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mManualView != null) {
                    mManualView.setVisibility(GONE);
                }

                if (mLoadingView == null) {
                    ViewStub viewStub = findViewById(R.id.loading_viewstub);
                    mLoadingView = viewStub.inflate();

                    mProgressView = mLoadingView.findViewById(R.id.loading_progressbar);
                    mLoadingText = mLoadingView.findViewById(R.id.loading_text);
                }

                mLoadingView.setVisibility(showView ? VISIBLE : GONE);

                mProgressView.setVisibility(View.VISIBLE);
                mProgressView.removeAllViews();
                mProgressView.addView(initIndicatorView(style));

                mLoadingText.setText(TextUtils.isEmpty(loadingHint) ? getResources().getString(R.string.list_footer_loading) : loadingHint);
                mLoadingText.setTextColor(ContextCompat.getColor(getContext(), hintColor));

                break;
            case NoMore:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mManualView != null) {
                    mManualView.setVisibility(GONE);
                }

                break;
            case ManualLoadMore:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mManualView == null) {
                    ViewStub viewStub = findViewById(R.id.manual_load_viewstub);
                    mManualView = viewStub.inflate();
                } else {
                    mManualView.setVisibility(VISIBLE);
                }

                break;
            default:
                break;
        }
    }
}