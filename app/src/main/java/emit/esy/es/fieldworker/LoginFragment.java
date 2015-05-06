package emit.esy.es.fieldworker;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by Emil Makovac on 22/03/2015.
 */
public class LoginFragment extends Fragment {

    EditText uName, uPass;
    Button btn_login;
    TextView label;
    private SharedPreferences sharedPrefs = null;
    SharedPreferences.Editor editor;
    Firebase ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);

        sharedPrefs = this.getActivity().getSharedPreferences(getResources().getString(R.string.pref_file), Context.MODE_PRIVATE);

        label = (TextView) rootView.findViewById(R.id.label);
        label.setTextColor(Color.RED);
        uName = (EditText) rootView.findViewById(R.id.uName);
        uPass = (EditText) rootView.findViewById(R.id.uPass);
        btn_login = (Button) rootView.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!haveNetworkConnection()){
                    // If no internet connection
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.no_net_title);
                    builder.setMessage(R.string.no_net_msg);
                    builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(i);
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    // get username and password
                    String user_name = String.valueOf(uName.getText());
                    String password = String.valueOf(uPass.getText());
                    if (user_name.length() <= 0 || password.length() <= 0) {
                        label.setText("*All fields are required");
                    } else {
                        // authenticate to firebase, if authentication succeed retrive user id and isAdmin
                        // and save to SharedPreferences. After that, start PostsActivity with userId embedded
                        // in Intent
                        ref = new Firebase(getString(R.string.domain));
                        ref.authWithPassword(user_name, password, new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                String id = authData.getUid();
                                Log.d("LoginFragment", "User ID: " + id);

                                editor = sharedPrefs.edit();
                                setPrefsAndStartActivity(id, editor);
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                // there was an error
                                label.setText("*Wrong username or password");
                            }
                        });
                    }
                }
            }
                   });
        return rootView;
    }

    private void setPrefsAndStartActivity(final String id, final SharedPreferences.Editor editor) {
        Firebase userRef = ref.child("users/" + id);
        Log.d("id", id);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // do some stuff once
                Map<String, String> user= (Map<String, String>) snapshot.getValue();
                String admin = user.get("isAdmin");
                Log.d("isAdmin", admin);
                editor.putString("isAdmin", admin);
                editor.putString("id", id);
                editor.commit();
                Intent i = new Intent(getActivity(), PostsActivity.class);
                i.putExtra("userId", id);
                startActivity(i);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                if (ni.isConnected()) {
                    haveConnectedWifi = true;
                }
            }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (ni.isConnected()) {
                    haveConnectedMobile = true;
                }
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}

