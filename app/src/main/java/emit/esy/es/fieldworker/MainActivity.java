package emit.esy.es.fieldworker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.firebase.client.Firebase;


public class MainActivity extends Activity {

    String isAdmin;
    private SharedPreferences sharedPrefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        /*
        * check if in preferences exist userId
        * if does not exist
        *   startloginFragment
        * else
        *   getUserId from preferences and start PostsActivity for this user
        */
        sharedPrefs = getSharedPreferences(getResources().getString(R.string.pref_file), MODE_PRIVATE);

        String uid = sharedPrefs.getString("id", getResources().getString(R.string.notLogged));

        if(uid.equals(getResources().getString(R.string.notLogged))){
            // hide actionbar
            getActionBar().hide();
            // start LogInFragment
             if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .add(R.id.container, new LoginFragment())
                        .commit();
            }
        } else {
            isAdmin = sharedPrefs.getString("isAdmin", "0");

            Intent i = new Intent(this, PostsActivity.class);
            i.putExtra("userId", uid);
            startActivity(i);
        }

    }

}
