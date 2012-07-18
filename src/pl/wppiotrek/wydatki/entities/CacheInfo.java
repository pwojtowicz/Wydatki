package pl.wppiotrek.wydatki.entities;

import java.io.Serializable;
import java.util.Date;

public class CacheInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	public String userLogin;
	public String eTAG;
	public String response;
	public String uri;
	public String postTAG;
	public Date timestamp;

	public CacheInfo() {

	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\nuri : " + uri);
		stringBuilder.append("\neTag : " + eTAG);
		stringBuilder.append("\npostEtag : " + postTAG);
		stringBuilder.append("\ntimestamp : " + timestamp.toString());
		stringBuilder.append("\n::");
		return stringBuilder.toString();
	}

}
