package comboDev.arsdiapason.mybatis.customMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomMapper {

    @Select("SELECT DISTINCT classe FROM rel_istituto_bambino WHERE id_istituto = #{id}")
    List<Integer> findClassi(@Param("id") Integer idIstituto);

    @Select("SELECT DISTINCT sezione FROM rel_istituto_bambino WHERE id_istituto = #{id}")
    List<String> findSezioni(@Param("id") Integer idIstituto);
}
