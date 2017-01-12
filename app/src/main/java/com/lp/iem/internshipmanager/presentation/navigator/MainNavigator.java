package com.lp.iem.internshipmanager.presentation.navigator;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lp.iem.internshipmanager.R;
import com.lp.iem.internshipmanager.presentation.ui.activity.BaseActivityLifeCycle;
import com.lp.iem.internshipmanager.presentation.ui.activity.CreateActivity;
import com.lp.iem.internshipmanager.presentation.ui.activity.MainActivity;
import com.lp.iem.internshipmanager.presentation.ui.fragment.StudentDetailsFragment;
import com.lp.iem.internshipmanager.presentation.ui.fragment.StudentListFragment;


/**
 * Created by romai on 09/01/2017.
 */

public class MainNavigator implements BaseActivityLifeCycle {
    public final static int FRAGMENT_STUDENT_LIST = 0;
    public final static int FRAGMENT_STUDENT_DETAILS = 1;

    private int currentFragmentId;

    private FragmentManager fragmentManager;
    private Activity activity;

    private StudentListFragment studentListFragment;

    public MainNavigator(Activity activity) {
        this.activity = activity;
        this.fragmentManager = ((MainActivity) activity).getSupportFragmentManager();
    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    public void onBackPressed() {
        if (currentFragmentId == FRAGMENT_STUDENT_DETAILS) {
            fragmentManager.popBackStack();
            studentListFragment.setActionBar();
            ((MainActivity) activity).onStudentDetailsBackPressed();
            currentFragmentId = FRAGMENT_STUDENT_LIST;
        }
    }

    public void displayStudentListFragment() {
        if (studentListFragment == null) {
            studentListFragment = StudentListFragment.newInstance();
        } else {
            fragmentManager.popBackStack();
        }
        fragmentTransactionReplace(studentListFragment);
        currentFragmentId = FRAGMENT_STUDENT_LIST;
    }

    public void displayStudentDetailsFragment(String studentId) {
        StudentDetailsFragment studentDetailsFragment = StudentDetailsFragment.newInstance(studentId);
        if (currentFragmentId != FRAGMENT_STUDENT_DETAILS) {
            if (isLandscape()) {
                fragmentTransactionAddOnSecond(studentDetailsFragment);
            } else {
                fragmentTransactionAdd(studentDetailsFragment);
            }
        } else {
            if (isLandscape()) {
                fragmentTransactionReplaceOnSecond(studentDetailsFragment);
            }
        }
        currentFragmentId = FRAGMENT_STUDENT_DETAILS;
    }

    public void displayCreateActivity(int fragmentType, String studentId) {
        Intent intent = new Intent(activity, CreateActivity.class);
        intent.putExtra(CreateActivity.FRAGMENT_TYPE_KEY, fragmentType);
        intent.putExtra(CreateActivity.STUDENT_ID_KEY, studentId);
        activity.startActivity(intent);
    }

    private Fragment getCurrentFragment() {
        return fragmentManager.findFragmentById(R.id.activity_main_fragment_container_one);
    }

    private void fragmentTransactionReplace(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_fragment_container_one, fragment, fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    private void fragmentTransactionAdd(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main_fragment_container_one, fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.commit();
    }

    private void fragmentTransactionReplaceOnSecond(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_fragment_container_two, fragment, fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    private void fragmentTransactionAddOnSecond(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main_fragment_container_two, fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    private boolean isLandscape() {
        return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
