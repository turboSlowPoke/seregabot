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

    public synchronized void changeUser(User user, ActionType persistType) {
        if (persistType == ActionType.SAVEROOTUSER) {
            log.info("Добавляем rootUser в базу...");
            EntityManager em = entityManagerFactory.createEntityManager();
            EntityTransaction tr = em.getTransaction();
            TypedQuery<Integer> maxRightKeyQuery = em.createQuery("SELECT MAX(u.rightKey) FROM User u", Integer.class);
            Integer maxRightKey = maxRightKeyQuery.getSingleResult();
            if (maxRightKey != null) {
                user.setLevel(0);
                user.setLeftKey(maxRightKey + 1);
                user.setRightKey(maxRightKey + 2);
            } else {
                user.setLevel(0);
                user.setLeftKey(1);
                user.setRightKey(2);
            }
            tr.begin();
            try {
                em.persist(user);
                tr.commit();
                log.info("В базу добавлен rootUser" + user);
            } catch (Exception e) {
                if (tr.isActive()) tr.rollback();
                log.error("Ошибка при сохранении rootUser" + user);
            } finally {
                em.clear();
                em.close();
            }
        }
    }
}
