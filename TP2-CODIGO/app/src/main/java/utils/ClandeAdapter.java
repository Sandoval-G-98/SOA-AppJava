package utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Authentication.R;

import java.util.List;

public class ClandeAdapter extends RecyclerView.Adapter<ClandeAdapter.ViewHolder> implements View.OnClickListener {

    private View.OnClickListener listener;

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView email, province, locality, street, numberStreet, fromHour, toHour, idClande, clandeDate;
        private ImageView clandeImage;
        public ViewHolder(View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.idEmail);
            province = itemView.findViewById(R.id.idProvince);
            locality = itemView.findViewById(R.id.idLocality);
            street = itemView.findViewById(R.id.idStreet);
            numberStreet = itemView.findViewById(R.id.idNumberStreet);
            fromHour = itemView.findViewById(R.id.idFromhour);
            toHour =  itemView.findViewById(R.id.idToHour);
            idClande = itemView.findViewById(R.id.idClande);
            clandeImage = itemView.findViewById(R.id.idImageClande);
            clandeDate = itemView.findViewById(R.id.idClandeDate);
        }
    }

    public List<Clande> clandesList;

    public ClandeAdapter(List<Clande> clandesList){
        this.clandesList = clandesList;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clande, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return ViewHolder;
    }

    @Override
    public  void onBindViewHolder(ViewHolder holder, int position){

        holder.email.setText(clandesList.get(position).getEmail());
        holder.province.setText("Provincia: " + clandesList.get(position).getProvince());
        holder.locality.setText("Localidad: " + clandesList.get(position).getLocality());
        holder.street.setText("Calle: " + clandesList.get(position).getStreetName());
        holder.numberStreet.setText("Altura: " + clandesList.get(position).getAltitudeStreet());
        holder.fromHour.setText("Desde: " + clandesList.get(position).getFromHourClande());
        holder.toHour.setText("Hasta: " + clandesList.get(position).getToHourClande());
        holder.toHour.setText("Hasta: " + clandesList.get(position).getToHourClande());
        holder.idClande.setText("Clande número: " + clandesList.get(position).getCodClande());
        holder.clandeDate.setText("Fecha: " + clandesList.get(position).getDateClande());
        holder.clandeImage.setBackgroundResource(clandesList.get(position).getImageClande());
    }

    @Override
    public int getItemCount(){
        return clandesList.size();
    }

}
