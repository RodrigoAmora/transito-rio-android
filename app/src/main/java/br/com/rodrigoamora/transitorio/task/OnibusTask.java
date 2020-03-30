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
import br.com.rodrigoamora.transitorio.util.TimeUtil;

public class OnibusTask extends AsyncTask<Void, List<Onibus>, List<Onibus>> {

    private Delegate<List<Onibus>> delegate;
    private List<Onibus> onibusLista;

    public OnibusTask(Delegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<Onibus> doInBackground(Void... voids) {
        String url = BuildConfig.BASE_URL_ONIBUS_API+"obterTodasPosicoes";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            get.addHeader("accept", "application/json");

            HttpResponse getResponse = httpClient.execute(get);
            InputStream content = getResponse.getEntity().getContent();

            Scanner scanner = new Scanner(content);
            String json = "";
            while(scanner.hasNextLine()) {
                json = scanner.nextLine();
            }
            content.close();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("DATA");

            onibusLista = parserJson(jsonArray);
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

    private List<Onibus> parserJson(JSONArray jsonArray) throws JSONException {
        onibusLista = new ArrayList();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray jsonOnibus = jsonArray.getJSONArray(i);

            String hora = jsonOnibus.get(0).toString().split(" ")[1];
            if (TimeUtil.calculateTimeInMinutes(hora) <= 5) {
                Onibus onibus = new Onibus();
                onibus.setHora(hora);
                onibus.setOrdem(jsonOnibus.get(1).toString());

                String linha = jsonOnibus.get(2).toString();
                if (linha.contains(".0")) {
                    linha = linha.replace(".0", "");
                }
                onibus.setLinha(linha.toString());

                onibus.setLatidude(Double.parseDouble(jsonOnibus.get(3).toString()));
                onibus.setLongitude(Double.parseDouble(jsonOnibus.get(4).toString()));
                onibus.setVelocidade(Double.parseDouble(jsonOnibus.get(5).toString()));

                onibusLista.add(onibus);
            }
        }

        return onibusLista;
    }
}
