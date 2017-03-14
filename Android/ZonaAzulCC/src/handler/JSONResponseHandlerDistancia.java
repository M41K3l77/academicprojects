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
import zonazulcc.ItemDistanciaJSON;
/**
 * Implementacion de la clase JSONResponseHandlerDistancia
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class JSONResponseHandlerDistancia implements
		ResponseHandler<List<ItemDistanciaJSON>> {

	@Override
	public List<ItemDistanciaJSON> handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {

		List<ItemDistanciaJSON> result = new ArrayList<ItemDistanciaJSON>();

		String JSONResponse = new BasicResponseHandler()
				.handleResponse(response);
		int indiceInicial;

		try {
			
			int posicion;
			int duracionV, distanciaV;
			String duracion, distancia, direccion, subDireccion;

			JSONObject object = (JSONObject) new JSONTokener(JSONResponse)
					.nextValue();

//			JSONArray array2 = object.getJSONArray("destination_addresses");
//			direccion = array2.getString(0);

			JSONArray array = object.getJSONArray("rows");

			for (int i = 0; i < array.length(); i++) {
				
				posicion = i;

				JSONObject rows = array.getJSONObject(i);

				JSONArray elements = rows.getJSONArray("elements");

				JSONObject datos = elements.getJSONObject(0);

				JSONObject distance = datos.getJSONObject("distance");

				distancia = distance.getString("text");
				distanciaV = distance.getInt("value");

				JSONObject duration = datos.getJSONObject("duration");
				duracion = duration.getString("text");
				duracionV = duration.getInt("value");

				// insertamos en la lista resultado un elemento nuevo
				result.add(new ItemDistanciaJSON(duracion,
						distancia, duracionV, distanciaV, posicion));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
