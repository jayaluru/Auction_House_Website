package db.services.impl;

import db.dao.impl.SculptureDaoImpl;
import db.services.SculpturePersistenceService;
import domain.product.Sculpture;

public class SculpturePersistenceServiceImpl extends AbstractProductCategoryPersistenceService<Sculpture> implements SculpturePersistenceService {
	
	private static SculpturePersistenceService instance;

	private SculpturePersistenceServiceImpl() {
		super(SculptureDaoImpl.getInstance());
	}
	
	public static SculpturePersistenceService getInstance() {
		if (instance == null) {
			instance = new SculpturePersistenceServiceImpl();
		}
		return instance;
	}

	@Override
	public Sculpture getProd() {
		return new Sculpture();
	}
}
