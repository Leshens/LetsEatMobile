import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.firebase.auth.FirebaseAuth
import com.leshen.letseatmobile.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.leshen.letseatmobile.R

class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var auth: FirebaseAuth

    @Before
    fun setup() {
        auth = FirebaseAuth.getInstance()
        // Upewnij się, że użytkownik jest wylogowany przed testem
        auth.signOut()
    }

    @After
    fun cleanup() {
        // Wyloguj użytkownika po teście, aby przywrócić pierwotny stan
        auth.signOut()
    }

    @Test
    fun testLoginAndLogout() {
        // Kliknij przycisk "Get Started"
        onView(withId(R.id.cvGetStarted)).perform(click())

        // Kliknij pole tekstowe "Email" i wpisz adres e-mail
        onView(withId(R.id.tilEmail)).perform(replaceText("css@gmail.com"))

        // Kliknij pole tekstowe "Password" i wpisz hasło
        onView(withId(R.id.tilPassword)).perform(replaceText("Karolina123!"))

        // Kliknij przycisk "Sign In"
        onView(withId(R.id.btnSignIn)).perform(click())

        // Kliknij przycisk "Profile"
        onView(withId(R.id.profile)).perform(click())

        // Kliknij przycisk "Sign Out"
        onView(withId(R.id.signOutButton1)).perform(click())
    }
}
