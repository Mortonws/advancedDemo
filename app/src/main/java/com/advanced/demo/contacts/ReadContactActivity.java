package com.advanced.demo.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
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
//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                startActivityForResult(intent, REQUEST_CODE_READ_CONTACT);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, REQUEST_CODE_READ_CONTACT);
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
//                        Uri contact = data.getData();
//                        ContentResolver contentResolver = getContentResolver();
//                        Cursor cursor = contentResolver.query(contact, null, null, null, null);
//                        if (cursor != null) {
//                            while (cursor.moveToNext()) {
//                                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//
//                                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                                contactBuilder.append("name:").append(name).append("\n");
//                                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                                            null,
//                                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                                            new String[]{id},
//                                            null);
//                                    if (phoneCursor != null) {
//                                        while (phoneCursor.moveToNext()) {
//                                            String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                                            contactBuilder.append("phoneNumber:").append(phone).append("\n");
//                                        }
//                                        phoneCursor.close();
//                                    }
//                                }
//                            }
//                            cursor.close();
//                        }
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
                        mContentContact.setText(contactBuilder.toString());
                    }

                    break;
            }
        }
    }

    //获取联系人电话
    private String getContactPhone(Cursor cursor) {

        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String phoneResult = "";
        //System.out.print(phoneNum);
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人的电话号码的cursor;
            Cursor phones = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            //int phoneCount = phones.getCount();
            //allPhoneNum = new ArrayList<String>(phoneCount);
            if (phones.moveToFirst()) {
                // 遍历所有的电话号码
                for (; !phones.isAfterLast(); phones.moveToNext()) {
                    int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phones.getInt(typeindex);
                    String phoneNumber = phones.getString(index);
                    switch (phone_type) {
                        case 2:
                            phoneResult = phoneNumber;
                            break;
                    }
                    //allPhoneNum.add(phoneNumber);
                }
                if (!phones.isClosed()) {
                    phones.close();
                }
            }
        }
        return phoneResult;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_read_contact;
    }
}
