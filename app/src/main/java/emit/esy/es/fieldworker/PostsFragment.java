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

import emit.esy.es.fieldworker.adapter.PostListAdapter;
import model.Post;

/**
 * Created by Emil Makovac on 31/03/2015.
 */
public class PostsFragment extends Fragment {

    PostListAdapter adapter;
    ArrayList<Post> list;
    ListView listPost;
    Post p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getActivity().getIntent();
        list = (ArrayList) i.getSerializableExtra("postList");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_layout, container, false);

        adapter = new PostListAdapter(getActivity(), list);

        listPost = (ListView) rootView.findViewById(R.id.list_view);
        listPost.setAdapter(adapter);
        listPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                Intent intent = new Intent(getActivity(), SingleActivity.class);
                p = (Post) adapterView.getItemAtPosition(position);
                intent.putExtra("target", "post");
                intent.putExtra("object", p);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
