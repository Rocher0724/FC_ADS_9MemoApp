package choongyul.android.com.memoappp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class IdFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";

    public IdFragment() {
        // Required empty public constructor
    }
    public static IdFragment newInstance(int columnCount) {
        IdFragment fragment = new IdFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_id, container, false);
    }

}
