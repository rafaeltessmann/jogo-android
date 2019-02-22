package com.example.a20161cmqads0220.jogo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 20161cmq.ads0220 on 20/07/2018.
 */

public class CriaturaAdapter extends ArrayAdapter<criatura>{

    private Context context;
    private ArrayList<criatura> criaturas =null;

    public CriaturaAdapter(Context context, ArrayList<criatura> criaturas){
        super(context, android.R.layout.simple_list_item_1, criaturas);
        this.context=context;
        this.criaturas=criaturas;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        final criatura Criatura = criaturas.get(position);

        if (view==null)
            view= LayoutInflater.from(context).inflate(R.layout.telaitemlistacriatura, null);

        ImageView imagemCriatura= (ImageView) view.findViewById(R.id.itemListaImagemCriaturaId);
        imagemCriatura.setImageResource(Criatura.getImagemCriatura());

        TextView nomeCriatura= (TextView) view.findViewById(R.id.itemListaTextoCriaturaId);
        nomeCriatura.setText(Criatura.getNomeCriatura());

        return view;

    }
}