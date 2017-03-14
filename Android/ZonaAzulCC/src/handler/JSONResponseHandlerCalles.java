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

import zonazulcc.ItemCalleJSON;
/**
 * Implementacion de la clase JSONResponseHandlerCalles
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class JSONResponseHandlerCalles implements
		ResponseHandler<List<ItemCalleJSON>> {
	@Override
	public List<ItemCalleJSON> handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		List<ItemCalleJSON> result = new ArrayList<ItemCalleJSON>();
		String JSONResponse = new BasicResponseHandler()
				.handleResponse(response);
		try {
			JSONObject object = (JSONObject) new JSONTokener(JSONResponse)
					.nextValue();
			JSONObject objectR = object.getJSONObject("results");

			JSONArray calles = objectR.getJSONArray("bindings");

			JSONObject tmp;
			JSONObject data;
			int indiceInicial;
			String subUri, URI, om_nombreVia, om_tipoVia, geo_lat_medio, geo_long_medio;

			for (int i = 0; i < calles.length(); i++) {
				tmp = (JSONObject) calles.get(i);

				// Rescatamos los valores del JSON para editar los atributos del
				// Objeto ItemCalleJSON
				data = (JSONObject) tmp.getJSONObject("URI");
				URI = data.getString("value");
				indiceInicial = URI.lastIndexOf("/");
				subUri = URI.substring(indiceInicial + 1, URI.length());

				data = (JSONObject) tmp.getJSONObject("om_nombreVia");
				om_nombreVia = data.getString("value");

				// Evitar problemas caracteres UTF-8
				om_nombreVia = new String (om_nombreVia.getBytes("ISO-8859-1"), "UTF-8");

				data = (JSONObject) tmp.getJSONObject("om_tipoVia");
				om_tipoVia = data.getString("value");

				data = (JSONObject) tmp.getJSONObject("geo_lat_medio");
				geo_lat_medio = data.getString("value");

				data = (JSONObject) tmp.getJSONObject("geo_long_medio");
				geo_long_medio = data.getString("value");

				// anhadimos a la lista resultado un elemento nuevo
				result.add(new ItemCalleJSON(subUri, om_tipoVia + " "
						+ om_nombreVia, Double.parseDouble(geo_lat_medio),
						Double.parseDouble(geo_long_medio), 0));

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
