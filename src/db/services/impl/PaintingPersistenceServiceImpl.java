package db.services.impl;

import db.dao.impl.PaintingDaoImpl;
import db.services.PaintingPersistenceService;
import domain.product.Painting;

public class PaintingPersistenceServiceImpl extends AbstractProductCategoryPersistenceService<Painting> implements PaintingPersistenceService {
	
	private static PaintingPersistenceService instance;

	private PaintingPersistenceServiceImpl() {
		super(PaintingDaoImpl.getInstance());
	}
	
	public static PaintingPersistenceService getInstance() {
		if (instance == null) {
			instance = new PaintingPersistenceServiceImpl();
		}
		return instance;
	}

	@Override
	public Painting getProd() {
		return new Painting();
	}
}
