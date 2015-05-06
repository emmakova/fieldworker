package emit.esy.es.fieldworker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

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
public class UsersActivity extends BaseActivity {

    HashMap<String, HashMap<String, String>> usersMap;
    HashMap<String,String> userMap;
    ArrayList<User> userList;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(getString(R.string.domain) + "users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                 //get map with String userId and the userMap defining a user
                usersMap = (HashMap) snapshot.getValue();
                Log.d("usersMap Count", Integer.toString(usersMap.size()));
               userList = new ArrayList<User>();
                for (Map.Entry<String, HashMap<String, String>> entry : usersMap.entrySet()) {
                    user = new User();
                    String userId = entry.getKey();
                    Log.d("UserID", userId);
                    // Dont need to see logged admin listed
                    if (!userId.equals(getAdminId())) {
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
                getIntent().putExtra("userList", userList);
                getFragmentManager().beginTransaction()
                        .add(R.id.loading_container, new UsersFragment())
                        .commit();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("UsersActivity", "The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private String getAdminId() {
        SharedPreferences sharedPrefs =  getSharedPreferences(getResources().getString(R.string.pref_file), Context.MODE_PRIVATE);
        return sharedPrefs.getString("id", getResources().getString(R.string.notLogged));
    }

}

