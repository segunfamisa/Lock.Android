package com.auth0.android.lock.views;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.auth0.android.lock.BuildConfig;
import com.auth0.android.lock.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = Config.NONE)
public class ViewUtilsTest {

    @Test
    public void shouldConvertDipToPixels() throws Exception {
        Resources resources = Mockito.mock(Resources.class);

        DisplayMetrics metricsMDPI = new DisplayMetrics();
        metricsMDPI.density = 1;
        Mockito.when(resources.getDisplayMetrics()).thenReturn(metricsMDPI);
        assertThat(ViewUtils.dipToPixels(resources, 4), is(4f));

        DisplayMetrics metricsHDPI = new DisplayMetrics();
        metricsHDPI.density = 1.5f;
        Mockito.when(resources.getDisplayMetrics()).thenReturn(metricsHDPI);
        assertThat(ViewUtils.dipToPixels(resources, 4), is(6f));

        DisplayMetrics metricsXHDPI = new DisplayMetrics();
        metricsXHDPI.density = 2;
        Mockito.when(resources.getDisplayMetrics()).thenReturn(metricsXHDPI);
        assertThat(ViewUtils.dipToPixels(resources, 4), is(8f));

        DisplayMetrics metricsXXHDPI = new DisplayMetrics();
        metricsXXHDPI.density = 3;
        Mockito.when(resources.getDisplayMetrics()).thenReturn(metricsXXHDPI);
        assertThat(ViewUtils.dipToPixels(resources, 4), is(12f));
    }

    @Test
    public void shouldGetRoundedBackground() throws Exception {
        Resources resources = Mockito.mock(Resources.class);
        final int color = 339999;
        Mockito.when(resources.getDimensionPixelSize(R.dimen.com_auth0_lock_widget_corner_radius)).thenReturn(1);

        final ShapeDrawable backgroundAll = ViewUtils.getRoundedBackground(resources, color, ViewUtils.Corners.ALL);
        assertThat(backgroundAll.getPaint().getColor(), is(color));
        assertThat(backgroundAll.getShape(), is(notNullValue()));
        assertThat(backgroundAll.getShape(), is(instanceOf(RoundRectShape.class)));

        final ShapeDrawable backgroundLeft = ViewUtils.getRoundedBackground(resources, color, ViewUtils.Corners.ONLY_LEFT);
        assertThat(backgroundLeft.getPaint().getColor(), is(color));
        assertThat(backgroundLeft.getShape(), is(notNullValue()));
        assertThat(backgroundLeft.getShape(), is(instanceOf(RoundRectShape.class)));

        final ShapeDrawable backgroundRight = ViewUtils.getRoundedBackground(resources, color, ViewUtils.Corners.ONLY_RIGHT);
        assertThat(backgroundRight.getPaint().getColor(), is(color));
        assertThat(backgroundRight.getShape(), is(notNullValue()));
        assertThat(backgroundRight.getShape(), is(instanceOf(RoundRectShape.class)));
    }

    @Test
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void shouldSetBackgroundBeforeJellyBean() throws Exception {
        ReflectionHelpers.setStaticField(Build.VERSION.class, "SDK_INT", 15);

        final View view = Mockito.mock(View.class);
        final Drawable drawable = Mockito.mock(Drawable.class);
        ViewUtils.setBackground(view, drawable);

        Mockito.verify(view).setBackgroundDrawable(drawable);
    }

    @Test
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void shouldSetBackgroundAfterJellyBean() throws Exception {
        ReflectionHelpers.setStaticField(Build.VERSION.class, "SDK_INT", 16);

        final View view = Mockito.mock(View.class);
        final Drawable drawable = Mockito.mock(Drawable.class);
        ViewUtils.setBackground(view, drawable);

        Mockito.verify(view).setBackground(drawable);
    }

    @Test
    public void shouldMeasureViewHeight() throws Exception {
        final View view = Mockito.mock(View.class);
        final int height = 11;
        final int topMargin = 9;
        final int bottomMargin = 5;
        final int topPadding = 12;
        final int bottomPadding = 8;
        final int sum = height + topMargin + bottomMargin + bottomPadding + topPadding;
        final ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(0, 0);
        params.topMargin = topMargin;
        params.bottomMargin = bottomMargin;

        Mockito.when(view.isShown()).thenReturn(true);
        Mockito.when(view.getMeasuredHeight()).thenReturn(height);
        Mockito.when(view.getLayoutParams()).thenReturn(params);
        Mockito.when(view.getPaddingTop()).thenReturn(topPadding);
        Mockito.when(view.getPaddingBottom()).thenReturn(bottomPadding);

        assertThat(ViewUtils.measureViewHeight(view), is(sum));
    }

    @Test
    public void shouldNotMeasureViewHeightIfNotShown() throws Exception {
        final View view = Mockito.mock(View.class);
        final int height = 11;
        final int topMargin = 9;
        final int bottomMargin = 5;
        final int topPadding = 12;
        final int bottomPadding = 8;
        final ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(0, 0);
        params.topMargin = topMargin;
        params.bottomMargin = bottomMargin;

        Mockito.when(view.isShown()).thenReturn(false);
        Mockito.when(view.getMeasuredHeight()).thenReturn(height);
        Mockito.when(view.getLayoutParams()).thenReturn(params);
        Mockito.when(view.getPaddingTop()).thenReturn(topPadding);
        Mockito.when(view.getPaddingBottom()).thenReturn(bottomPadding);

        assertThat(ViewUtils.measureViewHeight(view), is(0));
    }

    @Test
    public void shouldNotMeasureViewHeightIfNull() throws Exception {
        final View view = null;

        assertThat(ViewUtils.measureViewHeight(view), is(0));
    }

    @Test
    public void shouldNotTintProgressBarBeforeLollipop() throws Exception {
        ReflectionHelpers.setStaticField(Build.VERSION.class, "SDK_INT", 20);

        ProgressBar progressBar = Mockito.mock(ProgressBar.class);
        final int color = 339999;
        ViewUtils.tintWidget(progressBar, color);
        Mockito.verifyNoMoreInteractions(progressBar);
    }

    @Test
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void shouldTintProgressBarAfterLollipop() throws Exception {
        ReflectionHelpers.setStaticField(Build.VERSION.class, "SDK_INT", 21);

        ProgressBar progressBar = Mockito.mock(ProgressBar.class);
        final int color = 339999;
        ViewUtils.tintWidget(progressBar, color);
        Mockito.verify(progressBar).setIndeterminateTintList(ColorStateList.valueOf(color));
    }
}