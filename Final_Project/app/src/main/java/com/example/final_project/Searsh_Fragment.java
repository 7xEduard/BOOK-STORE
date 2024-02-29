package com.example.final_project;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.AdaptorPack.topBooksAdaptor;
import com.example.final_project.Model.TopBooks;
import com.example.final_project.AdaptorPack.topBooksAdaptor;
import com.example.final_project.Book_Database;
import com.example.final_project.Book_Templete;
import com.example.final_project.Model.TopBooks;
import com.example.final_project.RecyclerViewInterface;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Searsh_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Searsh_Fragment extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
     List<TopBooks> itemList=new ArrayList<TopBooks>();
     topBooksAdaptor h;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Searsh_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Searsh_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Searsh_Fragment newInstance(String param1, String param2) {
        Searsh_Fragment fragment = new Searsh_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_searsh, container, false);
        Book_Database b= Book_Database.getInstance(getActivity());
        itemList=b.fetchOneBook("all");
        ImageButton qr = (ImageButton) view.findViewById(R.id.qr_Code);
        ImageButton voice = (ImageButton) view.findViewById(R.id.voice_Ser);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVoice();
            }
        });
        RecyclerView recyclerView=view.findViewById(R.id.searchOutput);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        SearchView searchView=view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }

        });
        h=new topBooksAdaptor(getActivity(),itemList,this,"all");
        recyclerView.setAdapter(h);
        return view;
    }

    private void filterList(String text) {
        List<TopBooks> filteredList=new ArrayList<TopBooks>();
        for(TopBooks topBooks:itemList){
            if(topBooks.getBookName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(topBooks);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
        }else{
            h.setFilteredList(filteredList);
        }
    }

    @Override
    public void onItemClick(int position, String category) {
        Intent intent = new Intent(getActivity(), Book_Templete.class);
        Book_Database b= Book_Database.getInstance(getActivity());
        Cursor c1=b.fetchOneAuthor(b.fetchOneBook("all").get(position).getAuthorName());
        Cursor c2=b.fetchBooksByName(b.fetchOneBook("all").get(position).getBookName());
        intent.putExtra("bookId",c2.getString(0));
        intent.putExtra("bookName", b.fetchOneBook("all").get(position).getBookName());
        intent.putExtra("bookCategory",c2.getString(2));
        intent.putExtra("publishDate",c2.getString(3));
        intent.putExtra("description",c2.getString(4));
        intent.putExtra("language",c2.getString(5));
        intent.putExtra("chapters",c2.getString(6));
        intent.putExtra("pages",c2.getString(7));
        intent.putExtra("parts",c2.getString(8));
        intent.putExtra("counter",c2.getString(9));
        intent.putExtra("bookLink",c2.getString(11));
        intent.putExtra("imageLink", b.fetchOneBook("all").get(position).getImage());
        intent.putExtra("authorName",b.fetchOneBook("all").get(position).getAuthorName());
        intent.putExtra("authorNationality",c1.getString(1));
        intent.putExtra("noOfBooks",c1.getString(2));
        intent.putExtra("price",c2.getString(12));
        intent.putExtra("edition",c2.getString(13));
        intent.putExtra("availability",c2.getString(14));
        intent.putExtra("popularity",c2.getString(15));
        startActivity(intent);
    }

    private void openVoice() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent,200);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200&&resultCode==RESULT_OK)
        {
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voice = arrayList.get(0);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+voice));
            startActivity(intent);
            //Toast.makeText(getActivity(), voice, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(), "Some thing went wrong", Toast.LENGTH_SHORT).show();
        }
    }
    private void scanCode() {

        ScanOptions options = new ScanOptions();
        options.setPrompt("Press volume up key to flash on");
        options.setBeepEnabled(true);
        options.setCaptureActivity(CaptureAct.class);

        barLancher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLancher = registerForActivityResult(new ScanContract(), result->{
        if (result.getContents() != null)
        {
            //String url = "https://www.google.com";
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getContents()));
//            startActivity(intent);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+result.getContents()));
                    startActivity(intent);
                    //dialogInterface.dismiss();
                }
            }).show();
        }
    });
}