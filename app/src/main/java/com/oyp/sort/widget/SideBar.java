package com.oyp.sort.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.oyp.sort.R;


public class SideBar extends View {
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    // 26个字母
    private String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private int choose = -1;// 选中

    private Paint paint = new Paint();

    private TextView mTextDialog;

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context) {
        super(context);
    }


    public void setB(String[] b) {
        this.b = b;
        invalidate();
    }

    /**
     * 重写这个方法
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        // 单个字母高度
        float singleHeight = height / (float) b.length;

        for (int i = 0; i < b.length; i++) {
            paint.setColor(getResources().getColor(R.color.orange_ffaa22));
            paint.setAntiAlias(true);
            paint.setTextSize(getResources().getDimensionPixelOffset(R.dimen.text_size_15));
//			// 选中的状态
//			if (i == choose) {
//				paint.setColor(Color.parseColor("#3399ff"));
//				paint.setFakeBoldText(true);
//			}
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(b[i]) / 2;

            float yPos = singleHeight * i + singleHeight / 2;
//			float yPos = singleHeight * i + getResources().getDimensionPixelOffset(R.dimen.sidebar_item_split_height);

            // 绘制字母
//          canvas.drawText(b[i], xPos, yPos, paint);
            drawTextInCenter(canvas, b[i], xPos, yPos);

            paint.reset();// 重置画笔
        }
    }

    /**
     * @param canvas  画板
     * @param string  被绘制的字母
     * @param xCenter 字母的中心x方向位置
     * @param yCenter 字母的中心y方向位置
     */
    private void drawTextInCenter(Canvas canvas, String string, float xCenter, float yCenter) {

        Paint.FontMetrics fm = paint.getFontMetrics();
        //float fontWidth = paint.measureText(string);
        float fontHeight = paint.getFontSpacing();

        float drawY = yCenter + fontHeight / 2 - fm.descent;

        if (drawY < -fm.ascent - fm.descent) {
            drawY = -fm.ascent - fm.descent;
        }

        if (drawY > getHeight()) {
            drawY = getHeight();
        }

        paint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText(string, xCenter, drawY, paint);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:
//			setBackgroundDrawable(new ColorDrawable(0x00000000));

                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                // 设置右侧字母列表[A,B,C,D,E....]的背景颜色
//			setBackgroundResource(R.drawable.sortlistview_sidebar_background);

                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     */
    public interface OnTouchingLetterChangedListener {
        /**
         * 侧边栏滑动到的某个item的回调事件
         *
         * @param s 侧边栏滑动到的 列表item
         */
        void onTouchingLetterChanged(String s);
    }

}