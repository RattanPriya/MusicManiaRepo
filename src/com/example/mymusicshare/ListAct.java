package com.example.mymusicshare;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.mymusicshare.CustomListViewAdapter;
import com.example.mymusicshare.MainActivity;
import com.example.mymusicshare.R;
import com.example.mymusicshare.ListAct.ListViewItem;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ToggleButton;

public class ListAct extends Activity {
	
    public static ArrayList<String>	 cover =  new ArrayList<String>();

    public static ArrayList<String>	 name =  new ArrayList<String>();

    public static ArrayList<String>	 genre =  new ArrayList<String>();

    public static ArrayList<String>	 year =  new ArrayList<String>();

    public static ArrayList<String>	 details =  new ArrayList<String>();

    public static ArrayList<String>	 title =  new ArrayList<String>();

    public static ArrayList<String>	 performer =  new ArrayList<String>();

    public static ArrayList<String>	 composer =  new ArrayList<String>();

    public static String choice;
    public static String url;
    public static List<ListViewItem> items;
    public Facebook facebook;
    
    public class ListViewItem
	{
    
		public
		int ThumbnailResource;
		String name;
		String genre;
		String year;
		String cover;
		String title;
		String performer;
		String song;
		String composer;
		
		
	}
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.list);
		 EditText text = (EditText)findViewById(R.id.editText1);
		 Bundle bundle = getIntent().getExtras();
		   ListView mainListView ;  

		 url = bundle.getString("myServletURL");
		 choice = bundle.getString("myTitle");
		 
 		 Log.i("ACTIVITY2",choice);
	     HttpRequestTask newobj = new HttpRequestTask();
		 newobj.execute(url);
		 
		 //////////////////////////////////////////

		//	ListView lv = (ListView) findViewById(R.id.listView1);
		/*	 items = new ArrayList<ListAct.ListViewItem>();
			
			items.add(new ListViewItem()
			{{
				ThumbnailResource = R.drawable.ic_launcher;
				name = "item1";
				genre = "Item Description";

			}});/*
			items.add(new ListViewItem()
			{{
				ThumbnailResource = R.drawable.ic_launcher;
				Log.i("NAME",ListAct.name.get(1));
				name = ListAct.name.get(1);
				genre = "Item Description";

			}});
			CustomListViewAdapter adapter = new CustomListViewAdapter(ListAct.this,items);
			lv.setAdapter(adapter);

*/
/////////////CPY LINE BY LINE//
		 
	    // TODO Auto-generated method stub
	}




class HttpRequestTask extends AsyncTask<String, Void, String> {
	ListAct obj = null;
	Bitmap bitmap=null;
	public int i=0;
	 @Override
	
	public String doInBackground(String... url) {
		String geturl = new String();
		geturl = url[0];
    	String html = null;
    	String sendurl = geturl.toString();
    	 HttpResponse responseGet = null;
 
	try{
	 HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(geturl.toString());
 		HttpResponse response = client.execute(request);
	    html = "";
        InputStream in = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder str = new StringBuilder();
        String line = null;
        while((line = reader.readLine()) != null)
        {
            str.append(line);
        }
        in.close();
        html = str.toString();
        
	}
	 catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   Log.i("My link:", html);
        return html;
}
    protected void onPostExecute(String html) {
    	MainActivity context =null;
    	JSONObject json;
    	JSONObject json_array;
    	final ListAct obj = null ;
				try {
					
			     	 json = new JSONObject(html);
					Log.i("One level------",json.getString("results"));
					String json_Str = json.getString("results");
					json_array = new JSONObject(json_Str);
					
					JSONArray jsonr =  json_array.getJSONArray("result");
					Integer ij;
					ij=jsonr.length();
					if(ij.toString().equalsIgnoreCase("0"))
					{
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListAct.this);
		        		 
    					// set title
    					alertDialogBuilder.setTitle("Error");
    		 
    					// set dialog message
    					alertDialogBuilder
    						.setMessage("No Results Found!")
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
					
					if(obj.choice.equalsIgnoreCase("artists"))
					{
						
						for(int i=0;i<ij ;i++)
						{
							Log.i("Artists"," LOOP THE ARRAY FOR ARTISTS BEGINS...");
							JSONObject artists_obj = jsonr.getJSONObject(i);
						    obj.cover.add(artists_obj.getString("cover"));
						    obj.name.add(artists_obj.getString("name"));
							obj.genre.add(artists_obj.getString("genre"));
							obj.year.add(artists_obj.getString("year"));
							obj.details.add(artists_obj.getString("details"));
                            
							Integer j = obj.name.size();
							Log.i("......yay.......",j.toString());
						}
						   						ListView lv = (ListView) findViewById(R.id.listView1);
						 items = new ArrayList<ListAct.ListViewItem>();
						 i=0;
						 for( i=0;i< cover.size();i++)
						{
						 items.add(new ListViewItem()
							{{	
								if(obj.cover.get(i).trim().equalsIgnoreCase("mike.png"))
								{
									cover = "http://www-scf.usc.edu/~prattan/mike.png";
								}
								else
								{
								cover = obj.cover.get(i);
								}
							
								
								name = "Name:\n"+obj.name.get(i).trim();
								genre = "Genre\n"+obj.genre.get(i).trim();
								year = "Year\n"+obj.year.get(i).trim();

								

							}});	
						}
						 i=0;
							CustomListViewAdapter adapter = new CustomListViewAdapter(ListAct.this,items);
							lv.setAdapter(adapter);
							Log.i("ADAPPPPPPPPPTTTTERRRRRRRRRRRR","HERE");
							lv.setOnItemClickListener(new OnItemClickListener() {
								
								public void onItemClick(AdapterView<?> parent, View view,
										final int position, long id) {
									
									Log.i("ADAPPPPPPPPPTTTTERRRRRRRRRRRR","INSIDE ONITEM CLICK");
								    // When clicked, show a toast with the TextView text
									final Dialog dialog = new Dialog(ListAct.this);
									dialog.setContentView(R.layout.albums_button);
									dialog.setTitle("Post to Facebook");
									
									Button artists = (Button) dialog.findViewById(R.id.albums_facebook);
									/*Button play = (Button) dialog.findViewById(R.id.
											);
						 Windows -> Preferences -> Java -> Editor ->Content Assis			*/
									
								//	play.on
									artists.setOnClickListener(new View.OnClickListener() {
										@SuppressWarnings("deprecation")
										@Override
										public void onClick(View v) {
											Bundle parameters = new Bundle();
					         	             
	         	    						//parameters.putString("picture", coverimg);
	         	    						parameters.putString("name", name.get(position).toString().trim());
	         	    						if(cover.get(position).toString().trim().equalsIgnoreCase("mike.png"))
	         	    						{parameters.putString("link","http://www-scf.usc.edu/~prattan/mike.png");}
	         	    						else
	         	    						{parameters.putString("link", cover.get(position).toString().trim());}
	         	    						parameters.putString("caption", "I like "+ name.get(position).toString().trim()+ " who is active since "+year.get(position).toString().trim());
	         	    						parameters.putString("description", "Genre of music is: "+ genre.get(position).toString().trim());
	         	    						JSONObject p1 = new JSONObject();
	         	    						JSONObject p2 = new JSONObject();
	         	    						try {
												p2.put("text", "here");
											} catch (JSONException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
	         	    						try {
												p2.put("href",obj.details.get(position).toString().trim());
											} catch (JSONException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
	         	    						try {
												p1.put("Look at user Reviews ", p2);
											} catch (JSONException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
	         	    						parameters.putString("properties", p1.toString());
											
									facebook= new Facebook("439748549447226");
									facebook.dialog(ListAct.this, "feed",parameters, new Facebook.DialogListener()
											{
											public void onFacebookError(FacebookError e) {
												// TODO Auto-generated method stub
												
											}
											
											public void onError(DialogError e) {
												// TODO Auto-generated method stub
											}
											
											public void onComplete(Bundle values) {
												// TODO Auto-generated method stub
												
												AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListAct.this);
												alertDialogBuilder.setTitle("Wall Post");
												alertDialogBuilder.setMessage("Information posted successfully!!");
												alertDialogBuilder.setCancelable(true);
												alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
													@Override
													public void onClick(DialogInterface dialog, int which) {
														// TODO Auto-generated method stub
														dialog.cancel();
											} 
											});
												AlertDialog alertDialog = alertDialogBuilder.create();
												alertDialog.show();
											}								
											public void onCancel() {
												// TODO Auto-generated method stub
												
											}
											
											
											
											
											
										});
										
										
											/*Toast my = new Toast(ListAct.this);
											my.setText(t.getText().toString()+ ""  + p.getText().toString()+ "" + c.getText().toString());
											*/
									
										////////////
										}
									});

									dialog.show();	
								
								
								
								
								}
							});
							
					}
					
				

					if(obj.choice.equalsIgnoreCase("albums"))
					{ Log.i("ALBUMS PICKED","..............");
					
						int len = jsonr.length();
						if(len==0)
						{
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListAct.this);
			        		 
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
						for(int i =0;i<ij;i++)
						{
							JSONObject album_obj = jsonr.getJSONObject(i);
							
							   obj.cover.add(album_obj.getString("cover"));		
							   obj.title.add(album_obj.getString("title"));		

							   obj.name.add(album_obj.getString("name"));
								
								obj.genre.add(album_obj.getString("genre"));
								obj.year.add(album_obj.getString("year"));
								obj.details.add(album_obj.getString("details"));

						}
						

						ListView lv = (ListView) findViewById(R.id.listView1);
						 items = new ArrayList<ListAct.ListViewItem>();
				
						 items.add(new ListViewItem()
							{{
								if(obj.cover.get(0).trim().equalsIgnoreCase("disc.png"))
								{
									cover = "http://www-scf.usc.edu/~prattan/disc.png";
								}
								else
								{
								cover = obj.cover.get(0);
								}
								title = "Title:\n"+obj.title.get(0).trim();
								name = "Name:\n"+obj.name.get(0).trim();
								genre = "Genre\n"+obj.genre.get(0).trim();
								year = "Year\n"+obj.year.get(0).trim();

								

							}});	
						 
						 
						 	items.add(new ListViewItem()
							{{
								if(obj.cover.get(1).trim().equalsIgnoreCase("disc.png"))
								{
									cover = "http://www-scf.usc.edu/~prattan/disc.png";
								}
								else
								{
									cover = obj.cover.get(1);
								}
								title = "Title:\n"+obj.title.get(1).trim();
								name = "Name\n"+obj.name.get(1).trim();
								genre = "Genre\n"+obj.genre.get(1).trim();
								year = "Year\n"+obj.year.get(1).trim();

								

							}});
							items.add(new ListViewItem()
							{{
								if(obj.cover.get(2).trim().equalsIgnoreCase("disc.png"))
								{
									cover = "http://www-scf.usc.edu/~prattan/disc.png";
								}
								else
								{
									cover = obj.cover.get(2);
								}
								title = "Title:\n"+obj.title.get(2).trim();
								name = "Name\n"+obj.name.get(2).trim();
								genre = "Genre\n"+obj.genre.get(2).trim();
								year = "Year\n"+obj.year.get(2).trim();

								

							}});
							items.add(new ListViewItem()
							{{
								if(obj.cover.get(3).trim().equalsIgnoreCase("disc.png"))
								{
									cover = "http://www-scf.usc.edu/~prattan/disc.png";
								}
								else
								{
									cover = obj.cover.get(3);
								}
								title = "Title:\n"+obj.title.get(3).trim();
								name = "Name\n"+obj.name.get(3).trim();
								genre = "Genre\n"+obj.genre.get(3).trim();
								year = "Year\n"+obj.year.get(3).trim();
								

							}});
							 
							items.add(new ListViewItem()
							{{
								if(obj.cover.get(4).trim().equalsIgnoreCase("disc.png"))
								{
									cover = "http://www-scf.usc.edu/~prattan/disc.png";								}
								else
								{
								cover = obj.cover.get(4);
								
								}
								title = "Title:\n"+obj.title.get(4).trim();

								name = "Name\n"+obj.name.get(4).trim();
								genre = "Genre\n"+obj.genre.get(4).trim();
								year = "Year\n"+obj.year.get(4).trim();

								

							}});
							CustomListViewAdapter adapter = new CustomListViewAdapter(ListAct.this,items);
							lv.setAdapter(adapter);
							Log.i("ADAPPPPPPPPPTTTTERRRRRRRRRRRR","HERE");
							lv.setOnItemClickListener(new OnItemClickListener() {
								
								public void onItemClick(AdapterView<?> parent, View view,
										final int position, long id) {
									Log.i("ADAPPPPPPPPPTTTTERRRRRRRRRRRR","INSIDE ONITEM CLICK");
								    // When clicked, show a toast with the TextView text
									Log.i("ADAPPPPPPPPPTTTTERRRRRRRRRRRR","INSIDE ONITEM CLICK");
								    // When clicked, show a toast with the TextView text
									final Dialog dialog = new Dialog(ListAct.this);
									dialog.setContentView(R.layout.albums_button);
									dialog.setTitle("Post to Facebook");
									Button albums = (Button) dialog.findViewById(R.id.albums_facebook);
									
									albums.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
										
											
											
											Bundle parameters = new Bundle();
					         	             
	         	    						//parameters.putString("picture", coverimg);
	         	    						parameters.putString("name", "I like "+title.get(position).toString().trim()+"released in "+ year.get(position).toString().trim());
	         	    						if(cover.get(position).toString().trim().equalsIgnoreCase("disc.png"))
	         	    						{parameters.putString("link", "http://www-scf.usc.edu/~prattan/disc.png");}
	         	    						else{parameters.putString("link", cover.get(position).toString().trim());}
	         	    						parameters.putString("caption", "Artist "+name.get(position).toString().trim()+" Genre"+genre.get(position).toString().trim());
	         	    						parameters.putString("description", "");
	         	    						JSONObject p1 = new JSONObject();
	         	    						JSONObject p2 = new JSONObject();
	         	    						try {
												p2.put("text", "here");
											} catch (JSONException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
	         	    						try {
												p2.put("href",obj.details.get(position).toString().trim());
											} catch (JSONException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
	         	    						try {
												p1.put("Look at details here ", p2);
											} catch (JSONException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
	         	    						parameters.putString("properties", p1.toString());
											
									facebook= new Facebook("439748549447226");
									facebook.dialog(ListAct.this, "feed",parameters, new Facebook.DialogListener()
											{
											public void onFacebookError(FacebookError e) {
												// TODO Auto-generated method stub
												
											}
											
											public void onError(DialogError e) {
												// TODO Auto-generated method stub
											}
											
											public void onComplete(Bundle values) {
												// TODO Auto-generated method stub
												
												AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListAct.this);
												alertDialogBuilder.setTitle("Wall Post");
												alertDialogBuilder.setMessage("Information posted successfully!!");
												alertDialogBuilder.setCancelable(true);
												alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
													@Override
													public void onClick(DialogInterface dialog, int which) {
														// TODO Auto-generated method stub
														dialog.cancel();
											} 
											});
												AlertDialog alertDialog = alertDialogBuilder.create();
												alertDialog.show();
											}								
											public void onCancel() {
												// TODO Auto-generated method stub
												
											}
											
											
											
											
											
										});
										
										
										}
									});

									dialog.show();			
								
								
								
								
								}
							});	
					}	
					}
					if(obj.choice.equalsIgnoreCase("songs"))
					{
						if(jsonr.length()==0)
						{
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListAct.this);
			        		 
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
					
						for(int i =0;i< ij;i++)
						{
						JSONObject songs_obj = jsonr.getJSONObject(i);
						   obj.cover.add(songs_obj.getString("cover"));		
						   obj.title.add(songs_obj.getString("title"));		
						   obj.performer.add(songs_obj.getString("performer"));
						   obj.composer.add(songs_obj.getString("composer"));
						   obj.details.add(songs_obj.getString("details"));
						}
						ListView lv = (ListView) findViewById(R.id.listView1);
						 items = new ArrayList<ListAct.ListViewItem>();
				
						 items.add(new ListViewItem()
							{{
								cover = "http://www-scf.usc.edu/~prattan/Headphones.jpg";
								song = obj.cover.get(0);
								
								title = "Title:\n"+obj.title.get(0).trim();
								performer = "Performer:\n"+obj.performer.get(0).trim();
								composer = "Composer\n"+obj.composer.get(0).trim();
								
								

							}});	
						 
						 
						 	items.add(new ListViewItem()
							{{
								cover = "http://www-scf.usc.edu/~prattan/Headphones.jpg";
								song = obj.cover.get(1);
								
								title = "Title:\n"+obj.title.get(1).trim();
								performer = "Performer:\n"+obj.performer.get(1).trim();
								composer = "Composer\n"+obj.composer.get(1).trim();

								

							}});
							items.add(new ListViewItem()
							{{
								cover = "http://www-scf.usc.edu/~prattan/Headphones.jpg";
								song = obj.cover.get(2);
								
								title = "Title:\n"+obj.title.get(2).trim();
								performer = "Performer:\n"+obj.performer.get(2).trim();
								composer = "Composer\n"+obj.composer.get(2).trim();
								

							}});
							items.add(new ListViewItem()
							{{
								cover = "http://www-scf.usc.edu/~prattan/Headphones.jpg";
								song = obj.cover.get(3);
								
								title = "Title:\n"+obj.title.get(3).trim();
								performer = "Performer:\n"+obj.performer.get(3).trim();
								composer = "Composer\n"+obj.composer.get(3).trim();
								

							}});
							 
							items.add(new ListViewItem()
							{{
								cover = "http://www-scf.usc.edu/~prattan/Headphones.jpg";
								song = obj.cover.get(4);
								
								title = "Title:\n"+obj.title.get(4).trim();
								performer = "Performer:\n"+obj.performer.get(4).trim();
								composer = "Composer\n"+obj.composer.get(4).trim();

								

							}});
							CustomListViewAdapter adapter = new CustomListViewAdapter(ListAct.this,items);
							lv.setAdapter(adapter);
							Log.i("ADAPPPPPPPPPTTTTERRRRRRRRRRRR","HERE");
							lv.setOnItemClickListener(new OnItemClickListener() {
								
								public void onItemClick(AdapterView<?> parent, View view,
										final int position, long id) {
									Log.i("ADAPPPPPPPPPTTTTERRRRRRRRRRRR","INSIDE ONITEM CLICK");
								    // When clicked, show a toast with the TextView text
									final Dialog dialog;
									Log.i("COVER.............",obj.cover.get(position).toString());
									if(obj.cover.get(position).equalsIgnoreCase("Headphones.png"))
									{
										
										dialog = new Dialog(ListAct.this);
										dialog.setContentView(R.layout.albums_button);
										dialog.setTitle("Post to Facebook");
										
										Button myfbbutton = (Button) dialog.findViewById(R.id.albums_facebook);
										
										////////////////////////////
																				
								//////////////
										
										myfbbutton.setOnClickListener(new View.OnClickListener() {
											@Override
											public void onClick(View v) {

												
												
												Bundle parameters = new Bundle();
						         	             
		         	    						//parameters.putString("picture", coverimg);
		         	    						parameters.putString("name", title.get(position).toString().trim());
		         	    						parameters.putString("link", "http://www-scf.usc.edu/~prattan/Headphones.jpg");
		         	    						parameters.putString("caption", "I like "+ title.get(position).toString().trim()+" by "+ composer.get(position).toString().trim());
		         	    						parameters.putString("description", "Performer "+performer.get(position).toString().trim());
		         	    						JSONObject p1 = new JSONObject();
		         	    						JSONObject p2 = new JSONObject();
		         	    						try {
													p2.put("text", "here");
												} catch (JSONException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
		         	    						try {
													p2.put("href",obj.details.get(position).toString().trim());
												} catch (JSONException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
		         	    						try {
													p1.put("Look at details here ", p2);
												} catch (JSONException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
		         	    						parameters.putString("properties", p1.toString());
												
										facebook= new Facebook("439748549447226");
										facebook.dialog(ListAct.this, "feed",parameters, new Facebook.DialogListener()
												{
												public void onFacebookError(FacebookError e) {
													// TODO Auto-generated method stub
													
												}
												
												public void onError(DialogError e) {
													// TODO Auto-generated method stub
												}
												
												public void onComplete(Bundle values) {
													// TODO Auto-generated method stub
													
													AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListAct.this);
													alertDialogBuilder.setTitle("Wall Post");
													alertDialogBuilder.setMessage("Information posted successfully!!");
													alertDialogBuilder.setCancelable(true);
													alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog, int which) {
															// TODO Auto-generated method stub
															dialog.cancel();
												} 
												});
													AlertDialog alertDialog = alertDialogBuilder.create();
													alertDialog.show();
												}								
												public void onCancel() {
													// TODO Auto-generated method stub
													
												}
												
												
												
												
												
											});
											
												
												
												
												
												
											}
										});
									}
									else{
										 dialog = new Dialog(ListAct.this);
										dialog.setContentView(R.layout.buttons);
										dialog.setTitle("Post to Facebook");
										
										Button myfbbutton = (Button) dialog.findViewById(R.id.facebook);
									
										myfbbutton.setOnClickListener(new View.OnClickListener() {
											@Override
											public void onClick(View v) {
											
												TextView t,p,c;
												
												
												
												
												t = (TextView) findViewById(R.id.name);
												p = (TextView) findViewById(R.id.performer);
												c = (TextView) findViewById(R.id.composer);
												
												
												Bundle parameters = new Bundle();
						         	             
		         	    						//parameters.putString("picture", coverimg);
												parameters.putString("name", title.get(position).toString().trim());
		         	    						parameters.putString("link", "http://www-scf.usc.edu/~prattan/Headphones.jpg");
		         	    						parameters.putString("caption", "I like "+ title.get(position).toString().trim()+" by "+ composer.get(position).toString().trim());
		         	    						parameters.putString("description", "Performer "+performer.get(position).toString().trim());
		         	    						JSONObject p1 = new JSONObject();
		         	    						JSONObject p2 = new JSONObject();
		         	    						try {
													p2.put("text", "here");
												} catch (JSONException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
		         	    						try {
													p2.put("href",obj.details.get(position).toString().trim());
												} catch (JSONException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
		         	    						try {
													p1.put("Look at details here ", p2);
												} catch (JSONException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
		         	    						parameters.putString("properties", p1.toString());
												
										facebook= new Facebook("439748549447226");
										facebook.dialog(ListAct.this, "feed",parameters, new Facebook.DialogListener()
												{
												public void onFacebookError(FacebookError e) {
													// TODO Auto-generated method stub
													
												}
												
												public void onError(DialogError e) {
													// TODO Auto-generated method stub
												}
												
												public void onComplete(Bundle values) {
													// TODO Auto-generated method stub
													
													AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListAct.this);
													alertDialogBuilder.setTitle("Wall Post");
													alertDialogBuilder.setMessage("Information posted successfully!!");
													alertDialogBuilder.setCancelable(true);
													alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog, int which) {
															// TODO Auto-generated method stub
															dialog.cancel();
												} 
												});
													AlertDialog alertDialog = alertDialogBuilder.create();
													alertDialog.show();
												}								
												public void onCancel() {
													// TODO Auto-generated method stub
													
												}
												
												
												
												
												
											});
											
											
												
												
												
											}
										});
								////////////////////////
									Button play = (Button) dialog.findViewById(R.id.play);
								//	play.on
									play.setOnClickListener(new View.OnClickListener() {
										MediaPlayer mediaPlayer = new MediaPlayer();

										@Override
										public void onClick(View v) {
											// mediaPlayer = new MediaPlayer();
											if(mediaPlayer.isPlaying()==true)
											{
												
												mediaPlayer.stop();
											}
											mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
										        public void onPrepared(MediaPlayer mp) {
										            mp.start();
										            
										        }
										    });

										    preparePlayer();


											/*Toast my = new Toast(ListAct.this);
											my.setText(t.getText().toString()+ ""  + p.getText().toString()+ "" + c.getText().toString());
											*/
										}

										private void preparePlayer() {
											// TODO Auto-generated method stub
											 if (mediaPlayer == null) {
											        mediaPlayer = new MediaPlayer();
											    }
											    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
											    try {
											        mediaPlayer.setDataSource(obj.cover.get(position));
											        mediaPlayer.prepareAsync();

											    } catch (IllegalArgumentException e) {
											      											        e.printStackTrace();
											    } catch (IllegalStateException e) {
											        e.printStackTrace();
											    } catch (IOException e) {
											        e.printStackTrace();
											    }
										}
									});
								}
									dialog.show();			
									
									
									
									
								}
							});
					}
				}					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

    
    }
        	
    	
}

}
