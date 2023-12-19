package ci.esatic.services;

import ci.esatic.entities.AppRole;
import ci.esatic.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
    AppUser loaduserByUsername(String username);
    List<AppUser> listAppUsers();

    AppUser loaduserById(String id);

    void updateUser(AppUser appUser);

}
