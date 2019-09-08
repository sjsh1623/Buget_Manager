package com.example.jeju;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

        import java.util.ArrayList;

public class Activity_Add extends AppCompatActivity {

    Button save;
    ArrayList<String> addArray = new ArrayList<String>();
    EditText txt;
    ListView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add);

        txt = (EditText) findViewById(R.id.amount);
        show = (ListView)findViewById(R.id.listview);
        save = (Button)findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getInput = txt.getText().toString();

                if(addArray.contains(getInput)) {
                    Toast.makeText(getBaseContext(), "Item Already Added", Toast.LENGTH_LONG).show();
                }
                else if (getInput == null || getInput.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Field is Empty", Toast.LENGTH_LONG).show();
                }
                else
                {
                    addArray.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_Add.this, android.R.layout.simple_list_item_1, addArray);
                    show.setAdapter(adapter);
                    ((EditText)findViewById(R.id.amount)).setText(" ");
                }
            }
        });
    }
}
