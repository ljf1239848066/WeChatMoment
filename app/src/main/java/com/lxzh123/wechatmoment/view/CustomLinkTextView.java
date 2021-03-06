
package com.lxzh123.wechatmoment.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Layout;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.appcompat.widget.AppCompatTextView;

import com.lxzh123.wechatmoment.R;

/**
 * description: 自定义链接点击效果的TextView
 * author:      Created by lxzh on 2021/4/18.
 */
public class CustomLinkTextView extends AppCompatTextView {

    private static final String TAG = "CustomLinkTextView";
    private ClickableSpan mSelectedLink;
    private boolean mHasPerformedLongPress;
    private CheckForLongPress mPendingCheckForLongPress;
    private ForegroundColorSpan mForegroundColorSpan;
    private BackgroundColorSpan mBackgroundColorSpan;

    public CustomLinkTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLinkTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLinksClickable(false);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomLinkTextView, defStyle, 0);
        ColorStateList linkForegroundColor = a.getColorStateList(R.styleable.CustomLinkTextView_linkClickTextForeColor);
        if (linkForegroundColor != null) {
            mForegroundColorSpan = new ForegroundColorSpan(linkForegroundColor.getColorForState(EMPTY_STATE_SET, Color.RED));
        }
        ColorStateList linkBackgroundColor = a.getColorStateList(R.styleable.CustomLinkTextView_linkClickTextBackColor);
        if (linkBackgroundColor != null) {
            mBackgroundColorSpan = new BackgroundColorSpan(linkBackgroundColor.getColorForState(EMPTY_STATE_SET, Color.RED));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = handledLinkTouch(event);

        if (handled) {
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public void cancelLongPress() {
        removeLongPressCallback();
        super.cancelLongPress();
    }

    @Override
    protected void onDetachedFromWindow() {
        removeLongPressCallback();
        super.onDetachedFromWindow();
    }

    private boolean handledLinkTouch(MotionEvent event) {

        CharSequence text = getText();
        int pointCount = event.getPointerCount();
        if (!(text instanceof Spannable) || pointCount > 1) {
            return false;
        }
        int action = event.getAction();
        Spannable buffer = (Spannable) text;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= this.getTotalPaddingLeft();
                y -= this.getTotalPaddingTop();

                x += this.getScrollX();
                y += this.getScrollY();

                Layout layout = this.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
                if (link.length != 0) {
                    checkForLongClick(0);
                    mSelectedLink = link[0];
                    setLinkPressed(true);
                    return true;
                } else {
                    mSelectedLink = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mSelectedLink != null) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mSelectedLink != null) {
                    if (!mHasPerformedLongPress) {
                        // This is a tap, so remove the longpress check
                        removeLongPressCallback();
                        mSelectedLink.onClick(this);
                    }
                    setLinkPressed(false);
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mSelectedLink != null) {
                    setLinkPressed(false);
                }
                removeLongPressCallback();
                break;
            default:
                break;
        }
        return false;
    }

    private void checkForLongClick(int delayOffset) {
        if (isLongClickable()) {
            mHasPerformedLongPress = false;

            if (mPendingCheckForLongPress == null) {
                mPendingCheckForLongPress = new CheckForLongPress();
            }
            mPendingCheckForLongPress.rememberWindowAttachCount();
            postDelayed(mPendingCheckForLongPress, ViewConfiguration.getLongPressTimeout() - delayOffset);
        }
    }

    private void removeLongPressCallback() {
        if (mPendingCheckForLongPress != null) {
            removeCallbacks(mPendingCheckForLongPress);
        }
    }

    class CheckForLongPress implements Runnable {

        private int mOriginalWindowAttachCount;

        public void run() {
            if (mOriginalWindowAttachCount == getWindowAttachCount()) {
                if (performLongClick()) {
                    mHasPerformedLongPress = true;
                }
            }
        }

        public void rememberWindowAttachCount() {
            mOriginalWindowAttachCount = getWindowAttachCount();
        }
    }

    private void setLinkPressed(boolean pressed) {
        if (!(getText() instanceof Spannable) || mForegroundColorSpan == null) {
            return;
        }
        Spannable buffer = (Spannable) getText();
        if (buffer == null) {
            return;
        }
        if (pressed) {
            buffer.setSpan(mForegroundColorSpan, buffer.getSpanStart(mSelectedLink), buffer.getSpanEnd(mSelectedLink),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            buffer.setSpan(mBackgroundColorSpan, buffer.getSpanStart(mSelectedLink), buffer.getSpanEnd(mSelectedLink),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            buffer.removeSpan(mForegroundColorSpan);
            buffer.removeSpan(mBackgroundColorSpan);
            mSelectedLink = null;
        }
    }
}
