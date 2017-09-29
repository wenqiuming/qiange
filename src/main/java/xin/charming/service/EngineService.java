package xin.charming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.charming.bean.Engine;
import xin.charming.dao.EngineDao;

import java.util.List;

@Service
public class EngineService {
    @Autowired
    private EngineDao engineDao;

    public List<Engine> getEngines() {
        return engineDao.selectList();
    }
}
