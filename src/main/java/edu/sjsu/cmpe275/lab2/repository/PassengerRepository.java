package edu.sjsu.cmpe275.lab2.repository;

import edu.sjsu.cmpe275.lab2.DAO.PassengerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for passenger
 * Author: Manali Jain
 */
@Repository
public interface PassengerRepository extends JpaRepository<PassengerDAO, String>{

    /**
     * to get passenger by ID
     * @param id
     * @return PassengerDAO
     */
    PassengerDAO getById(Integer id);

    /**
     * to get passenger by phone
     * @param phone
     * @return
     */
    PassengerDAO getByPhone(String phone);

    /**
     * to delete by Id
     * @param id
     */
    void deleteById(int id);

}
