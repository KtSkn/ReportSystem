package jp.co.addon.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.addon.entity.Chohyo;
import jp.co.addon.mapper.ChohyoMapper;

@Repository
public class ChohyoRepository {

    @Autowired
	private ChohyoMapper chohyoMapper;

  public List<Chohyo> findAllChohyo() {
    return chohyoMapper.findAllChohyo();
  }

}
