package com.example.exit.portaleducando.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exit.portaleducando.R;
import com.example.exit.portaleducando.deserialize.HospitalarDes;
import com.example.exit.portaleducando.model.Hospitalar;
import com.example.exit.portaleducando.util.RestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by EXIT on 06/11/2016.
 */

public class FragmentAlimento extends Fragment {

    private View view;
    private RestManager mManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_alimentos,container,false);

        mManager = new RestManager();

        TextView tv = (TextView) getActivity().findViewById(R.id.id_user);
        String msg = tv.getText().toString();

        if(!msg.isEmpty()) {
            int id = Integer.parseInt(msg);

                Gson gson = new GsonBuilder().registerTypeAdapter(Hospitalar.class, new HospitalarDes()).create();
                Call<Hospitalar> dadosAlunoMedico = mManager.getFlowerService(gson).procurarHospitalar(id);
                dadosAlunoMedico.enqueue(new Callback<Hospitalar>() {
                    @Override
                    public void onResponse(Call<Hospitalar> call, Response<Hospitalar> response) {
                        if(response.isSuccessful()){
                            Hospitalar hospitalar = response.body();

                            TextView tvAlimento1 = (TextView) view.findViewById(R.id.alimento1);
                            tvAlimento1.setText(hospitalar.getAlimentos1());

                            TextView tvAlimento2 = (TextView) view.findViewById(R.id.alimento2);
                            tvAlimento2.setText(hospitalar.getAlimentos2());

                            TextView tvAlimento3 = (TextView) view.findViewById(R.id.alimento3);
                            tvAlimento3.setText(hospitalar.getAlimentos3());
                        }
                    }

                    @Override
                    public void onFailure(Call<Hospitalar> call, Throwable t) {
                        Toast.makeText(getContext(), "ERRO: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        }
        return view;
    }
}
