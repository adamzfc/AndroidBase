package com.adamzfc.androidbase.test.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adamzfc.androidbase.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;

/**
 * Created by adamzfc on 4/21/17.
 */

public class TestFragmentActivity extends FragmentActivity {
    @SuppressWarnings("membername")
//    private final static Logger logger = LoggerFactory.getLogger(TestFragmentActivity.class);
    private Logger logger;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);
        logger = LoggerFactory.getLogger(TestFragmentActivity.class);
        logger.info("recreate");
        DetailsFragment details = (DetailsFragment) getSupportFragmentManager().findFragmentByTag("details");
        if (details != null) {
            getSupportFragmentManager().popBackStack();
            logger.info("remove");
//            getSupportFragmentManager().beginTransaction()
//                    .remove(details)
//                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//        loggerContext.stop();
    }

    public static class DetailsActivity extends FragmentActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE) {
                // If the screen is now in landscape mode, we can show the
                // dialog in-line with the list so we don't need this activity.
                finish();
                return;
            }

            if (savedInstanceState == null) {
                // During initial setup, plug in the details fragment.
                DetailsFragment details = new DetailsFragment();
                details.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
            }
        }
    }

    public static class TitlesFragment extends ListFragment {
        boolean mDualPane;
        int mCurCheckPosition = 0;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // Populate list with our static array of titles.
            setListAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_activated_1, Shakespeare.TITLES));

            // Check to see if we have a frame in which to embed the details
            // fragment directly in the containing UI.
            View detailsFrame = getActivity().findViewById(R.id.details);
            mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

            if (savedInstanceState != null) {
                // Restore last state for checked position.
                mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
            }

            if (mDualPane) {
                // In dual-pane mode, the list view highlights the selected item.
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                // Make sure our UI is in the correct state.
                showDetails(mCurCheckPosition);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("curChoice", mCurCheckPosition);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            showDetails(position);
            Log.d("TitlesFragment", "onListItemClick");
        }

        /**
         * Helper function to show the details of a selected item, either by
         * displaying a fragment in-place in the current UI, or starting a
         * whole new activity in which it is displayed.
         */
        void showDetails(int index) {
            mCurCheckPosition = index;

            if (mDualPane) {
                // We can display everything in-place with fragments, so update
                // the list to highlight the selected item and show the data.
                getListView().setItemChecked(index, true);

                // Check what fragment is currently shown, replace if needed.
                DetailsFragment details = (DetailsFragment)
                        getFragmentManager().findFragmentById(R.id.details);
                if (details == null || details.getShownIndex() != index) {
                    // Make new fragment to show this selection.
                    details = DetailsFragment.newInstance(index);

                    // Execute a transaction, replacing any existing fragment
                    // with this one inside the frame.
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    if (index == 0) {
                        ft.replace(R.id.details, details);
//                    } else {
//                        ft.replace(R.id.a_item, details);
//                    }
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }

            } else {
                // Otherwise we need to launch a new activity to display
                // the dialog fragment with selected text.
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), DetailsActivity.class);
//                intent.putExtra("index", index);
//                startActivity(intent);
                DetailsFragment details = new DetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("index", index);
                details.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(android.R.id.content, details, "details")
                        .addToBackStack(null).commit();
            }
        }
    }

    public static class DetailsFragment extends Fragment {

        /**
         * Create a new instance of DetailsFragment, initialized to
         * show the text at 'index'.
         */
        public static DetailsFragment newInstance(int index) {
            DetailsFragment f = new DetailsFragment();

            // Supply index input as an argument.
            Bundle args = new Bundle();
            args.putInt("index", index);
            f.setArguments(args);
            return f;
        }

        public int getShownIndex() {
            return getArguments().getInt("index", 0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (container == null) {
                // We have different layouts, and in one of them this
                // fragment's containing frame doesn't exist.  The fragment
                // may still be created from its saved state, but there is
                // no reason to try to create its view hierarchy because it
                // won't be displayed.  Note this is not needed -- we could
                // just run the code below, where we would create and return
                // the view hierarchy; it would just never be used.
                return null;
            }

            ScrollView scroller = new ScrollView(getActivity());
            TextView text = new TextView(getActivity());
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    4, getActivity().getResources().getDisplayMetrics());
            text.setPadding(padding, padding, padding, padding);
            scroller.addView(text);
            text.setText(Shakespeare.DIALOGUE[getShownIndex()]);
            scroller.setBackgroundResource(android.R.color.white);
            return scroller;
        }

    }
}
