package com.caju.repository

import com.caju.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AccountRepository : CrudRepository<Account, UUID>