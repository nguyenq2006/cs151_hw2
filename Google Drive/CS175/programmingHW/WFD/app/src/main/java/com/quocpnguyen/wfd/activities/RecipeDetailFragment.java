package com.quocpnguyen.wfd.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quocpnguyen.wfd.R;
import com.quocpnguyen.wfd.Utils.RecipeContent;
import com.quocpnguyen.wfd.models.Recipe;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Recipe mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = RecipeContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.recipe_name)).setText(mItem.getName());
            ((TextView) rootView.findViewById(R.id.recipe_description)).setText("Description \n" + mItem.getDescription());
            String ingredients = "Ingredients";
            for(String s : mItem.getIngredients().split(",")){
                ingredients += "- " + s.trim() + "\n";
            }
            ((TextView) rootView.findViewById(R.id.recipe_ingredients)).setText(ingredients);
            ImageView img_view = (ImageView) rootView.findViewById(R.id.recipe_img);
            Picasso.with(getContext()).load(mItem.getImg_src()).into(img_view);
        }

        return rootView;
    }
}
