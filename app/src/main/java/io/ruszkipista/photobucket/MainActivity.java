package io.ruszkipista.photobucket;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final PhotoBucketAdapter movieQuoteAdapter = new PhotoBucketAdapter();
        recyclerView.setAdapter(movieQuoteAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_photobucket,null,false);
        builder.setTitle(R.string.dialog_title_add);
        builder.setView(view);
        final EditText captionEditTextView = view.findViewById(R.id.dialog_caption_field);
        final EditText urlEditTextView = view.findViewById(R.id.dialog_url_field);

        builder.setNegativeButton(android.R.string.cancel,null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String quote = captionEditTextView.getText().toString();
                String movie = urlEditTextView.getText().toString();

//                  create new item with captured details
                Map<String, Object> movieQuote = new HashMap< >();
                movieQuote.put(Constants.KEY_CAPTION,quote);
                movieQuote.put(Constants.KEY_URL,movie);
                movieQuote.put(Constants.KEY_CREATED, new Date());
                FirebaseFirestore.getInstance().collection(Constants.firebase_collection_pb).add(movieQuote);
            }
        });
        builder.create().show();
    }

}
