package e.dav.distanceconverter;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity{


    private static final String TAG = "ListDataActivity";
    DataManager mdbManager;
    ListView mListView;

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView= (ListView)findViewById(R.id.listView);
        mdbManager = new DataManager(this);

        populateListView();

    }

    private void populateListView() {
        Log.d(TAG,"populateListView: displaying data in the list view");

        //get the data and append to list
        Cursor data = mdbManager.selectAll();
        PopulateViewtask pvt = new PopulateViewtask();
        pvt.execute(data);





    }


    class PopulateViewtask extends AsyncTask<Cursor, String, SimpleCursorAdapter>{
        ProgressDialog progressDialog;

        @Override
        protected SimpleCursorAdapter doInBackground(Cursor... cursors) {
            Cursor data= cursors[0];
            publishProgress("Fetching Data from DataBase...");


            String[] fromFieldNames = new String[] {DataManager.TABLE_ROW_PLACEA, DataManager.TABLE_ROW_PLACEB, DataManager.TABLE_ROW_DIST};
            int[] toViewIds = new int[] {R.id.textViewPLaceA,R.id.textViewPlaceB, R.id.textViewDistance};
            SimpleCursorAdapter myCursorAdapter;
            myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.row_layout, data, fromFieldNames,toViewIds,0);
            return myCursorAdapter;
        }



        @Override
        protected void onPostExecute(SimpleCursorAdapter result){

            mListView.setAdapter(result);
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(ListDataActivity.this, "please wait",
                    Toast.LENGTH_LONG).show();
        }




    }


}
