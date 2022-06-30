package com.example.demo;

import static com.google.common.truth.Truth.assertThat;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AndroidAppTest {
    @Test
    public void useAppContext() {
        assertThat(InstrumentationRegistry.getInstrumentation().getTargetContext().getPackageName()).isEqualTo("com.laimiux.samples");
        assertThat(ApplicationProvider.getApplicationContext().getPackageName()).isEqualTo("com.laimiux.samples");
    }
}