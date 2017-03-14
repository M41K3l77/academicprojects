package handler;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;
/**
 * Implementacion de la clase MyTabContenFactory
 *
 * @version 1.0
 * Asignatura: Arquitecturas Software Para Entornos Empresariales <br/>
 * @author
 * <b> Juan Carlos Bonilla Bermejo </b><br>
 * <b> Miguel Angel Holgado Ceballos </b><br>
 * Curso 14/15
 */
public class MyTabContenFactory implements TabContentFactory{

	private final Context mContext;

	/**
	 * constructor
	 * @param context
	 */
    public MyTabContenFactory(Context context) {
        mContext = context;
    }

    public View createTabContent(String tag) {
        View v = new View(mContext);
        v.setMinimumWidth(0);
        v.setMinimumHeight(0);
        return v;
    }

}
