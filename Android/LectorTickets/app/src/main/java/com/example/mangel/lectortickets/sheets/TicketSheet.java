package com.example.mangel.lectortickets.sheets;

import com.example.mangel.lectortickets.model.Ticket;

/**
 * Project LectorTickets
 * Created by M.Angel on 07/07/2016.
 * Clase abstracta TicketSheet para las plantillas de los establecimientos.
 * EL unico metodo (Ticket formalizarTicket(String recognizedText)) que devuelve el ticket a partir del texto
 * que se obtiene del reconocedor tess-two deben implementarlo las clases que la extiendan (plantilla
 * de un supermercado concreto).
 */
public abstract class TicketSheet {

    public TicketSheet() {
    }

    /**
     * El texto reconocido se pasa a los edittext que se mostrara en la pantalla.
     */
    public abstract Ticket formalizarTicket(String recognizedText);

}
