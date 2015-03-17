package emit.esy.es.fieldworker;

import android.app.Activity;
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

import model.Location;
import model.Post;
import model.User;

/**
 * Created by Emil Makovac on 15/03/2015.
 */
public class PostsActivity extends Activity {

    boolean isAdmin;
    User loggedUser;

    HashMap<String, HashMap<String, Object>> postsMap;
    HashMap<String,Object> postMap;
    HashMap<String,Object> locationMap;
    ArrayList<Post> postList;
    Post post;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        loggedUser = (User) extras.getSerializable("loggedUser");
        isAdmin = loggedUser.isAdmin();

        Firebase ref = new Firebase(buildUri(loggedUser.getId()));

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                //get map with String userId and the userMap defining a user
                postsMap = (HashMap) snapshot.getValue();
                Log.d("postsMap Count", Integer.toString(postsMap.size()));
                post = new Post();
                postList = new ArrayList<Post>();
                for (Map.Entry<String, HashMap<String, Object>> entry : postsMap.entrySet()) {
                    String postId = entry.getKey();
                    post.setId(postId);
                    postMap = entry.getValue();
                    for (Map.Entry<String, Object> e : postMap.entrySet()) {
                        String key = e.getKey();
                        switch (key) {
                            case "title":
                                post.setTitle((String) postMap.get(key));
                                Log.d("Post title", post.getTitle());
                                break;
                            case "date":
                                post.setDate((String) postMap.get(key));
                                Log.d("Post date", post.getDate());
                                break;
                            case "desc":
                                post.setDescription((String) postMap.get(key));
                                Log.d("Post desc", post.getDescription());
                                break;
                            case "location":
                                location = new Location();
                                locationMap = (HashMap<String, Object>) e.getValue();
                                for (Map.Entry<String, Object> locationEntry : locationMap.entrySet()) {
                                    String locationKey = locationEntry.getKey();
                                    switch (locationKey){
                                        case "city":
                                            location.setCity((String) locationMap.get(locationKey));
                                            Log.d("Location city", location.getCity());
                                            break;
                                        case "street":
                                            location.setStreet((String) locationMap.get(locationKey));
                                            Log.d("Location street", location.getStreet());
                                            break;
                                        case "lat":
                                            location.setLat((Long) locationMap.get(locationKey));
                                            Log.d("Location lat", location.getLat().toString());
                                            break;
                                        case "long":
                                            location.setLng((Long) locationMap.get(locationKey));
                                            Log.d("Location long", location.getLng().toString());
                                            break;
                                    }
                                }
                                post.setLocation(location);
                                break;

                        }
                    }
                    postList.add(post);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("PostsActivity", "The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private String buildUri(String id) {
        Log.d("BuildUri","https://fieldworker.firebaseio.com/posts/" + id);
        return "https://fieldworker.firebaseio.com/posts/" + id;

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

