package br.com.rodrigoamora.transitorio.task;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.rodrigoamora.transitorio.BuildConfig;
import br.com.rodrigoamora.transitorio.delegate.Delegate;
import br.com.rodrigoamora.transitorio.model.Onibus;

public class OnibusTask extends AsyncTask<Void, List<Onibus>, List<Onibus>> {

    private Delegate<List<Onibus>> delegate;

    public OnibusTask(Delegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<Onibus> doInBackground(Void... voids) {
        String url = BuildConfig.BASE_URL_ONIBUS_API+"obterTodasPosicoes";
        List<Onibus> onibusLista = new ArrayList();
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet get = new HttpGet(url);
            get.addHeader("accept", "application/xml");

            HttpResponse getResponse = httpClient.execute(get);
            InputStream content = getResponse.getEntity().getContent();

            Scanner scanner = new Scanner(content);
            String json = "";
            while(scanner.hasNextLine()) {
                json = scanner.nextLine()+"\n";
            }
            content.close();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("DATA");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray jsonOnibus = jsonArray.getJSONArray(i);

                Onibus onibus = new Onibus();
                onibus.setHora(jsonOnibus.get(0).toString());
                onibus.setOrdem(jsonOnibus.get(1).toString());
                onibus.setLinha(jsonOnibus.get(2).toString());
                onibus.setLatidude(Double.parseDouble(jsonOnibus.get(3).toString()));
                onibus.setLongitude(Double.parseDouble(jsonOnibus.get(4).toString()));
                onibus.setVelocidade(Double.parseDouble(jsonOnibus.get(5).toString()));

                onibusLista.add(onibus);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return onibusLista;
    }

    @Override
    protected void onPostExecute(List<Onibus> onibusList) {
        super.onPostExecute(onibusList);
        if (onibusList != null) {
            delegate.success(onibusList);
        } else {
            delegate.error();
        }
    }

}
