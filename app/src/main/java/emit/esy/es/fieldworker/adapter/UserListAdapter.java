package emit.esy.es.fieldworker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import emit.esy.es.fieldworker.R;
import model.User;

/**
 * Created by Emil Makovac on 31/03/2015.
 */
public class UserListAdapter extends ArrayAdapter<User> {

    public UserListAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        User user = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, null);
        }

        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.item_tv_first);
        //TextView tvOnline = (TextView) convertView.findViewById(R.id.item_tv_second);

        // Populate the data into the template view using the data object
        tvTitle.setText(user.getFullName());
        //tvOnline.setText(user.getLastTimeOnline());

        // Return the completed view to render on screen
        return convertView;
    }


}

