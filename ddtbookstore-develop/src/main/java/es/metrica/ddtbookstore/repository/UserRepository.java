package es.metrica.ddtbookstore.repository;

import es.metrica.ddtbookstore.model.UserDTO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Long> {
	@Modifying
	@Query("update UserDTO u set u.activated=false where u.userId=:id")
	UserDTO deleteUserById(@Param("id")long id);

	@Modifying
	@Query("update UserDTO u set u.email=:email,u.firstName=:firstName, u.lastName=:lastName,u.userName=:userName,u.rol=:rol,u.userName=:username where u.userId=:id")
	UserDTO modifyUser(@Param("id")Long id,@Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("rol") UserDTO.Rol rol, @Param("username") String username);

	@Query("SELECT CASE WHEN EXISTS ( SELECT u FROM UserDTO u WHERE u.userId = :id ) THEN FALSE ELSE TRUE END from UserDTO")
	boolean checkIfExists(@Param("id") Long id);
	
	UserDTO findByUserName(String userName);

}
