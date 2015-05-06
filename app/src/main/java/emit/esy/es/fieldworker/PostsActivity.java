package emit.esy.es.fieldworker;

import android.os.Bundle;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Location;
import model.Post;

/**
 * Created by Emil Makovac on 15/03/2015.
 */
public class PostsActivity extends BaseActivity {

    boolean isAdmin;
    String uid;

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
        setContentView(R.layout.loading_layout);
        Bundle extras = getIntent().getExtras();
        uid =  extras.getString("userId");

        Firebase ref = new Firebase(buildUri(uid));

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                    //get map with String userId and the userMap defining a user
                    postsMap = (HashMap) snapshot.getValue();
                    Log.d("postsMap Count", Integer.toString(postsMap.size()));
                    postList = new ArrayList<Post>();
                    for (Map.Entry<String, HashMap<String, Object>> entry : postsMap.entrySet()) {
                        String postId = entry.getKey();
                        post = new Post();
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
                                        switch (locationKey) {
                                            case "city":
                                                location.setCity((String) locationMap.get(locationKey));
                                                Log.d("Location city", location.getCity());
                                                break;
                                            case "street":
                                                location.setStreet((String) locationMap.get(locationKey));
                                                Log.d("Location street", location.getStreet());
                                                break;
                                            case "lat":
                                                location.setLat((Double) locationMap.get(locationKey));
                                                Log.d("Location lat", location.getLat().toString());
                                                break;
                                            case "long":
                                                location.setLng((Double) locationMap.get(locationKey));
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
                try{
                    getIntent().putExtra("postList", postList);
                    getFragmentManager().beginTransaction()
                            .add(R.id.loading_container, new PostsFragment())
                            .commit();
                } catch(Exception e){
                    Log.d("PostsActivity", e.toString());
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
        return getString(R.string.domain) + "posts/" + id;

    }
}

