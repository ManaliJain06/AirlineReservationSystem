package edu.sjsu.cmpe275.lab2.repository;

import edu.sjsu.cmpe275.lab2.DAO.PassengerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerDAO, String>{

    /**
     * to get passenger by ID
     * @param id
     * @return PassengerDAO
     */
    PassengerDAO getById(Integer id);

    PassengerDAO getByPhone(String phone);

    void deleteById(int id);

}
