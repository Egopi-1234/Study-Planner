package com.simats.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CalendartasksFragment extends Fragment {

    private EditText searchEditText;
    private TextView pythonTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate your fragment layout
        View view = inflater.inflate(R.layout.fragment_calendartasks, container, false);

        searchEditText = view.findViewById(R.id.search_edittext);
        pythonTextView = view.findViewById(R.id.pythontypt);

        // Listen for "Enter" key on the keyboard
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                                && event.getAction() == KeyEvent.ACTION_DOWN)) {

                    String query = searchEditText.getText().toString().trim();

                    // Open coursedetailspageActivity only if user types "Python"
                    if (query.equalsIgnoreCase("Python")) {
                        Intent intent = new Intent(getActivity(), coursedetailspageActivity.class);
                        intent.putExtra("query", query); // optional
                        startActivity(intent);
                    }

                    return true; // consume the event
                }
                return false;
            }
        });

        // Make the TextView clickable
        pythonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open coursedetailspageActivity when clicked
                Intent intent = new Intent(getActivity(), coursedetailspageActivity.class);
                intent.putExtra("query", "Python"); // optional
                startActivity(intent);
            }
        });

        return view;
    }
}
