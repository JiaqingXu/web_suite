package com.cotech.service;

import com.cotech.dao.TUserDao;
import com.cotech.model.TUser;
import com.cotech.model.TopTenList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class TUserService {
    @Resource
    private TUserDao TUserDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<TopTenList> selectGoldTopTen(){
        return  TUserDao.selectGoldTopTen();
    }

    public List<TopTenList> selectScoreTopTen(){
        return  TUserDao.selectScoreTopTen();
    }

    public Integer countUserCountById(Long id){
        return TUserDao.countUserCountById(id);
    }

    public TUser getUserScoreAndGoldByID(Long id){
        return TUserDao.getUserScoreAndGoldByID(id);
    }

    public void updateGoldAndScoreById(TUser user){
        logger.debug(user.toString());
        TUserDao.updateGoldAndScoreById(user);
    }

    public Integer countUsernameByUsername(String username){
        return TUserDao.countUsernameByUsername(username);
    }

    public void saveUserSignUp(TUser user){
        TUserDao.saveUserSignUp(user);
    }
}
