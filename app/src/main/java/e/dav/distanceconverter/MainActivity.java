package e.dav.distanceconverter;



import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   // private EditText id;
    private EditText distance;
    private EditText placeA;
    private EditText placeB;
    private Button btncalc;
    private Button btnsave;
    private Button btnDelete;
    private Button btnSelect;
    private Button btnSearch;
    private Button btnviewData;

    private EditText editDelete;
    private EditText editSearch;
    distanceAB d;

    // This is our DataManager instance
    DataManager dm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        distance = findViewById(R.id.editdist);
        placeA = findViewById(R.id.editplca);
        placeB = findViewById(R.id.editplcb);
        editDelete = findViewById(R.id.editDelete);
        editSearch = findViewById(R.id.editSearch);

        dm = new DataManager(this);

        btncalc = (Button)findViewById(R.id.btn_calc);
        btnsave = (Button)findViewById(R.id.btn_save);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnviewData = (Button) findViewById(R.id.btnView);



        btnsave.setOnClickListener(this);
        btncalc.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnviewData.setOnClickListener(this);


    }

    // Output the cursor contents to the log
    public void showData(Cursor c){
        while (c.moveToNext()){
            Log.i(c.getString(1), c.getString(2));
        } }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_calc:
                RadioButton kmButton = findViewById(R.id.radioButton2);
                RadioButton mButton = findViewById(R.id.radioButton);
                if (distance.getText().length() == 0) {
                    Toast.makeText(this, "Please enter a valid number",
                            Toast.LENGTH_LONG).show();
                    return;
                }else if (distance.getText().length() > 6) {
                    Toast.makeText(this, "too many digits",
                            Toast.LENGTH_LONG).show();
                    return;

                }

                double inputValue = Double.parseDouble(distance.getText().toString());
                if (kmButton.isChecked()) {
                    distance.setText(ConverterUtil.convertMilesToKilometers(inputValue));
                    kmButton.setChecked(false);
                    mButton.setChecked(true);
                } else {
                    distance.setText(ConverterUtil.convertKilometersToMiles(inputValue));
                    mButton.setChecked(false);
                    kmButton.setChecked(true);
                }
                break;


            case R.id.btn_save:
                d = new distanceAB();
                d.setPlaceA(placeA.getText().toString());
                d.setPlaceB(placeB.getText().toString());
                d.setDistance(distance.getText().toString());
                dm.insert(d);
                Toast.makeText(this, "saved",
                        Toast.LENGTH_LONG).show();
                break;


            case R.id.btnDelete:
                dm.delete(editDelete.getText().toString());
                Toast.makeText(this, "deleted",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.btnSelect:
                showData(dm.selectAll());
                break;

            case R.id.btnSearch:
                showData(dm.searchName(editSearch.getText().toString()));
                break;


            case R.id.btnView:
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
        }

    }




}
