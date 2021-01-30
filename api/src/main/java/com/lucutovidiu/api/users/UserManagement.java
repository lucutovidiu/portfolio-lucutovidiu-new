package com.lucutovidiu.api.users;

import com.lucutovidiu.users.dto.UserBasicDto;
import com.lucutovidiu.users.dto.UserDto;
import com.lucutovidiu.users.dto.UserRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public interface UserManagement {

    @PostMapping(value = "/post-user")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    UserBasicDto postUser(@RequestBody @Valid UserRequestDto userRequest);

    @PutMapping(value = "/edit-user")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    boolean editUser(@RequestBody @Valid UserRequestDto userRequest);

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    List<UserBasicDto> getAllUsers();

    @DeleteMapping("/userId/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    boolean deleteUser(@PathVariable String userId);

    @GetMapping("/userId/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    UserDto getByUserId(@PathVariable String userId);
}
