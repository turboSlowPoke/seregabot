package dbservices;

import dbservices.entyties.KryptoCurrency;
import dbservices.entyties.User;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.List;

public class DbService {
    private static final Logger log = Logger.getLogger(DbService.class);
    private static DbService dbService;
    private final EntityManagerFactory entityManagerFactory;

    public DbService() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("eclipsMysql");
    }

    public synchronized static DbService getInstance(){
        if (dbService==null)
            dbService=new DbService();
        return dbService;
    }

    public synchronized User getUser(Long chatId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.chatId=:id",User.class)
                .setParameter("id",chatId);
        User user = null;
        try {
            user = query.getSingleResult();
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
            TypedQuery<Long> maxRightKeyQuery = em.createQuery("SELECT MAX(u.rightKey) FROM User u", Long.class);
            Long maxRightKey = maxRightKeyQuery.getSingleResult();
            maxRightKey = maxRightKey==null? 0 :maxRightKey;
            user.setLevel(0);
            user.setLeftKey(maxRightKey + 1);
            user.setRightKey(maxRightKey + 2);
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

    public synchronized List<KryptoCurrency> getCurrencyList() {
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("SELECT c FROM KryptoCurrency c ORDER BY c.shortName");
        List<KryptoCurrency> currencyList = null;
        try {
            currencyList = query.getResultList();
        }catch (Exception e){
            log.error("Ошибка в построении списка валют");
        }finally {
            em.clear();
            em.close();
        }
        return currencyList;
    }

    public synchronized KryptoCurrency getCurrency(Long idCurrency) {
        EntityManager em = entityManagerFactory.createEntityManager();
        KryptoCurrency currency = null;
        try {
            currency = em.find(KryptoCurrency.class,idCurrency);
        }catch (Exception e){
            log.error("Ошибка при поиске валюты");
        }finally {
            em.clear();
            em.close();
        }
        return currency;
    }
}
