package emit.esy.es.fieldworker;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import model.User;

/**
 * Created by Emil Makovac on 30/03/2015.
 */
public class UserFragment extends Fragment {

    User user;
    TextView fullName, mail, bDate, address;
    Button btn_deleteUser, btn_showUserPosts;
    Firebase ref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getActivity().getIntent().getSerializableExtra("object");
        Log.d("UserFragment", user.getFullName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("UserFragment", "onCreateView");
        View rootView = inflater.inflate(R.layout.user_layout, container, false);

        fullName = (TextView) rootView.findViewById(R.id.fName);
        mail = (TextView) rootView.findViewById(R.id.mail);
        //bDate = (TextView) rootView.findViewById(R.id.date);
        //address = (TextView) rootView.findViewById(R.id.address);

        if(user.getId().equals(getUserId())){
            LinearLayout btn_layout = (LinearLayout)rootView.findViewById(R.id.btn_layout);
            ((ViewGroup)btn_layout.getParent()).removeView(btn_layout);
        } else {

            btn_deleteUser = (Button) rootView.findViewById(R.id.btn_deleteUser);
            btn_showUserPosts = (Button) rootView.findViewById(R.id.btn_showUserPosts);

            btn_showUserPosts.setOnClickListener(clickListener);
            btn_deleteUser.setOnClickListener(clickListener);

        }

        fullName.setText(user.getFullName());
        mail.setText(user.getMail());
        //bDate.setText(user.getBDate());
        //address.setText(user.getAddress());

        return rootView;
    }

    Button.OnClickListener clickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.btn_showUserPosts:
                    Intent i = new Intent(getActivity(), PostsActivity.class);
                    i.putExtra("userId", user.getId());
                    startActivity(i);
                    break;
                case R.id.btn_deleteUser:
                    ref = new Firebase(getString(R.string.domain));
                    ref.removeUser(user.getMail(), user.getPassword(), new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getActivity(), "User deleted successfully", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(getActivity(), "An error ocured", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };

    private String getUserId() {
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences(getResources().getString(R.string.pref_file), Context.MODE_PRIVATE);
        return sharedPrefs.getString("id", getResources().getString(R.string.notLogged));
    }
}
