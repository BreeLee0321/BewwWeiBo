package bree.com.bewwweibo.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bree.com.bewwweibo.R;

/**
 * Created by bree on 2017/5/11.
 */

public class RichTextUtils {
    private static String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
    private static final Pattern WEB_PATTERN = Pattern.compile(regex);
    private static final Pattern MENTION_PATTERN = Pattern.compile("@[\\u4e00-\\u9fa5a-zA-Z0-9_-]+");

    public static SpannableString getRichText(Context context, String text) {
        SpannableString string = new SpannableString(text);
        if (!TextUtils.isEmpty(text)) {
            final int linkColor = ContextCompat.getColor(context, R.color.cw_blue);
            final int mentionColor = ContextCompat.getColor(context, R.color.cw_blue);
            Matcher matcher = WEB_PATTERN.matcher(text);

            while (matcher.find()) {
                final String url = matcher.group();
                string.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        LogUtil.d(url);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(linkColor);
                    }
                }, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            Matcher matcher2 = MENTION_PATTERN.matcher(text);
            while (matcher2.find()) {
                final String url = matcher2.group();
                string.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        LogUtil.d(url);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(mentionColor);
                    }
                }, matcher2.start(), matcher2.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }

        }
        return string;
    }
}
