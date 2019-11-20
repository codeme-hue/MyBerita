package id.kardihaekal.myberita;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import id.kardihaekal.myberita.response.BeritaItem;
import java.util.List;

public class AdapterBerita extends RecyclerView.Adapter<AdapterBerita.MyViewHolder> {

  Context context;
  List<BeritaItem> berita;
  public AdapterBerita(Context context, List<BeritaItem> data_berita) {
    // Inisialisasi
    this.context = context;
    this.berita = data_berita;
  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // Layout inflater
    View view = LayoutInflater.from(context).inflate(R.layout.berita_item, parent, false);

    // Hubungkan dengan MyViewHolder
    MyViewHolder holder = new MyViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(MyViewHolder holder, final int position) {
    // Set widget
    holder.tvJudul.setText(berita.get(position).getJudulBerita());
    holder.tvTglTerbit.setText(berita.get(position).getTanggalPosting());

    // Dapatkan url gambar
    final String urlGambarBerita = "http://192.168.9.16/portal_berita/images/" + berita.get(position).getFoto();
    // Set image ke widget dengna menggunakan Library Piccasso
    // krena imagenya dari internet
    Picasso.with(context).load(urlGambarBerita).into(holder.ivGambarBerita);

    // Event klik ketika item list nya di klik
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // Mulai activity Detail
        Intent varIntent = new Intent(context, DetailActivity.class);
        // sisipkan data ke intent
        varIntent.putExtra("JDL_BERITA", berita.get(position).getJudulBerita());
        varIntent.putExtra("TGL_BERITA", berita.get(position).getTanggalPosting());
        varIntent.putExtra("PNS_BERITA", berita.get(position).getPenulis());
        varIntent.putExtra("FTO_BERITA", urlGambarBerita);
        varIntent.putExtra("ISI_BERITA", berita.get(position).getIsiBerita());

        // method startActivity cma bisa di pake di activity/fragment
        // jadi harus masuk ke context dulu
        context.startActivity(varIntent);
      }
    });
  }
  // Menentukan Jumlah item yang tampil
  @Override
  public int getItemCount() {
    return berita.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    // Deklarasi widget
    ImageView ivGambarBerita;
    TextView tvJudul, tvTglTerbit, tvPenulis;
    public MyViewHolder(View itemView) {
      super(itemView);
      // inisialisasi widget
      ivGambarBerita = (ImageView) itemView.findViewById(R.id.ivPosterBerita);
      tvJudul = (TextView) itemView.findViewById(R.id.tvJudulBerita);
      tvTglTerbit = (TextView) itemView.findViewById(R.id.tvTglTerbit);
      tvPenulis = (TextView) itemView.findViewById(R.id.tvPenulis);
    }
  }
}

