package cn.exceptioncode.mybatis.mapper;

import cn.exceptioncode.mybatis.domain.City;
import org.apache.ibatis.annotations.*;


/**
 * @author zhangkai
 */
@Mapper
public interface CityMapper {

    @Insert("INSERT INTO city (name, state, country) VALUES(#{name}, #{state}, #{country})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(City city);

    @Select("SELECT id, name, state, country FROM city WHERE id = #{id}")
    City findById(long id);

    @Select("SELECT * FROM CITY WHERE state = #{state}")
    City findByState(@Param("state") String state);


}