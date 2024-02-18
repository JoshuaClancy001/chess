package service;

import dataAccess.MemoryDAO.MemoryAuthDAO;
import dataAccess.MemoryDAO.MemoryGameDAO;
import dataAccess.MemoryDAO.MemoryUserDAO;

public class Services {
    static protected MemoryUserDAO userDao = new MemoryUserDAO();
    static protected MemoryAuthDAO authDAO = new MemoryAuthDAO();
    static protected MemoryGameDAO gameDAO = new MemoryGameDAO();
}
