package emit.esy.es.fieldworker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;

import model.User;


public class MainActivity extends Activity {

    boolean isAdmin;
    User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        /*
        * check shared preferences for User with GSON
        * user = sharedpreferences(getUser)
        * if(user == null)
        *   startLoginFragment
        * else
        *   startPostActivity with user embedded in intent
        *
        */

        loggedUser = new User("-JkOLRo6OfbK7izKd8VK", "emil", "makovac", "emil.makovac@gmail.com", "pass", true);

        isAdmin = loggedUser.isAdmin();
        Intent i = new Intent(this, PostsActivity.class);
        i.putExtra("loggedUser", loggedUser);
        startActivity(i);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(isAdmin)
            getMenuInflater().inflate(R.menu.menu_admin, menu);
        else
            getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_addPost:
                break;
            case R.id.action_addUser:
                break;
            case R.id.action_viewProfile:
                break;
            case R.id.action_showUsers:
                break;
            case R.id.action_logout:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
