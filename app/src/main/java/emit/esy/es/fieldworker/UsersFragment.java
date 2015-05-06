package emit.esy.es.fieldworker;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import emit.esy.es.fieldworker.adapter.UserListAdapter;
import model.User;

/**
 * Created by Emil Makovac on 31/03/2015.
 */
public class UsersFragment extends Fragment {
    UserListAdapter adapter;
    ArrayList<User> list;
    ListView listUser;
    User u;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getActivity().getIntent();
        list = (ArrayList) i.getSerializableExtra("userList");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_layout, container, false);

        adapter = new UserListAdapter(getActivity(), list);

        listUser = (ListView) rootView.findViewById(R.id.list_view);
        listUser.setAdapter(adapter);
        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                Intent intent = new Intent(getActivity(), SingleActivity.class);
                u = (User) adapterView.getItemAtPosition(position);
                intent.putExtra("target", "user");
                intent.putExtra("object", u);
                startActivity(intent);
            }
        });

        return rootView;
    }
}

