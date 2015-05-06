package emit.esy.es.fieldworker;

import android.os.Bundle;

/**
 * Created by Emil Makovac on 14/03/2015.
 *
 *
 * This activity will have 2 fragments, one for add/edit User, another to add/edit Post
 * Depending on intents extra, the correct fragment will be launched
 *
 * Intent example:  target: user / post
 *                  operation: add / edit
 *
 */
public class AddEditActivity extends BaseActivity {

    String operation, target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addedit_layout);

        operation = getIntent().getStringExtra("operation");
        target = getIntent().getStringExtra("target");

        setFragment(target, operation);

    }

    private void setFragment(String target, String operation) {
        if(target.equals("user")){
            if(operation.equals("add")){
                //start addUserFragment
                getFragmentManager().beginTransaction()
                        .add(R.id.addeditcontainer, new AddUserFragment())
                        .commit();

            } else {
                //start editUserFragment
                getFragmentManager().beginTransaction()
                        .add(R.id.addeditcontainer, new EditUserFragment())
                        .commit();
            }
        }
        // target equals post
        else{
            if(operation.equals("add")){
                //start addPostFragment
                getFragmentManager().beginTransaction()
                        .add(R.id.addeditcontainer, new AddPostFragment())
                        .commit();
            } else {
                //start editPostFragment
                getFragmentManager().beginTransaction()
                        .add(R.id.addeditcontainer, new EditPostFragment())
                        .commit();
            }
        }
    }



}
