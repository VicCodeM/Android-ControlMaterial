package com.example.controlmaterial11;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlmaterial11.databinding.ActivityInicioBinding;

import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends DrawerBaseActivity {
    ActivityInicioBinding activityInicioBinding;
    private RecyclerView recyclerView;
    private ReporteAdapter reporteAdapter;
    private List<Reporte> listaReportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInicioBinding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(activityInicioBinding.getRoot());

        // Referenciar SwipeRefreshLayout
        activityInicioBinding.swipeRefreshLayout.setOnRefreshListener(() -> {
            cargarDatos(); // Cargar datos nuevamente
            reporteAdapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
            activityInicioBinding.swipeRefreshLayout.setRefreshing(false); // Detener la animación de refresco
        });

        recyclerView = activityInicioBinding.recyclerViewReportes;
        listaReportes = new ArrayList<>();

        // Cargar datos desde la base de datos
        cargarDatos();

        // Configurar el adaptador
        reporteAdapter = new ReporteAdapter(listaReportes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reporteAdapter);
    }

    private void cargarDatos() {
        DBHelper dbHelper = new DBHelper(this);
        listaReportes = dbHelper.obtenerTodosLosReportes(); // Ahora devuelve List<Reporte>
    }
}
