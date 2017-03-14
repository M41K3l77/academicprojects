package com.example.mangel.lectortickets.viewelements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.example.mangel.lectortickets.R;

/**
 * Project LectorTickets
 * Created by M.Angel on 08/07/2016.
 * basado en http://stackoverflow.com/questions/18765445/select-a-portion-of-image-in-imageview-and-retrieve-the-end-points-of-selected-r/18781747
 * sandrstar usuario de stackoverflow.
 * Clase que extiende de ImageView, se usa en el layout activity_ticket_image.xml para seleccionar un
 * area (si asi lo desea el usuario) de la imagen del ticket. devuelve un rectangulo que representa
 * el area seleccionada y lo pinta. Se usa en TicketRecognitionActivity donde se le añade un listener
 * para obtener el rectangulo que representa la zona seleccionada.
 */
public class SelectedView extends ImageView {

    private static final String TAG = "SelectedView";
    private Paint mRectPaint;
    private int mStartX = 0;
    private int mStartY = 0;
    private int mEndX = 0;
    private int mEndY = 0;
    public static boolean mDrawRect = false;
    private TextPaint mTextPaint = null;
    private OnUpCallback mCallback = null;

    /**
     * Interfaz a la que se le pasa el area.
     */
    public interface OnUpCallback {
        void onRectFinished(Rect rect);
    }

    /**
     *
     * @param context
     */
    public SelectedView(final Context context) {
        super(context);
        init();
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public SelectedView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SelectedView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * Sets callback for up
     *
     * @param callback {@link OnUpCallback}
     */
    public void setOnUpCallback(OnUpCallback callback) {
        mCallback = callback;
    }

    /**
     * Inits internal data
     */
    private void init() {
        mRectPaint = new Paint();
        mRectPaint.setColor(ContextCompat.getColor(getContext(), R.color.primary_dark));//getContext().getResources().getColor(android.R.color.holo_green_light));
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(12); // TODO: should take from resources

        mTextPaint = new TextPaint();
        //ContextCompat.getColor(TicketRecognitionActivity.this, R.color.primary_light)
        mTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.primary_dark));//(getContext().getResources().getColor(android.R.color.holo_green_light));
        mTextPaint.setTextSize(32);
    }

    /**
     * Controla la logica de cuando se toca la pantalla (evento).
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        // cuando se presiona la pantalla se empieza el dibujado MotionEvent.ACTION_DOWN
        // conforme se deslizen los dedos, se actualiza el rectangulo MotionEvent.ACTION_MOVE
        // cuando se deja de presionar la pantalla se termina de dibujar el rectangulo MotionEvent.ACTION_UP
        // TODO: be aware of multi-touches
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDrawRect = false;
                mStartX = (int) event.getX();
                mStartY = (int) event.getY();
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                final int x = (int) event.getX();
                final int y = (int) event.getY();

                if (!mDrawRect || Math.abs(x - mEndX) > 5 || Math.abs(y - mEndY) > 5) {
                    mEndX = x;
                    mEndY = y;
                    invalidate();
                }

                mDrawRect = true;
                break;

            case MotionEvent.ACTION_UP:
                if (mCallback != null) {
                    Rect selectedArea=null;
                    if(mStartX != mEndX && mStartY != mEndY){
                        selectedArea=new Rect(Math.min(mStartX, mEndX), Math.min(mStartY, mEndY),
                                Math.max(mEndX, mStartX), Math.max(mEndY, mStartY));
                    }
                    mCallback.onRectFinished(selectedArea);
                }
                invalidate();
                break;

            default:
                break;
        }

        return true;
    }

    /**
     * metodo que se encarga del repintado automatico.
     * @param canvas
     */
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if (mDrawRect && (mStartX != mEndX && mStartY != mEndY)) {
            // repintado
            canvas.drawRect(Math.min(mStartX, mEndX), Math.min(mStartY, mEndY),
                    Math.max(mEndX, mStartX), Math.max(mEndY, mStartY), mRectPaint);
            // se muentra datos del rectangulo en la pantalla (esto es opcional, si se quiere se quita)
            canvas.drawText("  (" + Math.abs(mStartX - mEndX) + ", " + Math.abs(mStartY - mEndY) + ")",
                    Math.max(mEndX, mStartX), Math.max(mEndY, mStartY), mTextPaint);
        }
    }

}
