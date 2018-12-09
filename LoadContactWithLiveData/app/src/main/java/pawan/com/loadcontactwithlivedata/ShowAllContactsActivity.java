package pawan.com.loadcontactwithlivedata;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pawan.com.loadcontactwithlivedata.viewmodel.ContactViewModel;

public class ShowAllContactsActivity extends AppCompatActivity {
@BindView(R.id.rvAllRank)
    RecyclerView rvAllRank;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showallcontacts);
        ButterKnife.bind(this);

        rvAllRank.setLayoutManager(new LinearLayoutManager(this));
        ContactViewModel mModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        mModel.getContacts().observe(this, data -> {
            // update UI
            if(data!=null&&data.size()>0){
                ShowContactAdapter adapter=new ShowContactAdapter(ShowAllContactsActivity.this,data);
                rvAllRank.setAdapter(adapter);
            }
        });

    }



}
