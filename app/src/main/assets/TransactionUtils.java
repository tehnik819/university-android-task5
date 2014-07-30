package com.noveogroup.internship.fragments.util;


import android.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * User: rzhilich
 * Date: 7/15/13
 */
public final class TransactionUtils {

    private TransactionUtils() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }


    /**
     * Replaces content of default container with given fragment.
     */
    public static void replace(final FragmentManager fragmentManager, final Fragment fragment,
        final boolean shouldBackstack, final String tag) {

        // Ensure there's no pending transactions for this fragment manager.
        fragmentManager.executePendingTransactions();

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(android.R.id.content, fragment, tag);
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (shouldBackstack) {transaction.addToBackStack(null);}
        transaction.commit();
    }

    public static void replace(final FragmentManager fragmentManager, final Fragment fragment,
        final boolean shouldBackstack) {

        replace(fragmentManager, fragment, shouldBackstack, null);
    }

    public static void replace(final FragmentManager fragmentManager, final Fragment fragment) {
        replace(fragmentManager, fragment, true, null);
    }

    public static void replace(final FragmentManager fragmentManager, final Fragment fragment, final String tag) {
        replace(fragmentManager, fragment, true, tag);
    }


    public static void add(final FragmentManager fragmentManager, final Fragment fragment) {
        fragmentManager.executePendingTransactions();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}