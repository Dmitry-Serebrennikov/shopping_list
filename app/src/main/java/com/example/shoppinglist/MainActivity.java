package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView shoppingsListView;

    SQLiteDatabase shoppingListDatabase;
    SimpleCursorAdapter simpleCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        shoppingListDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = shoppingListDatabase.rawQuery("SELECT * FROM shopping_list", null);

        String[] from = new String[] {"item", "price"};
        int[] to = new int[] { R.id.item_name, R.id.price};
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.shopping_list_item, cursor, from, to, 0);

        shoppingsListView = findViewById(R.id.shopping_list);
        shoppingsListView.setAdapter(simpleCursorAdapter);

        updateShoppingsCount();
    }

    public void onClick(View view){
        EditText item = findViewById(R.id.input_item);
        EditText price =findViewById(R.id.input_price);

        ContentValues values = new ContentValues();
        values.put("item", String.valueOf(item.getText()));
        values.put("price", String.valueOf(price.getText()));
        shoppingListDatabase.insert("shopping_list", null, values);

        Cursor cursor = shoppingListDatabase.rawQuery("SELECT * FROM shopping_list", null);

        simpleCursorAdapter.changeCursor(cursor);
        simpleCursorAdapter.notifyDataSetChanged();

        updateShoppingsCount();

        item.setText("");
        price.setText("");
    }

    private void updateShoppingsCount() {
        Cursor cursor = shoppingListDatabase.rawQuery("SELECT * FROM shopping_list", null);
        TextView itemCount = findViewById(R.id.itemCount);
        itemCount.setText("Количество покупок: "+ cursor.getCount());
        cursor = shoppingListDatabase.rawQuery("SELECT SUM(`price`) AS 'totalPrice' FROM shopping_list", null);
        TextView totalPrice = findViewById(R.id.totalPrice);
        //Log.d("mytag", "cursorCount: " + cursor.getCount());
        //Log.d("mytag", "columnIndex: " + cursor.getColumnIndex("totalPrice"));
        cursor.moveToFirst();
        totalPrice.setText("Общая стоимость: " + cursor.getDouble(cursor.getColumnIndex("totalPrice")) + " рублей");
    }
}