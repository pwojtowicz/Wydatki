package pl.wppiotrek.wydatki.repositories;


public abstract class AbstractReposiroty {

	public abstract Object create(Object item);

	public abstract Object readById(Integer id);

	public abstract Object[] readAll();

	public abstract boolean update(Object item);

	public abstract boolean delete(Object item);

	public abstract boolean deleteByIs(Integer id);
}
