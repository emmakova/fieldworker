package emit.esy.es.fieldworker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import model.Post;

/**
 * Created by Emil Makovac on 30/03/2015.
 */
public class PostFragment extends Fragment {

    Post post;
    TextView tv_title, tv_date, tv_desc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        post = (Post) getActivity().getIntent().getSerializableExtra("object");
        Log.d("PostFragment", "Post title:" + post.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.post_layout, container, false);

        tv_title = (TextView)rootView.findViewById(R.id.title);
        tv_date = (TextView) rootView.findViewById(R.id.date);
        tv_desc = (TextView) rootView.findViewById(R.id.tv_desc);

        tv_title.setText(post.getTitle());
        tv_date.setText(post.getDate());
        tv_desc.setText(post.getDescription());

        return rootView;
    }
}
