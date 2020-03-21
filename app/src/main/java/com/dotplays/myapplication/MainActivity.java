package com.dotplays.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void showContacts(View view) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    999);
        } else {
            getContacts();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 999) {
            // nguoi dung an No
            if (grantResults[0] < 0) {
                Toast.makeText(this, "De doc danh ban, ban can cap phep Read Contacts cho ung dung nay", Toast.LENGTH_SHORT).show();
                // yes
            } else {
                getContacts();
            }
        }

    }

    private void getContacts() {

        Uri uri = Uri.parse("content://contacts/people");

        ArrayList<String> strings = new ArrayList<>();

        CursorLoader cursorLoader = new CursorLoader(MainActivity.this,
                uri, null, null,
                null, null);
        Cursor c1 = cursorLoader.loadInBackground();
        if (c1 != null & c1.getCount() > 0) {
            Toast.makeText(this,
                    "There are some contacts here@@",
                    Toast.LENGTH_SHORT).show();

            c1.moveToFirst();

            while (c1.isAfterLast() == false) {
                String data = "";
                String id =
                        c1.getString(c1.getColumnIndex
                                (ContactsContract.Contacts._ID));
                String name = c1.getString(
                        c1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                data = id + " - " + name;
                strings.add(data);
                c1.moveToNext();
            }
            c1.close();
            // in ra 1 danh sach danh ba


        } else {
            Toast.makeText(this,
                    "There arent any contacts here@@",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
