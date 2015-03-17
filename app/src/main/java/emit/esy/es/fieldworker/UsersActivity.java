package emit.esy.es.fieldworker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.User;

/**
 * Created by Emil Makovac on 14/03/2015.
 */
public class UsersActivity extends Activity {

    HashMap<String, HashMap<String, String>> usersMap;
    HashMap<String,String> userMap;
    ArrayList<User> userList;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase ref = new Firebase("https://fieldworker.firebaseio.com/users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                //get map with String userId and the userMap defining a user
                usersMap = (HashMap) snapshot.getValue();
                Log.d("usersMap Count", Integer.toString(usersMap.size()));
                user = new User();
                userList = new ArrayList<User>();
                for (Map.Entry<String, HashMap<String, String>> entry : usersMap.entrySet()) {
                    String userId = entry.getKey();
                    user.setId(userId);
                    userMap = entry.getValue();
                    for (Map.Entry<String, String> e : userMap.entrySet()) {
                        String key = e.getKey();
                        switch (key) {
                            case "fName":
                                user.setfName((String) userMap.get(key));
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
                    userList.add(user);
                    Log.d("User", user.getId().toString() + " " + user.getfName());
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("UsersActivity", "The read failed: " + firebaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        if (id == R.id.action_addUser) {
            Intent i = new Intent(this, AddEditUserActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}

