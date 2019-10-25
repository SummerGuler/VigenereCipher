package edu.gatech.seclass.sdpencryptor;


import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class AssignmentExamples {

    @Rule
    public ActivityTestRule<SDPEncryptorActivity> tActivityRule = new ActivityTestRule<>(
            SDPEncryptorActivity.class);


    @Test
    public void Screenshot1() {
        onView(withId(R.id.messageInput)).perform(clearText(), replaceText("Cat&Dog"));
        onView(withId(R.id.keyPhrase)).perform(clearText(), replaceText("abc"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), replaceText("3"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.cipherText)).check(matches(withText("&EqgCbv")));
    }

    @Test
    public void Screenshot2() {
        onView(withId(R.id.messageInput)).perform(clearText(), replaceText("Up with the White And Gold!"));
        onView(withId(R.id.keyPhrase)).perform(clearText(), replaceText("abcabcxyz"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), replaceText("9"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.cipherText)).check(matches(withText("hf Wikqc Aof Hqib!Uq wjve s")));
    }

    @Test
    public void ScreenshotErrors1() {
        onView(withId(R.id.messageInput)).perform(clearText(), replaceText("123456"));
        onView(withId(R.id.keyPhrase)).perform(clearText(), replaceText("a"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), replaceText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.messageInput)).check(matches(hasErrorText("Message must contain letters")));
    }

    @Test
    public void ScreenshotErrors2() {
        onView(withId(R.id.messageInput)).perform(clearText(), replaceText("abc"));
        onView(withId(R.id.keyPhrase)).perform(clearText(), replaceText("abc543"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), replaceText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.keyPhrase)).check(matches(hasErrorText("Key Phrase must contain only letters")));
    }

    @Test
    public void ScreenshotErrors3() {
        onView(withId(R.id.messageInput)).perform(clearText(), replaceText("abc"));
        onView(withId(R.id.keyPhrase)).perform(clearText(), replaceText("abc543"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), replaceText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.keyPhrase)).check(matches(hasErrorText("Key Phrase must contain only letters")));
    }

    @Test
    public void ScreenshotErrors4() {
        onView(withId(R.id.messageInput)).perform(clearText(), replaceText("abc"));
        onView(withId(R.id.keyPhrase)).perform(clearText(), replaceText("abc543"));
        //0 is the default for shiftNumber
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.shiftNumber)).check(matches(hasErrorText("Shift Number must be >= 1")));
    }

    @Test
    public void ScreenshotLabel() {
        onView(withId(R.id.messageInput)).perform(clearText(), replaceText("Test Example !!"));
        onView(withId(R.id.keyPhrase)).perform(clearText(), replaceText("catdogbird"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), replaceText("22"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.cipherText)).check(matches(withText("idsne !!Velw Ky")));
    }

    @Test
    public void ExtraTest1() {
        onView(withId(R.id.messageInput)).perform(clearText(), typeText("Panda eats shoots and leaves."));
        onView(withId(R.id.keyPhrase)).perform(clearText(), replaceText("xyzabc"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), replaceText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.cipherText)).check(matches(withText("ymdb byss uemntt xlc mgxtds.M")));
    }

    @Test
    public void ExtraTest2() {
        onView(withId(R.id.keyPhrase)).perform(clearText(), replaceText("abc"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), replaceText("3"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.messageInput)).check(matches(hasErrorText("Message must contain letters")));
    }

}