package com.manoranjank.studentdiary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapter.TimeTableAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MyFragment extends Fragment implements FragmentListener {
    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM = "param";
    public String fragmentName;
    private List<Subject> subjects = new ArrayList<>();
    private TimeTableAdapter timeTableAdapter;


    @BindView(R.id.list)
    RecyclerView list;


    public MyFragment() {
    }

    public static MyFragment newInstance(String param) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        fragmentName = getArguments().getString(ARG_PARAM);
        timeTableAdapter = new TimeTableAdapter(subjects, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(list.getContext(), ((LinearLayoutManager) mLayoutManager).getOrientation()));
        list.setAdapter(timeTableAdapter);
        return view;
    }


    @Override
    public void populateList(String subjectName, String time, String day) {

        subjects.add(new Subject(subjectName, time, day));
        timeTableAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateList(int i) {
        subjects.remove(i);
        timeTableAdapter.notifyDataSetChanged();
    }


    @Override
    public String getFragmentName() {
        return fragmentName;
    }
}