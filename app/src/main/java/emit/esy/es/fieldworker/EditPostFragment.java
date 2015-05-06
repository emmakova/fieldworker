package emit.esy.es.fieldworker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Emil Makovac on 30/03/2015.
 */
public class EditPostFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.editpost_layout, container, false);

        return rootView;
    }
}
