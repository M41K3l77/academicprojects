package es.unex.holmel.rcmmchatapp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.unex.holmel.rcmmchatapp.R;
import es.unex.holmel.rcmmchatapp.model.Mensaje;

/**
 * Created by M.Angel on 18/01/2016.
 */
public class ListAdapter extends BaseAdapter {

    private final List <Mensaje> items=new ArrayList<>();
    private final Context context;
    private final String userName;

    public ListAdapter(Context context, String userName) {
        this.context = context;
        this.userName=userName;
    }

    public void add(Mensaje item){
        items.add(item);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout itemLayout = null;
        final Mensaje mensaje = (Mensaje) getItem(position);

        itemLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.list_item,null);
        Log.i("RCMM listadapter", "emisor receptor " +this.userName +" "+ mensaje.getUserName());
        if(mensaje.getUserName().equals(this.userName)){

        }else{
            itemLayout.setHorizontalGravity(Gravity.RIGHT);
        }
        final TextView title = (TextView) itemLayout.findViewById(R.id.message_item);
        final ImageView image = (ImageView) itemLayout.findViewById(R.id.image_item);

        if(mensaje.getText() != null){
            title.setText(mensaje.getUserName()+"\n"+mensaje.getText());
            image.setVisibility(View.GONE);
        }else{
            Bitmap bitemapImage = BitmapFactory.decodeByteArray(mensaje.getImage(), 0, mensaje.getImage().length);
            title.setText(mensaje.getUserName());
            image.setImageBitmap(bitemapImage);
            //title.setVisibility(View.GONE);
        }

        return itemLayout;
    }
}
