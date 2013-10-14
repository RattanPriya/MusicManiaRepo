package com.example.mymusicshare;

//import com.example.hw9.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

//import com.example.alert_test.MainActivity;


//import com.example.hw9.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	 public static String url= null;
	 public static String output;
	 public static String choice = null;
	 public static JSONArray myresult;
	 
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 TextView t; 
		 	t = (TextView) findViewById(R.id.editText1);
		String path[] = new String[] {"Artists","Albums","Songs"};
		setContentView(R.layout.activity_main);
		final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,path);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		
		final Button button = (Button)findViewById(R.id.facebook);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			
			
			@Override
			public void onClick(View v) {
				 EditText text = (EditText)findViewById(R.id.editText1);
        		 String myText = text.getText().toString().replace(" ", "+");
        		 if( myText.length() == 0 )
        		 {  
        				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        		 
        					// set title
        					alertDialogBuilder.setTitle("Error");
        		 
        					// set dialog message
        					alertDialogBuilder
        						.setMessage("Please enter your search")
        						.setCancelable(false)
        						.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
        							public void onClick(DialogInterface dialog,int id) {
        								// if this button is clicked, close
        								// current activity
	        								dialog.cancel();
        							}
        						  })
        						;
        		 
        						// create alert dialog
        						AlertDialog alertDialog = alertDialogBuilder.create();
        		 
        						// show it
        						alertDialog.show();
        			 
        			 
        		 }
        		 else{
        		  choice = (String) spinner.getItemAtPosition(spinner.getSelectedItemPosition()) ;
        		 if(choice.equalsIgnoreCase("Artists")){
        		url ="http://cs-server.usc.edu:27598/myapp/HomeServlet?URL="+ myText+"&myselect=artists";}
        		 else if(choice.equalsIgnoreCase("Albums"))
        		 {url ="http://cs-server.usc.edu:27598/myapp/HomeServlet?URL="+ myText+"&myselect=albums";}
        		 else if(choice.equalsIgnoreCase("Songs"))
        		 {url ="http://cs-server.usc.edu:27598/myapp/HomeServlet?URL="+ myText+"&myselect=songs";}
 		        
        	    
				
                
       		  Intent myIntent = new Intent(MainActivity.this,ListAct.class);
              myIntent.putExtra("myServletURL",url);
       		  myIntent.putExtra("myTitle",choice);
              startActivityForResult(myIntent,0);

                     		 }

				// TODO Auto-generated method stub
				
			}
		
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
///////////////////////////CLASS : HTTPREQUEST ASYNC TASK BEGINS////////////////////

