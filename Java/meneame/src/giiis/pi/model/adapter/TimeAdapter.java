package giiis.pi.model.adapter;

import java.sql.Time;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimeAdapter extends XmlAdapter<String, Time> {

	private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public Time unmarshal(String v) throws Exception {
		return (Time) timeFormat.parse(v);

	}

	@Override
	public String marshal(Time v) throws Exception {
		return timeFormat.format(v);
	}

}