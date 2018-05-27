package me.limeice.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseLifeFragmentV4 extends Fragment {

    /**
     * Temporary solution: Must call super {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)} method.
     * <p>
     * <p>Added to LifeFragmentV4,{@link LifeFragmentV4#addFragment(Fragment)}
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null. To do: Please use custom view.
     */
    @Nullable
    @Override
    @CallSuper
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LifeFragmentV4.getLifeFragment(inflater.getContext()).addFragment(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Called when the view previously created by {@link #onCreateView} has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after {@link #onStop()} and before {@link #onDestroy()}.  It is called
     * <em>regardless</em> of whether {@link #onCreateView} returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     * <p>
     * <p>Remove this Fragment form LifeFragmentV4
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Context context = getContext();
        if (context != null)
            LifeFragmentV4.getLifeFragment(context).removeFragment(this);
    }
}
