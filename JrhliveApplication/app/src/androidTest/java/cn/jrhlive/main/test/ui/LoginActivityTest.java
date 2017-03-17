package cn.jrhlive.main.test.ui;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cn.jrhlive.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testLogin(){

        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
        onView(withId(R.id.name)).perform(typeText("android"),closeSoftKeyboard());
        onView(withId(R.id.psd)).perform(typeText("12345d6"),closeSoftKeyboard());

        onView(withId(R.id.btn_login)).perform(click());


    }



}