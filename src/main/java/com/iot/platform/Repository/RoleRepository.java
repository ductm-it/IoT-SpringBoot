package com.iot.platform.Repository;

import com.iot.platform.Entity.RoleEntity;
import com.iot.platform.Interface.Repository.RoleRepositoryInterface;

import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository extends BaseRepository<RoleEntity> implements RoleRepositoryInterface<RoleEntity> {

}