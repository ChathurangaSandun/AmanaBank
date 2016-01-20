package com.clivekumara.amanabank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btLogin;

    DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dBhelper = new DBhelper(this);

        initComponent();



    }

    private void initComponent() {
        btLogin = (Button) findViewById(R.id.btlogin);
        btLogin.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btlogin:

                dBhelper.truncate();

                Branch b = new Branch(001,"Main branch",6.905825,79.851470,"480, Galle Road,Colombo 3","011-7756000","011-4718148");
                dBhelper.addBranch(b);
                b = new Branch(002,"Pettah",6.937672,79.851277,"129, Main Street, Colombo 11","011-7756000","011-4718148");
                dBhelper.addBranch(b);
                 b = new Branch(003,"Kandy",7.2955357,80.6355777,"480, Galle Road,Colombo 3","011-7756000","011-4718148");
                dBhelper.addBranch(b);
                 b = new Branch(004,"Kattankudy",7.6836273,81.7246413,"480, Galle Road,Colombo 3","011-7756000","011-4718148");
                dBhelper.addBranch(b);
                 b = new Branch(005,"Dehiwala",6.8618673,79.8615613,"28, Galle Road,Dehiwala","011-7756000","011-4718148");
                dBhelper.addBranch(b);





                Intent i = new Intent(getApplicationContext(),bankLocatorMap.class);
                startActivity(i);
                break;
        }
    }
}
