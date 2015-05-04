package com.example.ricardogarcia.politojobs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricardogarcia on 24/04/15.
 */
public class MessageAdapter extends BaseAdapter implements View.OnClickListener{

    private LayoutInflater inflater;
    private Activity activity;
    private List<Message> listmessages;


    public MessageAdapter(Activity activity,ArrayList list){
        inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity=activity;
        this.listmessages=list;

    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vholder;
        View v=convertView;

        if(listmessages.size()>0){
            if(v==null){
                v=inflater.inflate(R.layout.message_preview, parent, false);
                vholder= new ViewHolder();
                vholder.textSubject= (TextView) v.findViewById(R.id.textSubject);
                vholder.textMessage= (TextView) v.findViewById(R.id.textMessagePreview);
                v.setTag(vholder);
            }
            else{
                vholder= (ViewHolder) v.getTag();
            }
            vholder.textSubject.setText(listmessages.get(position).getSubject());
            vholder.textMessage.setText(listmessages.get(position).getMessage().substring(0,15));
        }
        else{
            if(v==null){
                v=inflater.inflate(R.layout.no_results_row, parent, false);
                vholder= new ViewHolder();
                vholder.textSubject= (TextView) v.findViewById(R.id.textSubject);
                v.setTag(vholder);
            }
            else{
                vholder= (ViewHolder) v.getTag();
            }
            vholder.textSubject.setText(activity.getText(R.string.no_results_text));
        }

        return v;
    }

    @Override
    public void onClick(View v) {

    }


    public static class ViewHolder {
        public TextView textSubject;
        public TextView textMessage;
    }
}


