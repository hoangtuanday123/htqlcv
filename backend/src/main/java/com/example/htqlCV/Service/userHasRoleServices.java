package com.example.htqlCV.Service;
import java.util.List;
import java.util.UUID;

import com.example.htqlCV.Model.role;
import com.example.htqlCV.Model.user;
import com.example.htqlCV.Model.userHasRole;
public interface userHasRoleServices  {
    UUID createUserHasRole(user userValue,role roleValue);
    List<userHasRole> getUserHasRoleByUser(user user);
} 