package emit.esy.es.fieldworker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import model.Location;
import model.Post;

/**
 * Created by Emil Makovac on 02/04/2015.
 */
public class LocationFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    Post post;
    Location location;

    TextView tv_title, tv_city, tv_address, tv_email, tv_phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        post = (Post) getActivity().getIntent().getSerializableExtra("object");
        location = post.getLocation();
        Log.d("PostFragment", "Post title:" + post.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_layout, container, false);



        tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        tv_city = (TextView) rootView.findViewById(R.id.tv_city);
        tv_address = (TextView) rootView.findViewById(R.id.tv_address);
        //tv_email = (TextView) rootView.findViewById(R.id.tv_mail);
        //tv_phone = (TextView) rootView.findViewById(R.id.tv_phone);

        if (location.getLat() == 0.0 || location.getLng() == 0.0) {
            ((ViewGroup) tv_address.getParent()).removeView(rootView.findViewById(R.id.map));
        } else {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        tv_title.setText(post.getTitle());
        tv_city.setText(location.getCity());
        tv_address.setText(location.getStreet());
        //tv_email.setText(location.getEmail());
        //tv_phone.setText(location.getPhone());

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d("LocationFragment", "onMapReady");

            LatLng coords = new LatLng(location.getLat(), location.getLng());

            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 15));

            map.addMarker(new MarkerOptions()
                    .title(post.getTitle())
                    .snippet(location.getCity() + ", " + location.getStreet())
                    .position(coords));
        }

}
