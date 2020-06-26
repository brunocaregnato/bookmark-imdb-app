package com.example.bookmarkimdb.ui.search;

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

import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private RecyclerView recyclerView;
    private List<Object> searchList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        final TextView textView = root.findViewById(R.id.text_gallery);
        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

//        final AdapterSearch adapter = new AdapterSearch(searchList);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

        //aqui chamar os detalhes
//        recyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(getContext().getApplicationContext(), recyclerView ,
//                        (view1, position) ->
//                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                                new Details(historyList.get(position).getId())).commit())
//        );
    }
}
