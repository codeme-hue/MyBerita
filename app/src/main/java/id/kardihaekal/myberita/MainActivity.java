package id.kardihaekal.myberita;

import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.kardihaekal.myberita.network.ApiServices;
import id.kardihaekal.myberita.network.InitRetrofit;
import id.kardihaekal.myberita.response.BeritaItem;
import id.kardihaekal.myberita.response.ResponseBerita;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
  // Deklarasi Widget
  private RecyclerView recyclerView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // Inisialisasi Widget
    recyclerView = (RecyclerView) findViewById(R.id.rvListBerita);
    // RecyclerView harus pakai Layout manager
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    // Eksekusi method
    tampilBerita();
  }

  private void tampilBerita() {
    ApiServices api = InitRetrofit.getInstance();
    // Siapkan request
    Call<ResponseBerita> beritaCall = api.request_show_all_berita();
    // Kirim request
    beritaCall.enqueue(new Callback<ResponseBerita>() {
      @Override
      public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
        // Pasikan response Sukses
        if (response.isSuccessful()){
          Log.d("response api", response.body().toString());
          // tampung data response body ke variable
          List<BeritaItem> data_berita = response.body().getBerita();
          boolean status = response.body().isStatus();
          // Kalau response status nya = true
          if (status){
            // Buat Adapter untuk recycler view
            AdapterBerita adapter = new AdapterBerita(MainActivity.this, data_berita);
            recyclerView.setAdapter(adapter);
          } else {
            // kalau tidak true
            Toast.makeText(MainActivity.this, "Tidak ada berita untuk saat ini", Toast.LENGTH_SHORT).show();
          }
        }
      }

      @Override
      public void onFailure(Call<ResponseBerita> call, Throwable t) {
        // print ke log jika Error
        t.printStackTrace();
      }
    });
  }
}
