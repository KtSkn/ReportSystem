package jp.co.addon.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.addon.entity.Chohyo;

@Mapper
public interface ChohyoMapper {

	List<Chohyo> findAllChohyo();
    
}
