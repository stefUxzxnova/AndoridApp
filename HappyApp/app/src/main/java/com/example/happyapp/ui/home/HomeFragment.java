package com.example.happyapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.happyapp.R;
import com.example.happyapp.ShoppingListDetails;
import com.example.happyapp.databinding.FragmentHomeBinding;
import com.example.happyapp.localDatabase.DatabaseHelper;
import com.example.happyapp.localDatabase.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ListView listView;
    private List<ShoppingList> shoppingLists;
    private CustomListAdapter adapter;
    private Button btnSearch;
    private EditText editTextSearch;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnSearch = root.findViewById(R.id.btnSearch);
        editTextSearch = root.findViewById(R.id.editTextSearch);
        // ListView with the id listView in fragment_home layout
        listView = root.findViewById(R.id.listView);

        // Populate userList with your data (e.g., from a database)
        shoppingLists = fillListView();

        // Set up the adapter
        adapter = new CustomListAdapter(requireContext(), shoppingLists);

        // Set the adapter to the ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ID = ((TextView)view.findViewById(R.id.textViewId)).getText().toString();
                String title = ((TextView)view.findViewById(R.id.shoppingListTitle_tv)).getText().toString();
                String totalPrice = ((TextView)view.findViewById(R.id.shoppingListPrice)).getText().toString();

                Intent intent = new Intent(requireActivity(), ShoppingListDetails.class);
                Bundle b = new Bundle();
                b.putString("ID", ID);
                b.putString("TITLE", title);
                b.putString("TOTALPRICE", totalPrice);

                intent.putExtras(b);
                startActivityForResult(intent, 200, b);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByTitle();
            }
        });
        return root;
    }

    private List<ShoppingList> searchByTitle() {
        try (DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext())){
            List<ShoppingList> shoppingLists = db.selectByTitle(editTextSearch.getText().toString());
            return shoppingLists;
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        return new ArrayList<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<ShoppingList> fillListView(){
        try (DatabaseHelper db = new DatabaseHelper(getActivity().getApplicationContext())){
            List<ShoppingList> shoppingLists = db.select();
            return shoppingLists;
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        return new ArrayList<>();
    }
}