package pl.wppiotrek.wydatki.repositories;

import pl.wppiotrek.wydatki.entities.ShopItem;
import pl.wppiotrek.wydatki.managers.DataBaseManager;
import android.content.Context;

public class ShopListRepository extends AbstractReposiroty {

	private DataBaseManager manager;

	public ShopListRepository(Context context) {
		this.manager = new DataBaseManager(context);
	}

	@Override
	public Object create(Object item) {
		ShopItem shopItem = null;
		if (item != null) {
			shopItem = (ShopItem) item;
			int id = (int) manager.insertShopitem(shopItem);

			shopItem.setId(id);
		}
		return (Object) shopItem;
	}

	@Override
	public Object readById(Integer id) {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] readAll() {
		return manager.getAllShopItems().toArray();

	}

	@Override
	public boolean update(Object item) {
		if (item != null) {
			ShopItem shopItem = (ShopItem) item;
		}
		return false;

	}

	@Override
	public boolean delete(Object item) {
		if (item != null) {
			ShopItem shopItem = (ShopItem) item;
		}
		return false;

	}

	@Override
	public boolean deleteByIs(Integer id) {
		return false;
		// TODO Auto-generated method stub

	}

}
