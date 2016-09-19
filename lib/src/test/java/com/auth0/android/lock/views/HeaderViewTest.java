package com.auth0.android.lock.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auth0.android.lock.BuildConfig;
import com.auth0.android.lock.R;
import com.auth0.android.lock.internal.Theme;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static android.support.v4.content.ContextCompat.getColor;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = Config.NONE)
public class HeaderViewTest {

    private HeaderView headerView;
    private View header;
    private ImageView logo;
    private TextView text;

    private int defaultHeaderColor = 333333;
    private Drawable defaultHeaderLogo = new ColorDrawable();
    private String defaultHeaderTitle = "title";
    private int defaultHeaderTitleColor = 444444;

    @Before
    public void setUp() throws Exception {
        final Theme theme = mock(Theme.class);
        when(theme.getHeaderColor(any(Context.class))).thenReturn(defaultHeaderColor);
        when(theme.getHeaderLogo(any(Context.class))).thenReturn(defaultHeaderLogo);
        when(theme.getHeaderTitle(any(Context.class))).thenReturn(defaultHeaderTitle);
        when(theme.getHeaderTitleColor(any(Context.class))).thenReturn(defaultHeaderTitleColor);
        headerView = new HeaderView(RuntimeEnvironment.application, theme);
        header = headerView.findViewById(R.id.com_auth0_lock_header_background);
        logo = (ImageView) headerView.findViewById(R.id.com_auth0_lock_header_logo);
        text = (TextView) headerView.findViewById(R.id.com_auth0_lock_header_text);
    }

    @Test
    public void shouldHaveDefaultColor() throws Exception {
        final ColorDrawable background = (ColorDrawable) header.getBackground();
        assertThat(background.getColor(), is(defaultHeaderColor));
    }

    @Test
    public void shouldSetColor() throws Exception {
        headerView.setColor(R.color.com_auth0_lock_social_unknown);
        final ColorDrawable background = (ColorDrawable) header.getBackground();
        assertThat(background.getColor(), is(getColor(RuntimeEnvironment.application, R.color.com_auth0_lock_social_unknown)));
    }

    @Test
    public void shouldHaveDefaultLogo() throws Exception {
        assertThat(logo.getDrawable(), is(defaultHeaderLogo));
    }

    @Test
    public void shouldSetLogo() throws Exception {
        headerView.setLogo(R.drawable.com_auth0_lock_ic_clock);
        Drawable drawable = ContextCompat.getDrawable(RuntimeEnvironment.application, R.drawable.com_auth0_lock_ic_clock);
        assertThat(logo.getDrawable(), is(drawable));
    }

    @Test
    public void shouldHaveDefaultTitle() throws Exception {
        assertThat(text.getText().toString(), is(defaultHeaderTitle));
    }

    @Test
    public void shouldHaveTitleColor() throws Exception {
        assertThat(text.getCurrentTextColor(), is(444444));
    }

    @Test
    public void shouldSetTitle() throws Exception {
        headerView.setTitle(R.string.com_auth0_lock_sign_up_terms);
        String titleText = RuntimeEnvironment.application.getString(R.string.com_auth0_lock_sign_up_terms);
        assertThat(text.getText().toString(), is(titleText));
    }

    @Test
    public void shouldShowTitleByDefault() throws Exception {
        assertThat(text.getVisibility(), is(View.VISIBLE));
    }

    @Test
    public void shouldChangeTitleVisibility() throws Exception {
        headerView.showTitle(false);
        assertThat(text.getVisibility(), is(View.GONE));

        headerView.showTitle(true);
        assertThat(text.getVisibility(), is(View.VISIBLE));
    }

    @Test
    public void shouldChangeHeaderTopPadding() throws Exception {
        headerView.setPaddingTop(123);
        assertThat(header.getPaddingTop(), is(123));
    }

}