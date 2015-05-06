package emit.esy.es.fieldworker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import emit.esy.es.fieldworker.R;
import model.Post;

/**
 * Created by Emil Makovac on 31/03/2015.
 */
public class PostListAdapter extends ArrayAdapter<Post> {

    public PostListAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Post post = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, null);
        }

        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.item_tv_first);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.item_tv_second);

        // Populate the data into the template view using the data object
        tvTitle.setText(post.getTitle());
        tvDesc.setText(post.getDescription());

        // Return the completed view to render on screen
        return convertView;
    }


}
