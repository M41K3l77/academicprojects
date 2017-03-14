package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import zonazulcc.ItemPlazaZonaAzulJSON;

/**
 * Implementacion de la clase JSONResponseHandlerPlazas
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class JSONResponseHandlerPlazas implements
		ResponseHandler<List<ItemPlazaZonaAzulJSON>> {
	@Override
	public List<ItemPlazaZonaAzulJSON> handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		List<ItemPlazaZonaAzulJSON> result = new ArrayList<ItemPlazaZonaAzulJSON>();
		String JSONResponse = new BasicResponseHandler()
				.handleResponse(response);
		try {
			JSONObject object = (JSONObject) new JSONTokener(JSONResponse)
					.nextValue();
			JSONObject objectR = object.getJSONObject("results");

			JSONArray plazas = objectR.getJSONArray("bindings");

			JSONObject tmp;
			JSONObject data;
			int indiceInicial;
			String subSituadoEnVia, situadoEnVia, horario, coste, precioAnulacion, geo_long, geo_lat;

			for (int i = 0; i < plazas.length(); i++) {
				tmp = (JSONObject) plazas.get(i);

				//Rescatamos los valores del JSON para editar los atributos del Objeto ItemPlazaZonaAzulJSON
				data = (JSONObject) tmp.getJSONObject("om_situadoEnVia");
				situadoEnVia = data.getString("value");
				indiceInicial = situadoEnVia.lastIndexOf("/");
				subSituadoEnVia = situadoEnVia.substring(indiceInicial+1, situadoEnVia.length());

				data = (JSONObject) tmp.getJSONObject("om_horarioZonaAzul");
				horario = data.getString("value");

				data = (JSONObject) tmp.getJSONObject("om_costeZonaAzul");
				coste = data.getString("value");

				data = (JSONObject) tmp.getJSONObject("om_precioAnulacionDenuncia");
				precioAnulacion = data.getString("value");

				data = (JSONObject) tmp.getJSONObject("geo_long");
				geo_long = data.getString("value");

				data = (JSONObject) tmp.getJSONObject("geo_lat");
				geo_lat = data.getString("value");

				//a�adimos a la lista resultado un elemento nuevo
				 result.add(new ItemPlazaZonaAzulJSON(subSituadoEnVia, horario, coste, Double.parseDouble(precioAnulacion), 
						 Double.parseDouble(geo_long), Double.parseDouble(geo_lat)));
		
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
