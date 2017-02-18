package com.ak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ak.dao.BookDao;
import com.ak.dao.RentDao;
import com.ak.entity.Book;
import com.ak.entity.Rent;
import com.ak.entity.User;

@Service
public class RentServiceImpl implements RentService {

	@Autowired
	private RentDao rentDao;
	
    @Autowired
    private BookDao bookDao;

	@Override
	public List<Rent> findAll() {

		return rentDao.findAll();
	}

	@Override
	public Rent findOne(Long id) {
		return rentDao.findOne(id);
	}

	@Override
	public void save(Rent rent) {
		rentDao.save(rent);

	}

	@Override
	public void delete(Long id) {
		rentDao.delete(id);

	}

    @Override
    @Transactional
    public void createRent(User user, Book book) {
        Rent rent = new Rent(user, book);

        rentDao.save(rent);
        book.decrementQuantity();
        bookDao.save(book);
    }
    

	@Override
	public List<Rent> findByUserOrderByCreatedDateDesc(User user) {
		return rentDao.findByUserOrderByCreatedDateDesc(user);
	}

}
