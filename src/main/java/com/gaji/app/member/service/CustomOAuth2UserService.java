package com.gaji.app.member.service;

import com.gaji.app.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private MemberRepository memberRepository;

    @Autowired
    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        // Here you can process the user information
        // For example, you might want to:
        // 1. Extract relevant details from oauth2User
        // 2. Check if the user exists in your database
        // 3. Create or update the user in your database
        // 4. Enrich the OAuth2User with additional information

        // Example: Extracting user details
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        // Example: Check if user exists and create if not
        // User user = userRepository.findByEmail(email)
        //     .orElseGet(() -> createNewUser(email, name));

        // Example: Enrich OAuth2User with custom details
        // return new CustomOAuth2User(oauth2User, user);

        return oauth2User;
    }
}
