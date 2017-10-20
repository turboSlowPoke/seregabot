package dbservices;

import dbservices.entyties.User;
import org.apache.log4j.Logger;

import javax.persistence.*;

public class DbService {
    private static final Logger log = Logger.getLogger(DbService.class);
    private static DbService dbService;
    private final EntityManagerFactory entityManagerFactory;

    public DbService() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("eclipsMysql");
    }

    public static DbService getInstance(){
        if (dbService==null)
            dbService=new DbService();
        return dbService;
    }

    public User getUser(Long chatId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.chatId=:id")
                .setParameter("id",chatId);
        User user = null;
        try {
            user = (User) query.getSingleResult();
        }catch (Exception e){
            log.error("ошибка при поиске юзера chatid="+chatId);
        }finally {
            em.clear();
            em.close();
            return user;
        }
    }
}