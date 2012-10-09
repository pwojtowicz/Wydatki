package pl.wppiotrek.wydatki.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.wppiotrek.wydatki.entities.CacheInfo;
import pl.wppiotrek.wydatki.entities.ShopItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DataBaseManager {

	private static final String DATABASE_NAME = "wydatki.db";
	private static final int DATABASE_VERSION = 9;

	private static final String CACHE_TABLE_NAME = "Cache";
	private static final String SHOP_TABLE_NAME = "ShopItem";

	private static final String INSERT_TO_CACHE = "INSERT INTO "
			+ CACHE_TABLE_NAME
			+ "(userLogin, uri, postTAG, eTAG, response, timestamp)  Values(?,?,?,?,?,?)";

	private static final String INSERT_TO_SHOP = "INSERT INTO "
			+ SHOP_TABLE_NAME + "(name, addDate,unit,userId )  Values(?,?,?,?)";

	private static final String UPDATE_TO_CACHE = "UPDATE " + CACHE_TABLE_NAME
			+ "SET postTAG=?, eTAG=?, response=?, timestamp=? "
			+ "WHERE userLogin=? AND uri=?";

	private int openConnections;
	private OpenHelper openHelper;
	private SQLiteDatabase db;
	private Context context;

	public DataBaseManager(Context context) {
		this.context = context;
		openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		this.db.needUpgrade(DATABASE_VERSION);
		this.db.close();

	}

	/**
	 * Sprawdzenie czy po¸�cznie do bazy danych jest otwarte jesli nie, to
	 * otwierane jest nowe
	 **/
	private void checkIsOpen() {
		if (!this.db.isOpen())
			this.db = openHelper.getWritableDatabase();
		openConnections++;
		// Log.d("DATABASE", "Open connections to DB: " + openConnections);

	}

	/**
	 * Jećli liczba po¸�czeÄ do bazy danych ==1 po¸�czenie jest zamykanie w
	 * pozosta¸ych przypadkach zmiejszana jest iloćŤ aktywnych po¸�czeÄ do bazy
	 **/
	private void close() {
		if (openConnections == 1) {
			this.db.close();
			// Log.d("DATABASE", "Close connection to DB");
		}
		openConnections--;
	}

	public void deleteOldCacheInfo(int dayDelay) {
		checkIsOpen();
		Date d1 = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d1);
		calendar.add(Calendar.DAY_OF_YEAR, -dayDelay);
		Date d2 = calendar.getTime();
		long result = this.db.delete(CACHE_TABLE_NAME, "timestamp<?",
				new String[] { String.valueOf(d2.getTime()) });
		System.out.println("CACHE: Removed " + String.valueOf(result)
				+ " cache rows");
		close();
	}

	public long insertShopitem(ShopItem item) {
		checkIsOpen();
		SQLiteStatement insertStmt = this.db.compileStatement(INSERT_TO_SHOP);
		insertStmt.bindString(1, item.getName());
		insertStmt.bindLong(2, item.getAddDate().getTime());
		insertStmt.bindLong(3, item.getUnit());
		insertStmt.bindLong(4, item.getUserId());

		long result = insertStmt.executeInsert();
		close();
		return result;
	}

	public long insertCache(CacheInfo cache) {
		checkIsOpen();
		SQLiteStatement insertStmt = this.db.compileStatement(INSERT_TO_CACHE);
		insertStmt.bindString(1, cache.userLogin);
		insertStmt.bindString(2, cache.uri);
		insertStmt.bindString(3, cache.postTAG);
		insertStmt.bindString(4, cache.eTAG);
		insertStmt.bindString(5, cache.response);
		insertStmt.bindLong(6, cache.timestamp.getTime());
		long result = insertStmt.executeInsert();
		// Log.d("DATABASE", "Insert new cache: " + result);
		close();
		return result;
	}

	public long updateCache(CacheInfo cache) {
		checkIsOpen();
		ContentValues values = new ContentValues();
		values.put("postTAG", cache.postTAG);
		values.put("eTAG", cache.eTAG);
		values.put("response", cache.response);
		long result = this.db.update(CACHE_TABLE_NAME, values,
				"userLogin=? AND uri=?", new String[] { cache.userLogin,
						cache.uri });
		// Log.d("DATABASE", "Update cache: " + result);
		close();
		return result;
	}

	public CacheInfo getCacheInfoForArguments(String uri, String userLogin,
			String postTAG) {
		checkIsOpen();
		CacheInfo ci = null;
		try {
			Cursor cursor = this.db
					.query(SHOP_TABLE_NAME,
							new String[] { "userLogin, uri, postTAG, eTAG, response, timestamp" },
							"userLogin=? AND uri=?", new String[] { userLogin,
									uri }, null, null, null);
			if (cursor.moveToFirst()) {
				do {
					String pTag = cursor.getString(2);
					if (postTAG == null || postTAG.equals(pTag)) {
						ci = new CacheInfo();
						ci.userLogin = cursor.getString(0);
						ci.uri = cursor.getString(1);
						ci.postTAG = cursor.getString(2);
						ci.eTAG = cursor.getString(3);
						ci.response = cursor.getString(4);
						ci.timestamp = new Date(cursor.getLong(5));
						break;
					}

				} while (cursor.moveToNext());
			}
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ci;

	}

	public ArrayList<ShopItem> getAllShopItems() {
		checkIsOpen();
		ArrayList<ShopItem> items = new ArrayList<ShopItem>();
		try {
			Cursor cursor = this.db.query(SHOP_TABLE_NAME,
					new String[] { "id,name, addDate,unit,userId" }, null,
					null, null, null, null);
			if (cursor.moveToFirst()) {
				do {
					ShopItem item = new ShopItem();
					item.setId((int) cursor.getLong(0));
					item.setName(cursor.getString(1));
					item.setAddDate(new Date(cursor.getLong(2)));
					item.setUnit((int) cursor.getLong(3));
					item.setUserId((int) cursor.getLong(4));

					items.add(item);

				} while (cursor.moveToNext());
			}
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;

	}

	public void insertCahceInfo(CacheInfo cache) {
		insertCache(cache);
	}

	public CacheInfo getCahceInfoForAddress(String uri, String userLogin,
			String postTAG) {
		return getCacheInfoForArguments(uri, userLogin, postTAG);
	}

	public void updateCahceInfo(CacheInfo cache) {
		updateCache(cache);
	}

	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL("CREATE TABLE "
					+ CACHE_TABLE_NAME
					+ " (cacheId INTEGER PRIMARY KEY , userLogin VARCHAR, uri VARCHAR, postTAG VARCHAR, eTAG VARCHAR, response VARCHAR, timestamp DATETIME)");

			db.execSQL("CREATE TABLE "
					+ SHOP_TABLE_NAME
					+ " (id INTEGER PRIMARY KEY , name VARCHAR, addDate DATETIME,unit INTEGER,userId INTEGER)");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Example",
					"Upgrading database, this will drop tables and recreate.");

			db.execSQL("DROP TABLE IF EXISTS " + CACHE_TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + SHOP_TABLE_NAME);

			onCreate(db);
		}
	}

}
