package com.example.user.smartfoody.View;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;

import com.example.user.smartfoody.Adapter.ProducesAdapter;
import com.example.user.smartfoody.Fragment.Shop;
import com.example.user.smartfoody.Model.Produces;
import com.example.user.smartfoody.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 05/01/2018.
 */

public class AnimationTab implements TabHost.OnTabChangeListener
{

    private Context context;
    private static final int ANIMATION_TIME = 180;
    private TabHost tabHost;
    private View previousview;
    private View currentview;
    private int currentTab;





    public AnimationTab(Context context, TabHost tabHost) {
        this.context = context;
        this.tabHost = tabHost;
        this.previousview = tabHost.getCurrentView();
    }

    @Override
    public void onTabChanged(String s) {
        if (s.equals(context.getResources().getString(R.string.tab1)))
        {
            // list produces tab 1

        }
        else if (s.equals(context.getResources().getString(R.string.tab2)))
        {

        }
        else if (s.equals(context.getResources().getString(R.string.tab3)))
        {

        }
        else if (s.equals(context.getResources().getString(R.string.tab4)))
        {

        }

        //Animation
        currentview = tabHost.getCurrentView();
        if (tabHost.getCurrentTab()>currentTab)
        {
            previousview.setAnimation(outToleftAnimation());
            currentview.setAnimation(RightAnimation());
        }
        else
        {
            previousview.setAnimation(outToRightAnimation());
            currentview.setAnimation(LeftAnimation());
        }
        previousview = currentview;
        currentTab = tabHost.getCurrentTab();

    }

    // move in layout from right to left
    private Animation LeftAnimation() {
        Animation infromleft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                -1.0f,
                Animation.RELATIVE_TO_PARENT,
                0.0f,
                Animation.RELATIVE_TO_PARENT,
                0.0f,
                Animation.RELATIVE_TO_PARENT,
                0.0f);
        return setProperties(infromleft);
    }

    private Animation setProperties(Animation animation) {

        animation.setDuration(ANIMATION_TIME);
        animation.setInterpolator(new AccelerateInterpolator());
        return animation;
    }
    //move out layout from current to right
    private Animation outToRightAnimation() {
        Animation outToright = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                0.0f,
                Animation.RELATIVE_TO_PARENT,
                1.0f,
                Animation.RELATIVE_TO_PARENT,
                0.0f,
                Animation.RELATIVE_TO_PARENT,
                0.0f);
        return setProperties(outToright);
    }
    // move in layout from left to right
    private Animation RightAnimation() {
        Animation infromright = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                1.0f,
                Animation.RELATIVE_TO_PARENT,
                0.0f,
                Animation.RELATIVE_TO_PARENT,
                0.0f,
                Animation.RELATIVE_TO_PARENT,
                0.0f);
        return setProperties(infromright);
    }
    // move out layout from current to left
    private Animation outToleftAnimation() {
        Animation outtoleft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                0.0f,
                Animation.RELATIVE_TO_PARENT,
                -1.0f,
                Animation.RELATIVE_TO_PARENT,
                0.0f,
                Animation.RELATIVE_TO_PARENT,
                0.0f);
        return setProperties(outtoleft);
    }
}
