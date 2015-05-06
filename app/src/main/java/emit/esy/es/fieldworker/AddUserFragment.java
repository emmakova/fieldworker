package emit.esy.es.fieldworker;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emil Makovac on 30/03/2015.
 */
public class AddUserFragment extends Fragment {

    EditText fName, lName, email, pass;
    Button save;
    CheckBox cb;

    Firebase ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.adduser_layout, container, false);

        fName = (EditText) rootView.findViewById(R.id.user_fName);
        lName = (EditText) rootView.findViewById(R.id.user_lName);
        email = (EditText) rootView.findViewById(R.id.user_email);
        pass = (EditText) rootView.findViewById(R.id.user_pass);
        cb = (CheckBox) rootView.findViewById(R.id.user_isAdmin);
        save = (Button) rootView.findViewById(R.id.btn_saveUser);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserToFirebase(fName.getText().toString(),
                                lName.getText().toString(),
                                email.getText().toString(),
                                pass.getText().toString(),
                                cb.isChecked());
            }
        });


        return rootView;
    }

    private void saveUserToFirebase(final String fName, final String lName, final String mail, final String pass, final boolean isAdmin) {

        if((fName.length() <= 0) || (lName.length() <= 0) || (email.length() <= 0) || (pass.length() <= 0)){
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
        } else {

            ref = new Firebase(getString(R.string.domain));
            Log.d("Add user fragment", "creating User");
            ref.createUser(mail, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {

                @Override
                public void onSuccess(Map<String, Object> result) {
                    Toast.makeText(getActivity(), "User created successfully", Toast.LENGTH_SHORT).show();
                    createUserData(result.get("uid").toString(), fName, lName, isAdmin);

                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    Toast.makeText(getActivity(), "An error ocured", Toast.LENGTH_SHORT).show();
                    Log.d("create user", firebaseError.toString());
                }
            });


        }
    }

    private void createUserData(String id, String fName, String lName, boolean isAdmin) {

        // Generate a reference to a users map
        Firebase usersRef = ref.child("users/" + id);

        String admin;
        //check boolean and cast to string
        if(isAdmin){
            admin = "1";
        } else {
            admin = "0";
        }
        Map<String, String> user = new HashMap<String, String>();
        user.put("fName", fName);
        user.put("lName", lName);
        user.put("isAdmin", admin);
        usersRef.setValue(user);

    }


}
