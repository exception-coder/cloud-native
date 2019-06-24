package cn.exceptioncode.mybatis.dao;

import cn.exceptioncode.mybatis.domain.City;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author zhangkai
 *
 */
@Repository
public class CityDao {

    private final SqlSession sqlSession;

    public CityDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public City selectCityById(long id) {
        return this.sqlSession.selectOne("selectCityById", id);
    }
}
