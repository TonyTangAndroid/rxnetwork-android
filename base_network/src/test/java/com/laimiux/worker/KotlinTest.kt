package com.laimiux.worker

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KotlinTest {
  @Test
  fun setActiveNetworkInfo_shouldBeHonored() {
    assertThat("1").isNotEqualTo(1)
  }
}