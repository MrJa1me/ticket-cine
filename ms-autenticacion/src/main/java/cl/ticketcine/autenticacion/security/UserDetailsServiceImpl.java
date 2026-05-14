package cl.ticketcine.autenticacion.security;

import cl.ticketcine.autenticacion.model.entity.Credencial;
import cl.ticketcine.autenticacion.repository.CredencialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CredencialRepository credencialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credencial credencial = credencialRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        List<GrantedAuthority> authorities = credencial.getRoleList().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(credencial.getUserEmail(), credencial.getPassHash(), authorities);
    }
}
