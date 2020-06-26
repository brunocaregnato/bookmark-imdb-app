package com.example.bookmarkimdb.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmarkimdb.R;
import com.example.bookmarkimdb.ui.utils.AdapterHome;
import com.example.bookmarkimdb.ui.models.Watched;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private List<Watched> watchedList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        watchedList = new ArrayList<Watched>();

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        this.setRecyclerView(root);

        return root;
    }

    private void setRecyclerView(View view){
        recyclerView = view.findViewById(R.id.homeFragment);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));

        final AdapterHome adapterHome = new AdapterHome(watchedList);
        recyclerView.setAdapter(adapterHome);
        adapterHome.notifyDataSetChanged();

        //aqui chamar os detalhes
//        recyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(getContext().getApplicationContext(), recyclerView ,
//                        (view1, position) ->
//                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                                new Details(historyList.get(position).getId())).commit())
//        );
    }
}
