package emit.esy.es.fieldworker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

import model.User;

/**
 * Created by Emil Makovac on 17/03/2015.
 */
public class BaseActivity extends Activity {

    private SharedPreferences sharedPrefs = null;
    String isAdmin;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        sharedPrefs = getSharedPreferences(getResources().getString(R.string.pref_file), MODE_PRIVATE);
        isAdmin = sharedPrefs.getString("isAdmin", "0");
        Log.d("BaseActivity", isAdmin);
        // Inflate the menu; this adds items to the action bar if it is present.
        if(isAdmin.equals("1"))
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
        sharedPrefs = getSharedPreferences(getResources().getString(R.string.pref_file), MODE_PRIVATE);

        int id = item.getItemId();
        Intent i;
        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_showPosts:
                i = new Intent(this, PostsActivity.class);
                i.putExtra("userId", getUserId());
                startActivity(i);
                break;
            case R.id.action_addPost:
                i = new Intent(this, AddEditActivity.class);
                i.putExtra("target", "post");
                i.putExtra("operation", "add");
                startActivity(i);
                break;
            case R.id.action_addUser:
                i = new Intent(this, AddEditActivity.class);
                i.putExtra("target", "user");
                i.putExtra("operation", "add");
                startActivity(i);
                break;
            case R.id.action_viewProfile:
                String uid = getUserId();
                getUserAndStartActivity(uid);
                break;
            case R.id.action_showUsers:
                i = new Intent(this, UsersActivity.class);
                startActivity(i);
                break;
            case R.id.action_logout:
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("isAdmin", "0");
                editor.putString("id", getResources().getString(R.string.notLogged));
                editor.commit();

                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void getUserAndStartActivity(final String uid) {
        Firebase ref = new Firebase(getString(R.string.domain) + "users/" + uid);
        final User user = new User();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> userMap = (HashMap) dataSnapshot.getValue();
                user.setId(uid);

                for (String key: userMap.keySet()) {
                    switch (key) {
                        case "fName":
                            user.setfName((String) userMap.get(key));
                            Log.d("fname", userMap.get(key));
                            break;
                        case "lName":
                            user.setlName((String) userMap.get(key));
                            break;
                        case "mail":
                            user.setMail((String) userMap.get(key));
                            break;
                        case "password":
                            user.setPassword((String) userMap.get(key));
                            break;
                        case "isAdmin":
                            user.setAdmin((String) userMap.get(key));
                            break;
                    }
                }

                Intent i = new Intent(getBaseContext(), SingleActivity.class);
                i.putExtra("target", "user");
                i.putExtra("object", user);
                startActivity(i);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }

    private String getUserId() {
        return sharedPrefs.getString("id", getResources().getString(R.string.notLogged));
    }
}
