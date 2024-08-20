package com.myproject.CortaCaminho.controller;

import com.myproject.CortaCaminho.domain.Profile.DataProfile;
import com.myproject.CortaCaminho.domain.Profile.GetDataProfile;
import com.myproject.CortaCaminho.domain.Profile.Profile;
import com.myproject.CortaCaminho.domain.Profile.ProfileRepository;
import com.myproject.CortaCaminho.domain.user.DataAuthentication;
import com.myproject.CortaCaminho.domain.user.User;
import com.myproject.CortaCaminho.domain.user.UserRepository;
import com.myproject.CortaCaminho.infra.DadosTokenJWT;
import com.myproject.CortaCaminho.infra.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid DataProfile dataProfile) {
        if (userRepository.existsByLogin(dataProfile.user().getLogin())){
            return new ResponseEntity<>("Login de Usario já existente", HttpStatus.FORBIDDEN);
        }

        // Criação do usuário com a senha criptografada
        User user = dataProfile.user();
        String senhaCriptografada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCriptografada);

        Profile profile = new Profile();

        // Verificação de idade
        if (!profile.isAdult(dataProfile.dateOfBirth())) {
            return new ResponseEntity<>("Usuário menor de idade", HttpStatus.FORBIDDEN);
        }

        // Salvamento do usuário
        user = userRepository.save(user);

        // Criação do perfil e associando o usuário
        profile.setUser(user);
        profile.setName(dataProfile.name());
        profile.setDateOfBirth(dataProfile.dateOfBirth());

        // Salvamento do perfil
        profileRepository.save(profile);

        return ResponseEntity.ok(true);
    }

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DataAuthentication data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }


    @GetMapping("/get-data")
    public ResponseEntity<?> getData(@RequestBody @Valid GetDataProfile data) {

        User user = userRepository.findByLogin(data.email());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404 se o usuário não for encontrado
        }

        return ResponseEntity.ok(user.toString());
    }




}