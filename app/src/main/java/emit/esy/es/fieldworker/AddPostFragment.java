package emit.esy.es.fieldworker;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.Location;
import model.Post;

/**
 * Created by Emil Makovac on 30/03/2015.
 */
public class AddPostFragment extends Fragment {

    GPSHelper gpsHelper;
    Post post;
    Location location;
    String street, city;
    EditText et_title, et_desc, et_city, et_street;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.addpost_layout, container, false);

        et_title = (EditText) rootView.findViewById(R.id.post_title);
        et_desc = (EditText) rootView.findViewById(R.id.post_desc);
        et_city = (EditText) rootView.findViewById(R.id.location_city);
        et_street = (EditText) rootView.findViewById(R.id.location_address);

        Button getLocation = (Button) rootView.findViewById(R.id.btn_getLocation);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLatLng();

            }
        });

        Button savePost = (Button) rootView.findViewById(R.id.btn_savePost);
        savePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_title.getText().toString().length() <= 0 || et_desc.getText().toString().length() <= 0
                   || et_city.getText().toString().length() <= 0 || et_street.getText().toString().length() <= 0) {

                    Toast.makeText(getActivity(), "All fields must be filled", Toast.LENGTH_SHORT).show();

                } else {
                    getLatLng();
                    post = new Post();
                    post.setTitle(et_title.getText().toString());
                    post.setDescription(et_desc.getText().toString());
                    post.setDate(getCurrentDate());
                    location.setCity(et_city.getText().toString());
                    location.setStreet(et_street.getText().toString());
                    post.setLocation(location);
                    saveToFirebase(post);
                }
            }
        });

        return rootView;
    }

    private String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return df.format(date).toString();
    }

    private void saveToFirebase(Post post) {
        try {
            Firebase ref = new Firebase(getString(R.string.domain) + "posts/" + getUserId());
            Firebase pushref = ref.push();

            Map<String, Object> postMap = new HashMap<>();
            postMap.put("title", post.getTitle());
            postMap.put("date", post.getDate());
            postMap.put("desc", post.getDescription());
            Map<String, Object> locationMap = new HashMap<>();
            locationMap.put("city", post.getLocation().getCity());
            locationMap.put("street", post.getLocation().getStreet());
            locationMap.put("lat", post.getLocation().getLat());
            locationMap.put("long", post.getLocation().getLng());
            postMap.put("location", locationMap);

            pushref.setValue(postMap);

            Toast.makeText(getActivity(),"Post created", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getActivity(),"Error. Post not created", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLatLng() {
        city = et_city.getText().toString();
        street = et_street.getText().toString();

        if(city.equals("") && street.equals("")){
            Toast.makeText(getActivity(), "City and street must be filled", Toast.LENGTH_SHORT).show();
        } else {
            location = new Location();
            try {
                gpsHelper = new GPSHelper(getActivity().getBaseContext());
                gpsHelper.getLocationFromAddress(city + ", " + street);
                location.setLat(gpsHelper.getLatitude());
                location.setLng(gpsHelper.getLongitude());
            } catch (Exception e){
                Toast.makeText(getActivity(), "Coordinates not fetched. Some error ocured", Toast.LENGTH_SHORT).show();
                location.setLat(0.0);
                location.setLng(0.0);
            }

        }
    }

    private String getUserId() {
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences(getResources().getString(R.string.pref_file), Context.MODE_PRIVATE);
        return sharedPrefs.getString("id", getResources().getString(R.string.notLogged));
    }
}
