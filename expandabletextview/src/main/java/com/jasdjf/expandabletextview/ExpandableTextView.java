package com.jasdjf.expandabletextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.v4.util.PatternsCompat.AUTOLINK_WEB_URL;

public class ExpandableTextView extends android.support.v7.widget.AppCompatTextView {

    //@用户名 正则匹配表达式
    public static final String REGEXP_MENTION = "@[\\w\\p{InCJKUnifiedIdeographs}-]{1,26}";
    //用于获取TextView的最大宽度，因为要在赋值之前就要得到TextView的宽度，所以这里先给TextView赋上一些空格以获取TextView在显示多行时宽度
    public static final String DEFAULT_CONTENT = "                                       " +
            "                                                                            " +
            "                                                                            ";
    public static final String EXPAND_STRING="... 展开 ";
    public static final String SHRINK_STRING="收起 ";//后面加个空格是为了防止收起两个字不在行末时，点击收起两个字后面的空白区域也能触发收起操作的情况
    public static final String LINK_STRING="网页链接";

    private static boolean clicked = false;

    private OnLinkClickListener linkClickListener = null;
    private List<LinkData> linkList = new ArrayList<>();
    private TextPaint paint;
    private int mWidth;
    private int retryTime = 0;
    private boolean splitOrNot = true;
    private int expandTextLength = 0;
    private CharSequence mContent;

    private int mMaxLine;
    private boolean mExpandable;
    private boolean mDealMention;
    private boolean mDealLink;
    private String mShrinkText;
    private String mExpandText;
    private int mExpandTextColor;
    private int mShrinkTextColor;
    private int mLinkColor;
    private int mMentionColor;
    private String mLinkText;
    private Drawable mLinkDrawable;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMovementMethod(CustomLinkMovementMethod.getInstance());
        paint = getPaint();
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView,
                            defStyleAttr, 0);
            mMaxLine = a.getInt(R.styleable.ExpandableTextView_max_line,4);
            mExpandable = a.getBoolean(R.styleable.ExpandableTextView_expandable,true);
            mDealMention = a.getBoolean(R.styleable.ExpandableTextView_deal_mention,true);
            mDealLink = a.getBoolean(R.styleable.ExpandableTextView_deal_link,true);
            mShrinkText = a.getString(R.styleable.ExpandableTextView_shrink_text);
            if(mShrinkText == null) mShrinkText = SHRINK_STRING;
            mExpandText = a.getString(R.styleable.ExpandableTextView_expand_text);
            if(mExpandText == null) mExpandText = EXPAND_STRING;
            mExpandTextColor = a.getColor(R.styleable.ExpandableTextView_expand_color,Color.BLUE);
            mShrinkTextColor = a.getColor(R.styleable.ExpandableTextView_shrink_color,Color.BLUE);
            mLinkColor = a.getColor(R.styleable.ExpandableTextView_link_color,Color.RED);
            mMentionColor = a.getColor(R.styleable.ExpandableTextView_mention_color,Color.RED);
            mLinkDrawable = a.getDrawable(R.styleable.ExpandableTextView_link_res);
            mLinkText = a.getString(R.styleable.ExpandableTextView_link_text);
            if(mLinkText == null) mLinkText = LINK_STRING;
            if(mLinkDrawable!=null){
                Rect rect = new Rect();
                paint.getTextBounds("回", 0, 1, rect);//获取当前画笔画一个字的大小
                mLinkDrawable.setBounds(rect);//必须设置图片大小，否则不显示
            }
            a.recycle();
        }
    }

    public void setContent(final CharSequence content){
        mContent = content;
        if (mWidth <= 0) {
            if (getWidth() > 0)
                mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        }

        if (mWidth <= 0) {
            /*if (retryTime > 10) {
                setText(DEFAULT_CONTENT);
            }
            this.post(new Runnable() {
                @Override
                public void run() {
                    retryTime++;
                    setContent(content);
                }
            });*/
            setText(DEFAULT_CONTENT);
            this.post(new Runnable() {
                @Override
                public void run() {
                    setContent(content);
                }
            });
        } else {
            matchLink(content);
        }
    }

    private void matchLink(CharSequence content){
        if(content==null || TextUtils.isEmpty(content)){
            return;
        }
        int startIndex,endIndex,index = 0;

        if (mDealLink || mDealMention) {
            StringBuilder builder = new StringBuilder();
            Pattern pattern;
            Matcher matcher;
            if (mDealLink) {
                pattern = AUTOLINK_WEB_URL;
                matcher = pattern.matcher(content);
                while(matcher.find()){
                    startIndex = matcher.start();
                    endIndex = matcher.end();
                    String linkUrl = content.subSequence(startIndex,endIndex).toString();
                    builder.append(content.subSequence(index,startIndex));
                    linkList.add(new LinkData(builder.length(),builder.length()+mLinkText.length(),linkUrl,LinkType.URL));
                    builder.append(mLinkText);
                    index = endIndex;
                }
                builder.append(content.subSequence(index,content.length()));
                content = builder.toString();
            }

            if (mDealMention) {
                pattern = Pattern.compile(REGEXP_MENTION, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(content);
                builder = new StringBuilder();
                index = 0;
                while(matcher.find()){
                    startIndex = matcher.start();
                    endIndex = matcher.end();
                    String mentionUserName = content.subSequence(startIndex,endIndex).toString();
                    builder.append(content.subSequence(index,startIndex));
                    linkList.add(new LinkData(builder.length(),builder.length()+endIndex-startIndex,mentionUserName,LinkType.MENTION));
                    builder.append(mentionUserName);
                    index = endIndex;
                }
                builder.append(content.subSequence(index,content.length()));
            }
            handleLink(splitWhenShrink(builder.toString()));
        } else {
            handleLink(splitWhenShrink(content));
        }
    }

    private CharSequence splitWhenShrink(CharSequence content){
        if (mExpandable) {
            if (splitOrNot) {
                DynamicLayout dynamicLayout = new DynamicLayout(content,paint,mWidth, Layout.Alignment.ALIGN_NORMAL, 1.2f, 0.0f,true);
                int lineCount = dynamicLayout.getLineCount();
                if(lineCount>=mMaxLine){
                    int endIndex = dynamicLayout.getLineEnd(mMaxLine);
                    expandTextLength = (mExpandText+" ").length();
                    if(content.length()+expandTextLength<endIndex){
                        return content+mExpandText+" ";
                    }
                    return content.subSequence(0,endIndex-expandTextLength)+mExpandText+" ";
                }
                return content;
            } else {
                expandTextLength = (mShrinkText+" ").length();
                return content+mShrinkText+" ";
            }
        } else {
            return content;
        }
    }

    private void handleLink(CharSequence content){
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        Collections.sort(linkList, new Comparator<LinkData>() {
            @Override
            public int compare(LinkData o1, LinkData o2) {
                if(o1.getStartIndex() < o2.getStartIndex()){
                    return -1;
                } else if(o1.getStartIndex()==o2.getStartIndex()){
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        int index = 0;
        for (int i = 0; i < linkList.size(); i++) {
            final LinkData data = linkList.get(i);
            if(data.getEndIndex()>content.length()){
                continue;
            }
            if(data.getLinkType()==LinkType.MENTION){
                ssb.append(content.subSequence(index,data.getEndIndex()));
                ssb.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        if (linkClickListener != null) {
                            linkClickListener.onClick(data.getLinkContent(),data.getLinkType());
                        }
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(mMentionColor);
                    }
                },ssb.length()-data.getEndIndex()+data.getStartIndex(),ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if(data.getLinkType()==LinkType.URL){
                ssb.append(content.subSequence(index,data.getEndIndex()));
                //ImageSpan
                int linkLength = mLinkText.length();
                if(mLinkDrawable!=null){
                    linkLength += 1;
                    ssb.setSpan(new CustomImageSpan(mLinkDrawable,ImageSpan.ALIGN_BASELINE),ssb.length()-linkLength,ssb.length()-linkLength+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                ssb.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        if (linkClickListener != null) {
                            linkClickListener.onClick(data.getLinkContent(),data.getLinkType());
                        }
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(mLinkColor);
                    }
                },ssb.length()-linkLength,ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            index = data.getEndIndex();
        }
        ssb.append(content.subSequence(index,content.length()));
        if (mExpandable) {
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    splitOrNot = !splitOrNot;
                    setContent(mContent);
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(splitOrNot?mExpandTextColor:mShrinkTextColor);
                }
            },ssb.length()-expandTextLength,ssb.length()-1,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        setHighlightColor(Color.TRANSPARENT);
        setText(ssb);
    }

    public enum LinkType{
        MENTION,URL
    }

    class LinkData{
        private int startIndex;
        private int endIndex;
        private String linkContent;
        private LinkType linkType;
        LinkData(int startIndex, int endIndex, String linkContent, LinkType linkType){
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.linkContent = linkContent;
            this.linkType = linkType;
        }

        public LinkType getLinkType() {
            return linkType;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public String getLinkContent() {
            return linkContent;
        }
    }

    public interface OnLinkClickListener{
        void onClick(String linkContent,LinkType linkType);
    }

    public void setOnLinkClickListener(OnLinkClickListener linkClickListener){
        this.linkClickListener =linkClickListener;
    }

    static class CustomLinkMovementMethod extends LinkMovementMethod {
        static CustomLinkMovementMethod instance;


        public static CustomLinkMovementMethod getInstance() {
            if (instance == null)
                instance = new CustomLinkMovementMethod();

            return instance;
        }

        @Override
        public boolean onTouchEvent(TextView widget,
                                    Spannable buffer, MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(
                        off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        if(clicked){
                            link[0].onClick(widget);
                            clicked = false;
                            Log.d("jasdjf", "1");
                            return true;
                        } else {
                            Log.d("jasdjf", "2");
                            return Touch.onTouchEvent(widget, buffer, event);
                        }
                    } else {
                        Log.d("jasdjf", "3");
                        Selection.setSelection(buffer,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0]));
                        clicked = true;
                        return Touch.onTouchEvent(widget, buffer, event);
                    }
                } else {
                    Log.d("jasdjf", "4");
                    Selection.removeSelection(buffer);

                    return Touch.onTouchEvent(widget, buffer, event);
                }
            }
            Log.d("jasdjf", "5");
            return Touch.onTouchEvent(widget, buffer, event);
        }
    }

    /**
     * 自定义ImageSpan，使图片的底部与文字的底部对齐
     */
    class CustomImageSpan extends ImageSpan {
        private Drawable drawable;

        CustomImageSpan(Drawable d, int verticalAlignment) {
            super(d, verticalAlignment);
            this.drawable = d;
        }

        @Override
        public Drawable getDrawable() {
            return drawable;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text,
                         int start, int end, float x,
                         int top, int y, int bottom, @NonNull Paint paint) {
            Drawable b = getDrawable();
            canvas.save();
            canvas.translate(x, y);
            b.draw(canvas);
            canvas.restore();
        }
    }
}
