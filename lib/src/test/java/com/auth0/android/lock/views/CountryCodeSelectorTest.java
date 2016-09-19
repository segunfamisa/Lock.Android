package com.auth0.android.lock.views;

import android.widget.TextView;

import com.auth0.android.lock.BuildConfig;
import com.auth0.android.lock.R;
import com.auth0.android.lock.adapters.Country;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = Config.NONE)
public class CountryCodeSelectorTest {

    private CountryCodeSelectorView countryCodeSelectorView;
    private TextView countryNameTextView;
    private TextView countryCodeTextView;

    @Before
    public void setUp() throws Exception {
        countryCodeSelectorView = new CountryCodeSelectorView(RuntimeEnvironment.application);
        countryNameTextView = (TextView) countryCodeSelectorView.findViewById(R.id.com_auth0_lock_country_name);
        countryCodeTextView = (TextView) countryCodeSelectorView.findViewById(R.id.com_auth0_lock_country_code);
    }

    @Test
    public void shouldHaveDefaultCountry() throws Exception {
        final Country selectedCountry = countryCodeSelectorView.getSelectedCountry();
        assertThat(selectedCountry.getDialCode(), is("+1"));
        assertThat(selectedCountry.getDisplayName(), is("United States"));
        assertThat(selectedCountry.getIsoCode(), is("US"));
    }

    @Test
    public void shouldSetCountry() throws Exception {
        final Country country = mock(Country.class);
        Mockito.when(country.getDisplayName()).thenReturn("Argentina");
        Mockito.when(country.getDialCode()).thenReturn("+54");
        countryCodeSelectorView.setSelectedCountry(country);
        assertThat(countryCodeSelectorView.getSelectedCountry(), is(country));
        assertThat(countryNameTextView.getText().toString(), is("Argentina"));
        assertThat(countryCodeTextView.getText().toString(), is("+54"));
    }
}