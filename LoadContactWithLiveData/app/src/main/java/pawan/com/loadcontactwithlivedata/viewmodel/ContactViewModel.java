package pawan.com.loadcontactwithlivedata.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ContactViewModel extends AndroidViewModel {

    private final MutableLiveData<Map<String,String>> data = new MutableLiveData<>();

    public ContactViewModel(@NonNull Application application) {
        super(application);
        loadData();
    }

    public LiveData<Map<String,String>> getContacts(){
        return data;
    }

    private void loadData() {
        new AsyncTask<Void,Void,Map<String,String>>() {
            @Override
            protected Map<String,String> doInBackground(Void... voids) {
                Map<String,String> data = fetchContacts();
                return data;
            }
            @Override
            protected void onPostExecute(Map<String,String> data) {
               ContactViewModel.this.data.setValue(data);
            }
        }.execute();
    }

    private Map<String,String>  fetchContacts() {
        Map<String,String> data = new HashMap<>();
        ContentResolver cr =getApplication().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        data.put(name,phoneNo);
                       // Logger.i("Name: "+name + " Phone No: " + phoneNo, "Phone No: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        return data;
    }

}
