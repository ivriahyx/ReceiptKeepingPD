package c346.rp.edu.sg.receiptkeepingdatabase;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Button btnAdd;
    CommentsDataSource datasource;
    ArrayList<Comment> al;
    ArrayAdapter<Comment> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lv = (ListView)findViewById(R.id.listView);
        btnAdd = (Button)findViewById(R.id.btnAdd);


        datasource = new CommentsDataSource(this);
        al = new ArrayList<Comment>();
        al.addAll(datasource.getAllComments());

        aa = new ArrayAdapter<Comment>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);


        //EntryClick
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intentItem = new Intent(MainActivity.this, EntryClicked.class);
                Comment comment = al.get(position);
                intentItem.putExtra("mode", "Edit");
                intentItem.putExtra("id", comment.getId());
                intentItem.putExtra("content", comment.getContent());
                intentItem.putExtra("date", comment.getDate());
                intentItem.putExtra("priority", comment.getPriority());
                intentItem.putExtra("category", comment.getCategory());
                intentItem.putExtra("image", comment.getImage());
                startActivity(intentItem);

            }
        });

        //Add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EntryActivity.class);
                i.putExtra("mode", "Add");
                startActivityForResult(i, 0);
            }
        });
        registerForContextMenu(lv);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            al.clear();
            al.addAll(datasource.getAllComments());
            aa.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//Context Menu
    @Override public void onCreateContextMenu(ContextMenu menu, View v,
                                              ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.main, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo;
        menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = menuInfo.position;
        Comment comment = al.get(index);
        if (item.getItemId() == R.id.cmEdit) {
            Intent i = new Intent(MainActivity.this, EntryActivity.class);
            i.putExtra("mode", "Edit");
            i.putExtra("id", comment.getId());
            i.putExtra("content", comment.getContent());
            i.putExtra("date", comment.getDate());
            i.putExtra("priority", comment.getPriority());
            i.putExtra("category", comment.getCategory());
            i.putExtra("image", comment.getImage());
            startActivityForResult(i, 0);
        } else if (item.getItemId() == R.id.cmDelete) {
            datasource.deleteComment(comment.getId());
            al.clear();
            al.addAll(datasource.getAllComments());
            aa.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }


}
