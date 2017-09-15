package com.advanced.demo.contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by sangw on 2017/9/14.
 */

public class ReadContactActivity extends BaseActivity {
    private final static String TAG = "ReadContact";
    private final static int REQUEST_CODE_READ_CONTACT = 1001;
    private final static int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2001;
    private Button mReadContact;
    private TextView mContentContact;

    @Override
    protected void initPages() {
        super.initPages();
    }

    @Override
    protected void initView() {
        super.initView();
        mReadContact = (Button) findViewById(R.id.btn_read_contacts);
        mContentContact = (TextView) findViewById(R.id.content_contact);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mReadContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ReadContactActivity.this, Manifest.permission.READ_CONTACTS)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(ReadContactActivity.this,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                    intent.putExtra("js_callback", "callback_js_contact");
                    startActivityForResult(intent, REQUEST_CODE_READ_CONTACT);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_READ_CONTACT:
                    if (data != null) {
                        StringBuilder contactBuilder = new StringBuilder();
                        Uri uri = data.getData();
                        if (uri != null) {
                            Cursor cursor = getContentResolver().query(uri,
                                    new String[]{ ContactsContract.CommonDataKinds.Phone.NUMBER,
                                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME },
                                    null, null, null);
                            if (cursor != null) {
                                while (cursor.moveToNext()) {
                                    String number = cursor.getString(0);
                                    String name = cursor.getString(1);
                                    contactBuilder.append("name: ").append(name).append("\n");
                                    contactBuilder.append("phoneNumber: ").append(number);
                                }
                                cursor.close();
                            }
                        }
                        String jsCallback = data.getExtras().getString("js_callback");
                        mContentContact.setText(contactBuilder.toString() + "\njscallback" + (TextUtils.isEmpty(jsCallback) ? "" : jsCallback));
                    }

                    break;
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                    startActivityForResult(intent, REQUEST_CODE_READ_CONTACT);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    mContentContact.setText("no permission read contacts");
                }
                break;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_read_contact;
    }
}
