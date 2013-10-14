package com.example.mymusicshare;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import com.example.mymusicshare.R;

import com.example.mymusicshare.ListAct.HttpRequestTask;
import com.example.mymusicshare.ListAct.ListViewItem;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomListViewAdapter extends BaseAdapter
{ 	Bitmap bitmap =null;
  	ListViewItem item = null;
	LayoutInflater inflater;
	List<ListViewItem> items;
	ImageView imgThumbnail=null;
	
    public CustomListViewAdapter(Activity context, List<ListViewItem> items) {
        super();
		
        this.items = items;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }
    
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
    	// TODO Auto-generated method stub
    	  
  	    ListViewItem item = items.get(position);
    	
    	View vi=convertView;
        
        if(convertView==null){
        
        if(ListAct.choice.equalsIgnoreCase("artists"))
        {
            vi = inflater.inflate(R.layout.row, null);

	        ImageView imgThumbnail = (ImageView) vi.findViewById(R.id.imgThumbnail);
	        TextView name_txt = (TextView) vi.findViewById(R.id.name);
	        TextView genre_txt = (TextView) vi.findViewById(R.id.genre);
	        TextView year_txt = (TextView) vi.findViewById(R.id.year);
	       ////////
	        
	        /////////
	        
	        imgThumbnail.setTag(item.cover);
	        Getcover obj =  new Getcover();
	        obj.execute(imgThumbnail);
	        name_txt.setText(item.name);
	        genre_txt.setText(item.genre);
	        year_txt.setText(item.year);
	               }
        
        if(ListAct.choice.equalsIgnoreCase("albums"))
        {	
            vi = inflater.inflate(R.layout.albums, null);
            ImageView imgThumbnail = (ImageView) vi.findViewById(R.id.imageView1);
             TextView name_txt = (TextView) vi.findViewById(R.id.name_1);
				//Log.i("222222222222",imgThumbnail.toString());

             TextView genre_txt = (TextView) vi.findViewById(R.id.genre_a);
             TextView year_txt = (TextView) vi.findViewById(R.id.year_a);
             TextView title_txt = (TextView) vi.findViewById(R.id.title_a);
				//Log.i("333333  ",imgThumbnail.toString());

            imgThumbnail.setTag(item.cover);
             Getcover obj =  new Getcover();
			/*	Log.i("calling execute function",imgThumbnail.toString());

             */obj.execute(imgThumbnail);
             name_txt.setText(item.name);
             genre_txt.setText(item.genre);
             year_txt.setText(item.year);
             title_txt.setText(item.title);

             
        	
        }
        
        
        if(ListAct.choice.equalsIgnoreCase("songs"))
        {
            vi = inflater.inflate(R.layout.songs, null);

             ImageView imgThumbnail = (ImageView) vi.findViewById(R.id.imageView1);
             TextView name_txt = (TextView) vi.findViewById(R.id.title);
             TextView genre_txt = (TextView) vi.findViewById(R.id.performer);
             TextView year_txt = (TextView) vi.findViewById(R.id.composer);
             imgThumbnail.setTag(item.cover);
             Getcover obj =  new Getcover();
             obj.execute(imgThumbnail);
             name_txt.setText(item.title);
             genre_txt.setText(item.performer);
             year_txt.setText(item.composer);
             
             
        	
        }
        
        }
        return vi;
       
       
        // TextView title_txt = (TextView) vi.findViewById(R.id.title);
         
        ////////////IMAGE CODE//////
    }
      
    
    public class Getcover extends AsyncTask<ImageView, Void, Bitmap> {
                  ///////////////////////////
    	Bitmap bnew =null;
    	ImageView myimage= null;
		@Override
		protected Bitmap doInBackground(ImageView... params) {
			Log.i("ENTERED DOINBACKGROUND FUNCTION","HERE");
			this.myimage = params[0];
			Log.i("CALLING new_func in DOINBACKGROUND FUNCTION","HERE");

			return new_func((String) myimage.getTag());
			
			
		}
		
		 private Bitmap new_func(String url){
			 Bitmap bmp = null;
				Log.i("ENTERED new_func in DOINBACKGROUND FUNCTION",url);

				URL new_url = null;
				try {
					
					new_url = new URL(url);
					Log.i("ENTERED new_func in DOINBACKGROUND FUNCTION",new_url.toString());

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					Log.i("||Hey|$", "Hey");
					  HttpURLConnection connection = null;
					try {
						Log.i("RETURNING FROM MAKING CONNECTION","normal something");
						connection = (HttpURLConnection)new_url.openConnection();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			           connection.setDoInput(true);
			           try {
			   			Log.i("RETURNING FROM CONNECTION","normal some other thing");
						connection.connect();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			              InputStream inputStream = null;
						try {
							Log.i("RETURNING FROM STREAM","normal some other thing dwiteeya");
							inputStream = connection.getInputStream();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			                
			              bnew = BitmapFactory.decodeStream(inputStream);
				 
			              if(null!= bnew)
			              {
			      			Log.i("RETURNING FROM NOT NULL","triteeya normal some other thing");
			            	  return bnew;
			            	  
			              }
			Log.i("RETURNING FROM BITMAP","bnew.toString()");
			 return bnew;
			 
			 
		 }
		 protected void onPostExecute(Bitmap bitmap) {
			 Log.i("Reached onPostExecute",bitmap.toString());
             myimage.setImageBitmap(bitmap);
             

			 
		 }
		 
		 }
    
     
   
       	
}
    